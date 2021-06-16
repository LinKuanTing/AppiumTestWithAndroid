package com.example.wghtest.Level1.ForgetPwd;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level1.FnMailEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.strLoginMainPageID;
import static com.example.wghtest.WGHTestBase.tmoutLoading;
import static java.lang.Thread.sleep;

public class TestCaseNormalForgetPwd extends WDFnL1ForgetPwd {
    private static String __strMail;

    private static String __strFileName = "TestDataNormalForgetPwd";
    private static String __strFilePath;

    public void execute() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalForgetPwd.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strMail = (String) alData.get(0);

        forgetPwd(__strMail);
        sleep(10000);

        FnMailEvent fnMailEvent = new FnMailEvent();
        try{
            WebDriverWait wait = new WebDriverWait(adDriver, tmoutLoading);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));
            sleep(5000);
            if(fnMailEvent.isExistForgetPwdMail()){
                logger.info(strPassMsg);
            }
            else{
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't get the forget password mail");
            }
        }
        catch (Exception eTimeOut) {
            //沒有順利返為到登入頁面  仍然在忘記密碼頁面
            logger.warn(strFailMsg);
            adDriver.pressKeyCode(4);
        }



    }
}
