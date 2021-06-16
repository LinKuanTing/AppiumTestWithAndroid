package com.example.wghtest.Level3.BloodGlucose;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3BloodGlucoseRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _tvDate,_tvSession,_tvGLU;

    private String __strIdxRecordXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.ListView";

    private static String __strDateXpath = "/android.widget.LinearLayout[1]/android.widget.TextView";
    private static String __strSessionXpath = "/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.TextView[2]";
    private static String __strGLUXpath = "/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.TextView[2]";

    private static String __strFileName = "BloodGlucoseRecord";
    private static String __strFilePath;

    TouchAction tchAction = new TouchAction(adDriver);

    public WDFnL3BloodGlucoseRecord(int index){
        this._index = index;
        this.__strIdxRecordXpath += "/android.widget.LinearLayout[" + _index + "]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath));
        this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strDateXpath));
        this._tvSession = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strSessionXpath));
        this._tvGLU = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strGLUXpath));
    }

    public WDFnL3BloodGlucoseRecord(){}

    public String getDate(){
        return _tvDate.getText();
    }

    public String getSession() {
        return _tvSession.getText();
    }

    public String getGLU(){
        return _tvGLU.getText();
    }

    public void update(){
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3BloodGlucose wdFnL3BloodGlucose = new WDFnL3BloodGlucose();
        try{
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
            wdFnL3BloodGlucose.setValue();
        }catch (Exception eNoFindElement){
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
        }
    }

    ArrayList alReadLine = new ArrayList<>();
    public boolean compare(ArrayList alBG) throws IOException {
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameCount = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alBG.get(i))){
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
