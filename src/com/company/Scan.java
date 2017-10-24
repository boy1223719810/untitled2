package com.company;

        import java.io.FileInputStream;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.util.ArrayList;

public class Scan {
    private static String inputPath = "";
    public String input;
    public int pointer;

    public Scan(String filename){
        File sourceFile = new File(Scan.inputPath+filename);
        ArrayList<Character> trans = new ArrayList<Character>();
        try {
            FileInputStream in = new FileInputStream(sourceFile);
            char ch1 = ' ';
            char ch2 = ' ';//用于在验证是否为引号内结尾或者注释结尾
            while(in.available()>0){
                if(ch2 != ' '){
                    ch1 = ch2;
                } else {
                    ch1 = (char) in.read();
                }

                if(ch1 == '\''){//避免删除空白时将‘’包含的空白字符剔除
                    trans.add(ch1);
                    trans.add((char)in.read());
                    trans.add((char)in.read());
                } else if (ch1 == '\"'){//避免将字符串中的空白剔除
                    trans.add(ch1);
                    while(in.available()>0){
                        ch1 = (char)in.read();
                        trans.add(ch1);
                        if(ch1 == '\"'){
                            break;
                        }
                    }
                } else if (ch1 == '/'){//剔除字符串
                    ch2 = (char)in.read();
                    if(ch2 == '/'){
                        while(in.available() > 0){
                            ch2 = (char)in.read();
                            if(ch2 == '\n'){
                                break;
                            }
                        }
                        ch2 = ' ';
                    } else if (ch2 == '*') {
                        while(in.available() > 0){
                            ch1 = (char)in.read();
                            if(ch1 == '*'){
                                ch2 = (char)in.read();
                                if(ch2 == '/'){
                                    break;
                                }
                            }
                        }
                    } else {
                        if(ch2 == ' '){
                            while(ch2 == ' '){
                                ch2 = (char)in.read();
                            }
                        }
                        trans.add(ch1);
                        trans.add(ch2);
                        ch2 = ' ';
                    }
                } else if(ch1 == ' '){
                    if(trans.get(trans.size()-1) == ' '){
                        continue;
                    } else {
                    }
                } else {
                    if((int)ch1 == 13 ||(int)ch1 == 10 ||(int)ch1 == 32){//去除换行

                    } else {
                        trans.add(ch1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] chStr = new char[trans.size()];
        for(int i = 0;i < trans.size();i++){
            chStr[i] = trans.get(i);
        }
        String result = new String(chStr);
        this.input = result;
        this.pointer = 0;
    }

    public char getNextChar(){
        if(pointer==input.length()){
            return (char)0;
        } else {
            return input.charAt(pointer++);
        }
    }

    //回退n个字符
    public void retract(int n){
        for(int i = 0;i < n;i++){
            pointer--;
        }
    }

    public int getIndex(){
        return pointer;
    }

    public int getLength(){
        return this.input.length();
    }

    public void move(int n){
        for(int i = 0;i < n;i++){
            pointer++;
        }
    }

}
