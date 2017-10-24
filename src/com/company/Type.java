package com.company;

public class Type {
    //	运算符
    public static final int ASSIGN = 1; // =
    public static final int ADD = 2;  // +
    public static final int SUB = 3;  // -
    public static final int MUL = 4;//*
    public static final int DIV = 5; //  /
    //数字
    public static final int NUM = 6;

    public static final int PARENTHESIS_L = 7;// (
    public static final int PARENTHESIS_R = 8;// )
    /**
     * 判断是否为计算型的符号
     * @param type
     * @return
     */
    public static boolean isCalc(int type){
        if(type == Type.ASSIGN || type == Type.ADD || type == Type.SUB || type == Type.DIV ||
                type == Type.MUL){
            return true;
        } else {
            return false;
        }
    }
}
