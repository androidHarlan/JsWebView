package com.tentcoo.pineapple.core.framework.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;

import com.tentcoo.pineapple.core.common.bean.IComponent;
import com.tentcoo.pineapple.core.common.factory.delegate.BasicDelegatedFactoryObject;
import com.tentcoo.pineapple.core.dispatch.mapping.HandlerMapping;
import com.tentcoo.pineapple.core.framework.ioc.definition.AbstractBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.definition.FactoryBeanDefinition;
import com.tentcoo.pineapple.core.framework.ioc.dependency.AbstractDependency;
import com.tentcoo.pineapple.core.framework.ioc.factory.BeanFactory;
import com.tentcoo.pineapple.core.framework.resource.BeanResolver;
import com.tentcoo.pineapple.core.framework.resource.ClassPathPattenFileLoader;
import com.tentcoo.pineapple.core.framework.resource.IResourceLoader;
import com.tentcoo.pineapple.core.framework.template.ContextTemplate;
import com.tentcoo.pineapple.core.framework.template.annotation.Autowired;
import com.tentcoo.pineapple.core.framework.template.annotation.Config;
import com.tentcoo.pineapple.utils.Logger;

public class IocManager extends BasicDelegatedFactoryObject {

	@Autowired
	public BeanScanner beanScanner;
	@Autowired(clazz = PackageLocationPattenLoader.class)
	public IPackageLoader packageLoader;
	@Autowired(clazz = ClassPathPattenFileLoader.class)
	public IResourceLoader beanDefinitionFileLoader;
	@Autowired
	public BeanResolver beanResolver;
	private Map<String, AbstractBeanDefinition> beanIdDefinitionMap;

	@Config(id = "iocManager.packageLocations")
	public List<String> packageLocations; // 配置文件配置

	@Config(id = "iocManager.xmlConfigFileLocations")
	public String xmlConfigFileLocation; // 配置文件配置

	/** 加载bean */
	public void loadBenas(Context context) {
		// 第一遍，扫描包中定义的实现了Bean接口或其子接口的类，并构造beanDecoration */
		List<String> plocations = getPackageLocations();
		beanIdDefinitionMap = beanScanner.scan(getContext(), packageLoader,plocations);
		Logger.d("扫描包的类：" + beanIdDefinitionMap);
		// 第二遍,扫描xml配置文件资源，并对刚才构造出来的beanDoration进行（）*/
		String xmlLocation = getXmlConfigFileLocation();
		if (xmlLocation != null) {
			Map<String, AbstractBeanDefinition> beanDecorationMap = (Map<String, AbstractBeanDefinition>) (beanResolver
					.resolve(xmlLocation));
			Logger.d("扫描Xml的类：" + beanDecorationMap);
			mergeBeanDecorationMap(beanDecorationMap);
		} else {
			Logger.e("没有xml配置文件资源");
		}
		checkDependency();
	}

	/***
	 * 查找类
	 * 
	 * @param beanDecorationFilter
	 *            存放筛选条件的对象
	 * @param count
	 *            //筛选个数， 小于等于0为筛选全部
	 * @return
	 */
	public List<Object> searchBean(IBeanDecorationFilter beanDecorationFilter,int count) {
		Logger.d("查找bean,id:"+beanDecorationFilter.toString());
		
		if("transactionHandlers".equals(beanDecorationFilter.getId()))
		{
			System.out.println();
		}
		
		List<Object> matchedBeanList = new ArrayList<Object>();
		if (beanIdDefinitionMap == null) {
			Logger.e("IocManager.beanIdDecorationMap为空！");
			return matchedBeanList;
		}
		// 1、迭代遍历iocManager中的beanIdDecorationMap
		Set<String> keySet = beanIdDefinitionMap.keySet();
		for (String key : keySet) {
			if("transactionHandlers".equals(key))
			{
				System.out.println();
			}
			AbstractBeanDefinition beanDecoration = beanIdDefinitionMap.get(key);
			// if("bridgeHandlerAdapter".equals(key)){
			// Logger.d("这里是WebviewJsBridgeAdapter");
			// }
			boolean matched = beanDecorationFilter.onFilter(beanDecoration);
			if (matched) {
				Object bean = getBean(beanDecoration);
				if (count <= 0 || matchedBeanList.size() < count) {
					matchedBeanList.add(bean);
				} else {
					break;
				}
			}
		}
		if (matchedBeanList.size() == 0) {
			Logger.e("找不到id为：" + beanDecorationFilter.getId());
		}
		return matchedBeanList;
	}

	/** 获取要扫描的包路径 */
	private List<String> getPackageLocations() {
		return packageLocations;
	}

