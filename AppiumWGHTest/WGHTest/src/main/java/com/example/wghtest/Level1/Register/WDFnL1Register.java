package com.example.wghtest.Level1.Register;

import com.example.wghtest.Level1.Login.WDFnL1Login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.strLoginMainPageID;
import static com.example.wghtest.WGHTestBase.tmoutLoading;
import static java.lang.Thread.sleep;

public class WDFnL1Register {
    protected static AndroidElement _etRegisterName,_etSN,_etNickName,_etBirYear,_etMail,_etPhone,_etRegisterPwd,_etRegisterPwdCfm;
    protected static AndroidElement _btnFemale,_btnMale,_btnPersonalPic,_btnUseTerms,_btnAcceptTerms,_btnTitleRegister;

    public static String strPrefixWord = "GSH";

    private static String __strRegisterNameID = "tw.com.wgh3h:id/etAccount";
    private static String __strSNID = "tw.com.wgh3h:id/etSN";
    private static String __strNickNameID = "tw.com.wgh3h:id/etNickname";
    private static String __strBirYearID = "tw.com.wgh3h:id/etBirDay";
    private static String __strMailID = "tw.com.wgh3h:id/etEmail";
    private static String __strPhoneID = "tw.com.wgh3h:id/etPhone";
    private static String __strRegisterPwdID = "tw.com.wgh3h:id/etPwd";
    private static String __strRegisterPsdCfmID = "tw.com.wgh3h:id/etPwdCfm";
    private static String __strFemaleID = "tw.com.wgh3h:id/ivFemale";
    private static String __strMaleID = "tw.com.wgh3h:id/ivMale";
    private static String __strPersonalPicID = "tw.com.wgh3h:id/tvSetAvatar";
    private static String __strUseTermsID = "tw.com.wgh3h:id/ivPrivacyUserTerm";
    private static String __strTitleRegisterID = "tw.com.wgh3h:id/btnTitle";
    private static String __strAcceptTermsID = "tw.com.wgh3h:id/tvAgreePrivacy";

    protected static String _strRegisterMsg1ID = "android:id/message";  //帳號重複訊息
    protected static String _strRegisterMsg2ID = "tw.com.wgh3h:id/tvTitle"; //註冊正常訊息
    protected static String _strRegisterMsg3ID = "tw.com.wgh3h:id/tvMessage";//資料不齊全訊息 序號錯誤

    protected static String _strButton1ID = "android:id/button1";
    protected static String _strButton2ID = "android:id/button2";
    protected static String _strButton3ID = "android:id/button3";


    TouchAction touchAction = new TouchAction(adDriver);

    public static int width = adDriver.manage().window().getSize().width;
    public static int height = adDriver.manage().window().getSize().height;

    public WDFnL1Register(){
        clickRegister();
        this._etSN = (AndroidElement) adDriver.findElementById(__strSNID);
        this._etRegisterName = (AndroidElement) adDriver.findElementById(__strRegisterNameID);
        this._btnTitleRegister = (AndroidElement) adDriver.findElementById(__strTitleRegisterID);
        this._etNickName = (AndroidElement) adDriver.findElementById(__strNickNameID);
        this._etBirYear = (AndroidElement) adDriver.findElementById(__strBirYearID);
        this._etMail = (AndroidElement) adDriver.findElementById(__strMailID);
    }

    public void clickRegister(){
        new WDFnL1Login().clickRegister();
    }

    public void clickUseTerms() {
        touchAction.press(PointOption.point(width/2,height*4/5)).waitAction().moveTo(PointOption.point(width/2,height/5)).release().perform();
        this._btnUseTerms = (AndroidElement) adDriver.findElementById(__strUseTermsID);
        _btnUseTerms.click();

    }

    public void clickReadAndAccept() throws InterruptedException {
        this._btnAcceptTerms = (AndroidElement) adDriver.findElementById(__strAcceptTermsID);
        _btnAcceptTerms.click();
        sleep(3000);
    }

