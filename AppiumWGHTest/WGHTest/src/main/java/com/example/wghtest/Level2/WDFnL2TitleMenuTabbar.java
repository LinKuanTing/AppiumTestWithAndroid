package com.example.wghtest.Level2;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class WDFnL2TitleMenuTabbar {
    protected AndroidElement _btnTitlePersonal,_btnTitleQRcode;
    protected AndroidElement _btnMenuWeight,_btnMenuSport,_btnMenuDiet;
    protected AndroidElement _btnMenuBloodPressure,_btnMenuBloodGlucose,_btnMenuSmartWristband;
    protected AndroidElement _btnMenuThermometer,_btnMenuStressMood,_btnMenuAdvice;
    protected AndroidElement _btnMenuShop,_btnMenuMyOuendan,_btnMenuProgress;
    protected AndroidElement _btnTabbarProgress,_btnTabbarPoints,_btnTabbarShop,_btnTabbarMenu;


    private static String __strTitlePersonalID = "tw.com.wgh3h:id/button_left";
    private static String __strTitleQRcodeID = "tw.com.wgh3h:id/btnTitle";

    private static String __strMenuWeightID = "tw.com.wgh3h:id/button2";
    private static String __strMenuSportID = "tw.com.wgh3h:id/button3";
    private static String __strMenuDietID = "tw.com.wgh3h:id/button4";
    private static String __strMenuBloodPressureID = "tw.com.wgh3h:id/buttonbp";
    private static String __strMenuBloodGlucoseID = "tw.com.wgh3h:id/buttonglu";
    private static String __strMenuSmartWristbandID = "tw.com.wgh3h:id/button6";
    private static String __strMenuThermometerID = "tw.com.wgh3h:id/button8";
    private static String __strMenuStressMoodID = "tw.com.wgh3h:id/button1";
    private static String __strMenuAdviceID = "tw.com.wgh3h:id/button12";
    private static String __strMenuShopID = "tw.com.wgh3h:id/button11";
    private static String __strMenuMyOuendanID = "tw.com.wgh3h:id/button10";
    private static String __strMenuOximeterID = "tw.com.wgh3h:id/button9";

    private static String __strTabbarProgressID = "tw.com.wgh3h:id/tab_me";
    private static String __strTabbarPointsID = "tw.com.wgh3h:id/tab_points";
    private static String __strTabbarShopID = "tw.com.wgh3h:id/tab_shop";
    private static String __strTabbarMenuID = "tw.com.wgh3h:id/tab_menu";


    public void clickTitlePersonal(){
        this._btnTitlePersonal = (AndroidElement) adDriver.findElement(By.id(__strTitlePersonalID));
        _btnTitlePersonal.click();
    }

    public void clickTitleQRcode(){
        this._btnTitleQRcode = (AndroidElement) adDriver .findElement(By.id(__strTitleQRcodeID));
        _btnTitleQRcode.click();
    }

    public void clickWeight(){
        this._btnMenuWeight = (AndroidElement) adDriver.findElement(By.id(__strMenuWeightID));
        _btnMenuWeight.click();
    }

    public void clickSport(){
        this._btnMenuSport = (AndroidElement) adDriver.findElement(By.id(__strMenuSportID));
        _btnMenuSport.click();
    }

    public void clickDiet(){
        this._btnMenuDiet = (AndroidElement) adDriver.findElement(By.id(__strMenuDietID));
        _btnMenuDiet.click();
    }

    public void clickBloodPressure(){
        this._btnMenuBloodPressure = (AndroidElement) adDriver.findElement(By.id(__strMenuBloodPressureID));
        _btnMenuBloodPressure.click();
    }

    public void clickBloodGlucose(){
        this._btnMenuBloodGlucose = (AndroidElement) adDriver.findElement(By.id(__strMenuBloodGlucoseID));
        _btnMenuBloodGlucose.click();
    }

    public void clickThermometer(){
        this._btnMenuThermometer = (AndroidElement) adDriver.findElement(By.id(__strMenuThermometerID));
        _btnMenuThermometer.click();
    }

    public void clickStressMood(){
        this._btnMenuStressMood = (AndroidElement) adDriver.findElement(By.id(__strMenuStressMoodID));
        _btnMenuStressMood.click();
    }

    public void clickAdvice(){
        this._btnMenuAdvice = (AndroidElement) adDriver.findElement(By.id(__strMenuAdviceID));
        _btnMenuAdvice.click();
    }

    public void clickShop(){
        this._btnMenuShop = (AndroidElement) adDriver.findElement(By.id(__strMenuShopID));
        _btnMenuShop.click();
    }

    public void clickOuendan(){
        this._btnMenuMyOuendan = (AndroidElement) adDriver.findElement(By.id(__strMenuMyOuendanID));
        _btnMenuMyOuendan.click();
    }

    public void clickProgress(){
        this._btnMenuProgress = (AndroidElement) adDriver.findElement(By.id(__strTabbarProgressID));
        _btnMenuProgress.click();
    }


    public void clickTabbarProgress(){
        this._btnTabbarProgress = (AndroidElement) adDriver.findElement(By.id(__strTabbarProgressID));
        _btnTabbarProgress.click();
    }

    public void clickTarbarPoints(){
        this._btnTabbarPoints = (AndroidElement) adDriver.findElement(By.id(__strTabbarPointsID));
        _btnTabbarPoints.click();
    }

    public void clickTarbarShop(){
        this._btnTabbarShop = (AndroidElement) adDriver.findElement(By.id(__strTabbarShopID));
        _btnTabbarShop.click();
    }

    public void clickTabbarMenu(){
        this._btnTabbarMenu = (AndroidElement) adDriver.findElement(By.id(__strTabbarMenuID));
        _btnTabbarMenu.click();
        try{
            sleep(3000);
        }catch (Exception e){

        }

    }

}
