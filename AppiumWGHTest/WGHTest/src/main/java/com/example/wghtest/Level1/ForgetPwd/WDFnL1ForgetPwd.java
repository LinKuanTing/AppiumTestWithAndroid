package com.example.wghtest.Level1.ForgetPwd;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;


public class WDFnL1ForgetPwd {
    protected static AndroidElement _etMail;
    protected static AndroidElement _btnQuery;

    protected static String _strCHResult = "處理中…";
    protected static String _strENResult = "Processing…";

    protected static String _strResultID = "tw.com.wgh3h:id/tvResult";
    protected static String _strDescID = "tw.com.wgh3h:id/tvDesc";


    private static String __strMailID = "tw.com.wgh3h:id/etEmail";
    private static String __strQueryID = "tw.com.wgh3h:id/tvQuery";

    public WDFnL1ForgetPwd(){
        this._etMail = (AndroidElement) adDriver.findElement(By.id(__strMailID));
        this._btnQuery = (AndroidElement) adDriver.findElement(By.id(__strQueryID));
    }

    public void forgetPwd(String strMail) {
        _etMail.clear();
        _etMail.sendKeys(strMail);
        _btnQuery.click();
    }
}
