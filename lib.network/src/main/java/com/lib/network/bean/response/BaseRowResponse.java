package com.lib.network.bean.response;

import java.util.List;

/**
 * @ClassName: BaseRowResponse
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class BaseRowResponse<T, B> extends BaseResponse<T>{
    private List<B> list;

    private List<B> row;

    private List<B> rows;

    private int total;

    public List<B> getList() {
        if (list != null) {
            return list;
        }
        if (rows != null) {
            return rows;
        }
        if (row != null) {
            return row;
        }
        return null;
    }

    public void setList(List<B> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<B> getRow() {
        return getList();
    }

    public void setRow(List<B> row) {
        this.row = row;
    }

    public List<B> getRows() {
        return getList();
    }

    public void setRows(List<B> rows) {
        this.rows = rows;
    }
}
