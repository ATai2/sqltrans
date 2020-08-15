package com.ppx.sqltrans.databases;

/**
 *  where语句操作符
 */
public enum Opt {

    Equals(" = "),
    NotEquals(" <> "),
    GreatThan(" > "),
    SmallThan( " < "),
    GreaterOrEqual(" >= "),
    SmallerOrEqual(" <= "),
    Like( " like "),
    BiLike( " like "),
    And( " and "),
    Or( " or "),
    In( " in "),


    ;


    String operation;

    Opt(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

}
