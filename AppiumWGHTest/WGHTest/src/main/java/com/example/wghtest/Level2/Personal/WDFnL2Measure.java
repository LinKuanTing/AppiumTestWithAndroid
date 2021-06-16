package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import org.openqa.selenium.By;

import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.appSizeX;
import static com.example.wghtest.WGHTestBase.appSizeY;

public class WDFnL2Measure extends WDFnBaseInfoSetting {
    int intBGUnits;
    protected AndroidElement _BPmeasurements, _BGmeasurements;

    private static String __strBPmsmID = "tw.com.wgh3h:id/editText_bp";
    private static String __strBGmsmID = "tw.com.wgh3h:id/editText_bg";
    private static String __strUnitMgdLID = "tw.com.wgh3h:id/btn_glu_mgdl";
    private static String __strUnitMmolLID = "tw.com.wgh3h:id/btn_glu_mmol";

    private String __strMsgENText = "Update Complete";
    private String __strMsgCHText = "完成更新";
    private String __strMsgID = "tw.com.wgh3h:id/tvMessage";

    TouchAction touchAction = new  TouchAction(adDriver);

    public void setMeasurements(){
        this._BPmeasurements = (AndroidElement) adDriver.findElement(By.id(__strBPmsmID));
        this._BGmeasurements = (AndroidElement) adDriver.findElement(By.id(__strBGmsmID));
        String times = String.valueOf((int) (Math.random()*30+1));
        _BPmeasurements.clear();
        _BPmeasurements.sendKeys(times);
        _BGmeasurements.clear();
        _BGmeasurements.sendKeys(times);
        clickSave();
    }

    public int setBGUnits(){

        touchAction.press(PointOption.point(appSizeX/2,appSizeY/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(appSizeX/2,appSizeY/8)).release().perform();

        int BGUnits = (int) (Math.random()*1);
        if (BGUnits == 0){
            adDriver.findElementById(__strUnitMgdLID).click();
        }else {
            adDriver.findElementById(__strUnitMmolLID).click();
        }

        clickSave();
        return BGUnits;

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

    public int getBPMeasurements(){
        return Integer.parseInt(_BPmeasurements.getText());
    }

    public int getBGMeasurements(){
        return Integer.parseInt(_BGmeasurements.getText());
    }


    public int getBGUnits(){
        return intBGUnits;
    }

    public void clickMgdL(){
        adDriver.findElementById(__strUnitMgdLID).click();
        clickSave();
        adDriver.findElement(By.id(_strButton1ID)).click();
    }
}
