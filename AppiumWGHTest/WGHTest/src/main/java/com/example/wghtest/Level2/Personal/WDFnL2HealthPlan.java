package com.example.wghtest.Level2.Personal;

import com.example.wghtest.Level2.WDFnBaseInfoSetting;

import org.openqa.selenium.By;

import java.time.Duration;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL2HealthPlan extends WDFnBaseInfoSetting {
    protected AndroidElement _nlHealthPlanInfo;
    protected AndroidElement _tvAge,_etHeight;
    protected AndroidElement _etInitialWeight,_etTargetWeight,_etInitialBodyFat,_etTargetBodtFat;
    protected AndroidElement _etReduceCal;
    protected AndroidElement _etBreakfastStart,_etBreakfastEnd,_etLunchStart,_etLunchEnd,_etDinnerStart,_etDinnerEnd;
    protected AndroidElement _etFastingStart,_etFastingEnd;

    int startX,startY,endX,endY;

    protected int _intAge,_intExerciseNo,_intReduceCal;
    protected double _dbHeight, _dbInitialWeight, _dbTargetWeight;
    protected String _strBreakfastStart,_strBreakfastEnd,_strLunchStart,_strLunchEnd,_strDinnerStart,_strDinnerEnd;
    protected String _strFastingStart,_strFastingEnd;

    private static String __strHealthPlanInfoID = "tw.com.wgh3h:id/linearLayout1";
    private static String __strAgeID = "tw.com.wgh3h:id/etAge";
    private static String __strHeightID = "tw.com.wgh3h:id/etHeight";
    private static String __strInitialWeightID = "tw.com.wgh3h:id/etInitWeight";
    private static String __strTargetWeightID = "tw.com.wgh3h:id/etTargetWeight";
    private static String __strInitialBodyFatID = "tw.com.wgh3h:id/etInitBodyFat";
    private static String __strTargetBodtFatID = "tw.com.wgh3h:id/etTargetBodyFat";

    private static String __strCalorieTitleID = "tw.com.wgh3h:id/relativeLayoutkal1";
    private static String __strDayExerciseLightID = "tw.com.wgh3h:id/bt_left";
    private static String __strDayExerciseNormalID = "tw.com.wgh3h:id/bt_center";
    private static String __strDayExerciseHeavyID = "tw.com.wgh3h:id/bt_right";
    private static String __strReduceCalID = "tw.com.wgh3h:id/etdayreducekal1";

    private static String __strMealTitleID = "tw.com.wgh3h:id/relativeLayout13";
    private static String __strBreakfastStartID = "tw.com.wgh3h:id/etBreakfastStart";
    private static String __strBreakfastEndID = "tw.com.wgh3h:id/etBreakfastEnd";
    private static String __strLunchStartID = "tw.com.wgh3h:id/etLunchStart";
    private static String __strLunchEndID = "tw.com.wgh3h:id/etLunchEnd";
    private static String __strDinnerStartID = "tw.com.wgh3h:id/etDinnerStart";
    private static String __strDinnerEndID = "tw.com.wgh3h:id/etDinnerEnd";
    private static String __strFastingStartID = "tw.com.wgh3h:id/etFastStart";
    private static String __strFastingEndID = "tw.com.wgh3h:id/etFastEnd";

    private String __strMsgENText = "Update Complete";
    private String __strMsgCHText = "完成更新";
    private String __strMsgID = "tw.com.wgh3h:id/tvMessage";

    public WDFnL2HealthPlan(){
        this._nlHealthPlanInfo = (AndroidElement) adDriver.findElement(By.id(__strHealthPlanInfoID));
        endX = _nlHealthPlanInfo.getLocation().getX()+_nlHealthPlanInfo.getSize().getWidth()/2;
        endY = _nlHealthPlanInfo.getLocation().getY();
    }

    public void setHealthPlan() throws InterruptedException {
        _intAge = getAge();
        _dbHeight = getHeight();
        _dbInitialWeight = getInitialWeight();
        _dbTargetWeight = getTargetWeight();
        startX = endX;
        startY = adDriver.findElement(By.id(__strCalorieTitleID)).getLocation().getY();
        touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endX,endY)).release().perform();
        Thread.sleep(2000);

        _intExerciseNo = (int) (Math.random()*3)+1;
        switch (_intExerciseNo){
            case 1:
                adDriver.findElement(By.id(__strDayExerciseLightID)).click();
                break;
            case 2:
                adDriver.findElement(By.id(__strDayExerciseNormalID)).click();
                break;
            case 3:
                adDriver.findElement(By.id(__strDayExerciseHeavyID)).click();
                break;
        }
        _intReduceCal = getReduceCal();
        startY = adDriver.findElement(By.id(__strMealTitleID)).getLocation().getY();
        touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endX,endY)).release().perform();

        setMealTime();

        clickSave();

    }

    public boolean checkSaveSuccessfullyMsg(){
        String strMsg = getMessage();
        adDriver.findElement(By.id(_strButton1ID)).click();

        if (strMsg.equals(__strMsgENText) || strMsg.equals(__strMsgCHText)){
            return true;
        }else {
            return false;
        }
    }

    public String getMessage(){
        return adDriver.findElementById(__strMsgID).getText();
    }

    public int getAge(){
        this._tvAge = (AndroidElement) adDriver.findElement(By.id(__strAgeID));
        return Integer.parseInt(_tvAge.getText());
    }

    public double getHeight(){
        this._etHeight = (AndroidElement) adDriver.findElement(By.id(__strHeightID));
        return Double.parseDouble(_etHeight.getText());
    }

    public double getInitialWeight(){
        this._etInitialWeight = (AndroidElement) adDriver.findElement(By.id(__strInitialWeightID));
        return Double.parseDouble(_etInitialWeight.getText());
    }

    public double getTargetWeight(){
        this._etTargetWeight = (AndroidElement) adDriver.findElement(By.id(__strTargetWeightID));
        return Double.parseDouble(_etTargetWeight.getText());
    }

    public int getReduceCal(){
        this._etReduceCal = (AndroidElement) adDriver.findElement(By.id(__strReduceCalID));
        return Integer.parseInt(_etReduceCal.getText());
    }


    public void setHeight(String strHeight){
        this._etHeight = (AndroidElement) adDriver.findElement(By.id(__strHeightID));
        _etHeight.clear();
        _etHeight.sendKeys(strHeight);
    }

    public void setMealTime(){
        _strBreakfastStart = adDriver.findElement(By.id(__strBreakfastStartID)).getText();
        _strBreakfastEnd = adDriver.findElement(By.id(__strBreakfastEndID)).getText();
        _strLunchStart = adDriver.findElement(By.id(__strLunchStartID)).getText();
        _strLunchEnd = adDriver.findElement(By.id(__strLunchEndID)).getText();
        _strDinnerStart = adDriver.findElement(By.id(__strDinnerStartID)).getText();
        _strDinnerEnd = adDriver.findElement(By.id(__strDinnerEndID)).getText();
        _strFastingStart = adDriver.findElement(By.id(__strFastingStartID)).getText();
        _strFastingEnd = adDriver.findElement(By.id(__strFastingEndID)).getText();
    }

}
