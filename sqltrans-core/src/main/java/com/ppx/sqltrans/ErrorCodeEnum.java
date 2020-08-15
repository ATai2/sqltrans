package com.ppx.sqltrans;

/**
 * 错误码建议按照模块写，
 */
public enum ErrorCodeEnum implements AbstractBaseExceptionEnum {

    /**
     * example  语义化错误码
     * example  非语义化错误码： 模块/功能+6位错误码（主功能，次功能，错误类型）  注意注释，可以体现在msg中
     */
    SYS_ERROR("sys.error", "系统错误"),
    DB_000001("db.000001", "数据库主键冲突"),
    DB_TYPE_NOT_SUPPORT("DB_TYPE_NOT_SUPPORT", "数据库类型不支持"),
    DB_OPERA_DELETE("DB_OPERA_DELETE", "数据库删除操作失败"),
    DB_OPERA_SELECT("DB_OPERA_SELECT", "数据库查询失败"),
    //    数据报送
    REPORT_FILE_NOT_EXIST("report.file-not-exist", "报送文件不存在"),

    DB_QUERYCLAUSE_NOT_EMPTY("db.queryclausenotempty", "查询语句不能为空"),

    API_ERROR("api.error", "请检查配置的url地址是否填写正确"),
    //    数据服务
    SZYD_STATIC_FILE_000001("staticFile.000001", "文件名称不合法，应该为：社会信用代码_TEMP_业务类型_版本号_时间戳_UUID"),
    SZYD_STATIC_FILE_000002("staticFile.000002", "缺少参数ORGID"),
    SZYD_STATIC_FILE_000003("staticFile.000003", "缺少参数FILE_NAME"),
    SZYD_STATIC_FILE_000004("staticFile.000004", "缺少参数BUSTYPE"),
    SZYD_STATIC_FILE_000005("staticFile.000005", "未设置机构代码对应的接入点"),
    SZYD_STATIC_FILE_000006("staticFile.000006", "配置信息功能中未配置下发采集目录地址"),

    DB_REPORT_000001("dbReport.000001", "信息资源没有进行元数据挂接"),
    DB_REPORT_000002("dbReport.000002", "信息资源绑定的模型没有设置主键"),
    DB_REPORT_000003("dbReport.000003", "由于本次报送0条数据，取消报送"),
    DB_REPORT_000004("dbReport.000004", "指标未进行元数据挂接"),
    DB_REPORT_000005("dbReport.000005", "信息资源没有设置扩展属性"),
    DB_REPORT_000006("dbReport.000006", "未配置SM2公钥"),
    DB_REPORT_000007("dbReport.000007", "未配置SM24秘钥"),

    API_AUTHENTICATION_000001("apiAuthentication.000001", "USER或者PASSOWRD参数为空"),
    API_AUTHENTICATION_000002("apiAuthentication.000002", "用户名参数USER值不正确"),
    API_AUTHENTICATION_000003("apiAuthentication.000003", "密码参数PASSOWRD值不正确"),
    API_AUTHENTICATION_000004("apiAuthentication.000004", "文件命名格式为业务编码_版本号_数据日期，如0004_1000_20180801.zip"),
    API_AUTHENTICATION_000005("apiAuthentication.000005", "业务类型编码BUSINESS_TYPE为空或者没有传参"),
    API_AUTHENTICATION_000006("apiAuthentication.000006", "文件名FILE_NAME为空或者没有传参"),

    B_INTERFACE_000001("BInterface.000001", "没有传入参数USER,或者USER参数为空"),
    B_INTERFACE_000002("BInterface.000002", "没有传入参数PASSOWRD,或者PASSOWRD参数为空"),
    B_INTERFACE_000003("BInterface.000003", "没有权限"),
    B_INTERFACE_000004("BInterface.000004", "没有传入参数RESCODE,或者RESCODE参数为空"),
    B_INTERFACE_000005("BInterface.000005", "没有传入参数Ver,或者Ver参数为空"),
    B_INTERFACE_000006("BInterface.000006", "没有传入参数filename,或者filename参数为空"),
    B_INTERFACE_000007("BInterface.000007", "未获取到上传文件"),
    B_INTERFACE_000008("BInterface.000008", "该接口未启用"),
    B_INTERFACE_000009("BInterface.000009", "没有启用的资源"),

    ;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
