package com.zlandzbt.tools.jv.runtime_permissions.permission;

/**
 * @author zhao.botao
 * 2018.12.15
 * 动态权限获取回调接口
 */
public interface PermissionCallBack {
    class ErrorCode {
        /**
         * 全部权限获取成功
         */
        public static final int SUCCESS = 0;

        /**
         * 至少一个权限获取失败
         */
        public static final int FAIL = 1;

        /**
         * 获取权限过程中发生错误
         */
        public static final int ERROR = 2;
    }

    /**
     * 权限申请成功
     *
     * @param permission
     * @param code       请求码
     */
    public void permissionGranted(String permission, int code);


    /**
     * 权限被拒绝，勾选了不在提醒
     *
     * @param shouldShowRational 是否勾选了不在提醒
     * @param permission         申请的权限
     * @param code               请求码
     */
    public void permissionReject(boolean shouldShowRational, String permission, int code);

    /**
     * 全部过程执行完毕
     *
     * @param errorCode {@link ErrorCode}
     * @param msg       信息
     * @param code      请求码
     */
    public void finish(int errorCode, String msg, int code);
}
