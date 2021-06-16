package com.example.wghtest.Level3.Sport;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3SportRecord {

    protected AndroidElement _btnToday;
    protected AndroidElement _rlRecordDate;
    protected AndroidElement _lvRecordIdx;
    protected AndroidElement _btnPreviousYear, _lvMonthYears;
    protected AndroidElement _tvSportItem, _tvDuration, _tvCalorie;

    private static String __strTodayID = "tw.com.wgh3h:id/btnTitle";
    private static String __strRecordDate;
    private static String __strRecordIdx = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.GridView";
    private static String __strPreviousID = "tw.com.wgh3h:id/ivPrevious";
    private static String __strMonthYearID = "tw.com.wgh3h:id/title";
    private static String __strSportItemXpath = "/android.widget.RelativeLayout/android.widget.TextView[1]";
    private static String __strDurationXpath = "/android.widget.RelativeLayout/android.widget.TextView[2]";
    private static String __strCalorieXpath = "/android.widget.RelativeLayout/android.widget.TextView[3]";


    TouchAction touchAction = new TouchAction(adDriver);


    public void clickToday() {
        this._btnToday = (AndroidElement) adDriver.findElement(By.id(__strTodayID));
        _btnToday.click();
    }

    public void clickPreviousYear(){
        this._btnPreviousYear = (AndroidElement) adDriver.findElement(By.id(__strPreviousID));
        _btnPreviousYear.click();
    }

    public String getTitleMonthYear(){
        this._lvMonthYears = (AndroidElement) adDriver.findElement(By.id(__strMonthYearID));
        return _lvMonthYears.getText();
    }

    public void clickDate(String MonthYear,String Date){
        while (true){
            if (MonthYear.equals(getTitleMonthYear())){ break; }
            else{ clickPreviousYear(); }
        }
        Date = String.valueOf(Integer.parseInt(Date));

        this.__strRecordDate = "//*[@text=\""+Date+"\"]";
        this._rlRecordDate = (AndroidElement) adDriver.findElement(By.xpath(__strRecordDate));
        _rlRecordDate.click();
    }

    //取得日期當日裡所有紀錄
    public ArrayList getDateRecord() {

        try{
            this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[1]/android.widget.RelativeLayout"));
        }catch (Exception eNoFindElement){
            //當日日期沒有運動紀錄
            return null;
        }
        int sizeX = _lvRecordIdx.getSize().getWidth();
        int sizeY = _lvRecordIdx.getSize().getHeight();
        int startX = _lvRecordIdx.getLocation().getX() + sizeX / 2;
        int startY = _lvRecordIdx.getLocation().getY() + sizeY * 4;
        int endX = startX;
        int endY = _lvRecordIdx.getLocation().getY();

        //取記錄日期裡的資料
        ArrayList<String> strRecordContent = new ArrayList<>();
        boolean storeFistRecord = false;
        String strRecord = "";
        A:while (true) {
            try {
                if (!storeFistRecord) {
                    for (int i = 1; i <= 4; ++i) {
                        this._tvSportItem = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strSportItemXpath));
                        this._tvDuration = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strDurationXpath));
                        this._tvCalorie = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strCalorieXpath));
                        strRecord = _tvSportItem.getText() + "/" + _tvDuration.getText() + "/" + _tvCalorie.getText().replace("(", "").replace(")", "");
                        strRecordContent.add(strRecord);
                    }
                    storeFistRecord = true;
                }
                else if (storeFistRecord) {
                    for (int i = 1; i <= 4; ++i) {
                        this._tvSportItem = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strSportItemXpath));
                        this._tvDuration = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strDurationXpath));
                        this._tvCalorie = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[" + i + "]" + __strCalorieXpath));
                        strRecord = _tvSportItem.getText() + "/" + _tvDuration.getText() + "/" + _tvCalorie.getText().replace("(", "").replace(")", "");
                        int size = strRecordContent.size();
                        boolean isExist = false;
                        //判斷記錄裡是否重複紀錄
                        for (int j = 0; j < strRecordContent.size(); j++) {
                            if (strRecord.equals(strRecordContent.get(j))){
                                isExist = true;
                                //break ;
                            }
                            if (!isExist && j==strRecordContent.size()-1){
                                strRecordContent.add(strRecord);
                            }
                        }

                        //判斷List大小有無改變  改變->表示上有資料 不改變->資料以重複不變(資料華到底部)
                        if (size == strRecordContent.size() && i==4){
                            break A;
                        }
                    }
                }
                touchAction.press(PointOption.point(startX, startY - 10)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX, endY)).release().perform();
                while (true) {
                    if ((_lvRecordIdx.getSize().getHeight())<(sizeY/2)){
                        touchAction.press(PointOption.point(endX, endY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX, endY - 40)).release().perform();
                    }
                    else{
                        break ;
                    }
                }
            } catch (Exception eNoFindElement) {
                //若紀錄資料不到4筆
                break A;
            }
        }

        return strRecordContent;

    }

    //檢查新增當天資料是否上傳到紀錄裡
    public boolean findRecord(ArrayList alAddRecord,ArrayList alRecord){
        int sameNum = 0;
        for (int i=0; i<alAddRecord.size(); i++){
            for (int j=0; j<alRecord.size(); j++){
                String str = alRecord.get(j).toString().replace("消耗","");
                alRecord.set(j,str);
                if(alAddRecord.get(i).equals(alRecord.get(j))){
                    sameNum++;
                }
            }
        }
        if (sameNum == alAddRecord.size()){
            return true;
        }
        else{
            return false;
        }
    }

    //檢查之前資料是否正確出現在日期紀錄裡
    public boolean findRecord(ArrayList alReadData){
        int sameNum = 0;

        String strRecordDate = "";
        ArrayList alDateRecord = new ArrayList();
        for (int i=0; i<alReadData.size(); i++){
            String[] strTxtData = alReadData.get(i).toString().split("/");
            String strMonthYear = strTxtData[0];
            String strDate = strTxtData[1];
            String strSportItem = strTxtData[2];

            //如果比較的紀錄為同一天 就不必重新抓當天所有紀錄
            if (!strRecordDate.equals(strDate + "/" + strDate)){
                strRecordDate = strDate + "/" + strDate;
                clickDate(strMonthYear,strDate);
                alDateRecord = getDateRecord();
            }

            for (int j=0; j<alDateRecord.size(); j++){
                if (alDateRecord.get(j).toString().indexOf(strSportItem)!=-1){
                    sameNum++;
                    break;
                }
            }
        }
        if (sameNum == alReadData.size()){
            return true;
        }
        else {
            return false;
        }
    }

    public void update() throws InterruptedException {
        this._lvRecordIdx = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[1]"));
        this._tvSportItem = (AndroidElement) adDriver.findElement(By.xpath(__strRecordIdx + "/android.widget.RelativeLayout[1]" + __strSportItemXpath));
        String strSportItem = _tvSportItem.getText();
        int pointX = _lvRecordIdx.getLocation().getX() + _lvRecordIdx.getSize().getWidth() / 2;
        int pointY = _lvRecordIdx.getLocation().getY() + _lvRecordIdx.getSize().getHeight() / 2;
        touchAction.tap(PointOption.point(pointX,pointY)).release().perform();
        WDFnL3Sport wdFnL3Sport = new WDFnL3Sport();


        if (strSportItem.equals("Pedometer")){
            wdFnL3Sport.setPedometerValue();
        }
        else{
            wdFnL3Sport.setValue();
        }
    }

    public int getWeeklySportTime() throws IOException {
        String strLocal = Locale.getDefault().getLanguage();
        //日期處理
        //格式固定
        SimpleDateFormat dateFormat;
        if (strLocal.equals("us")){
            dateFormat = new SimpleDateFormat("yyyy/MMMM/dd", Locale.US);
        }
        else if(strLocal.equals("zh")){
            dateFormat = new SimpleDateFormat("yyyy/M月/dd", Locale.TAIWAN);
        }
        else {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        }

        //抓取今天日期
        Calendar cal = Calendar.getInstance();
        Date TodayDate = cal.getTime();
        //抓取星期 0~6
        int week = Integer.parseInt(new SimpleDateFormat("u").format(TodayDate)); //u 為抓取星期
        //推算當週 日~六 日期
        ArrayList alWeekDate = new ArrayList();
        cal.add(Calendar.DATE,-week);
        for (int i=0; i<7; i++){
            cal.add(Calendar.DATE,+i);
            alWeekDate.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,-i);
        }
        //將 Year/Month/Date 轉成 Month Year/Date 方便查詢
        for (int i=0; i<alWeekDate.size(); i++){
            String[] strWeekDate = alWeekDate.get(i).toString().split("/");
            if (strWeekDate[2].charAt(0)=='0'){
                strWeekDate[2] = strWeekDate[2].replaceAll("0","");
            }
            //[0]=year [1]=month [2]=date
            alWeekDate.set(i,strWeekDate[1]+" "+strWeekDate[0]+"/"+strWeekDate[2]);
        }


        //尋找一周內的運動紀錄
        ArrayList alWeekRecord = new ArrayList();
        for (int i=week; i>=0; i--){
            String[] strDate = alWeekDate.get(i).toString().split("/");
            //[0]=MonthYear [1]=Date
            clickDate(strDate[0],strDate[1]);
            ArrayList al = getDateRecord();
            if (al != null){
                alWeekRecord.addAll(al);
                //System.out.println(alWeekRecord);
            }

        }


        //計算運動時間
        int weeklyExercise = 0;
        for (int i=0; i<alWeekRecord.size(); i++){
            String strWeekRecord[] = alWeekRecord.get(i).toString().split("/");
            //運動紀錄為計步器跳過
            if (strWeekRecord[1].contains("steps") || strWeekRecord[1].contains("步")){
                continue;
            }

            if (strLocal.equals("us")){
                //處理時間 X hour Y minute
                if (strWeekRecord[1].contains("minute")){
                    String strExerciseTime[] = strWeekRecord[1].replaceAll("hour","/").replaceAll("minute","").split("/");
                    weeklyExercise += (Integer.parseInt(strExerciseTime[0])*60 + Integer.parseInt(strExerciseTime[1]));
                }
                //處理時間 X hour
                else{
                    String strExerciseTime = strWeekRecord[1].replaceAll("hour","");
                    weeklyExercise += (Integer.parseInt(strExerciseTime)*60 );
                }
            }else if (strLocal.equals("zh")){
                //處理時間 X 小時 Y 分鐘
                if (strWeekRecord[1].contains("分鐘")){
                    String strExerciseTime[] = strWeekRecord[1].replaceAll("小時","/").replaceAll("分鐘","").split("/");
                    weeklyExercise += (Integer.parseInt(strExerciseTime[0])*60 + Integer.parseInt(strExerciseTime[1]));
                }
                //處理時間 X 小時
                else{
                    String strExerciseTime = strWeekRecord[1].replaceAll("小時","");
                    weeklyExercise += (Integer.parseInt(strExerciseTime)*60 );
                }
            }


        }
        //System.out.println(weeklyExercise);
        return weeklyExercise;

    }

}