package com.hivescm.code.cache;

import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 规则模板缓存数据内容 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/21 10:06
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class TemplateCacheData {
	/**
	 * 编码规则
	 */
	private CodeRuleBean codeRule;

	/**
	 * 编码项
	 */
	private List<CodeItemBean> codeItems;

	public TemplateCacheData() {
	}

	public CodeRuleBean getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(CodeRuleBean codeRule) {
		this.codeRule = codeRule;
	}

	public List<CodeItemBean> getCodeItems() {
		return codeItems;
	}

	public void setCodeItems(List<CodeItemBean> codeItems) {
		this.codeItems = codeItems;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TemplateCacheData{");
		sb.append("codeRule=").append(codeRule);
		sb.append(", codeItems=").append(codeItems);
		sb.append('}');
		return sb.toString();
	}
}
