package com.example.wghtest.Level3.Shop;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.appSizeX;
import static com.example.wghtest.WGHTestBase.appSizeY;

public class TestCaseShop extends WDFnL3Shop {
    private String __strSearchValue = "星空藍牙體重計";

    private String __strNickNameID = "dnn_dnnUser_enhancedRegisterLink";
    private String __strBtnAddCartID = "dnn_ctr483_ProductPage_lstProducts_btnlistaddtocart_1";
    //private String __strCheckBoxDeleteID = "dnn_ctr486_ShoppingCart_grdCartContent_RemoveItem_0";
    private String __strCheckBoxDeleteXpath = "//img[@src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAMAAAAoyzS7AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAZQTFRFAAAAAAAApWe5zwAAAAF0Uk5TAEDm2GYAAAAMSURBVHjaYmAACDAAAAIAAU9tWeEAAAAASUVORK5CYII=']";
    private String __strPayNoteID = "dnn_ctr486_ShoppingCart_lblUnPaidHelp";
    private String __strNoteNoItemsCartID = "dnn_ctr486_ShoppingCart_lblCartError";

    private String __strFileName = "AccountInfo";
    private String __strFilePath;

    TouchAction touchAction = new TouchAction(adDriver);
    Logger logger;

    public TestCaseShop() throws InterruptedException {
        logger = LoggerFactory.getLogger(TestCaseShop.class);
        Thread.sleep(15000);
    }

    public void CheckShopNickName() throws IOException {
        try{
            contextWebview();
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }


        String strNickName = "";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Nick Name")){
                strNickName = br.readLine();
            }
        }

        String strRead = adDriver.findElement(By.id(__strNickNameID)).getText();
        if (strRead.equals(strNickName)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect: "+strNickName);
            logger.debug("Read  : "+strRead);
        }
    }

    public void CheckShopPoints() throws InterruptedException, IOException {
        contextWebview();

        gotoWowPoints();
        String strReadPoints = getWowPoints();
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));

        String strExpectData = "";
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Points")){
                strExpectData = br.readLine();
            }
        }

        if (strReadPoints.contains(strExpectData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect data: " +strExpectData);
            logger.debug("Read   data: " +strReadPoints);
        }
    }

    public void AddShoppingCart() throws InterruptedException, IOException {
        contextWebview();

        gotoShopMarket();
        touchAction.press(PointOption.point(appSizeX/2,appSizeY*3/4)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(appSizeX/2,appSizeY/4)).release().perform();
        Thread.sleep(5000);
        adDriver.findElementById(__strBtnAddCartID).click();
        Thread.sleep(10000);

        gotoShoppintCart();

        try {
            adDriver.findElementById(__strPayNoteID);
            logger.info(strPassMsg);
        }catch (Exception e){
            logger.warn(strFailMsg);
            System.out.println(adDriver.getPageSource());
            if (adDriver.getPageSource().equals("")){
                logger.debug("Reason: Webview content load fail.");
            }
        }
    }

    public void RemoveShoppingCart() throws InterruptedException, IOException {
        contextWebview();

        gotoShoppintCart();
        touchAction.press(PointOption.point(appSizeX/2,appSizeY*4/5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(appSizeX/2,appSizeY/5)).release().perform();

        adDriver.findElementByXPath(__strCheckBoxDeleteXpath).click();

        Thread.sleep(5000);
        clickUpdataCart();

        Thread.sleep(8000);

        try {
            adDriver.findElementById(__strNoteNoItemsCartID);
            logger.info(strPassMsg);
        }catch (Exception e){
            logger.warn(strFailMsg);
            System.out.println(adDriver.getPageSource());
            if (adDriver.getPageSource().equals("")){
                logger.debug("Reason: Webview content load fail.");
            }
        }

    }

    public void SearchCommodity() throws InterruptedException, IOException {

        while (true){
            try {
                contextWebview();

                gotoShopMarket();
                searchCommodity(__strSearchValue);
                Thread.sleep(15000);
                break;
            }catch (Exception ePageLoadFail){
                contextNative();
                clickHome();
                new WDFnL2TitleMenuTabbar().clickShop();
            }
        }


        ArrayList alCommodityItems = getAllCommodityName();
        for (int i=0; i<alCommodityItems.size(); i++){
            if (alCommodityItems.get(i).equals(__strSearchValue)){
                logger.info(strPassMsg);
            }else {
                if (i == alCommodityItems.size()-1){
                    logger.warn(strFailMsg);
                    logger.debug("Expect: " + __strSearchValue);
                    logger.debug("Read  : " + alCommodityItems);
                }
            }
        }

        contextNative();

    }

}
