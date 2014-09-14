package com.online.spring.websocket.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring零配置入口类。
 * 
 * @author tongyufu
 *
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 指定有@Configuration的类，相当于指定applicationContext.xml<br /><br />
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 指定Spring MVC的配置<br /><br />
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * 设置DispatcherServlet的url-pattern<br /><br />
	 * {@inheritDoc}
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 配置DispatcherServlet启动参数<br /><br />
	 * {@inheritDoc}
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		//支持RESTful的OPTIONS请求
		registration.setInitParameter("dispatchOptionsRequest", "true");
		//支持RESTful的TRACE请求
		registration.setInitParameter("dispatchTraceRequest", "true");
	}

	/**
	 * 配置ContextLoaderListener<br /><br />
	 * {@inheritDoc}
	 */
	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		super.registerContextLoaderListener(servletContext);
	}
}
