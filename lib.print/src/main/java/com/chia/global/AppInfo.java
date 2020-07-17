package com.chia.global;

/**
 * @ClassName: AppInfo
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class AppInfo {
    private String productName = "";

    public AppInfo() {
    }

    public AppInfo(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName == null ? "" : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
