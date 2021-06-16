package com.example.wghtest.getPicOfUI;

import com.example.wghtest.Level1.Login.WDFnL1Login;
import com.example.wghtest.Level3.FnPicEvent;
import com.example.wghtest.Level3.Weight.WDFnL3Weight;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.time.Duration;
import java.util.List;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.Level1.Register.WDFnL1Register.height;
import static com.example.wghtest.Level1.Register.WDFnL1Register.width;


public class TestCaseUIScreenShot {
    Logger loger = LoggerFactory.getLogger(FnPicEvent.class);
    TouchAction touchAction = new TouchAction(adDriver);
    static int idx = 0;


    public void screenShot() throws InterruptedException {
        File file = new File("D:\\TestData\\TestUI");
        if(!file.exists()){
            file.mkdir();
        }
        File PicFile = new File("D:\\TestData\\TestUI\\UI"+ idx + ".png");
        Thread.sleep(3000);
        File screenshot = adDriver.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(screenshot, PicFile);
        } catch (Exception eFileNoFind) {
            loger.warn("Reason: Can't find the picture path.");
        }
        idx ++;
    }


    public void ScreenshotUIpage() throws InterruptedException {
        //main page
        screenShot();

        //***forget password page***
        try {
            adDriver.findElement(By.id("tw.com.wgh3h:id/tvGetAcdAndPwd")).click();
            screenShot();
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        } catch (Exception eElementNoFind){ }


        //***register page***
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvRegister")).click();
        screenShot();
        touchAction.press(PointOption.point(width / 2, height * 4 / 5)).waitAction().moveTo(PointOption.point(width / 2, height / 6)).release().perform();
        screenShot();
        //take picture option
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvSetAvatar")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button2")).click();
        //user terms
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvServiceRule")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //register wrong message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //***login message***
        new WDFnL1Login().login("teee831","1234567");
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        new WDFnL1Login().login("GSHTing831","12345678");
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        new WDFnL1Login().login("GSHTing831","1234567");
        screenShot();
        //use different account message
        try {
            adDriver.findElement(By.id("android:id/button1")).click();
        }catch (Exception eElementNoFind){}

        //***personal setting page***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button_left")).click();
        screenShot();
        touchAction.press(PointOption.point(width / 2, height * 4 / 5)).waitAction().moveTo(PointOption.point(width / 2, height / 6)).release().perform();
        screenShot();

        //***check Save message***
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();

        //***health plan page***
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_plan")).click();
        screenShot();
        int startX = width / 2;
        int startY = adDriver.findElement(By.id("tw.com.wgh3h:id/linearLayout1")).getLocation().getY()-1 + adDriver.findElement(By.id("tw.com.wgh3h:id/linearLayout1")).getSize().getHeight();
        int endX = startX;
        int endY = startY - adDriver.findElement(By.id("tw.com.wgh3h:id/linearLayout1")).getSize().getHeight();
        adDriver.findElement(By.id("tw.com.wgh3h:id/ivinfokal1")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        adDriver.findElement(By.id("tw.com.wgh3h:id/ivinfokal2")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        touchAction.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX, endY)).release().perform();
        screenShot();


        //***measure page***
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_measure")).click();
        screenShot();
        //check save message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();

        //check health plan save message
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_plan")).click();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        try{
            adDriver.findElement(By.id("android:id/button3")).click();
        }catch (Exception eElementNoFind){}
        adDriver.findElement(By.id("android:id/button1")).click();

        //***logout message***
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button2")).click();

        //***weight manager***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button2")).click();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        //check date option
        adDriver.findElement(By.id("tw.com.wgh3h:id/tv_wgtAddHeader")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //check warning message of bodyfat and weight
        String msgBMI = adDriver.findElement(By.id("tw.com.wgh3h:id/tx_wgtWarningMessage")).getText();
        String msgBodyfat = adDriver.findElement(By.id("tw.com.wgh3h:id/tx_bodyFatWarningMessage")).getText();
        WDFnL3Weight wdFnL3Weight = new WDFnL3Weight();
        while (true){
            if (adDriver.findElement(By.id("tw.com.wgh3h:id/tx_wgtWarningMessage")).getText().equals(msgBMI)){
                wdFnL3Weight.weightSwipeLeft();
            }
            else{
                screenShot();
                break;
            }
        }
        while (true){
            if (adDriver.findElement(By.id("tw.com.wgh3h:id/tx_bodyFatWarningMessage")).getText().equals(msgBodyfat)){
                wdFnL3Weight.bodyfatSwipeLeft();
            }
            else{
                screenShot();
                break;
            }
        }
        touchAction.press(PointOption.point(width / 2, height * 4 / 5)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).moveTo(PointOption.point(width / 2, height / 5)).release().perform();
        screenShot();
        touchAction.press(PointOption.point(width / 2, height * 4 / 5)).waitAction().moveTo(PointOption.point(width / 2, height / 5)).release().perform();
        screenShot();

        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_list")).click();
        Thread.sleep(5000);
        touchAction.press(PointOption.point(width / 2, height / 6)).waitAction().moveTo(PointOption.point(width / 2, height * 5 / 6)).release().perform();
        Thread.sleep(5000);
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //chart
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        List<AndroidElement> listWeightChart = adDriver.findElementsByClassName("android.widget.Button");
        for (int i=0; i<listWeightChart.size(); ++i){
            listWeightChart.get(i).click();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btnWeekMode")).click();
            screenShot();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btnDoubleWeekMode")).click();
            screenShot();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btnMonthMode")).click();
            screenShot();
            if(i == listWeightChart.size()-1){
                adDriver.pressKeyCode(4);
                break;
            }
            else { adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click(); }
        }

        //share option
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_share")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //save message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();

        //update message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_list")).click();
        AndroidElement record = (AndroidElement) adDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView/android.widget.FrameLayout[1]"));
        record.click();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();

        //delete message
        int pointX = record.getLocation().getX()+record.getSize().width/2;
        int pointY = record.getLocation().getY()+record.getSize().height/2;
        new TouchAction<>(adDriver).press(PointOption.point(pointX,pointY)).waitAction().moveTo(PointOption.point(pointX-250,pointY)).release().perform();
        screenShot();
        adDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView/android.widget.FrameLayout[1]/android.widget.LinearLayout[2]/android.widget.LinearLayout")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //check data mean
        adDriver.findElement(By.id("tw.com.wgh3h:id/relativeLayout_note")).click();
        int StartWebviewX = width/3;
        int StartWebviewY = adDriver.findElement(By.id("tw.com.wgh3h:id/fragment_tab")).getLocation().getY()-1;
        int EndWebviewX = StartWebviewX;
        int EndWebviewY = adDriver.findElement(By.id("android:id/title_container")).getLocation().getY()+adDriver.findElement(By.id("android:id/title_container")).getSize().getHeight();
        screenShot();
        touchAction.press(PointOption.point(StartWebviewX,StartWebviewY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000))).moveTo(PointOption.point(EndWebviewX, EndWebviewY)).release().perform();
        screenShot();
        touchAction.press(PointOption.point(StartWebviewX,StartWebviewY)).waitAction().moveTo(PointOption.point(EndWebviewX, EndWebviewY)).release().perform();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //check tips
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvInfo")).click();
        for (int i = 0; i < 4; i++){
            screenShot();
            touchAction.press(PointOption.point(width * 3 / 4,height / 2)).waitAction().moveTo(PointOption.point(width / 4, height / 2 )).release().perform();
        }
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Sport manager***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button3")).click();
        screenShot();
        //sport item option
        adDriver.findElement(By.id("tw.com.wgh3h:id/rlSportItem")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_btnlist")).click();
        //download data message
        try{
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
            Thread.sleep(3000);
        }catch (Exception eElementNoFind){ }
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        screenShot();
        //save message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        //only for record data having biofit2
        try {
            adDriver.findElement(By.id("tw.com.wgh3h:id/sportBioFitBtn")).click();
            screenShot();
        }catch (Exception eElementNoFind){}

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Eating manager***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button4")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_meal_Category")).click();
        screenShot();
        List<AndroidElement> category = adDriver.findElements(By.id("tw.com.wgh3h:id/miastorow_text"));
        category.get(4).click();
        adDriver.findElement(By.id("android:id/button1")).click();
        //save message
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnSaveAndUpload")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();
        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_btnlist")).click();
        //download record data message
        try{
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
            Thread.sleep(3000);
        }catch (Exception eElementNoFind){ }
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //check diet suggestions
        adDriver.findElement(By.id("tw.com.wgh3h:id/btmealsuggest")).click();
        screenShot();
        touchAction.press(PointOption.point(StartWebviewX,StartWebviewY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000))).moveTo(PointOption.point(EndWebviewX, EndWebviewY)).release().perform();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Blood Pressure***
        adDriver.findElement(By.id("tw.com.wgh3h:id/buttonbp")).click();
        screenShot();

        //save
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();

        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_btnlist")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //char
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn14d")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn1m")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn3m")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //data mean
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvNote")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Blood Glucose***
        adDriver.findElement(By.id("tw.com.wgh3h:id/buttonglu")).click();
        screenShot();

        //save
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.findElement(By.id("android:id/button1")).click();

        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_btnlist")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //chart
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn1m")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn3m")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn6m")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //data mean
        adDriver.findElement(By.id("tw.com.wgh3h:id/tvNote")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Smart Wristband***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button6")).click();
        screenShot();
        /** avd con't find the element
             //compare setting
             adDriver.findElement(By.id("tw.com.wgh3h:id/bt_delete")).click();
            screenShot();
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
             **/
        //sport
        //sport chart
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        try{
            adDriver.findElement(By.id("android:id/message"));
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
        }catch (Exception eElementNoFind){
            screenShot();
        }
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnSportWeekMode")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnSportMonthMode")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //sleep
        adDriver.findElement(By.id("tw.com.wgh3h:id/sleepBtn")).click();
        screenShot();
        //sleep chart
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        try{
            adDriver.findElement(By.id("android:id/message"));
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
        }catch (Exception eElementNoFind){
            screenShot();
        }
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnSportWeekMode")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnSportMonthMode")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Thermometer***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button8")).click();
        screenShot();

        //save
        adDriver.findElement(By.id("tw.com.wgh3h:id/action_btn_right")).click();
        screenShot();

        //record
        adDriver.findElement(By.id("tw.com.wgh3h:id/rl_btnlist")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //chart
        adDriver.findElement(By.id("tw.com.wgh3h:id/btn_chart")).click();
        try{
            adDriver.findElement(By.id("android:id/message"));
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
        }catch (Exception eElementNoFind){ }
        adDriver.findElement(By.id("tw.com.wgh3h:id/action_btn_right")).click();
        screenShot();
        List<AndroidElement> listThermometerChart = adDriver.findElementsById("android:id/text1");
        for (int i=0; i<listThermometerChart.size(); ++i){
            listThermometerChart.get(i).click();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btnDay")).click();
            try{
                adDriver.findElement(By.id("android:id/message"));
                screenShot();
                adDriver.findElement(By.id("android:id/button1")).click();
            }catch (Exception eElementNoFind){ }
            screenShot();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btn_weekly")).click();
            try{
                adDriver.findElement(By.id("android:id/message"));
                screenShot();
                adDriver.findElement(By.id("android:id/button1")).click();
            }catch (Exception eElementNoFind){ }
            screenShot();
            adDriver.findElement(By.id("tw.com.wgh3h:id/btn_monthly")).click();
            try{
                adDriver.findElement(By.id("android:id/message"));
                screenShot();
                adDriver.findElement(By.id("android:id/button1")).click();
            }catch (Exception eElementNoFind){ }
            screenShot();
            if(i == listThermometerChart.size()-1){
                adDriver.pressKeyCode(4);
                break;
            }
            else { adDriver.findElement(By.id("tw.com.wgh3h:id/action_btn_right")).click(); }
        }
        //data mean
        adDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.TextView")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Stress Mood***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button1")).click();
        screenShot();
        //bluetooth
        adDriver.findElement(By.id("tw.com.wgh3h:id/iv_si_ble")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //Six index record
        adDriver.findElement(By.id("tw.com.wgh3h:id/ll_history")).click();
        try{
            //network busy message
            adDriver.findElement(By.id("android:id/message"));
            screenShot();
            adDriver.findElement(By.id("android:id/button1")).click();
            while (true){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                adDriver.findElement(By.id("tw.com.wgh3h:id/ll_history")).click();
                adDriver.findElement(By.id("android:id/message"));
                adDriver.findElement(By.id("android:id/button1")).click();
            }
        }catch (Exception eWeakNetwork){
            Thread.sleep(5000);
            screenShot();
        }
        //stress mood page record data
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        startX = width/2;
        startY = adDriver.findElementById("tw.com.wgh3h:id/lv_index_result").getLocation().getY()-1 + adDriver.findElementById("tw.com.wgh3h:id/lv_index_result").getSize().height ;
        endX = startX;
        endY = startY - adDriver.findElementById("tw.com.wgh3h:id/lv_index_result").getSize().height;
        screenShot();
        for (int i=0; i<2; i++){
            touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY)).release().perform();
            screenShot();
        }
        //breathing training
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTabBreathingTraining")).click();
        screenShot();
        adDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout[1]")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnOK_NO_CONN")).click();
        //record*******update
        adDriver.findElement(By.id("tw.com.wgh3h:id/ll_calendar")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***Advice***
        //Browse
        adDriver.findElement(By.id("tw.com.wgh3h:id/button9")).click();
        screenShot();
        //Service
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_right")).click();
        screenShot();
        //question
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnTitle")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //back to menu items
        Thread.sleep(2000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //***Shop***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button11")).click();
        screenShot();
        Thread.sleep(3000);
        adDriver.findElement(By.id("tw.com.wgh3h:id/button_left")).click();

        //***My Ouendan***
        //create page
        adDriver.findElement(By.id("tw.com.wgh3h:id/button10")).click();
        screenShot();
        //join page
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_right")).click();
        screenShot();
        //enter the ouendan
        adDriver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.LinearLayout[3]/android.widget.LinearLayout[2]/android.widget.ListView/android.widget.RelativeLayout[1]/android.widget.TextView[1]")).click();
        Thread.sleep(3000);
        screenShot();
        //partners
        adDriver.findElement(By.id("tw.com.wgh3h:id/bt_right")).click();
        screenShot();
        //leader board
        adDriver.findElement(By.id("tw.com.wgh3h:id/imgLeaderBoard")).click();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnDayMode")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnWeekMode")).click();
        screenShot();
        adDriver.findElement(By.id("tw.com.wgh3h:id/btnMonthMode")).click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        Thread.sleep(2000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        Thread.sleep(2000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //***Progress***
        adDriver.findElement(By.id("tw.com.wgh3h:id/button12")).click();
        String awardXpath = "";
        int i=1;
        while(true){
            try {
                adDriver.findElement(By.xpath("//android.view.View[@content-desc=\"awards\"]"));
                awardXpath = "//android.view.View[@content-desc=\"awards\"]";
                screenShot();
                break;
            }catch (Exception eElementNoFind){ }
            try {
                adDriver.findElement(By.xpath("//android.view.View[@content-desc=\""+i+"\"]"));
                awardXpath = "//android.view.View[@content-desc=\""+i+"\"]";
                screenShot();
                break;
            }catch (Exception eElementNoFind){ }
            i++;
            if (i>6){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                adDriver.findElement(By.id("tw.com.wgh3h:id/button12")).click();
                i=0;
            }
        }
        screenShot();
        //award
        try {
            adDriver.findElement(By.xpath(awardXpath)).click();
            screenShot();
        }catch (Exception eElementNoFind){ }

        try {
            adDriver.findElement(By.xpath("//android.view.View[@content-desc=\"info\"]")).click();
            screenShot();
        }catch (Exception eElementNoFind){}



        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        Thread.sleep(2000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //news
        adDriver.findElement(By.xpath("//android.view.View[@content-desc=\"News\"]")).click();
        screenShot();
        try {
            adDriver.findElementById("android:id/button1").click();
        }catch (Exception eElementNoFind){}

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //***point****
        adDriver.findElementById("tw.com.wgh3h:id/tab_points").click();
        screenShot();
        //terms of user
        adDriver.findElementById("tw.com.wgh3h:id/tv_use_terms").click();
        screenShot();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        //used
        adDriver.findElementById("tw.com.wgh3h:id/usedBtn").click();
        screenShot();
        //Deadline
        adDriver.findElementById("tw.com.wgh3h:id/deadlineBtn").click();
        screenShot();

        //back to menu items
        adDriver.findElement(By.id("tw.com.wgh3h:id/tab_menu")).click();

        //exit app message
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        screenShot();
        adDriver.findElementById("android:id/button2").click();

    }


}