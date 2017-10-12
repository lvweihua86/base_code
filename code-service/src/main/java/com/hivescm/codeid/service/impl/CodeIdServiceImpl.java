package com.hivescm.codeid.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.codeid.common.Constants;
import com.hivescm.codeid.error.ErrorCode;
import com.hivescm.codeid.mapper.CodeIDItemMapper;
import com.hivescm.codeid.mapper.CodeIdMapper;
import com.hivescm.codeid.mapper.SeriaNumberMapper;
import com.hivescm.codeid.mapper.SingleSeriaNumberMapper;
import com.hivescm.codeid.pojo.CodeIDItem;
import com.hivescm.codeid.pojo.CodeId;
import com.hivescm.codeid.pojo.SeriaNumber;
import com.hivescm.codeid.service.CodeIdService;
import com.hivescm.codeid.service.OrgService;
import com.hivescm.generated.api.GeneratedIncrApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("codeIdService")
public class CodeIdServiceImpl implements CodeIdService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeIdServiceImpl.class);

	@Autowired
	private CodeIdMapper codeIdMapper;

	@Autowired
	private CodeIDItemMapper codeIDItemMapper;

	@Autowired
	private SeriaNumberMapper seriaNumberMapper;

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;

	@Autowired
	private JedisClient jc;

	@Autowired
	private SingleSeriaNumberMapper singleSeriaNumberMapper;

	@Autowired
	private GeneratedIncrApi generatedIncrApi;

	/**
	 * 生成id
	 *
	 * @param businessCode 业务id
	 * @param orgId        组织id
	 * @param json         业务数据
	 */

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String generateID(String businessCode, String orgId, String json) throws
			Exception {
		LOGGER.info("generate id req param,businessCode:{},orgId:{},json:{}.", businessCode, orgId, json);

		String[] jsonStrs = json.split(",");
		// 获取该组织，该业务模块下的 编码id模版
		String key = orgId + "_" + businessCode;
		String template = jc.get(key);
		if (null == template || template.length() <= 0) {
			String[] doubuless = getKey(orgId, businessCode);
			key = doubuless[1];
			orgId = doubuless[0];
			template = jc.get(key);
		}

        /*   具体的编码规则 对应的 业务实体+组织id
         *  key -- DYBM_orgId_businessCode
         *  value -- codeid:time_format:code_type:zero_reason:is_default
         */
		String codeIdStr = jc.get(Constants.CODEID_BUSINESSCODE_ORGID + "_" + key);
		String[] xsdf = codeIdStr.split(":");

		if (1 == Integer.parseInt(xsdf[5])) {
			// 优先使用断号
			String id = findBreakID(businessCode, orgId);
			if (null != id && !"".equals(id) && id.length() > 0) {
				return id;
			}
		}

		// 根据编码id模板，生成编码id
		String[] strs = template.split("\\+");
		List<String> generatorList = new ArrayList<>();//记录动态字段的值
		List<Integer> indexList = new ArrayList<>();//记录动态字段在模版中的位置
		List<Integer> FLIndexList = new ArrayList<>();//记录流水依据在模版中的位置
		List<String> FLValueList = new ArrayList<>();//记录流水依据的值
		Map<String, String> map = new HashMap<>();

        /*              遍历模版规则
         * 流水号:流水号类型:流水号长度:+CGDD3:常量:+time:时间:按日流水:yyyyMMdd:+name:字符串:3:x:1:按字符串流水:
         *
         * 使用 + 拆分后：
         *    1.       流水号:流水号类型:流水号长度:
         *    2.           CGDD3:常量:
         *    3.    time:时间:按日流水:yyyyMMdd:
         *    4.    name:字符串:3:x:1:按字符串流水:
         */
		int flowNumLength = 0; //流水号长度
		int flowType = 0; //流水号类型
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			String[] ss = str.split(":");
			if ("常量".equals(ss[1])) {
				indexList.add(i);
				generatorList.add(ss[0]);
			} else if ("字符串".equals(ss[1])) {
				String s = "";
				//jsonStrs 传递来的真实数据  列名:值,列名:值
				for (String st : jsonStrs) {
					String[] kvs = st.split(":");
					if (ss[0].equals(kvs[0])) {
						//判断传入的值是否与编码id中字段的位数吻合
						if (kvs[1].length() > Integer.parseInt(ss[2])) {
							s = kvs[1].substring(0, Integer.parseInt(ss[2]));
							indexList.add(i);
							generatorList.add(s);
						} else if (kvs[1].length() < Integer.parseInt(ss[2])) {
							s = fillField(Integer.parseInt(ss[4]), kvs[1], ss[3],
									(Integer.parseInt(ss[2])) - (kvs[1].length()));
							indexList.add(i);
							generatorList.add(s);
						} else {
							s = kvs[1];
							indexList.add(i);
							generatorList.add(kvs[1]);
						}
					}
				}
				// 是否是流水依据字段
				if (null != ss[5] && ss[5].length() > 0) {
					// 列名:真实值
					FLIndexList.add(i);
					FLValueList.add(ss[0]);
					map.put(ss[0], s);
				}
			} else if ("时间".equals(ss[1])) {
				String timeStr = "";
				String timeFormat = ss[3];
				if ("now".equals(ss[0])) {//使用系统时间值
					timeStr = formatDate(timeFormat, new Date());
					indexList.add(i);
					generatorList.add(timeStr);
				} else {
					for (String st : jsonStrs) {//使用数据库字段的时间值
						String[] kvs = st.split(":");
						if (ss[0].equals(kvs[0])) {
							timeStr = formatDate(timeFormat, StringToDate(timeFormat, kvs[1]));
							indexList.add(i);
							generatorList.add(timeStr);
						}
					}
				}
				// 日期的 流水依据规则
				if (null != ss[2] && ss[2].length() > 0) {
					FLIndexList.add(i);
					FLValueList.add(ss[0]);
					map.put(ss[0], timeStr);
				}
			} else if ("流水号".equals(ss[0])) {
				// 流水号:流水号类型:流水号长度:
				flowNumLength = Integer.parseInt(ss[2]); //流水号长度
				flowType = Integer.parseInt(ss[1]); //流水号类型
				indexList.add(i);
				generatorList.add("");
			}
		}
		String flowNum = ""; //流水号值
		try {
			List<String> list = new ArrayList<>();
			for (int i = 0; i < FLIndexList.size(); i++) {
				list.add(map.get(FLValueList.get(i)));
			}
			String[] ssStrs = codeIdStr.split(":");
			flowNum = generateFlowNum(Long.parseLong(ssStrs[0]), orgId,
					Integer.parseInt(ssStrs[3]), flowNumLength,
					flowType, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		for (Integer ii : indexList) {
			if (!"".equals(generatorList.get(ii))) {
				sb.append(generatorList.get(ii));
			} else {
				sb.append(flowNum);
			}

		}
		return sb.toString();
	}

	// 断号查找int
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String findBreakID(String businessCode, String orgId) {
		String key = "DH:" + orgId + ":" + businessCode;
		String dhs = jc.get(key);
		String id = "";
		if (null != dhs && !"".equals(dhs) && dhs.length() > 0) {
			String[] strs = dhs.split(",");

			if (null != strs && !"".equals(strs) && strs.length > 0) {
				id = strs[0];
				dhs = dhs.substring(dhs.indexOf(",") + 1);
				jc.set(key, dhs);
			}
		}
		return id;
	}

	/**
	 * 获取默认的编码规则
	 *
	 * @param orgId
	 * @param businessCode
	 * @return
	 * @throws Exception
	 */
	private String[] getKey(String orgId, String businessCode) throws
			Exception {
		// 使用集团的规则
		String jtId = orgService.getJTID(orgId);
		String id = jtId;
		String key = jtId + "_" + businessCode;
		String template = jc.get(key);

		if (null == template || "".equals(template)) {
			// 使用蜂网默认规则
			String fwID = orgService.getFWID();
			id = fwID;
			key = fwID + "_" + businessCode;
			template = jc.get(key);
			;
			if (null == template || "".equals(template)) {
				// throw new Exception("该业务没有创建编码规则");
				throw ErrorCode.NO_RULE.throwError();
			}
		}
		return new String[] { id, key };
	}

	// 补位
	private String fillField(int fillPattern, String sourceStr, String fillStr, int num) {
		if (1 == fillPattern) {
			StringBuffer sb = new StringBuffer(sourceStr);
			for (int i = 0; i < num; i++) {
				sb.append(fillStr);
			}
			return sb.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < num; i++) {
				sb.append(fillStr);
			}
			sb.append(sourceStr);
			return sb.toString();
		}
	}

	// 格式化日期
	private String formatDate(String dateFormatStr, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	// 字符串转日期
	private Date StringToDate(String dateFormatStr, String time) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
		Date date = sdf.parse(time);
		return date;
	}

	// 通过业务id 获取模版字段
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CodeIDItem> getCodeIDItemsByCodeId(long codeId) throws Exception {
		List<CodeIDItem> codeIDItems = codeIDItemMapper.getCodeIDItemsByCodeId(codeId);
		return codeIDItems;
	}

	// 根据排序位置，获取模版中所有的字段
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getCodeIDTemplateFields(String businessCode, String orgId, List<CodeIDItem> list) {
		/*
		 *  key -- DYBM_orgId_businessCode
		 *  value -- codeid:time_format:code_type:zero_reason:is_default
		 */
		String k = orgId + "_" + businessCode;
		// 编码id 的主键
		String codeIdStr =
				jc.get(Constants.CODEID_BUSINESSCODE_ORGID + "_" + k).split(":")[0];
		// 获取日期型字段的生成规则
		String timeFormat =
				jc.get(Constants.CODEID_BUSINESSCODE_ORGID + "_" + k).split(":")[1];
		if (null == codeIdStr || "".equals(codeIdStr)) {
			CodeId codeId = codeIdMapper.getCodeIdByBusinessIDAndOrgId(orgId, businessCode);
			codeIdStr = String.valueOf(codeId.getId());
			timeFormat = codeId.getTimeFormat();
		}

		StringBuffer template = new StringBuffer();
		//遍历编码id详情，转换成编码id模版
		for (CodeIDItem item : list) {
			//1:常量 2:字符串 3:时间 4:纯数字流水号 5:字母数字流水号
			if (1 == item.getCodeType()) {
				template.append(item.getCodeColumn() + ":常量:+");
			} else if (2 == item.getCodeType()) {
				String columnName = item.getCodeColumn();
				String[] strs = columnName.split(":");
				if (0 == item.getIsSerial()) {
					template.append(strs[2] + ":字符串:" + item.getCodeLongth() + ":" + item.getFillStr() + ":"
							+ item.getFillPattern() + ":无流水:+");
				} else {
					template.append(strs[2] + ":字符串:" + item.getCodeLongth() + ":" + item.getFillStr() + ":"
							+ item.getFillPattern() + ":按字符串流水:+");
				}
			} else if (3 == item.getCodeType()) {
				String columnName = item.getCodeColumn();
				String[] strs = columnName.split(":");

				if (0 == item.getIsSerial()) {
					if (null != strs && strs.length == 3) {
						template.append(strs[2] + ":时间:无流水:" + timeFormat + ":+");
					} else {
						template.append(columnName + ":时间:无流水:" + timeFormat + ":+");
					}
				} else {
					switch (item.getSerialType()) {
					case 1:
						if (null != strs && strs.length == 3) {
							template.append(strs[2] + ":时间:按日流水:" + timeFormat + ":+");
						} else {
							template.append(columnName + ":时间:按日流水:" + timeFormat + ":+");
						}
						break;
					case 2:
						if (null != strs && strs.length == 3) {
							template.append(strs[2] + ":时间:按月流水:" + timeFormat + ":+");
						} else {
							template.append(columnName + ":时间:按月流水:" + timeFormat + ":+");
						}
						break;
					case 3:
						if (null != strs && strs.length == 3) {
							template.append(strs[2] + ":时间:按年流水:" + timeFormat + ":+");
						} else {
							template.append(columnName + ":时间:按年流水:" + timeFormat + ":+");
						}
						break;
					}
				}
			} else if (4 == item.getCodeType()) {
				template.append("流水号:" + item.getCodeType() + ":" + item.getCodeLongth() + ":+");
			} else if (5 == item.getCodeType()) {
				template.append("流水号:" + item.getCodeType() + ":" + item.getCodeLongth() + ":+");
			}
		}
		String templateStr = template.toString();
		String str = templateStr.substring(0, templateStr.length() - 1);
		String key = orgId + "_" + businessCode;
		jc.set(key, str);
		return str;
	}

	// 生成流水号
	public String generateFlowNum(long codeId, String orgId, int zero_reason,
			int length, int flowType, List<String> list)
			throws Exception {

		// 生成自然流水号
		if (null == list) {
			return finalFlowNum(codeId, orgId, null, zero_reason, flowType, length);
		} else if (1 == list.size()) {
			return finalFlowNum(codeId, orgId, list, zero_reason, flowType, length);
		} else if (2 == list.size()) {
			return finalFlowNum(codeId, orgId, list, zero_reason, flowType, length);
		}
		//throw new Exception("流水号生成错误");
		throw ErrorCode.FLOWNUM_ERROR.throwError();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String finalFlowNum(long codeId, String orgId, List<String> list,
			int zero_reason, int flowType, int length)
			throws Exception {
		String v1 = "null";
		String v2 = "null";
		if (null != list && list.size() == 2) {
			v1 = list.get(0);
			v2 = list.get(1);
		} else if (null != list && list.size() == 1) {
			v1 = list.get(0);
		}
		if (1 == zero_reason) {// 使用该规则的全局唯一流水号
			/*
			 * initialKey = A,B,C
			 * key = 0001,0002,0003
			 * initialKey + key = A0001,A0002
			 */
			// 纯数字(流水)
			String key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + v1 + "_" + v2;
			// 字母
			String initialKey = Constants.GLOBAL_LETTER_FLOW_NUMBER + "_"
					+ codeId + "_" + orgId + "_" + v1 + "_" + v2;

			if (4 == flowType) {//如果是数字流水号
				String currentMaxFlowNum = jc.get(key);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}

			if (5 == flowType) {
				String currentMaxFlowNum = jc.get(initialKey);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}
		} else if (2 == zero_reason) {// 使用集团流水号
			// 纯数字(流水)
			String key = Constants.BLOC_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + v1 + "_" + v2;
			// 字母
			String initialKey = Constants.BLOC_LETTER_FLOW_NUMBER + "_"
					+ codeId + "_" + orgId + "_" + v1 + "_" + v2;

			if (4 == flowType) {//如果是数字流水号
				String currentMaxFlowNum = jc.get(key);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}

			if (5 == flowType) {
				String currentMaxFlowNum = jc.get(initialKey);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}
		} else if (3 == zero_reason) {// 组织个人的流水号
			// 纯数字(流水)
			String key = Constants.ORG_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + v1 + "_" + v2;
			// 字母
			String initialKey = Constants.ORG_LETTER_FLOW_NUMBER + "_"
					+ codeId + "_" + orgId + "_" + v1 + "_" + v2;

			if (4 == flowType) {//如果是数字流水号
				String currentMaxFlowNum = jc.get(key);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}

			if (5 == flowType) {
				String currentMaxFlowNum = jc.get(initialKey);
				if (null == currentMaxFlowNum) {
					return initAndUbsert(flowType, key, initialKey, length, v1, v2, codeId, orgId);
				} else {
					return getFlowNumValue(flowType, key, initialKey, length);
				}
			}
		}
		//throw new Exception("无法获取流水号");
		throw ErrorCode.NO_FETCH_FLOWNUM.throwError();
	}

	private String initAndUbsert(int flowType, String key, String initialKey, int length,
			String v1, String v2, long codeId, String
			orgId) {
		// 初始化流水号
		String flowNum = initFlowNumValue(flowType, key, initialKey, length);
		// 将流水号记录进数据库
		String[] ssss = new String[2];
		ssss[0] = v1;
		ssss[1] = v2;
		insertSeriaNumber(flowNum, codeId, orgId, ssss);
		return flowNum;
	}

	private String initDigitFlowNum(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i != length - 1) {
				sb.append(0);
			} else {
				sb.append(1);
			}
		}
		return sb.toString();
	}

	private String initLetterDigitFlowNum(int length) {
		StringBuffer sb = new StringBuffer("A");
		for (int i = 0; i < length - 1; i++) {
			if (i != length - 2) {
				sb.append(0);
			} else {
				sb.append(1);
			}
		}
		return sb.toString();
	}

	public String digitFillZero(String value, int length) throws Exception {
		int diff = length - value.length();
		if (diff < 0) {
			//throw new Exception("序列号流水已经到最大值，无法再生成");
			throw ErrorCode.MAX_FLOWNUM_NO_CREATE.throwError();
		}
		StringBuffer sb = new StringBuffer();
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				sb.append("0");
			}
			sb.append(value);
			return sb.toString();
		}
		return value;
	}

	public String letterDigitIncr(String k1, String k2, int length) throws Exception {
		String v1 = String.valueOf(jc.incr(k1, 1));
		char v2 = jc.get(k2).charAt(0);
		StringBuffer sb = new StringBuffer();
		if (v1.length() > (length - 1)) {
			if ('Z' == v2) {
				//throw new Exception("序列号流水已经到最大值，无法再生成");
				throw ErrorCode.MAX_FLOWNUM_NO_CREATE.throwError();
			} else {
				v2 = (char) (v2 + 1);
				for (int i = 0; i < length - 1; i++) {
					if (i != length - 2) {
						sb.append("0");
					} else {
						sb.append("1");
					}
				}
				jc.set(k1, sb.toString());
				jc.set(k2, String.valueOf(v2));
				return (String.valueOf(v2)) + (sb.toString());
			}
		} else {
			String currentMaxFlowNum = digitFillZero(v1, length);
			return (String.valueOf(v2)) + currentMaxFlowNum;
		}
	}

	//初始化流水号，并将该流水号记录到redis中
	private String initFlowNumValue(int flowType, String key, String initialKey, int length) {
		if (4 == flowType) {
			String currentMaxFlowNum = initDigitFlowNum(length);
			jc.set(key, "1");
			return currentMaxFlowNum;
		} else if (5 == flowType) {
			String currentMaxFlowNum = initLetterDigitFlowNum(length);
			jc.set(initialKey, currentMaxFlowNum.substring(0, 1));
			jc.set(key, "1");
			return currentMaxFlowNum;
		}
		return "";
	}

	private String getFlowNumValue(int flowType, String key, String initialKey, int length)
			throws Exception {
		if (4 == flowType) {
			String currentMaxFlowNum = String.valueOf(jc.incrOneByKey(key));
			currentMaxFlowNum = digitFillZero(currentMaxFlowNum, length);
			return currentMaxFlowNum;
		} else if (5 == flowType) {
			String currentMaxFlowNum = letterDigitIncr(key, initialKey, length);
			return currentMaxFlowNum;
		}
		return "";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertSeriaNumber(String currentMaxFlowNum, long codeId, String orgId,
			String[] s) {
		SeriaNumber seriaNumber = new SeriaNumber();
		seriaNumber.setId(generatedIncrApi.getUniqueId("test").getResult().longValue());
		seriaNumber.setCodeId(codeId);
		seriaNumber.setOrgId(orgId);
		if (null != s && s.length == 1) {
			seriaNumber.setFlowBy1(s[0]);
			seriaNumber.setFlowBy2("");
		}
		if (null != s && s.length == 2) {
			seriaNumber.setFlowBy1(s[0]);
			seriaNumber.setFlowBy2(s[1]);
		}
		seriaNumber.setCurrentMaxNum(currentMaxFlowNum);
		seriaNumberMapper.insert(seriaNumber);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void initCodeIDTemplate() {
		List<CodeId> usedCodeIds = codeIdMapper.getAllUsedCodeIds();
		for (CodeId codeId : usedCodeIds) {
			List<CodeIDItem> codeIDItems = null;
			try {
                /*   具体的编码规则 对应的 业务实体+组织id
		         *  key -- DYBM_orgId_businessCode
		         *  value -- codeid:time_format:code_type:zero_reason:is_default:is_break_code
		         */
				String key = Constants.CODEID_BUSINESSCODE_ORGID + "_" + codeId.getOrgId()
						+ "_" + codeId.getBusinessCode();

				String value = codeId.getId() + ":" + codeId.getTimeFormat() + ":" + codeId.getCodeType()
						+ ":" + codeId.getZeroReason() + ":" + codeId.getIsDefault() + ":" + codeId.getIsBreakCode();

				jc.set(key, value);

				codeIDItems = getCodeIDItemsByCodeId(codeId.getId());

				getCodeIDTemplateFields(codeId.getBusinessCode(), codeId.getOrgId(),
						codeIDItems);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

