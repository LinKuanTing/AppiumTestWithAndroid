package com.example.wghtest.Level3.BloodPressure;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3BloodPressureRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _tvDate,_tvSYS, _tvDIA, _tvPulse;

    private String __strIdxRecordXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.ListView";

    private static String __strDateXpath = "/android.widget.LinearLayout[1]/android.widget.TextView";
    private static String __strSYSXpath = "/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.TextView[2]";
    private static String __strDIAXpath = "/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.TextView[4]";
    private static String __strPulseXpath = "/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.TextView[2]";

    private static String __strFileName = "BloodPressureRecord";
    private static String __strFilePath;

    TouchAction tchAction = new TouchAction(adDriver);

    public WDFnL3BloodPressureRecord(int index){
        this._index = index;
        this.__strIdxRecordXpath += "/android.widget.LinearLayout[" + _index + "]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath));
        try {
            this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strDateXpath));
        }catch (Exception e){
            //System.out.println(adDriver.getPageSource());
        }

        this._tvSYS = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strSYSXpath));
        this._tvDIA = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strDIAXpath));
        this._tvPulse = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strPulseXpath));
    }

    public WDFnL3BloodPressureRecord(){}

    public String getDate(){
        return _tvDate.getText();
    }

    public String getSYS(){
        return _tvSYS.getText();
    }

    public String getDIA(){
        return _tvDIA.getText();
    }

    public String getPulse(){
        return _tvPulse.getText();
    }

    public void update(){
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3BloodPressure wdFnL3BloodPressure = new WDFnL3BloodPressure();
        try{
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
            wdFnL3BloodPressure.setValue();
        } catch (Exception eNoFindElement) {
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
        }

    }

    ArrayList alReadLine = new ArrayList();
    public boolean compare(ArrayList alBP) throws IOException {
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);


        int sameCount = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alBP.get(i))){
                sameCount ++;
            }
        }
        if(sameCount == alReadLine.size()){
            return true;
        }else {
            return false;
        }

    }

}
