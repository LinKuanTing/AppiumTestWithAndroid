package com.example.wghtest.Level3.Advice;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Advice {
    protected AndroidElement _btnQuestion;
    protected AndroidElement _btnBrowse,_btnServices;
    protected AndroidElement _rlGSH;

    protected static int _pointX,_pointY;
    protected static int _sizeX,_sizeY;


    protected AndroidElement _lvRemindIdx,_tvType,_tvDate,_tvContent,_tvStarComment;

    private String __strQuestionID = "tw.com.wgh3h:id/btnTitleBarButton";
    private String __strContentID = "tw.com.wgh3h:id/etcontent";
    private String __strSendID = "tw.com.wgh3h:id/btnTitleBarButton";

    private String __strBrowseID = "tw.com.wgh3h:id/bt_left";
    private String __strServicesID = "tw.com.wgh3h:id/bt_right";
    private String __strBackToAdviceID = "tw.com.wgh3h:id/tvTitleBarBack";
    private String __strBackToMenuID = "tw.com.wgh3h:id/tvTitleBarBack";

    //Browse
    protected static String _strBrowseFirst = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.view.ViewGroup/android.support.v4.view.ViewPager/android.widget.LinearLayout/android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]";
    private String __strBrowseIdx = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.view.ViewGroup/android.support.v4.view.ViewPager/android.widget.LinearLayout/android.view.ViewGroup/android.support.v7.widget.RecyclerView";
    private String __strBrowseType = "/android.widget.TextView[1]";
    private String __strBrowseIdxDate = "/android.widget.TextView[2]";
    private String __strBrowseIdxContent = "/android.widget.LinearLayout/android.widget.TextView";
    private String __strBrowseIdxStar = "/android.widget.TextView[3]";

    private String __strBtnStarID = "tw.com.wgh3h:id/btnStar_5";
    private String __strAlertSendID = "tw.com.wgh3h:id/tvDialogSend";
    private String __strAlertCancelID = "tw.com.wgh3h:id/tvDialogCancel";

    //Services
    private String __strServicesGSH = "tw.com.wgh3h:id/ivArrow";

    public static ArrayList alMeasureTime = new ArrayList();

    TouchAction touchAction = new TouchAction(adDriver);

    public WDFnL3Advice(int index){
        this.__strBrowseIdx += "/android.view.ViewGroup[" + index + "]";
        this._tvType = (AndroidElement) adDriver.findElement(By.xpath(__strBrowseIdx+__strBrowseType));
        this._tvDate = (AndroidElement) adDriver.findElement(By.xpath(__strBrowseIdx+__strBrowseIdxDate));
        this._tvContent = (AndroidElement) adDriver.findElement(By.xpath(__strBrowseIdx+__strBrowseIdxContent));
        this._tvStarComment = (AndroidElement) adDriver.findElement(By.xpath(__strBrowseIdx+__strBrowseIdxStar));
    }

    public WDFnL3Advice(){ }


    public void getBrowseLocation(){
        AndroidElement elRecord1st;
        try{
            elRecord1st = (AndroidElement) adDriver.findElementByXPath(_strBrowseFirst);
        }catch (Exception eNoFindElement){
            adDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            new WDFnL2TitleMenuTabbar().clickAdvice();
            elRecord1st = (AndroidElement) adDriver.findElementByXPath(_strBrowseFirst);
        }
        _sizeX = elRecord1st.getSize().getWidth();
        _sizeY = elRecord1st.getSize().getHeight();
        _pointX = elRecord1st.getLocation().getX()+_sizeX/2;
        _pointY = elRecord1st.getLocation().getY()+_sizeY/2;
    }

    public void clickBackToAdvice(){
        adDriver.findElementById(__strBackToAdviceID).click();
    }

    public void clickBackToMenu(){
        adDriver.findElementById(__strBackToMenuID).click();
    }

    public void sendQuestion(String strContent){
        this._btnQuestion = (AndroidElement) adDriver.findElementById(__strQuestionID);
        _btnQuestion.click();

        adDriver.findElementById(__strContentID).sendKeys(strContent);
        adDriver.findElementById(__strSendID).click();
    }

    public String getType(){
        return _tvType.getText();
    }

    public String getDate(){
        return _tvDate.getText();
    }

    public String getContent(){
        return _tvContent.getText();
    }


    public void clickBrowse(){
        this._btnBrowse = (AndroidElement) adDriver.findElement(By.id(__strBrowseID));
        _btnBrowse.click();
    }

    public void clickBrowseIdx(){
        adDriver.findElementByXPath(__strBrowseIdx).click();
    }

    public void clickBtnStar(){
        adDriver.findElementById(__strBtnStarID).click();
    }
    public String getStarText(){
        return _tvStarComment.getText();
    }

    public void clickMsgCancel(){
        adDriver.findElementById(__strAlertCancelID).click();
    }

    public void clickMsgSend(){
        adDriver.findElementById(__strAlertSendID).click();
    }



    public void clickServices(){
        try {
            this._btnServices = (AndroidElement) adDriver.findElement(By.id(__strServicesID));
            _btnServices.click();
        }catch (Exception e){
            int windowX = adDriver.manage().window().getSize().width;
            int windowY = adDriver.manage().window().getSize().height;
            touchAction.press(PointOption.point(windowX*4/5,windowY/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(windowX/10,windowY/2)).release().perform();
        }

    }

    public void clickServicesGSH () {
        this._rlGSH = (AndroidElement) adDriver.findElement(By.id(__strServicesGSH));
        _rlGSH.click();
    }


}
