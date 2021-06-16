package com.example.wghtest.Level3.Ouendan;

import org.openqa.selenium.By;


import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Ouendan {
    protected AndroidElement _btnOptionCreate,_btnOptionJoin;
    protected AndroidElement _etName,_btnCreate;
    protected AndroidElement _etCode,_etPassword,_btnJoin;

    private static String __strOptionCreateID = "tw.com.wgh3h:id/bt_left";
    private static String __strOptionJoinID = "tw.com.wgh3h:id/bt_right";
    private static String __strNameID = "tw.com.wgh3h:id/etUrgeName";
    private static String __strCreateID = "tw.com.wgh3h:id/btnCreate";
    private static String __strCreateMsgID = "tw.com.wgh3h:id/cfmMsg";
    private static String __strCodeID = "tw.com.wgh3h:id/etUrgeCode";
    private static String __strPasswordID = "tw.com.wgh3h:id/etUrgePwd";
    private static String __strJoinID = "tw.com.wgh3h:id/btnJoin";



    protected static String _strButton1ID = "android:id/button1";
    protected static String _strButton2ID = "android:id/button2";

    public WDFnL3Ouendan(){
        this._btnOptionCreate = (AndroidElement) adDriver.findElement(By.id(__strOptionCreateID));
        this._btnOptionJoin = (AndroidElement) adDriver.findElement(By.id(__strOptionJoinID));
    }

    public void clickOptionCreate(){
        _btnOptionCreate.click();
    }

    public void clickOptionJoin(){
        _btnOptionJoin.click();
    }

    public ArrayList getOuendan(){
        List<AndroidElement> listOuendan = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvName\"]"));
        ArrayList alOuendanName  = new ArrayList();
        for (int i=0; i<listOuendan.size(); i++){
            alOuendanName.add(listOuendan.get(i).getText());
        }
        return alOuendanName;
    }

    public void clickOuendan(String strOuendanName){
        List<AndroidElement> listOuendan = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvName\"]"));
        for (int i=0; i<listOuendan.size(); i++){
            if (listOuendan.get(i).getText().contains(strOuendanName)){
                listOuendan.get(i).click();
                break;
            }
        }
    }

    public void create(String name){
        _btnOptionCreate.click();
        this._etName = (AndroidElement) adDriver.findElement(By.id(__strNameID));
        this._btnCreate = (AndroidElement) adDriver.findElement(By.id(__strCreateID));

        _etName.sendKeys(name);
        _btnCreate.click();
    }

    public String getCreateMsg(){
        return adDriver.findElement(By.id(__strCreateMsgID)).getText();
    }

    public void join(String code,String password){
        _btnOptionJoin.click();
        this._etCode = (AndroidElement) adDriver.findElement(By.id(__strCodeID));
        this._etPassword = (AndroidElement) adDriver.findElement(By.id(__strPasswordID));
        this._btnJoin = (AndroidElement) adDriver.findElement(By.id(__strJoinID));
        _etCode.sendKeys(code);
        _etPassword.sendKeys(password);
        _btnJoin.click();
    }
}