	/** 获取xml 路径 */
	private String getXmlConfigFileLocation() {
		return xmlConfigFileLocation;
	}

	/**
	 * 将load出来的beanDecorationMap与第一遍产生的map进行合并，如果bean已存在，则更新bean中的定义，如果不存在，
	 * 则将bean加入到原来的那个map中
	 * 
	 * @param beanDefinitionMap
	 *            resource产生的map
	 */
	private void mergeBeanDecorationMap(
			Map<String, AbstractBeanDefinition> beanDefinitionMap) {
		Set<String> keySet = beanDefinitionMap.keySet();
		for (String key : keySet) {
			// 如果bean存在
			if (beanIdDefinitionMap.containsKey(key)) {
				AbstractBeanDefinition beanDefinition = beanIdDefinitionMap.get(key);
				AbstractBeanDefinition beanDefinition2 = beanDefinitionMap.get(key);
				if(beanDefinition.getClass()== beanDefinition2.getClass()){
					mergeBeanDefinition(beanDefinition, beanDefinition2);
				}else{
					Logger.e("BeanDefinition合并key("+key+")相同,类型("+beanDefinition.getClass()+"--"+beanDefinition2.getClass()+")不同");
				}
			} else {
				// 则将bean加入到原来的那个map中
				beanIdDefinitionMap.put(key, beanDefinitionMap.get(key));
			}
		}
	}

	/***
	 * 合并两个beanDecoration
	 * 
	 * @param beanDefinition
	 *            主beanDecoration
	 * @param beanDefinition2
	 *            副beanDecoration
	 */
	private void mergeBeanDefinition(AbstractBeanDefinition beanDefinition,
			AbstractBeanDefinition beanDefinition2) {
		Class beanType = beanDefinition2.getBeanType();
		String nameSpace = beanDefinition2.getNameSpace();
		boolean singleton = beanDefinition2.isSingleton();

		if (beanType != null) { // 2
			beanDefinition.setBeanType(beanType);
		}
		if (nameSpace != null) { // 3
			beanDefinition.setNameSpace(nameSpace);
		}
		beanDefinition.setSingleton(singleton); // 4
		//
		if (beanDefinition2 instanceof FactoryBeanDefinition) {
			FactoryBeanDefinition factoryBeanDefinition = (FactoryBeanDefinition) beanDefinition;
			FactoryBeanDefinition factoryBeanDefinition2 = (FactoryBeanDefinition) beanDefinition2;
			
			Object prototype = factoryBeanDefinition2.getPrototype();
			Map<String, AbstractDependency> dependencies = factoryBeanDefinition2.getDependencies();
			if (prototype != null) { // 5
				factoryBeanDefinition.setPrototype(prototype);
			}
			if (dependencies != null && dependencies.size() > 0) { // 6
				factoryBeanDefinition.mergeDependencies(dependencies);
			}
		}

	}

	/** 检查bean之间的依赖关系 */
	private void checkDependency() {
		// TODO 未指定
		Logger.d("合并后的：" + beanIdDefinitionMap);
		Logger.d("检查bean之间的依赖关系！");
	}

	/**
	 * 1、找对象池 2、直接build
	 * */
	public Object getBean(AbstractBeanDefinition beanDefinition) {
		Object bean =null;
		if(beanDefinition.getBeanType()!=null){
			bean = ContextTemplate.getInstance().getBeanByNameAndType(beanDefinition.getNameSpace(), beanDefinition.getBeanId(),beanDefinition.getBeanType().getName());
			Logger.d("对象池查找的对象类型:" + beanDefinition.getBeanType().getName());
		}else{
			Logger.e("查找对象池的对象类型为空！");
		}
		if (bean == null) {
			bean = beanDefinition.createObject();
			if(beanDefinition.getBeanId()!=null){
			ContextTemplate.getInstance().addObjectToPool(
					beanDefinition.getBeanId(), bean,
					beanDefinition.getNameSpace());
			}else{
				Logger.e("beanid为空,不会保存进对象池");
			}
		}
		System.out.print(bean.toString());
		return bean;
	}

	/***
	 * 通过beanId从对象池中找到对象
	 * 
	 * @param beanId
	 * @return
	 */
	public Object getBeanById(String beanId) {
		Object bean = null;
		AbstractBeanDefinition beanDecoration = beanIdDefinitionMap.get(beanId);
		if (beanDecoration != null) {
			bean = getBean(beanDecoration);
			System.out.print(bean.toString());
		} else {
			Logger.e("找不到Beanid：" + beanId + "的依赖");
		}
		return bean;
	}

	public Map<String, AbstractBeanDefinition> getBeanIdDecorationMap() {
		return beanIdDefinitionMap;
	}

}
