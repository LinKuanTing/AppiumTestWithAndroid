package com.example.wghtest.Level3.Weight;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;
import static java.lang.Thread.sleep;

public class WDFnL3WeightRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx,_lvDeleteIdx;
    protected AndroidElement _tvDate,_tvWeight,_tvBodyfat,_tvBMI,_tvWater,_tvMM,_tvBone,_tvBMR,_tvVFL,_tvProtein,_tvMP;

    protected int _sizeRecordX ;
    protected int _sizeRecordY ;
    //用來計算一個記錄的大小
    protected static String _strOneRecord = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView/android.widget.FrameLayout[1]";

    private String __strIdx = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView";
    private static String __strDateXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView";
    private static String __strWeightXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[1]/android.widget.TextView[2]";
    private static String __strBodyfatXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[2]/android.widget.TextView[2]";
    private static String __strBMIXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[3]/android.widget.TextView[2]";
    private static String __strWaterXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[4]/android.widget.TextView[2]";
    private static String __strMMXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[5]/android.widget.TextView[2]";
    private static String __strBoneXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[6]/android.widget.TextView[2]";
    private static String __strBMRXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[7]/android.widget.TextView[2]";
    private static String __strVFLXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[8]/android.widget.TextView[2]";
    private static String __strProteinXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[9]/android.widget.TextView[2]";
    private static String __strMPXpath = "/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TableLayout/android.widget.TableRow[11]/android.widget.TextView[2]";
    private static String __strRecordDeleteXpath = "/android.widget.LinearLayout[2]/android.widget.LinearLayout";

    private static String __strButton1ID = "android:id/button1";

    private static String __strFileName = "WeightRecord";
    private static String __strFilePath;

    TouchAction tchAction = new TouchAction(adDriver);

    public WDFnL3WeightRecord(int index){
        this._sizeRecordX = adDriver.findElementByXPath(_strOneRecord).getSize().getWidth();
        this._sizeRecordY = adDriver.findElementByXPath(_strOneRecord).getSize().getHeight();
        this._index = index;
        this.__strIdx += "/android.widget.FrameLayout["+_index+"]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strIdx));
        if (_index > 2){
            int startX = adDriver.manage().window().getSize().getWidth()/2;
            int startY = adDriver.manage().window().getSize().getHeight()/2;
            tchAction.press(PointOption.point(startX,startY)).waitAction().moveTo(PointOption.point(startX,startY-_sizeRecordY+50)).release().perform();
        }
        this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strDateXpath));
        this._tvWeight = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strWeightXpath));
        this._tvBodyfat = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strBodyfatXpath));
        this._tvBMI = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strBMIXpath));
        this._tvWater = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strWaterXpath));
        this._tvMM = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strMMXpath));
        this._tvBone = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strBoneXpath));
        this._tvBMR = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strBMRXpath));
        this._tvVFL = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strVFLXpath));
        this._tvProtein = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strProteinXpath));
        this._tvMP = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strMPXpath));
    }

    public WDFnL3WeightRecord(){}

    public String getDate(){
        return _tvDate.getText();
    }

    public String getWeight(){
        return _tvWeight.getText();
    }

    public String getBodyfat(){
        return _tvBodyfat.getText();
    }

    public String getBMI(){
        return _tvBMI.getText();
    }

    public String getWater(){
        return _tvWater.getText();
    }

    public String getMM(){
        return _tvMM.getText();
    }

    public String getBone(){
        return  _tvBone.getText();
    }

    public String getBMR() {
        return  _tvBMR.getText();
    }

    public String getVFL(){
        return _tvVFL.getText();
    }

    public String getProtein(){
        return _tvProtein.getText();
    }

    public String getMP(){
        return _tvMP.getText();
    }

    public void update(){
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Weight wdFnL3Weight = new WDFnL3Weight();
        try{
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
            wdFnL3Weight.setValue();
        } catch (Exception eNoFindElement) {
            //呼叫設定修改紀錄 點選儲存 不會直接跳出信息確認
        }
    }

    public void delete() throws InterruptedException {
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int startX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int startY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.press(PointOption.point(startX,startY)).waitAction().moveTo(PointOption.point(startX-sizeX/2,startY)).release().perform();
        _lvDeleteIdx = (AndroidElement) adDriver.findElement(By.xpath(__strIdx+__strRecordDeleteXpath));
        _lvDeleteIdx.click();
        adDriver.findElement(By.id(__strButton1ID)).click();
        sleep(3000);
    }


    ArrayList alReadLine = new ArrayList<>();
    public boolean compare(ArrayList arylstWeight) throws IOException {
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        alReadLine = fnFileEvent.getContent(__strFilePath);

        int sameCount = 0;
        for (int i=0; i<alReadLine.size(); i++){
            if (alReadLine.get(i).equals(arylstWeight.get(i))){
                sameCount ++;
            }
        }
        if (sameCount == alReadLine.size()){
            return true;

        }
        else {
            return false;
        }
    }



}
