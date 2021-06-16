package com.example.wghtest.Level3.Shop;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Shop {
    protected AndroidElement _btnHome;

    private String __strHomeID = "tw.com.wgh3h:id/button_left";
    private String __strToggleNavigationCN = "navbar-toggle";
    private String __strShoppingHomeLT = "首頁";
    private String __strShoppingMarketLT = "商店街";
    private String __strMyAccountLT = "我的帳戶";

    private String __strSearchInputID = "dnn_ctr481_Search_cmbSearch_Input";
    private String __strSearchButtonID = "dnn_ctr481_Search_imgbtnSearch";
    private String __strListCommodityNameCN = "CATListProductName";

    private String __strWowPointsXpath = "//*[@title='WoW Points - ']";
    private String __strMyOrderXpath = "//*[@title='我的訂單 - ']";
    private String __strShoppingCartXpath = "//*[@title='購物車 - ']";

    private String __strUpdataCartID = "dnn_ctr486_ShoppingCart_UpdateBtn";
    private String __strNowPointsID = "dnn_ctr491_LoyaltyPoints_dgBalancelist_lblPendingAmount2_0";

    public WDFnL3Shop(){
        this._btnHome = (AndroidElement) adDriver.findElement(By.id(__strHomeID));
    }

    //~~~~~以下必須切換至 NATIVE 才能夠被呼叫~~~~~
    public void contextNative() throws IOException {
        Set context = adDriver.getContextHandles();
        adDriver.context((String) context.toArray()[0]);
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
    }
    public void clickHome(){
        _btnHome.click();
    }


    //https://shop.wowgohealth.com.tw/gshshop/
    //~~~~~以下必須切換到 WEBVIEW 才能夠被呼叫~~~~~
    public void contextWebview() {
        Set context = adDriver.getContextHandles();
        adDriver.context((String) context.toArray()[1]);
    }

    public void gotoShopMarket() throws InterruptedException {
        while (true){
            try {
                adDriver.findElementByClassName( __strToggleNavigationCN).click();
                Thread.sleep(5000);
                adDriver.findElement(By.partialLinkText(__strShoppingMarketLT)).click();
                Thread.sleep(5000);
                break;
            }catch (Exception e) { }
        }


    }

    public void searchCommodity(String name) throws InterruptedException {
        adDriver.findElementById(__strSearchInputID).click();
        adDriver.findElementById(__strSearchInputID).sendKeys(name);
        adDriver.findElementById(__strSearchButtonID).click();
        Thread.sleep(10000);
    }

    public ArrayList getAllCommodityName(){
        ArrayList<AndroidElement> alCommodityName = (ArrayList<AndroidElement>) adDriver.findElementsByClassName(__strListCommodityNameCN);
        ArrayList alName = new ArrayList();
        for (int i=0; i<alCommodityName.size(); i++){
            alName.add(alCommodityName.get(i).getText());
        }
        return alName;
    }


    public void gotoWowPoints() throws InterruptedException {
        while (true){
            try{
                adDriver.findElementByClassName( __strToggleNavigationCN).click();
                Thread.sleep(5000);
                adDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
                Thread.sleep(5000);
                adDriver.findElement(By.xpath(__strWowPointsXpath)).click();
                Thread.sleep(5000);
                break;
            }catch (Exception e) { }
        }

    }

    public String getWowPoints(){
        return adDriver.findElement(By.id(__strNowPointsID)).getText();
    }

    public void gotoMyOrder() throws InterruptedException {
        adDriver.findElementByClassName( __strToggleNavigationCN).click();
        Thread.sleep(5000);
        adDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
        Thread.sleep(5000);
        adDriver.findElement(By.xpath(__strMyOrderXpath)).click();
        Thread.sleep(5000);
    }

    public void gotoShoppintCart() throws InterruptedException, IOException {
        try {
            adDriver.findElementByClassName( __strToggleNavigationCN).click();
            Thread.sleep(8000);
            adDriver.findElement(By.partialLinkText(__strMyAccountLT)).click();
            Thread.sleep(8000);
            adDriver.findElement(By.xpath(__strShoppingCartXpath)).click();
            Thread.sleep(8000);
        }catch (Exception e){
            contextNative();
            clickHome();
            new WDFnL2TitleMenuTabbar().clickShop();
            contextWebview();
            adDriver.findElementById("dnn_CartLink_hypLink").click();
        }

    }

    public void clickUpdataCart(){
        adDriver.findElementById(__strUpdataCartID).click();
    }

}
