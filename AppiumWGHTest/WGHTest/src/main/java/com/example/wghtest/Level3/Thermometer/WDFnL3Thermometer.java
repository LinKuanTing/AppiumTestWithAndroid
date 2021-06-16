package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Thermometer extends WDFnBaseMeasureMenuItems {

    protected AndroidElement _btnSave;
    protected AndroidElement _tvDataMean;
    protected AndroidElement _lvType,_lvTempTen,_lvTempDigit,_lvTempPoint;
    protected AndroidElement _etMemo;

    protected static String _strBluetoothID = "tw.com.wgh3h:id/imageView_ble";
    private String __strDataMeanID = "tw.com.wgh3h:id/ll_tips";
    protected String _strTimeID = "tw.com.wgh3h:id/textView_record_time";
    private String __strTypeID = "tw.com.wgh3h:id/wv_one";
    private String __strTempTenID = "tw.com.wgh3h:id/wv_two";
    private String __strTempDigitID = "tw.com.wgh3h:id/wv_three";
    private String __strTempPointID = "tw.com.wgh3h:id/wv_four";
    private String __strMemoID = "tw.com.wgh3h:id/editText_memo";

    private String __strSaveID = "tw.com.wgh3h:id/action_btn_right";
    protected String _strBLEMessageID = "android:id/message";



    public WDFnL3Thermometer(){
        super._strDateTimeID = this._strTimeID;
    }


    public void setValue() throws InterruptedException {
        setTime();
        this._lvType = (AndroidElement) adDriver.findElement(By.id(__strTypeID));
        this._lvTempTen = (AndroidElement) adDriver.findElement(By.id(__strTempTenID));
        this._lvTempDigit = (AndroidElement) adDriver.findElement(By.id(__strTempDigitID));
        this._lvTempPoint = (AndroidElement) adDriver.findElement(By.id(__strTempPointID));
        int Type_X = _lvType.getLocation().getX()+_lvType.getSize().getWidth()/2;
        int Type_Y = _lvType.getLocation().getY()+_lvType.getSize().getHeight();
        touchAction.press(PointOption.point(Type_X,Type_Y-1)).waitAction().moveTo(PointOption.point(Type_X,Type_Y-(int)(Math.random()*400-200) )).release().perform();
        int TempTen_X = _lvTempTen.getLocation().getX()+_lvTempTen.getSize().getWidth()/2;
        int TempTen_Y = _lvTempTen.getLocation().getY()+_lvTempTen.getSize().getHeight();
        touchAction.press(PointOption.point(TempTen_X,TempTen_Y-1)).waitAction().moveTo(PointOption.point(TempTen_X,TempTen_Y-(int)(Math.random()*400-200) )).release().perform();
        int TempDigit_X = _lvTempDigit.getLocation().getX()+_lvTempDigit.getSize().getWidth()/2;
        int TempDigit_Y = _lvTempDigit.getLocation().getY()+_lvTempDigit.getSize().getHeight();
        touchAction.press(PointOption.point(TempDigit_X,TempDigit_Y-1)).waitAction().moveTo(PointOption.point(TempDigit_X,TempDigit_Y-(int)(Math.random()*600-400) )).release().perform();
        int TempPoint_X = _lvTempPoint.getLocation().getX()+_lvTempPoint.getSize().getWidth()/2;
        int TempPoint_Y = _lvTempPoint.getLocation().getY()+_lvTempPoint.getSize().getHeight();
        touchAction.press(PointOption.point(TempPoint_X,TempPoint_Y-1)).waitAction().moveTo(PointOption.point(TempPoint_X,TempPoint_Y-(int)(Math.random()*600-300) )).release().perform();

        _etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        if (_etMemo.getText().equals("Add Demo")){
            _etMemo.clear();
            _etMemo.setValue("Update Demo");
        }else {
            _etMemo.setValue("Add Demo");
        }

        clickSave();

        try {
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){
            //新增紀錄時  會跳出訊息顯示儲存成功
            //更新紀錄時  不會跳出訊息
        }
    }


    public void clickDataMean(){
        this._tvDataMean = (AndroidElement) adDriver.findElement(By.id(this.__strDataMeanID));
        _tvDataMean.click();
    }

    public void clickSave(){
        this._btnSave = (AndroidElement) adDriver.findElement(By.id(__strSaveID));
        _btnSave.click();
    }

    public void scanBle() {
        clickBLE(_strBluetoothID);
    }
}

