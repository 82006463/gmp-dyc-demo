package com.zhanlu.framework.common.taglibs;

/**
 * 标签构建接口
 *
 * @author 杨新伦
 * @date 2016-04-11
 */
public interface TagBuilder {
	/**
	 * 根据标签变量构建控件元素
	 * @param dto 标签之间数据传输对象，包括标签属性、值以及常用的上下文
	 * @return
	 */
	public String build(TagDTO dto);
}
