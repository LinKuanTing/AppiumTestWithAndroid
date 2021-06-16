package com.example.wghtest.Level3.Ouendan;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3InOuendan {

    protected AndroidElement _btnLatestNews,_btnMyPartners,_btnRank;
    protected AndroidElement _btnEncourage,_btnQuit,_btnReply;

    protected AndroidElement _etMessage,_btnSend;

    private static String __strLatestNewsID = "tw.com.wgh3h:id/bt_left";
    private static String __strMyPartnersID = "tw.com.wgh3h:id/bt_right";
    private static String __strRankID = "tw.com.wgh3h:id/imgLeaderBoard";
    private static String __strEncourageID = "tw.com.wgh3h:id/btnTitle";
    private static String __strQuitID = "tw.com.wgh3h:id/btnTitle";
    private static String __strReplyID = "tw.com.wgh3h:id/btnTitle";
    private static String __strOuendanNameID = "tw.com.wgh3h:id/tvUrgeSetInfo";
    private static String __strOuendanCodePwdID = "tw.com.wgh3h:id/tvUrgeSetIn";

    private static String __strOuendanInfo1ID = "tw.com.wgh3h:id/tvUrgeSetInfo";
    private static String __strOuendanInfo2ID = "tw.com.wgh3h:id/tvUrgeSetInfo2";

    private static String __strMessageID = "tw.com.wgh3h:id/etMessage";
    private static String __strSendID = "tw.com.wgh3h:id/btnTitle";

    private static String __strButton1ID = "android:id/button1";
    private static String __strButton2ID = "android:id/button2";


    public WDFnL3InOuendan(){ }

    public void ckickLatestNews(){
        this._btnLatestNews = (AndroidElement) adDriver.findElement(By.id(__strLatestNewsID));
        _btnLatestNews.click();
    }

    public void clickMyPartners(){
        this._btnMyPartners = (AndroidElement) adDriver.findElement(By.id(__strMyPartnersID));
        _btnMyPartners.click();
    }

    public void clickRank(){
        this._btnRank = (AndroidElement) adDriver.findElement(By.id(__strRankID));
        _btnRank.click();
    }

    public void encourage(String strMessage){
        this._btnEncourage = (AndroidElement) adDriver.findElement(By.id(__strEncourageID));
        _btnEncourage.click();
        this._etMessage = (AndroidElement) adDriver.findElement(By.id(__strMessageID));
        this._btnSend = (AndroidElement) adDriver.findElement(By.id(__strSendID));
        _etMessage.sendKeys(strMessage);
        _btnSend.click();
    }

    public ArrayList getLatestNewsContent(){
        List<AndroidElement> listContent = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvContent\"]"));
        ArrayList alContent = new ArrayList();
        for (int i=0; i<listContent.size(); i++){
            alContent.add(listContent.get(i).getText());
        }
        return alContent;
    }

    public void clickImgGoodAndBad(){
        List<AndroidElement> listImgGood = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/ivGood\"]"));
        listImgGood.get(0).click();
        List<AndroidElement> listImgBad = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/ivBad\"]"));
        listImgBad.get(0).click();
    }

    public void clickImgReply(){
        List<AndroidElement> listImgReply = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/ivReply\"]"));
        listImgReply.get(0).click();
    }

    public ArrayList getReplyContent(){
        List<AndroidElement> listContent = adDriver.findElements(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvContent\"]"));
        ArrayList alContent = new ArrayList();
        for (int i=0; i<listContent.size(); i++){
            alContent.add(listContent.get(i).getText());
        }
        return alContent;
    }

    public void reply(String strMessage){
        this._btnReply = (AndroidElement) adDriver.findElement(By.id(__strReplyID));
        _btnReply.click();
        this._etMessage = (AndroidElement) adDriver.findElement(By.id(__strMessageID));
        this._btnSend = (AndroidElement) adDriver.findElement(By.id(__strSendID));
        _etMessage.sendKeys(strMessage);
        _btnSend.click();
    }

    public ArrayList getOuendanInfo(){
        String strOuendan = "";
        strOuendan += adDriver.findElement(By.id(__strOuendanInfo1ID)).getText();
        strOuendan += adDriver.findElement(By.id(__strOuendanInfo2ID)).getText();
        char[] charArray = strOuendan.toCharArray();

        ArrayList alInfo = new ArrayList();
        String strInfo = "";
        for (int i =0; i<charArray.length; i++){
            if (charArray[i] == '「') {
                for (int j=i+1; ;j++){

                    if (charArray[j] == '」'){
                        alInfo.add(strInfo);
                        strInfo = "";
                        break;
                    }
                    else {
                        strInfo += charArray[j];
                    }

                }
            }
        }
        return alInfo;
    }

    public String getPersonalContent(){
        return adDriver.findElement(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvContent\"]")).getText();
    }
    public String getPersonalName(){
        return adDriver.findElement(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/tvName\"]")).getText();
    }

    public void quit() throws InterruptedException {
        clickMyPartners();
        this._btnQuit = (AndroidElement) adDriver.findElement(By.id(__strQuitID));
        _btnQuit.click();
        adDriver.findElement(By.id(__strButton1ID)).click();
        adDriver.findElement(By.id(__strButton2ID)).click();
        Thread.sleep(3000);
    }
}
