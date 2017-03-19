/**
 * Copyright (c) 2005-2010 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p>
 * $Id: Page.java 1183 2010-08-28 08:05:49Z calvinxiu $
 */
package com.zhanlu.framework.common.page;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * <p>
 * 注意所有序号从1开始.
 *
 * @param <T> Page中记录的类型.
 * @author calvin
 */
public class PageBean<T> {

    //-- 分页参数 --//
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected String orderBy;
    protected boolean autoCount = true;

    //-- 返回结果 --//
    protected List<T> result = Lists.newArrayList();
    protected long totalCount = 0;
    protected long totalPage = 0;

    //-- 构造函数 --//
    public PageBean() {
    }

    public PageBean(final int pageSize) {
        this.pageSize = pageSize;
    }

    //-- 分页参数访问函数 --//

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;
        if (this.pageNo < 1)
            this.pageNo = 1;
    }

    /**
     * 返回Page对象自身的setPageNo函数,可用于连续设置。
     */
    public PageBean<T> pageNo(final int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    /**
     * 获得每页的记录数量, 默认为-1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回Page对象自身的setPageSize函数,可用于连续设置。
     */
    public PageBean<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 获得排序字段,无默认值. 多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 返回Page对象自身的setOrderBy函数,可用于连续设置。
     */
    public PageBean<T> orderBy(final String theOrderBy) {
        setOrderBy(theOrderBy);
        return this;
    }

    /**
     * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 设置查询对象时是否自动先执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * 返回Page对象自身的setAutoCount函数,可用于连续设置。
     */
    public PageBean<T> autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    //-- 访问查询结果函数 --//

    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPage() {
        if (totalCount < 0)
            return 0;
        totalPage = totalCount / pageSize;
        if (totalCount % pageSize > 0)
            totalPage++;
        return totalPage;
    }

    /**
     * 是否还有下一页.
     */
    public boolean hasNext() {
        return (pageNo + 1 <= getTotalPage());
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        return hasNext() ? pageNo + 1 : pageNo;
    }

    /**
     * 是否还有上一页.
     */
    public boolean hasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        return hasPre() ? pageNo - 1 : pageNo;
    }
}
