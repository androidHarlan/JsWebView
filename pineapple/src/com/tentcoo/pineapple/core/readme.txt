上级为core包，下级管辖config/context/ioc/template 4个子包，依赖pinapple.common包。Framework包中的内容为pineapple.core中的其他模块提供基础设施，core中其他模块都依赖framework模块：

Config – 配置功能
Context – 上下文功能
Ioc – 自动注入功能
Template – 核心模块的代码模板


//原先包含静态方法的代理类(现在修改)
 1、Application  Init()
 2、TransationBuilder  getInstance();		//静态单例
 3、AntWildcardMatcher	isMatcher();
 4、 Formatter 								//静态单例                                                 
 5、BeanResolver							    //静态单例
 
 
 //-----------问题
  1、viewContainer.transactionHandlers属性如何初始化，(目前是直接new个对象)
  
  
  
//1、cglib代理异常的原因
		1)	context 为空
		2)  代理的类存在静态方法
        3)	反射获取对象属性时，只能用getField(),不能用getDeclaredFields()(拿不到属性，估计是字节码编写的问题), 且属性必须是public
  		4)  调用的方法名 是  方法名+$super$  如：Pool$Enhancer$.getBeanByNameAndType$Super$
 
  

注意：  assets的文件格式    utf-8无dom格式的
//------------------config-----------------------
<config key="" file ="" resolver=""><config>  

key=value    #注意编码格式要是utf-8无dom格式的，dom格式头部会带编码格式