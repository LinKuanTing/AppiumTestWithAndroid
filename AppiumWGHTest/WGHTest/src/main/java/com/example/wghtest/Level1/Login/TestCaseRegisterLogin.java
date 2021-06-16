package com.example.wghtest.Level1.Login;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.Logout.WDFnL2Logout;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.Level1.Register.WDFnL1Register.strPrefixWord;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseRegisterLogin extends WDFnL1Login {
    private static String __strUserName,__strUserPwd;

    private static String __strActENMessage = "Delete other account data?";
    private static String __strActCHMessage = "若使用不同帳號登錄";
    private static String __strPlanENMessage = "No health plan";
    private static String __strPlanCHMessage = "您尚未制定體重控制計劃";

    private static String __strFileName = "AccountByRegistered";
    private static String __strFilePath;

    private static String __strMenuTextEN = "Menu";
    private static String __strMenuTextCH = "功能選單";

    private static String __strMessageID = "tw.com.wgh3h:id/tvMessage";
    private static String __strActHeightID = "tw.com.wgh3h:id/etHeight";
    private static String __strActInitialWeightID = "tw.com.wgh3h:id/etInitWeight";
    private static String __strActTargetWeightID = "tw.com.wgh3h:id/etTargetWeight";
    private static String __strSaveID = "tw.com.wgh3h:id/btnTitle";
    private static String __strMenuTitleID = "tw.com.wgh3h:id/textView1";


    public void execute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalLogin.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strUserName = strPrefixWord + alData.get(alData.size()-2);
        __strUserPwd = (String) alData.get(alData.size()-1);


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
            if (adDriver.findElement(By.id(__strMessageID)).getText().contains(__strActENMessage) || adDriver.findElement(By.id(__strMessageID)).getText().contains(__strActCHMessage)) {
                adDriver.findElement(By.id(_strButton1ID)).click();
            }
        }catch (Exception eNoFindElement){ }

        Thread.sleep(20000);

        String height = "170", initWeight = "50", targetWeight = "60";
        //註冊帳密後登入跳出尚未制定健康計畫訊息  需要先輸入必要的資料填寫
        try{
            if (adDriver.findElement(By.id(__strMessageID)).getText().contains(__strPlanENMessage) || adDriver.findElement(By.id(__strMessageID)).getText().contains(__strPlanCHMessage)){
                adDriver.findElement(By.id(_strButton1ID)).click();

                adDriver.findElement(By.id(__strActHeightID)).sendKeys(height);
                adDriver.findElement(By.id(__strActInitialWeightID)).sendKeys(initWeight);
                adDriver.findElement(By.id(__strActTargetWeightID)).sendKeys(targetWeight);
                adDriver.findElement(By.id(__strSaveID)).click();
                try {
                    //跳出BMI注意提醒訊息
                    adDriver.findElement(By.id(_strButton1ID)).click();
                }catch (Exception eNoFindElement) { }
                adDriver.findElement(By.id(_strButton1ID)).click();
            }
        }catch (Exception eNoFindElement){
            //eNoFindElement.printStackTrace();
            //非使用註冊帳密登入
        }

        //等待5秒後 是否出現Menu頁面
        Thread.sleep(5000);
        try {
            if (adDriver.findElement(By.id(__strMenuTitleID)).getText().equals(__strMenuTextEN)){
                logger.info(strPassMsg);
            }else if(adDriver.findElement(By.id(__strMenuTitleID)).getText().equals(__strMenuTextCH)){
                logger.info(strPassMsg);
            }
            else {
                logger.warn(strFailMsg);
                System.out.println(adDriver.findElement(By.id(__strMenuTitleID)).getText());
            }
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            eNoFindElement.printStackTrace();
        }

        new WDFnL2Logout().logout();
    }


}
