package com.example.wghtest.Level3.Diet;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3DietRecord {
    protected int _index;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _tvDate, _tvMealInfo;
    protected AndroidElement _gvRecord;
    static int heightDateRecord,heightMealRecord;

    private String __strRecordIdx = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.GridView";
    private static String __strRecordDateXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.GridView/android.widget.RelativeLayout[1]";
    private static String __strRecordDateText = "/android.widget.TextView";
    private static String __strRecordMealXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.GridView/android.widget.RelativeLayout[2]";
    private static String __strRecordMealText = "/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]";

    private static String __strRecordContentID = "tw.com.wgh3h:id/gvMealRecord";

    TouchAction tchAction = new TouchAction(adDriver);


    public WDFnL3DietRecord(int index){
        this._index = index;
        __strRecordIdx += "/android.widget.RelativeLayout["+_index+"]";
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx));
        this._gvRecord = (AndroidElement) adDriver.findElement(By.id(__strRecordContentID));

    }

    public WDFnL3DietRecord(){
        this._gvRecord = (AndroidElement) adDriver.findElement(By.id(__strRecordContentID));
    }

    public String getIdxRecord(){
        int heightRecord = _lvRecordIdx.getSize().getHeight();

        if (heightRecord<=heightDateRecord){
            try {
                this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx+__strRecordDateText));
                return _tvDate.getText();
            }catch (Exception eNoFindElement) {
                return null;
            }
        }
        else {//if (heightRecord<=heightMealRecord && heightRecord > heightRecord){
            this._tvMealInfo = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx+__strRecordMealText));
            return _tvMealInfo.getText();
        }

    }

    public ArrayList getDateRecord(String strDate){
        heightDateRecord = adDriver.findElement(By.xpath(__strRecordDateXpath)).getSize().getHeight();
        heightMealRecord = adDriver.findElement(By.xpath(__strRecordMealXpath)).getSize().getHeight();
        ArrayList alDateRecord = new ArrayList();
        int idx=1;
        while(true){
            //取得頁面當前所有元素
            String strPageSource = adDriver.getPageSource();
            try {
                //宣告當前頁面紀錄
                WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord(idx);
                String strRecordText = wdFnL3DietRecord.getIdxRecord();

                //比較記錄日期是否所需要的日期紀錄
                if (strRecordText.contains(strDate)){
                    alDateRecord.add(strRecordText);
                    idx++;
                    continue;
                }
                //當遇到第二個日期紀錄時且已經找到所需的日期紀錄
                if (strRecordText.contains(".") && alDateRecord.size()!=0){
                    break;
                }
                //若還沒找到第二個日期紀錄則儲存所需日期底下的用餐紀錄
                else if(strRecordText.contains(" ") && alDateRecord.size()!=0){
                    alDateRecord.add(strRecordText);
                    idx++;
                    continue;
                }
                idx++;

            }catch (Exception eNoFindElement){
                //第一紀錄被切割時
                if (idx ==1){
                    idx = 2;
                }else {
                    int startX = _gvRecord.getLocation().getX()+_gvRecord.getSize().getWidth()/2;
                    int startY = _gvRecord.getLocation().getY()+_gvRecord.getSize().getHeight()-5;
                    int endX = startX;
                    int endY = _gvRecord.getLocation().getY();
                    tchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY)).release().perform();
                    idx = 1;
                    if (strPageSource.equals(adDriver.getPageSource())){
                        break;
                    }
                }

            }
        }

        for (int i=0; i<alDateRecord.size(); i++){
            for(int j=i+1; j<alDateRecord.size(); j++){
                if (alDateRecord.get(i).equals(alDateRecord.get(j))){
                    alDateRecord.remove(j);
                    j--;
                }
            }
        }

        return alDateRecord;
    }

    public void update(){
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int pointX = _lvRecordIdx.getLocation().getX() + sizeX/2;
        int pointY = _lvRecordIdx.getLocation().getY() + sizeY/2;
        tchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Diet wdFnL3Diet = new WDFnL3Diet();
        try{
            //呼叫方法 但此方法最後會跳出訊息確定 但在修改時不會跳出 因此拋出例外
            wdFnL3Diet.setValue("Dessert");
        }catch (Exception eNoFindElement){ }
    }




}
