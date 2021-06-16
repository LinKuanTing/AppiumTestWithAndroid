package com.example.wghtest.Level1.ForgetPwd;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseFailForgetPwdByWrongFormat extends WDFnL1ForgetPwd {
    private static String __strMail;

    private static String __strFileName = "TestDataFailForgetPwdByWrongFormat";
    private static String __strFilePath;


    public void execute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailForgetPwdByWrongFormat.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strMail = (String) alData.get(0);

        forgetPwd(__strMail);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);
        try{
            adDriver.findElement(By.id(_strDescID));
            logger.info(strPassMsg);
        } catch (Exception eElementNoFind){
            logger.warn(strFailMsg);
        }
    }
}
