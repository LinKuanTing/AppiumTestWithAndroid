package com.example.wghtest.Level2.Logout;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseFailLogoutByClickCancel extends WDFnL2Logout{

    private static String __strTitleTextEN = "Personal";
    private static String __strTitleTextZH = "個人設定";
    private static String __strTitleXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.RelativeLayout/android.widget.TextView";

    public void execute() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailLogoutByClickCancel.class);
        logoutByCancel();

        if (adDriver.findElement(By.xpath(__strTitleXpath)).getText().equals(__strTitleTextEN)){
            logger.info(strPassMsg);
        }else if (adDriver.findElement(By.xpath(__strTitleXpath)).getText().equals(__strTitleTextZH)){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
