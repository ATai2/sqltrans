package com.ppx.sqltrans;

/**
 * DMP自定义异常类
 */
public class DmpException extends RuntimeException {

    private String code;
    private String msg;

    public DmpException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public DmpException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public DmpException(AbstractBaseExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public DmpException(AbstractBaseExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum.getMsg(), cause);
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }
}
