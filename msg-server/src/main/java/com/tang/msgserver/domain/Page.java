package com.tang.msgserver.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页pojo.本类实例化时的泛型T决定了分页数据的泛型。 <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-11-7 上午11:51:03
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class Page implements Serializable {
    /**
     * 弹出的列表页面，默认一般每页显示10条，可以用此常量来重新设置rowsPerPage
     */
    public static final int POP_ROWS = 10;

    private int totalRows = 0; // 总记录数

    private int currentPage = 1; // 当前页面数

    private int totalPage = 1; // 总页面数

    // 这个值在列表的时候作为iterator标签的length属性值调用，例如request.setAttribute("length",rowsPerpage);
    private int rowsPerPage = 10; // 每页显示记录数

    private Map<String,String> searchParams = new HashMap<String, String>();// 查询参数

    public Page() {

    }

    /**
     * 构造器 初始化当前页面数
     *
     * @param curPage
     *            当前页面数
     */
    public Page(int curPage) {
        this.currentPage = curPage;
    }

    public Page(int curPage, int totalRows) {
        this.currentPage = curPage;
        this.totalRows = totalRows;
        this.count();
    }

    private void count() {
        if ((totalRows != 0) && (this.totalRows % this.rowsPerPage == 0)) {
            this.totalPage = this.totalRows / this.rowsPerPage;
        } else {
            this.totalPage = this.totalRows / this.rowsPerPage + 1;
        }
        if(this.currentPage > this.totalPage) {
            this.currentPage = this.totalPage;
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        this.count();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getCurrentPage() {
        return (this.currentPage);
    }

    /**
     * 根据总记录数和每页显示记录数可以得到总页数
     *
     * @return 总页数
     */
    public int getTotalPage() {
        return this.totalPage;
    }

    public int getRowsPerPage() {
        return (this.rowsPerPage);
    }

    public Map<String, String> getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Map<String, String> searchParams) {
        this.searchParams = searchParams;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalRows=" + totalRows +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", rowsPerPage=" + rowsPerPage +
                ", searchParams=" + searchParams +
                '}';
    }
}