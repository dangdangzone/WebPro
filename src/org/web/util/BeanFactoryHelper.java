package org.web.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
public class BeanFactoryHelper implements BeanFactoryAware
{
	private static BeanFactory beanFactory ; 
	
	private BeanFactoryHelper(){}
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException
	{
		this.beanFactory = beanFactory;
	}

	public static BeanFactory getBeanfactory()
	{
		if(beanFactory == null)
		{
			Resource res = new ClassPathResource("springbean-config.xml");
			beanFactory  = new XmlBeanFactory(res);
		}
		return beanFactory;
	}
	
	public static Object getBean(String serviceName)
	{
		if(beanFactory == null)
		{
			getBeanfactory();
		}
		return beanFactory.getBean(serviceName);
	}
}
