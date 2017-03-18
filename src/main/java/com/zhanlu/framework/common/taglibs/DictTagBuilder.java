package com.zhanlu.framework.common.taglibs;

import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 选择控件构建器（支持select、radio、checkbox选择控件）
 *
 * @author 杨新伦
 * @date 2016-04-11
 */
@Component
public class DictTagBuilder implements TagBuilder {

    private static final Logger log = LoggerFactory.getLogger(DictTagBuilder.class);

    private static final String TYPE_SELECT = "select";
    private static final String TYPE_RADIO = "radio";
    private static final String TYPE_CHECKBOX = "checkbox";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String TYPE = "type"; //select、radio、checkbox
    public static final String TYPE_CODE = "typeCode";
    public static final String READ_ONLY = "readonly";
    public static final String DISPLAY_TYPE = "displayType";
    public static final String CSS_STYLE = "cssStyle";
    public static final String CSS_CLASS = "cssClass";

    private WebApplicationContext springContext;
    //控件名称
    private String name;
    //对象已选择的值
    private String value;
    //控件类型
    private String type;
    //配置实体名称
    private String typeCode;
    //控件是否只读
    private String readonly;
    //显示类型：1:方便显示
    private String displayType;
    //控件style
    private String cssStyle;
    //控件css
    private String cssClass;
    //值列表
    private List<String> values = new ArrayList<>();
    //选择列表
    private List<DataDict> items = null;
    private Map<String, List<DataDict>> dictMap = new HashedMap();

    /**
     * 获取DTO传递的参数，并依此构建选择控件的html信息
     */
    @Override
    public String build(TagDTO dto) {
        this.springContext = dto.getSpringContext();
        dataProcess(dto);
        if (name == null || type == null) {
            log.error("please confirm tag name or tag type is null.");
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        if (type.equalsIgnoreCase(TYPE_CHECKBOX)) {
            buildCheckOrRadio(buffer);
        } else if (type.equalsIgnoreCase(TYPE_RADIO)) {
            buildCheckOrRadio(buffer);
        } else if (type.equalsIgnoreCase(TYPE_SELECT)) {
            buildSelect(buffer);
        }
        return buffer.toString();
    }

    /**
     * 根据dto对象，转换为builder的对象属性。并且对于configName，从注入的ConfigManager对象中获取配置数据
     *
     * @param dto
     */
    private void dataProcess(TagDTO dto) {
        this.values.clear();
        this.name = dto.getProperty(NAME);
        this.value = dto.getProperty(VALUE);
        this.type = dto.getProperty(TYPE);
        this.typeCode = dto.getProperty(TYPE_CODE);
        this.readonly = dto.getProperty(READ_ONLY);
        this.displayType = dto.getProperty(DISPLAY_TYPE);
        this.cssStyle = dto.getProperty(CSS_STYLE);
        this.cssClass = dto.getProperty(CSS_CLASS);
        if (StringUtils.isNotEmpty(value)) {
            if (value.indexOf(";") > -1) {
                String[] vs = value.split(";");
                for (String v : vs) {
                    values.add(v);
                }
            } else {
                values.add(value);
            }
        }
        if (dictMap.containsKey(typeCode)) {
            items = dictMap.get(typeCode);
        } else {
            DataDictService dataDictService = springContext.getBean(DataDictService.class);
            items = dataDictService.findItems(typeCode);
            dictMap.put(typeCode, items);
        }
    }

    /**
     * 构造checkbox、radio类型的控件元素
     *
     * @param buffer
     */
    private void buildCheckOrRadio(StringBuffer buffer) {
        if (items != null && items.size() > 0) {
            for (DataDict item : items) {
                if (this.displayType == null || !this.displayType.equals("1")) {
                    buffer.append("<label><input type='" + type + "' name='" + name + "' value='" + item.getCode() + "' ");
                    if (values.contains(item.getCode())) {
                        buffer.append("checked='checked' ");
                    }
                    buffer.append("/>" + item.getName() + "</label>");
                } else {
                    if (values.contains(item.getCode())) {
                        buffer.append(item.getName());
                    }
                }
            }
        }
    }

    /**
     * 构造select类型的控件元素
     *
     * @param buffer
     */
    private void buildSelect(StringBuffer buffer) {
        if (this.displayType == null || !this.displayType.equals("1")) {
            buffer.append("<select name='" + name + "' ");
            if (cssClass != null) {
                buffer.append("class='" + cssClass + "' ");
            }
            if (this.cssStyle != null) {
                buffer.append("style='" + this.cssStyle + "' ");
            }
            if (readonly != null && readonly.equalsIgnoreCase("true")) {
                buffer.append("readonly disabled ");
            }
            buffer.append(">");
            buffer.append("<option value='' selected>------请选择------</option>");

            if (items != null && !items.isEmpty()) {
                for (DataDict item : items) {
                    buffer.append("<option ");
                    if (values.contains(item.getCode())) {
                        buffer.append("selected='selected' ");
                    }
                    buffer.append("value='" + item.getCode() + "'>");
                    buffer.append(item.getName() + "</option>");
                }
            }
            buffer.append("</select>");
        } else {
            for (DataDict item : items) {
                if (values.contains(item.getCode())) {
                    buffer.append(item.getName());
                }
            }
        }
    }

}
