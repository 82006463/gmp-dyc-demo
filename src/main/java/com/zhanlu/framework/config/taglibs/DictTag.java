package com.zhanlu.framework.config.taglibs;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * 自定义选择标签。根据选择的configName从数据字典表中获取配置列表，产生具体的select、radio或checkbox类型的控件
 * 该类继承RequestContextAwareTag，主要用于获取WebApplicationContext
 *
 * @author 杨新伦
 * @date 2016-04-11
 */
public class DictTag extends RequestContextAwareTag {

    private static final long serialVersionUID = -3678484078716531743L;

    //字段名称
    private String name;
    //对象已选择的值
    private String value;
    //为空时的默认值
    private String defaultVal;
    //选择类型：select/radio/checkbox
    private String type;
    //类型编号：数据字典中pcode的值
    private String typeCode;
    //是否只读
    private String readOnly;
    //显示类型：1:方便显示
    private String displayType;
    //控件样式
    private String cssStyle;
    //控件css
    private String cssClass;
    //Spring的上下文
    private WebApplicationContext springContext;
    //Servlet的上下文
    private ServletContext servletContext;

    /**
     * 继承RequestContextAwareTag的doStartTagInternal方法，实际上是doStartTag的模板方法
     */
    @Override
    protected int doStartTagInternal() throws Exception {
        //获取ServletContext
        servletContext = pageContext.getServletContext();
        //获取spring上下文
        springContext = getRequestContext().getWebApplicationContext();
        JspWriter writer = pageContext.getOut();
        try {
            TagDTO dto = new TagDTO(servletContext);
            dto.setProperty(DictTagBuilder.NAME, this.name);
            dto.setProperty(DictTagBuilder.VALUE, value);
            dto.setProperty(DictTagBuilder.DEFAULT_VAL, defaultVal);
            dto.setProperty(DictTagBuilder.TYPE, this.type);
            dto.setProperty(DictTagBuilder.TYPE_CODE, this.typeCode);
            dto.setProperty(DictTagBuilder.READ_ONLY, this.readOnly);
            dto.setProperty(DictTagBuilder.DISPLAY_TYPE, this.displayType);
            dto.setProperty(DictTagBuilder.CSS_STYLE, this.cssStyle);
            dto.setProperty(DictTagBuilder.CSS_CLASS, this.cssClass);
            dto.setSpringContext(springContext);
            DictTagBuilder builder = springContext.getBean(DictTagBuilder.class);
            writer.write(builder.build(dto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }
}
