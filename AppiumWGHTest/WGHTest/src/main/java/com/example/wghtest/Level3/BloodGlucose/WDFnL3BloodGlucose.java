package com.example.wghtest.Level3.BloodGlucose;


import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import java.time.Duration;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3BloodGlucose extends WDFnBaseMeasureMenuItems {

    protected AndroidElement _tvSession,_tvGLU100,_tvGLU10,_tvGLU1,_tvGLUpoint;
    protected AndroidElement _etMemo;

    int height ,width;

    protected static String _strBluetoothID = "tw.com.wgh3h:id/bt_glu";

    protected static String _strTimeID = "tw.com.wgh3h:id/tv_Date";
    private static String __strSessionID = "android:id/numberpicker_input";
    private static String __strGLUhudredID = "tw.com.wgh3h:id/wv_one";
    private static String __strGLUtenID = "tw.com.wgh3h:id/wv_two";
    private static String __strGLUunitID = "tw.com.wgh3h:id/wv_three";
    private static String __strGLUpointID = "tw.com.wgh3h:id/wv_four";
    private static String __strMemoID = "tw.com.wgh3h:id/GLUremark";

    private static String __strTitleGLUXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[2]";

    public WDFnL3BloodGlucose(){
        super._strDateTimeID = this._strTimeID;
   }


    public void setValue() throws InterruptedException {
        setTime();

        this._tvSession = (AndroidElement) adDriver.findElement(By.id(__strSessionID));
        this._tvGLU100 = (AndroidElement) adDriver.findElement(By.id(__strGLUhudredID));
        this._tvGLU10 = (AndroidElement) adDriver.findElement(By.id(__strGLUtenID));
        this._tvGLU1 = (AndroidElement) adDriver.findElement(By.id(__strGLUunitID));
        this._tvGLUpoint = (AndroidElement) adDriver.findElement(By.id(__strGLUpointID));
        int session_X = _tvSession.getLocation().getX()+_tvSession.getSize().getWidth()/2;
        int session_Y = _tvSession.getLocation().getY()+_tvSession.getSize().getHeight();
        touchAction.press(PointOption.point(session_X,session_Y-1)).waitAction().moveTo(PointOption.point(session_X,session_Y+(int)(Math.random()*300-150) )).release().perform();
        int GLU100_X = _tvGLU100.getLocation().getX()+_tvGLU100.getSize().getWidth()/2;
        int GLU100_Y = _tvGLU100.getLocation().getY()+_tvGLU100.getSize().getHeight();
        touchAction.press(PointOption.point(GLU100_X,GLU100_Y-1)).waitAction().moveTo(PointOption.point(GLU100_X,GLU100_Y+(int)(Math.random()*300-150) )).release().perform();
        int GLU10_X = _tvGLU10.getLocation().getX()+_tvGLU10.getSize().getWidth()/2;
        int GLU10_Y = _tvGLU10.getLocation().getY()+_tvGLU10.getSize().getHeight();
        touchAction.press(PointOption.point(GLU10_X,GLU10_Y-1)).waitAction().moveTo(PointOption.point(GLU10_X,GLU10_Y+(int)(Math.random()*300-150))).release().perform();
        int GLU1_X = _tvGLU1.getLocation().getX()+_tvGLU1.getSize().getWidth()/2;
        int GLU1_Y = _tvGLU1.getLocation().getY()+_tvGLU1.getSize().getHeight();
        touchAction.press(PointOption.point(GLU1_X,GLU1_Y-1)).waitAction().moveTo(PointOption.point(GLU1_X,GLU1_Y+(int)(Math.random()*300-150))).release().perform();
        int GLUpoint_X = _tvGLUpoint.getLocation().getX()+_tvGLUpoint.getSize().getWidth()/2;
        int GLUpoint_Y = _tvGLUpoint.getLocation().getY()+_tvGLUpoint.getSize().getHeight();
        touchAction.press(PointOption.point(GLUpoint_X,GLUpoint_Y-5)).waitAction().moveTo(PointOption.point(GLUpoint_X,GLUpoint_Y+(int)(Math.random()*300-150))).release().perform();
        Thread.sleep(3000);


        height = adDriver.findElementByXPath(__strTitleGLUXpath).getLocation().getY() + adDriver.findElement(By.xpath(__strTitleGLUXpath)).getSize().getHeight()/2;
        width = adDriver.findElement(By.xpath(__strTitleGLUXpath)).getLocation().getX() + adDriver.findElementByXPath(__strTitleGLUXpath).getSize().getWidth()/2;
        touchAction.press(PointOption.point(width,height)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).moveTo(PointOption.point(width,height/2)).release().perform();
        Thread.sleep(3000);

        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        if (_etMemo.getText().equals("Add Demo")){
            _etMemo.clear();
            _etMemo.setValue("Update Demo");
        }else {
            _etMemo.setValue("Add Demo");
        }


        clickSave();
        try {
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){ }
    }

    public void scanBle() {
        clickBLE(_strBluetoothID);
    }


}
