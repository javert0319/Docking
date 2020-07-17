package com.lib.network;

import android.util.Base64;

import com.lib.network.utils.Base64Util;
import com.lib.network.utils.MD5Util;

/**
 * @ClassName: LoginService
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class LoginService {
    public static final String TOKEN = "token";
    public static final String HAS_ACCOUNT = "hasAccount";
    public static final String PWD = "pwd";
    public static final String MEMBERID = "memberId";
    public static final String ACCOUNT = "account";
    public static final String THIRDTYPE = "third_type";
    public static final String THIRDLOGINTIME = "third_login_time";
    public static final String EXIT_ACCOUNT = "exitAccount";
    public static final String FIRST_OPEN = "first_open";

    public static final int TYPE_NORMAL = 0;//自建账号
    public static final int TYPE_WEIXIN = 1;//微信
    public static final int TYPE_QQ = 2;//qq
    public static final int TYPE_SINA = 3;//新浪微博

    //是否sdk模式下
    private boolean isSDKMode = false;
    private boolean isHideUserInfo = false; //是否隐藏个人信息
    private boolean isHideLogout = false;//是否隐藏退出按钮键
    private boolean isDebug;

    private boolean isNeutral = false; // 是否是中性版本

    public boolean isNeutral() {
        return isNeutral;
    }

    public void setNeutral(boolean neutral) {
        isNeutral = neutral;
    }

    private String mAccount;

    //第三方登录类型
    private int thirdPartyType = 0;
    private String thirdPartyUnionid;
    private long thirdPartyExpire;

    private int chooseType = 1;

    //微信登录信息
//    private WXGetToeknResponse wxTokenResponse;

    //微博登录信息
//    private String WBUnionID;
//    private long WBEXPIRE;

    //qq登录信息
//    private String QQUnionId;
//    private long QQEXPIRE;


    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String token = "";
    public String tokenBase64 = "";
    public String tokenMd5 = "";
    public long deviceId = 0L;
    public String buildType = "debug";

    public String getTokenBase64() {
        return tokenBase64;
    }

    public void setTokenBase64(String tokenBase64) {
        this.tokenBase64 = tokenBase64;
    }

    private LoginService() {
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    private static class SingleTonHolder {
        private static LoginService sInstance = new LoginService();
    }

    public static LoginService getInstance() {
        return SingleTonHolder.sInstance;
    }

    private long lastTime;

    public boolean login(String account, String token, long deviceId, int type) {
        this.token = token;
        this.deviceId = deviceId;
        this.tokenMd5 = MD5Util.md5(token);
        this.mAccount = account;
        this.tokenBase64 = new String(Base64Util.encode(token.getBytes()));
        this.thirdPartyType = type;
        if (type == TYPE_NORMAL) {
            lastTime = System.currentTimeMillis();
        }
        return true;
    }

    public void logout() {
        this.mAccount = "";
        this.token = "";
        this.deviceId = 0L;
        this.tokenMd5 = "";
        this.tokenBase64 = "";
        lastTime = 0;
    }

    public synchronized LoginStatus isLogin() {
        long currTime = System.currentTimeMillis();
        long dTime = currTime - lastTime;
        LoginStatus status = LoginStatus.LOGOUT;
        switch (thirdPartyType) {
            case TYPE_NORMAL:
                if (lastTime > 0 && lastTime <= currTime) {
                    status = LoginStatus.LOGIN;
                }
                break;

            case TYPE_WEIXIN:
            case TYPE_SINA:
            case TYPE_QQ:
                if (lastTime > 0 && thirdPartyExpire > dTime) {
                    status = LoginStatus.LOGIN;
                } else if (thirdPartyExpire <= dTime) {
                    status = LoginStatus.EXPIRED;
                }
                break;
        }
        return status;
    }

    public enum LoginStatus {
        LOGIN, //1已登录
        LOGOUT, //未登录
        EXPIRED //登录过期
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }


    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }


    public String getThirdPartyUnionid() {
        return thirdPartyUnionid;
    }

    public void setThirdPartyUnionid(String thirdPartyUnionid) {
        this.thirdPartyUnionid = thirdPartyUnionid;
    }

    public long getThirdPartyExpire() {
        return thirdPartyExpire;
    }

    public void setThirdPartyExpire(long thirdPartyExpire) {
        this.thirdPartyExpire = thirdPartyExpire;
    }

    public int getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(int thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public int getChooseType() {
        return chooseType;
    }

    public void setChooseType(int chooseType) {
        this.chooseType = chooseType;
    }

    public boolean isSDKMode() {
        return isSDKMode;
    }

    public void setSDKMode(boolean SDKMode) {
        isSDKMode = SDKMode;
    }

    public void setHideMode(boolean isHideUserInfo, boolean isHideLogout) {
        this.isHideUserInfo = isHideUserInfo;
        this.isHideLogout = isHideLogout;
    }

    public boolean isHideUserInfo() {
        return isHideUserInfo;
    }

    public void setHideUserInfo(boolean hideUserInfo) {
        isHideUserInfo = hideUserInfo;
    }

    public boolean isHideLogout() {
        return isHideLogout;
    }

    public void setHideLogout(boolean hideLogout) {
        isHideLogout = hideLogout;
    }
}
