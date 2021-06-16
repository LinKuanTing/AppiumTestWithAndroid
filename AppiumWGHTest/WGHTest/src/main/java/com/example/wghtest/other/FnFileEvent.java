package com.example.wghtest.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FnFileEvent {
    private static String __strFilePath = "D:\\TestData\\TxtPath.txt";

    static ArrayList arylstData = new ArrayList();

    public void loadPath() throws IOException {
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        while (br.ready()){
            String strReadLine = br.readLine();
            if (!(strReadLine.substring(0,3).equals("###"))){
                arylstData.add(strReadLine);
            }
        }
    }

    public ArrayList getContent(String strPath) throws IOException {
        FileInputStream fis = new FileInputStream(strPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        ArrayList al = new ArrayList();
        while (br.ready()){
            String strReadLine = br.readLine();
            if (!(strReadLine.contains("###"))){
                al.add(strReadLine);
            }
        }
        return al;
    }

    public String getStrContent(String strPath) throws IOException {
        FileInputStream fis = new FileInputStream(strPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        String strContent = "";
        while (br.ready()){
            strContent += br.readLine();
        }
        return strContent;
    }

    public String getPath(String fileName){
        for (int i=0; i<arylstData.size(); ++i){
            if (arylstData.get(i).toString().indexOf(fileName) != -1){
                return arylstData.get(i).toString();
            }
        }
        return null;
    }

    public String readToString(String strPath) {
        File file = new File(strPath);
        Long filelength = file.length();     //取得文件內容長度
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(filecontent);//返回文件内容,默認編碼
    }
}
