package com.hivescm.code.task;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.cache.CodeCacheDataInfo;
import com.hivescm.code.common.Constants;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.RuleItemRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <b>Description:</b><br>
 * 处理流水号缓存任务 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.task
 * <br><b>Date:</b> 2017/10/23 12:11
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class SerialNumIncrTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerialNumIncrTask.class);

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 10,
			TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

	/**
	 * Redis 缓存信息
	 */
	private CodeCacheDataInfo cacheData;

	private JedisClient jedisClient;

	private RuleItemRelationMapper ruleItemRelationMapper;

	public SerialNumIncrTask(CodeCacheDataInfo cacheData, JedisClient jedisClient,
			RuleItemRelationMapper ruleItemRelationMapper) {
		this.cacheData = cacheData;
		this.jedisClient = jedisClient;
		this.ruleItemRelationMapper = ruleItemRelationMapper;
	}

	/**
	 * 流水号缓存
	 */
	private void serialNumCache() {
		final String maxSerialNumKey = cacheData.getMaxSerialNumKey();
		final long cacheMaxSerialNum = Long.valueOf(jedisClient.get(maxSerialNumKey));
		final long cacheSerialNum = Long.valueOf(jedisClient.get(cacheData.getSerialNumKey()));
		if (reachThresholdValue(cacheMaxSerialNum, cacheSerialNum)) {

			final RuleItemRelationBean relationBean = ruleItemRelationMapper
					.queryDefaultBandingRuleLock(cacheData.getGroupId(), cacheData.getOrgId(), cacheData.getBizCode());

			if (null == relationBean) {
				LOGGER.warn("获取规则分配信息失败，疑似并发修改异常，cacheData:{}.", cacheData);
				throw new CodeException(CodeErrorCode.DATE_NOT_EXIST_ERROR_CODE, "获取规则分配信息失败，疑似并发修改异常");
			}

			ruleItemRelationMapper.incrCacheStepNum(relationBean.getId(), 1);

			jedisClient.incr(maxSerialNumKey, Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE);
		}
	}

	public void execute() {
		executor.execute(this);
	}

	@Override
	public void run() {
		serialNumCache();
	}

	/**
	 * 验证当前流水号是否已经到达流水号的缓存值
	 *
	 * @param cacheSerialNum    当前缓存流水号
	 * @param cacheMaxSerialNum 当前缓存最大流水号
	 * @return <code>true</code> 到达阈值；<code>true</code> 未到阈值；
	 */
	private boolean reachThresholdValue(final long cacheMaxSerialNum, final long cacheSerialNum) {
		// 没缓存的一步新增了多少
		double incrByStep = cacheSerialNum % Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE + 0.0;
		double cacheThreshold = incrByStep / Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE;
		if (cacheThreshold >= Constants.CACHE_SERIAL_NUM_THRESHOLD_VALUE) {
			return (cacheMaxSerialNum - cacheSerialNum) < Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE;
		}
		return false;
	}
}