    public void register(String strSN, String strRegisterName, String strNickName, String strMail, String strPhone, String strRegisterPwd, String strRegisterPwdCfm) throws InterruptedException {
        _etSN.sendKeys(strSN);
        _etRegisterName.setValue(strRegisterName);
        this._etNickName = (AndroidElement) adDriver.findElementById(__strNickNameID);
        _etNickName.sendKeys(strNickName);
        //點選出生西元做下滑後點選確認按鈕
        this._etBirYear = (AndroidElement) adDriver.findElementById(__strBirYearID);
        _etBirYear.click();
        sleep(2000);
        touchAction.press(PointOption.point(width / 2, height / 2)).waitAction().moveTo(PointOption.point(width / 2, height / 5)).release().perform();
        sleep(2000);
        adDriver.findElement(By.id(_strButton1ID)).click();
        this._etMail = (AndroidElement) adDriver.findElementById(__strMailID);
        _etMail.sendKeys(strMail);
        this._etPhone = (AndroidElement) adDriver.findElementById(__strPhoneID);
        _etPhone.sendKeys(strPhone);
        this._btnFemale = (AndroidElement) adDriver.findElementById(__strFemaleID);
        this._btnMale = (AndroidElement) adDriver.findElementById(__strMaleID);
        _btnFemale.click();
        _btnMale.click();
        touchAction.press(PointOption.point(width / 2, height * 4 / 5)).waitAction().moveTo(PointOption.point(width / 2, height / 5)).release().perform();
        sleep(3000);
        this._btnPersonalPic = (AndroidElement) adDriver.findElementById(__strPersonalPicID);
        _btnPersonalPic.click();
        adDriver.findElement(By.id(_strButton2ID)).click();
        this._etRegisterPwd = (AndroidElement) adDriver.findElement(By.id(__strRegisterPwdID));
        _etRegisterPwd.sendKeys(strRegisterPwd);
        this._etRegisterPwdCfm = (AndroidElement) adDriver.findElement(By.id(__strRegisterPsdCfmID));
        _etRegisterPwdCfm.sendKeys(strRegisterPwdCfm);
        clickReadAndAccept();
        sleep(2000);
        _btnTitleRegister.click();
        sleep(2000);
    }

    public void nrRegister(String strSN, String strRegisterName, String strNickName, String strMail, String strPhone, String strRegisterPwd, String strRegisterPwdCfm) throws InterruptedException {
        _etSN.sendKeys(strSN);
        strPrefixWord = _etRegisterName.getText();
        _etRegisterName.setValue(strRegisterName);
        this._etNickName = (AndroidElement) adDriver.findElementById(__strNickNameID);
        _etNickName.sendKeys(strNickName);
        //點選出生西元做下滑後點選確認按鈕
        this._etBirYear = (AndroidElement) adDriver.findElementById(__strBirYearID);
        _etBirYear.click();
        sleep(2000);
        touchAction.press(PointOption.point(width/2,height/2)).waitAction().moveTo(PointOption.point(width/2,height/5)).release().perform();
        adDriver.findElement(By.id(_strButton1ID)).click();
        this._etMail = (AndroidElement) adDriver.findElementById(__strMailID);
        _etMail.sendKeys(strMail);
        this._etPhone = (AndroidElement) adDriver.findElementById(__strPhoneID);
        _etPhone.sendKeys(strPhone);
        this._btnFemale = (AndroidElement) adDriver.findElementById(__strFemaleID);
        this._btnMale = (AndroidElement) adDriver.findElementById(__strMaleID);
        _btnFemale.click();
        _btnMale.click();
        touchAction.press(PointOption.point(width/2,height*4/5)).waitAction().moveTo(PointOption.point(width/2,height/5)).release().perform();
        sleep(2000);
        this._btnPersonalPic = (AndroidElement) adDriver.findElementById(__strPersonalPicID);_btnPersonalPic.click();
        adDriver.findElement(By.id(_strButton2ID)).click();
        this._etRegisterPwd = (AndroidElement) adDriver.findElement(By.id(__strRegisterPwdID));
        _etRegisterPwd.sendKeys(strRegisterPwd);
        this._etRegisterPwdCfm= (AndroidElement) adDriver.findElement(By.id(__strRegisterPsdCfmID));
        _etRegisterPwdCfm.sendKeys(strRegisterPwdCfm);
        _btnTitleRegister.click();

    }

    //清空註冊頁面資料
    public void clearPage() throws InterruptedException {
        _etRegisterPwdCfm.clear();
        _etRegisterPwd.clear();
        touchAction.press(PointOption.point(width/2,height/5)).waitAction().moveTo(PointOption.point(width/2,height*4/5)).release().perform();
        _etPhone.clear();
        _etMail.clear();
        _etBirYear.clear();
        sleep(2000);
        touchAction.press(PointOption.point(width/2,height/2)).waitAction().moveTo(PointOption.point(width/2,height*4/5)).release().perform();
        adDriver.findElement(By.id(_strButton1ID)).click();
        _etNickName.clear();
        _etRegisterName.clear();
    }



}
