# JsWebView
与js交互
### 第一步：复制dist和xml里的文件到assets里<br>
### 第二步：新建Application初始化	PineApple.Init("xml/config_config.properties", getApplicationContext());
### 第三步在项目里命名一个包名为bean(注意一定是该名称的包名)，新建两个类，一个是implements IHandlerInterceptor，一个类是根据web端，例如如下
~~~~
jsTransactionManagers.call("service:testClass.wifi_ssid","[" + name + "]",function(data){
~~~~
注意上面的service：必须写这个
service:testClass里的testClass,你可以新建一个testClass类，而这个类implements IService
### 第四步修改config.xml里的<packageLocations> 里的第二项<item> ,把这个改成，刚才新建bean的整体包名，也修改<beans>里的<bean id="b">里的class,改成
第一个类你新建的包名和名称，例如：com.tentcoo.jswebview.bean.MyHanderInterceter
