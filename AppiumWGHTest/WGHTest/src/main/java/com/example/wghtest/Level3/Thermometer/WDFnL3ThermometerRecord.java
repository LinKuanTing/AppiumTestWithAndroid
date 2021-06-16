package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3ThermometerRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _tvDate, _tvType, _tvTemp;

    private String __strIdxRecordXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView";

    private String __strDateXpath = "/android.widget.TextView";
    private String __strTypeXpath = "/android.widget.RelativeLayout/android.widget.TextView[1]";
    private String __strTempXpath = "/android.widget.RelativeLayout/android.widget.TextView[2]";

    private static String __strFileName = "ThermometerRecord";
    private static String __strFilePath;

    TouchAction tchAction = new TouchAction(adDriver);

    public WDFnL3ThermometerRecord(int index){
        this._index = index;
        this.__strIdxRecordXpath += "/android.widget.RelativeLayout["+_index+"]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath));
        this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strDateXpath));
        this._tvType = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strTypeXpath));
        this._tvTemp = (AndroidElement) adDriver.findElement(By.xpath(__strIdxRecordXpath+__strTempXpath));
    }

    public WDFnL3ThermometerRecord(){}

    public String getDate(){
        return _tvDate.getText();
    }

    public String getType(){
        return _tvType.getText();
    }

    public String getTemp(){
        return _tvTemp.getText();
    }

    public void update() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Thermometer wdFnL3Thermometer = new WDFnL3Thermometer();

        wdFnL3Thermometer.setValue();

    }

    ArrayList alReadLine = new ArrayList<>();
    public boolean compare(ArrayList alTemp) throws IOException {
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameCount = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(alTemp.get(i))){
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
