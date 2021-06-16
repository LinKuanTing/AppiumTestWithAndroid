package com.example.wghtest.Level2;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.TouchActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnBaseInfoSetting {

    protected AndroidElement _btnSave;

    private static String __strSaveID = "tw.com.wgh3h:id/btnTitle";
    protected static String _strButton1ID = "android:id/button1";
    protected static String _strButton2ID = "android:id/button2";

    protected TouchAction touchAction = new TouchAction(adDriver);

    public WDFnBaseInfoSetting(){
        this._btnSave = (AndroidElement) adDriver.findElement(By.id(__strSaveID));
    }

    public void clickSave(){
        _btnSave.click();
    }
}
