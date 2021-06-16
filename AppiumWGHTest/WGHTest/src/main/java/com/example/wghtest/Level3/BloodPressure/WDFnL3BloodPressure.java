package com.example.wghtest.Level3.BloodPressure;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class WDFnL3BloodPressure extends WDFnBaseMeasureMenuItems {

    protected AndroidElement _lvSYS,_lvDIA,_lvPulse;
    protected AndroidElement _etMemo;

    protected static String _strBluetoothID = "tw.com.wgh3h:id/bt_bp";
    protected static String _strTimeID = "tw.com.wgh3h:id/tv_bpAddHeader";
    private static String __strSYSID = "tw.com.wgh3h:id/wv_sbp";
    private static String __strDIAID = "tw.com.wgh3h:id/wv_dbp";
    private static String __strPulseID = "tw.com.wgh3h:id/wv_pulse";
    private static String __strMemoID = "tw.com.wgh3h:id/BPremark";



    public WDFnL3BloodPressure() {
        super._strDateTimeID = this._strTimeID;
    }

    public void setValue() throws InterruptedException {
        setTime();
        this._lvSYS = (AndroidElement) adDriver.findElement(By.id(__strSYSID));
        this._lvDIA = (AndroidElement) adDriver.findElement(By.id(__strDIAID));
        this._lvPulse = (AndroidElement) adDriver.findElement(By.id(__strPulseID));
        int SYS_X = _lvSYS.getLocation().getX()+_lvSYS.getSize().getWidth()/2;
        int SYS_Y = _lvSYS.getLocation().getY()+_lvSYS.getSize().getHeight()/2;
        touchAction.press(PointOption.point(SYS_X,SYS_Y)).waitAction().moveTo(PointOption.point(SYS_X,SYS_Y-(int)(Math.random()*1000-500))).release().perform();
        int DIA_X = _lvDIA.getLocation().getX()+_lvDIA.getSize().getWidth()/2;
        int DIA_Y = _lvDIA.getLocation().getY()+_lvDIA.getSize().getHeight()/2;
        touchAction.press(PointOption.point(DIA_X,DIA_Y)).waitAction().moveTo(PointOption.point(DIA_X,DIA_Y-(int)(Math.random()*1000-500))).release().perform();
        int Pulse_X = _lvPulse.getLocation().getX()+_lvPulse.getSize().getWidth()/2;
        int Pulse_Y = _lvPulse.getLocation().getY()+_lvPulse.getSize().getHeight()/2;
        touchAction.press(PointOption.point(Pulse_X,Pulse_Y)).waitAction().moveTo(PointOption.point(Pulse_X,Pulse_Y-(int)(Math.random()*1000-500))).release().perform();

        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        if (_etMemo.getText().equals("Add Demo")){
            _etMemo.clear();
            _etMemo.setValue("Update Demo");
        }else {
            _etMemo.setValue("Add Demo");
        }

        Thread.sleep(3000);
        clickSave();

        adDriver.findElement(By.id(_strButton1ID)).click();
    }


    public void scanBle() {
        clickBLE(_strBluetoothID);
    }


}
