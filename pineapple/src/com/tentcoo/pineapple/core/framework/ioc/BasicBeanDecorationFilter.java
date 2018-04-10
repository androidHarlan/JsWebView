package com.tentcoo.pineapple.core.framework.ioc;

import com.tentcoo.pineapple.core.common.factory.BasicFactoryObject;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;

public class BasicBeanDecorationFilter extends BasicFactoryObject implements
		IBeanDecorationFilter {
	public String beanId; // 要筛选的beanId,如果为空，仅根据type来筛选
	public Class beanType; // 要筛选的bean的类型，如果为空，仅根据Id来筛选
	public boolean matchSubtype; // 是否选出子类的对象，默认值为false

	@Override
	public boolean onFilter(AbstractBeanDefinition beanDecoration) {
		String beanId2 = beanDecoration.getBeanId();
		Class beanType2 = beanDecoration.getBeanType();
		if (beanId != null && beanType != null && !"".equals(beanId)
				&& beanType != Object.class) { // 根据id和type 筛选
			boolean match = beanId.equals(beanId2)
					&& matchType(beanType, beanType2, matchSubtype);
			return match;

		} else if (beanId != null && !"".equals(beanId)
				&& (beanType == null || beanType == Object.class)) { // 仅根据id 筛选
			boolean match = beanId.equals(beanId2);
			return match;

		} else if ((beanId == null || "".equals(beanId)) && beanType != null
				&& beanType != Object.class) { // 仅根据type 筛选
			boolean match = matchType(beanType, beanType2, matchSubtype);
			return match;
		}
		return false;
	}

	public void setBeanId(String id) {
		this.beanId = id;
	}

	/** 按类型筛选的输入类型 */
	public void setBeanType(Class type) {
		this.beanType = type;
	}

	/** 是否匹配子类对象 */
	public void setMatchSubtype(boolean matchSubtype) {
		this.matchSubtype = matchSubtype;
	}

	/***
	 * 判断类型是否符合
	 * 
	 * @param type1
	 *            要筛选的类型
	 * @param type2
	 *            目标类型
	 * @param isMatchSubtype
	 *            是否筛选子类
	 * @return
	 */
	public boolean matchType(Class type1, Class type2, boolean isMatchSubtype) {
		if (isMatchSubtype) {
			if (type1 != null && type2 != null) {
				if (type1.isAssignableFrom(type2)) {
					return true;
				}
			}
		} else {
			if (type1 == type2) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return beanId;
	}

	@Override
	public String toString() {
		return "BasicBeanDecorationFilter [beanId=" + beanId + ", beanType="
				+ beanType + ", matchSubtype=" + matchSubtype + "]";
	}

	@Override
	public Class getBeanType() {
		return beanType;
	}

}
