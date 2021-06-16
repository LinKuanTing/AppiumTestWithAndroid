package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.Logout.WDFnL2Logout;
import com.example.wghtest.other.FnFileEncrypt;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.Level1.Register.WDFnL1Register.strPrefixWord;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseDifferentActLogin extends WDFnL1Login{
    private static String __strUserName,__strUserPwd;

    private static String __strActENMessage = "Delete other account data?";
    private static String __strActCHMessage = "若使用不同帳號登錄";

    private static String __strFileName = "TestDataNormalLogin";
    private static String __strFilePath;

    private static String __strMessageID = "tw.com.wgh3h:id/tvMessage";
    private static String __strLoginPageID = "tw.com.wgh3h:id/rlLoginMain";
    private static String __strMenuTitleID = "tw.com.wgh3h:id/textView1";

    public void execute() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestCaseDifferentActLogin.class);

        //取得帳號密碼 (須從加密文件解密取得)
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FnFileEncrypt fnFileEncrypt = new FnFileEncrypt();
        byte[] bytes = fnFileEncrypt.loadFileContent(__strFilePath);
        String[] strContent = fnFileEncrypt.Decryptor(bytes).split("\r\n");
        __strUserName = strContent[0];
        __strUserPwd = strContent[1];

        //登入
        login(__strUserName,__strUserPwd);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        //出現登錄帳號不同是否刪除前筆資料
        try{
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){}

        //等待5秒後 登出
        Thread.sleep(5000);
        new WDFnL2Logout().logout();


        //取得另一組帳號密碼登入
        __strFileName = "AccountByRegistered";
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strUserName = strPrefixWord + alData.get(alData.size()-2);
        __strUserPwd = (String) alData.get(alData.size()-1);

        //登入
        new WDFnL1Login().login(__strUserName,__strUserPwd);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        String strMessage = adDriver.findElement(By.id(__strMessageID)).getText();
        //跳出刪除不同帳號資料
        if (strMessage.contains(__strActENMessage) || strMessage.contains(__strActCHMessage)) {
            adDriver.findElement(By.id(_strButton2ID)).click();

            try {
                adDriver.findElementById(__strLoginPageID);
            }catch (Exception eNoFindElement){
                //表示另外頁面  或是  點擊取消卻登入
                logger.warn(strFailMsg);
                new WDFnL2Logout().logout();
            }

            _btnLogin.click();
            adDriver.findElementById(_strButton1ID).click();

            try {
                adDriver.findElementById(__strMenuTitleID);
                logger.info(strPassMsg);
            }catch (Exception eNoFindElement){
                logger.warn(strFailMsg);
            }

        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Not expect message");
            logger.debug("Read: " + strMessage);
        }

        new WDFnL2Logout().logout();
    }
}
