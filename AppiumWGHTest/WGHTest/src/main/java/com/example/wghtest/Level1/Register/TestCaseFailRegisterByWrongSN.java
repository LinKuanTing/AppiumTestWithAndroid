package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseFailRegisterByWrongSN extends WDFnL1Register {
    protected static String _strRegisterName,_strSN,_strNickName,_strMail,_strPhone,_strRegisterPwd,_strRegisterPwdCfm;

    protected static String _strCHRegisterMsg = "請輸入正確的序號(3)";
    protected static String _strENRegisterMsg = "Need Valid No.(3)";


    private static String __strFileName = "TestDataFailRegisterByWrongSN";
    private static String __strFilePath;

    public void execute() throws InterruptedException, IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByWrongSN.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        _strSN = (String) alData.get(0);

        _etSN.sendKeys(_strSN);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        Thread.sleep(3000);
        if (adDriver.findElement(By.id(_strRegisterMsg3ID)).getText().equals(_strCHRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else if (adDriver.findElement(By.id(_strRegisterMsg3ID)).getText().equals(_strENRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else {
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            logger.warn(strFailMsg);
        }

        Thread.sleep(3000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
