�ϼ�Ϊcore�����¼���Ͻconfig/context/ioc/template 4���Ӱ�������pinapple.common����Framework���е�����Ϊpineapple.core�е�����ģ���ṩ������ʩ��core������ģ�鶼����frameworkģ�飺

Config �C ���ù���
Context �C �����Ĺ���
Ioc �C �Զ�ע�빦��
Template �C ����ģ��Ĵ���ģ��


//ԭ�Ȱ�����̬�����Ĵ�����(�����޸�)
 1��Application  Init()
 2��TransationBuilder  getInstance();		//��̬����
 3��AntWildcardMatcher	isMatcher();
 4�� Formatter 								//��̬����                                                 
 5��BeanResolver							    //��̬����
 
 
 //-----------����
  1��viewContainer.transactionHandlers������γ�ʼ����(Ŀǰ��ֱ��new������)
  
  
  
//1��cglib�����쳣��ԭ��
		1)	context Ϊ��
		2)  ���������ھ�̬����
        3)	�����ȡ��������ʱ��ֻ����getField(),������getDeclaredFields()(�ò������ԣ��������ֽ����д������), �����Ա�����public
  		4)  ���õķ����� ��  ������+$super$  �磺Pool$Enhancer$.getBeanByNameAndType$Super$
 
  

ע�⣺  assets���ļ���ʽ    utf-8��dom��ʽ��
//------------------config-----------------------
<config key="" file ="" resolver=""><config>  

key=value    #ע������ʽҪ��utf-8��dom��ʽ�ģ�dom��ʽͷ����������ʽ