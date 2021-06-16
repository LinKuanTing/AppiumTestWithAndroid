package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.other.FnFileEncrypt;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseNormalLogin extends WDFnL1Login{
    private static String __strUserName,__strUserPwd;

    private static String __strFileName = "TestDataNormalLogin";
    private static String __strFilePath;

    private static String __strMenuTitleID = "tw.com.wgh3h:id/textView1";

    public TestCaseNormalLogin() throws Exception{
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FnFileEncrypt fnFileEncrypt = new FnFileEncrypt();
        byte[] bytes = fnFileEncrypt.loadFileContent(__strFilePath);
        String[] strContent = fnFileEncrypt.Decryptor(bytes).split("\r\n");
        __strUserName = strContent[0];
        __strUserPwd = strContent[1];

        //System.out.println(__strUserName+"  "+__strUserPwd);
    }

    public void execute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalLogin.class);

        /*
        Description:
            This case is to test Login whether  success
         */
        // This tests Login with correct account and password
        // Result: Enter the Features Menu
        login(__strUserName,__strUserPwd);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        //出現登錄帳號不同是否刪除前筆資料
        try{
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){}

        //等待5秒後 是否出現Menu頁面
        Thread.sleep(5000);
        try {
            adDriver.findElement(By.id(__strMenuTitleID));
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }
    }

    public void login() throws InterruptedException {
        login(__strUserName,__strUserPwd);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        //出現登錄帳號不同是否刪除前筆資料
        try{
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){}

        //等待5秒後 是否出現Menu頁面
        Thread.sleep(5000);
    }

}
