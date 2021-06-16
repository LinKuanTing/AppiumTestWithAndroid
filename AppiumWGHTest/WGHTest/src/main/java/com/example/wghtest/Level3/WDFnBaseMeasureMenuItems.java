package com.example.wghtest.Level3;

import com.example.wghtest.Level1.Login.TestCaseNormalLogin;
import com.example.wghtest.Level1.Login.WDFnL1Login;
import com.example.wghtest.Level2.Logout.WDFnL2Logout;
import com.example.wghtest.Level2.Personal.WDFnL2Personal;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;

import java.io.IOException;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class WDFnBaseMeasureMenuItems {
    protected AndroidElement _btnRecord,_btnChart,_btnShare,_btnSave,_btnBluetooth;
    protected AndroidElement _tvDataMean,_tvTime;

    protected AndroidElement _lvDays,_lvHours,_lvMinutes;
    protected AndroidElement _btnCancel; //share's

    protected static String _strRecordID = "tw.com.wgh3h:id/btn_list";
    protected static String _strChartID = "tw.com.wgh3h:id/btn_chart";
    protected static String _strShareID = "tw.com.wgh3h:id/btn_share";
    protected static String _strSaveID = "tw.com.wgh3h:id/btnTitle";
    protected static String _strDataMeanID = "tw.com.wgh3h:id/tvNote";

    protected static String _strDateTimeID;
    protected static String _strDaysID = "tw.com.wgh3h:id/day";
    protected static String _strHoursID = "tw.com.wgh3h:id/hour";
    protected static String _strMinutesID = "tw.com.wgh3h:id/mins";

    protected static String _strButton1ID = "android:id/button1";
    protected static String _strButton2ID = "android:id/button2";
    protected static String _strCancelID = "tw.com.wgh3h:id/btn_cancel";

    //click ble will show search device message
    protected static String _strProcessID = "tw.com.wgh3h:id/tvProcess";
    protected static String _strBleCancelID = "tw.com.wgh3h:id/btnConfirm";
    protected static String _strMessageID = "tw.com.wgh3h:id/tvMessage";



    public TouchAction touchAction = new TouchAction(adDriver);

    protected int _timeSleep;

    public WDFnBaseMeasureMenuItems(){
        this._timeSleep = 2000;
    }

    public void setTime() throws InterruptedException {
        _tvTime = (AndroidElement) adDriver.findElement(By.id(_strDateTimeID));
        _tvTime.click();
        _lvDays = (AndroidElement) adDriver.findElement(By.id(_strDaysID));
        _lvHours = (AndroidElement) adDriver.findElement(By.id(_strHoursID));
        _lvMinutes = (AndroidElement) adDriver.findElement(By.id(_strMinutesID));
        int dayX =_lvDays.getLocation().getX()+_lvDays.getSize().getWidth()/2;
        int dayY =_lvDays.getLocation().getY()+_lvDays.getSize().getHeight()/2;
        int hourX =_lvHours.getLocation().getX()+_lvHours.getSize().getWidth()/2;
        int hourY =_lvHours.getLocation().getY()+_lvHours.getSize().getHeight()/2;
        int minX =_lvMinutes.getLocation().getX()+_lvMinutes.getSize().getWidth()/2;
        int minY =_lvMinutes.getLocation().getY()+_lvMinutes.getSize().getHeight()/2;
        touchAction.press(PointOption.point(dayX,dayY)).waitAction().moveTo(PointOption.point(dayX, (int) (dayY+Math.random()*300))).release().perform();
        sleep(_timeSleep);
        touchAction.press(PointOption.point(hourX,hourY)).waitAction().moveTo(PointOption.point(hourX, (int) (hourY+Math.random()*300))).release().perform();
        sleep(_timeSleep);
        touchAction.press(PointOption.point(minX,minY)).waitAction().moveTo(PointOption.point(minX, (int) (minY+Math.random()*300))).release().perform();
        sleep(_timeSleep);
        //點取消  因為只判斷滑動是否正常
        adDriver.findElement(By.id(_strButton2ID)).click();
    }

    public String getDate(){
        _tvTime = (AndroidElement) adDriver.findElement(By.id(_strDateTimeID));
        return  _tvTime.getText();
    }

    public void setValue() throws InterruptedException {}

    public void clickRecord(){
        this._btnRecord = (AndroidElement) adDriver.findElement(By.id(_strRecordID));
        _btnRecord.click();
    }

    public void clickChart(){
        this._btnChart = (AndroidElement) adDriver.findElement(By.id(_strChartID));
        _btnChart.click();
    }

    public void clickShare(){
        this._btnShare = (AndroidElement) adDriver.findElement(By.id(_strShareID));
        _btnShare.click();
        _btnCancel = (AndroidElement) adDriver.findElement(By.id(_strCancelID));
        _btnCancel.click();
    }

    public void clickSave(){
        this._btnSave = (AndroidElement) adDriver.findElement(By.id(_strSaveID));
        _btnSave.click();
    }

    public void clickDataMean(){
        this._tvDataMean = (AndroidElement) adDriver.findElement(By.id(_strDataMeanID));
        _tvDataMean.click();
    }

    public void clickBLE(String strBtnBleID){
        this._btnBluetooth = (AndroidElement) adDriver.findElement(By.id(strBtnBleID));
        _btnBluetooth.click();
    }

    public void LoadRecord(){}

    public void AddRecord(){}

    public void UpdateRecord(){}

    public void CompareRecord() throws Exception {}

    public void CheckDataMean() throws InterruptedException, IOException {}

    //切換帳號再切回原來帳號 為了刪除Local DB
    public void DeleteLocalDataBase() throws Exception {

        //登出切換帳號
        WDFnL2Logout wdFnL2Logout = new WDFnL2Logout();
        wdFnL2Logout.logout();

        WDFnL1Login wdFnL1Login = new WDFnL1Login();
        wdFnL1Login.login("gshgshtest831","1234567");
        try {
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){
            System.out.println("No display 'login different account' message");
        }
        wdFnL2Logout.logout();
        new TestCaseNormalLogin().login();

        //進入個人設定 重新下載個人設定的資料
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();
        WDFnL2Personal wdFnL2Personal = new WDFnL2Personal();
        wdFnL2Personal.clickHealthPlan();
        wdFnL2Personal.clickMeasure();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

    }
}
