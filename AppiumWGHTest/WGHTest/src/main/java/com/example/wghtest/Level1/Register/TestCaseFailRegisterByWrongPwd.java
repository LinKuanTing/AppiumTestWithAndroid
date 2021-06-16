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

public class TestCaseFailRegisterByWrongPwd extends WDFnL1Register {
    private static String __strRegisterName,__strSN,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm;

    private static String __strCHRegisterMsg = "*密碼最少7碼英數字或符號\n";
    private static String __strENRegisterMsg = "*Password at least 7 characters\n";

    private static String __strFileName = "TestDataFailRegisterByWrongPwd";
    private static String __strFilePath;

    public void execute() throws InterruptedException, IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByWrongPwd.class);
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
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            logger.warn(strFailMsg);
        }

        Thread.sleep(3000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
