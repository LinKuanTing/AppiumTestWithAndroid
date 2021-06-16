package com.example.wghtest.Level1.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.strLoginMainPageID;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class WDFnL1Login {
    protected static AndroidElement _etUserName,_etUserPwd;
    protected static AndroidElement _btnLogin,_btnRegister,_btnForgetPwd;

    public long StartLoadTime;
    public long EndLoadTime;
    public static double loadTime;

    private static String __strUserNameID = "tw.com.wgh3h:id/etUserName";
    private static String __strUserPwdID = "tw.com.wgh3h:id/etPwd";
    private static String __strLoginID = "tw.com.wgh3h:id/tvLogin";
    private static String __strRegisterID = "tw.com.wgh3h:id/tvRegister";
    private static String __strForgetPwdID = "tw.com.wgh3h:id/tvGetAcdAndPwd";
    private static String __strWaitPage = "tw.com.wgh3h:id/textView1";

    protected static String _strMsgID = "tw.com.wgh3h:id/tvMessage";
    protected static String _strButton1ID = "android:id/button1";
    protected static String _strButton2ID = "android:id/button2";

    public WDFnL1Login(){
        this._etUserName = (AndroidElement) adDriver.findElement(By.id(__strUserNameID));
        this._etUserPwd = (AndroidElement) adDriver.findElement(By.id(__strUserPwdID));
        this._btnLogin = (AndroidElement) adDriver.findElement(By.id(__strLoginID));
        this._btnRegister = (AndroidElement) adDriver.findElement(By.id(__strRegisterID));
        this._btnForgetPwd = (AndroidElement) adDriver.findElement(By.id(__strForgetPwdID));
    }
    /*
        Function: Login()
        Parameters:
            strName: String - user name
            strPwd: String - user password
        Exception:
            InterruptedException: Method will throw an InterruptedException when the process is interrupted (by the tester).
        Return:
            None
    */
    public void login(String strName,String strPwd) {
        _etUserName.clear();
        _etUserName.sendKeys(strName);
        _etUserPwd.clear();
        _etUserPwd.sendKeys(strPwd);
        _btnLogin.click();
    }

    public void ctLogin(String strName,String strPwd) {
        _etUserName.clear();
        _etUserName.sendKeys(strName);
        _etUserPwd.clear();
        _etUserPwd.sendKeys(strPwd);
        StartLoadTime = System.currentTimeMillis();
        _btnLogin.click();
        try{
            //使用不同帳號登入
            //顯示是否刪除之前帳號紀錄
            StartLoadTime = System.currentTimeMillis();
            adDriver.findElement(By.id(_strButton1ID)).click();
            WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(__strWaitPage)));
            EndLoadTime = System.currentTimeMillis();

        }catch (Exception eNoFindElement){

            WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(__strWaitPage)));
            EndLoadTime = System.currentTimeMillis();
        }
        loadTime = (EndLoadTime - StartLoadTime)/1000.0;
    }

    public double getLoadTime(){
        return loadTime;
    }

    public void clickRegister(){
        _btnRegister.click();
    }

    public void clickForgetPwd(){
        _btnForgetPwd.click();
    }

}
