package com.example.wghtest.Level3.StressMood;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3StressMood extends WDFnBaseMeasureMenuItems{
    protected AndroidElement _btnWellnessIndexes,_btnBreathingTraining;

    private static String __strWellnessIdxID = "tw.com.wgh3h:id/btnTabSixIndex";
    private static String __strBreathingTrainID = "tw.com.wgh3h:id/btnTabBreathingTraining";
    private static String __strBreathTrainRecordID = "tw.com.wgh3h:id/ll_calendar";

    public WDFnL3StressMood(){
        this._btnWellnessIndexes = (AndroidElement) adDriver.findElement(By.id(__strWellnessIdxID));
        this._btnBreathingTraining = (AndroidElement) adDriver.findElement(By.id(__strBreathingTrainID));
    }

    public void clickWellnessIndexes(){
        _btnWellnessIndexes.click();
    }

    public void clickBreathingTraining(){
        _btnBreathingTraining.click();
    }

    public void clickBreathTrainRecord(){
        adDriver.findElement(By.id(__strBreathTrainRecordID)).click();
    }


}

