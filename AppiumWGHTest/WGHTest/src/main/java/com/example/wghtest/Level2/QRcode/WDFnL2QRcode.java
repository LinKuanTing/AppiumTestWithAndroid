package com.example.wghtest.Level2.QRcode;

import org.openqa.selenium.By;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL2QRcode {

    private static String __strQRAccountID = "tw.com.wgh3h:id/tvMessage";
    private static String __strBtnOkID = "tw.com.wgh3h:id/btn_ok";

    public String getAccount(){
        return adDriver.findElement(By.id(__strQRAccountID)).getText();
    }

    public void clickOk(){
        adDriver.findElement(By.id(__strBtnOkID)).click();
    }
}
