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

public class TestCaseFailRegisterByWrongMail extends WDFnL1Register {
    private static String __strRegisterName,__strSN,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm;

    private static String __strCHRegisterMsg = "*資料填寫不完整請確認\n" + "*請輸入正確的Email\n";
    private static String __strENRegisterMsg = "Data incomplete\n"+"*Need Valid E-mail\n";

    private static String __strFileName = "TestDataFailRegisterByWrongMail";
    private static String __strFilePath;

    public void execute() throws InterruptedException, IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByWrongMail.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strSN = (String) alData.get(0);
        __strRegisterName = (String) alData.get(1);
        __strNickName = (String) alData.get(2);
        __strMail = (String) alData.get(3);
        __strPhone = (String) alData.get(4);
        __strRegisterPwd = (String) alData.get(5);
        __strRegisterPwdCfm = (String) alData.get(6);

        register(__strSN,__strRegisterName,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        Thread.sleep(3000);
        if (adDriver.findElement(By.id(_strRegisterMsg3ID)).getText().equals(__strCHRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else if(adDriver.findElement(By.id(_strRegisterMsg3ID)).getText().equals(__strENRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else{
            logger.warn(strFailMsg);
            System.out.println(" Read:  " + adDriver.findElement(By.id(_strRegisterMsg3ID)).getText());
            System.out.println("Expect: " + __strCHRegisterMsg + " / " + __strENRegisterMsg);
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }
        Thread.sleep(3000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
