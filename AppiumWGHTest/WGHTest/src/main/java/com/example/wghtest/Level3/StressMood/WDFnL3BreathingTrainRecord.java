package com.example.wghtest.Level3.StressMood;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3BreathingTrainRecord {
    protected AndroidElement _btnToday;
    protected AndroidElement _rlRecordDate;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _btnPreviousYear, _lvYearMonth;

    protected AndroidElement _tvTime,_tvLevel;

    private static String __strTodayID = "tw.com.wgh3h:id/action_btn_right";
    private static String __strPreviousID = "tw.com.wgh3h:id/llPrevious";
    ////*[@resource-id=\"tw.com.wgh3h:id/textView_date\"]
    private static String __strYearMonthXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.TextView";
    private static String __strRecordDate;
    private static String __strRecordIdx = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.GridView";
    private static String __strTimeXpath = "/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]";
    private static String __strLevelXpath = "/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]";

    TouchAction touchAction = new TouchAction(adDriver);

    public WDFnL3BreathingTrainRecord(){ }

    public void clickToday(){
        this._btnToday = (AndroidElement) adDriver.findElement(By.id(__strTodayID));
        _btnToday.click();
    }

    public void clickPreviousYear(){
        this._btnPreviousYear = (AndroidElement) adDriver.findElement(By.id(__strPreviousID));
        _btnPreviousYear.click();
    }

    public String getTitleYearMonth(){
        this._lvYearMonth = (AndroidElement) adDriver.findElement(By.xpath(__strYearMonthXpath));
        return _lvYearMonth.getText();
    }

    public void clickDate(String YearMonth,String Date){
        while (true){
            if (YearMonth.equals(getTitleYearMonth())){ break; }
            else{ clickPreviousYear(); }
        }
        this.__strRecordDate = "//*[@text=\""+Date+"\"]";
        this._rlRecordDate = (AndroidElement) adDriver.findElement(By.xpath(__strRecordDate));
        _rlRecordDate.click();
    }

    public ArrayList getDateRecord(){
        try{
            this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[1]"));
        }catch (Exception eNoFindElement){
            //??????????????????????????????
            return null;
        }

        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int startX = _lvRecordIdx.getLocation().getX() + sizeX / 2;
        int startY = _lvRecordIdx.getLocation().getY() + sizeY * 2;
        int endX = startX;
        int endY = _lvRecordIdx.getLocation().getY();

        //???????????????????????????
        ArrayList<String> alRecordContent = new ArrayList<>();
        boolean storeFistRecord = false;
        String strRecord = "";

        A:while (true) {
            try {
                if (!storeFistRecord){
                    for (int i=1; i<=3; i++){
                        this._tvTime = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout["+i+"]" + __strTimeXpath));
                        this._tvLevel = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout["+i+"]" + __strLevelXpath));
                        strRecord = _tvTime.getText() + "/" + _tvLevel.getText();
                        if (!(strRecord.contains("Total Time") || strRecord.contains("HR Value"))){
                            alRecordContent.add(strRecord);
                        }

                    }
                    storeFistRecord = true;
                }
                else if (storeFistRecord){
                    int size = alRecordContent.size();
                    for (int i=2; i<=3; i++){
                        this._tvTime = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout["+i+"]" + __strTimeXpath));
                        this._tvLevel = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout["+i+"]" + __strLevelXpath));
                        strRecord = _tvTime.getText() + "/" + _tvLevel.getText();
                        //?????????????????????????????????????????????????????????(???????????????)
                        if (!(strRecord.contains("Time")&&strRecord.contains("Level"))){
                            continue ;
                        }

                        boolean isExist = false;
                        for (int j=0; j<alRecordContent.size(); j++){
                            if (strRecord.equals(alRecordContent.get(j))){
                                isExist = true;
                            }
                            if (!isExist && j==alRecordContent.size()-1){
                                alRecordContent.add(strRecord);
                            }
                        }

                        //??????List??????????????????  ??????->?????????????????? ?????????->?????????????????????(??????????????????)
                        if (size == alRecordContent.size() && i==2){
                            break A;
                        }
                    }
                }
                touchAction.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX, endY)).release().perform();

                String strPageSource = adDriver.getPageSource();
                while (true) {
                    if ((_lvRecordIdx.getSize().getHeight())<(sizeY/2)){
                        touchAction.press(PointOption.point(endX, endY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX, endY - 40)).release().perform();
                        if (strPageSource.equals(adDriver.getPageSource())){
                            break;
                        }
                    }
                    else{
                        break;
                    }
                }

            }catch (Exception eNoFindElement){
                //????????????2??????
                break A;
            }
        }

        return alRecordContent;
    }
}
