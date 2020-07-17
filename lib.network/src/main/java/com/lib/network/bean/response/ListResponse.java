package com.lib.network.bean.response;

import java.util.List;

/**
 * @ClassName: ListResponse
 * @Description: 具有list字段的list Response
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class ListResponse<T, B> extends BaseResponse<T> {
    private List<B> list;

    public List<B> getList() {
        return list;
    }

    public void setList(List<B> list) {
        this.list = list;
    }
}
