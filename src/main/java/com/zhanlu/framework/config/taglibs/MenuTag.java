package com.zhanlu.framework.config.taglibs;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;

/**
 * 系统首界面左栏导航菜单自定义标签 该类继承RequestContextAwareTag，主要用于获取WebApplicationContext
 * 
 * @author 杨新伦
 * @date 2016-04-11
 */
public class MenuTag extends RequestContextAwareTag {

	private static final long serialVersionUID = -3041636263647268721L;
	
	// Spring的上下文
	private WebApplicationContext springContext;
	// Servlet的上下文
	private ServletContext servletContext = null;

	/**
	 * 继承RequestContextAwareTag的doStartTagInternal方法，实际上是doStartTag的模板方法
	 */
	@Override
	protected int doStartTagInternal() throws Exception {
		// 获取ServletContext
		servletContext = pageContext.getServletContext();
		// 获取spring上下文
		springContext = getRequestContext().getWebApplicationContext();
		JspWriter writer = pageContext.getOut();
		if (springContext == null) {
			writer.write("获取菜单项失败");
		} else {
			TagDTO dto = new TagDTO(servletContext);
			dto.setSpringContext(springContext);
			MenuTagBuilder builder = springContext.getBean(MenuTagBuilder.class);
			writer.write(builder.build(dto));
		}
		return 0;
	}
}
