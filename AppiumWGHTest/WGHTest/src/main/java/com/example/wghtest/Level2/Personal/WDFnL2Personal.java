package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL2Personal extends WDFnBaseInfoSetting {
    protected AndroidElement _btnPersonal,_btnHealthPlan,_btnMeasure;

    protected int _intGender;

    private String __strPersonalID = "tw.com.wgh3h:id/bt_profile";
    private String __strHealthPlanID = "tw.com.wgh3h:id/bt_plan";
    private String __strMeasureID = "tw.com.wgh3h:id/bt_measure";

    private String __strFemaleID = "tw.com.wgh3h:id/ivFemale";
    private String __strMaleID = "tw.com.wgh3h:id/ivMale";

    private String __strMsgENText = "Update Complete";
    private String __strMsgCHText = "完成更新";
    private String __strMsgID = "tw.com.wgh3h:id/tvMessage";


    public WDFnL2Personal(){

        this._btnPersonal = (AndroidElement) adDriver.findElement(By.id(__strPersonalID));
        this._btnHealthPlan = (AndroidElement) adDriver.findElement(By.id(__strHealthPlanID));
        this._btnMeasure = (AndroidElement) adDriver.findElement(By.id(__strMeasureID));
    }

    public void setPersonalInfo(){
        getGender();
        clickSave();
    }

    public boolean checkSaveSuccessfullyMsg(){
        String strMsg = adDriver.findElementById(__strMsgID).getText();
        adDriver.findElement(By.id(_strButton1ID)).click();

        if (strMsg.equals(__strMsgENText) || strMsg.equals(__strMsgCHText)){
            return true;
        }else {
            return false;
        }

    }


    public int getGender(){
        _intGender = (int) (Math.random()*2);
        if(_intGender == 0){
            adDriver.findElement(By.id(__strFemaleID)).click();
        }
        else {
            adDriver.findElement(By.id(__strMaleID)).click();
        }
        return _intGender;
    }

    public void clickPersonal(){
        _btnPersonal.click();
    }

    public void clickHealthPlan(){
        _btnHealthPlan.click();
    }

    public void clickMeasure(){
        _btnMeasure.click();
    }

}
