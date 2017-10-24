package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LexicalAnalyzer {

    public static void main(String[] args){
        LexicalAnalyzer lex = new LexicalAnalyzer("a.c");
        ArrayList<Token> tokens = lex.getTokenList();
        lex.output(tokens);
    }
    /*
     * 自己实现的扫描和缓存类
     */
    private Scan scan;

    /**
     * 词法分析器类的构造方法
     * @param filename 文件位置和文件名
     */
    public LexicalAnalyzer(String filename){
        this.scan = new Scan(filename);
    }

    /**
     * 将词法分析结果输出到文件中
     * @param list 词法分析结果的TokenList
     * @throws FileNotFoundException
     */
    @SuppressWarnings("resource")
    public void output(ArrayList<Token> list){

        for(int i = 0;i < list.size();i++){
            String str = "( "+list.get(i).type+","+list.get(i).value+" )";
            System.out.println(str);
        }
    }
    /**
     * 通过语法分析获得Token序列
     * @return Token序列
     */
    public ArrayList<Token> getTokenList(){
        ArrayList<Token> result = new ArrayList<Token>();
        int index = 0;
        while(index < scan.getLength()){
            Token token = analyze(index);
            result.add(token);
            index = scan.getIndex();
        }
        this.scan.retract(scan.getLength()-1);
        return result;
    }
    /**
     * 对某一位置进行词法分析
     * @param index 字母开始的位置
     * @return 单个Token
     */
    private Token analyze(int index){
        int length = scan.getLength();
        int type = -1;
        String value = "";
        while(index < length){
            char ch = scan.getNextChar();
            index++;
            if(isDigit(ch)){//判断是否为一个数字
                if(Type.isCalc(type)){
                    scan.retract(1);
                    break;
                }
                if(value == ""){
                    value = new Character(ch).toString();
                    type = Type.NUM;
                } else {
                    value += new Character(ch).toString();
                }

            } else {
                if(type == Type.NUM){
                    scan.retract(1);
                    return new Token(type,value);
                }
                switch(ch){
                    case '='://==,=
                        if(type == -1){
                            type = Type.ASSIGN;
                            value = "=";
                        }
                        break;
                    case '+':
                        if(type == -1){
                            type = Type.ADD;
                            value = "+";
                        }
                        break;
                    case '-':
                        if(type == -1){
                            type = Type.SUB;
                            value = "-";
                        }
                        break;
                    case '*':
                        if(type == -1){
                            type = Type.MUL;
                            value = "*";
                        }
                        break;
                    case '/':
                        if(type == -1){
                            type = Type.DIV;
                            value = "/";
                        }
                        break;

                    case '(':
                        if(type == -1){
                            type = Type.PARENTHESIS_L;
                            value = "(";
                        }
                        break;
                    case ')':
                        if(type == -1){
                            type = Type.PARENTHESIS_R;
                            value = ")";
                        }
                        break;
                    default:
                        break;
                }
                if(!Type.isCalc(type)){
                    break;
                }
            }
        }
        if(value.length()>1){
            scan.move(value.length()-1);
        }
        Token token = new Token(type,value);
        return token;
    }

    private boolean isDigit(char c){
        if((c<='9'&&c>='0')||c=='.'){
            return true;
        } else {
            return false;
        }
    }
}
