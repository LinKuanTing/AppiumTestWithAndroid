package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3WellnessIndexesRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _tvDate,_tvHR;

    private String __strRecordXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView";
    private String __strDateXpath = "/android.widget.TextView";
    private String __strHRXpath = "/android.widget.LinearLayout/android.widget.RelativeLayout[1]/android.widget.TextView[1]";

    private static String __strFileName = "WellnessIndexesRecord";
    private static String __strFilePath;

    public WDFnL3WellnessIndexesRecord(int index){
        this._index = index;
        this.__strRecordXpath += "/android.widget.RelativeLayout[" + _index + "]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strRecordXpath));
        this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strRecordXpath+__strDateXpath));
        this._tvHR = (AndroidElement) adDriver.findElement(By.xpath(__strRecordXpath+__strHRXpath));
    }

    public WDFnL3WellnessIndexesRecord(){ }

    public String getDate(){
        return _tvDate.getText();
    }

    public String getHR(){
        return _tvHR.getText();
    }

    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alWellnessIdx) throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameNum = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alWellnessIdx.get(i).equals(alReadLine.get(i))){
                sameNum++;
            }
        }
        if (sameNum == 3){
            return true;
        }
        else{
            return false;
        }
    }
}
