package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level1.FnMailEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseNormalRegister extends WDFnL1Register{
    private static String __strSN,__strRegisterName,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm;

    private static String __strCHRegisterMsg = "APP隱私權暨使用服務條款";
    private static String __strENRegisterMsg = "Privacy policy and terms of use";

    private static String __strFileName = "TestDataNormalRegister";
    private static String __strFileActTxt = "AccountByRegistered";
    private static String __strFilePath;

    public void execute() throws Exception {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalRegister.class);
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


        while (true){
            //註冊帳號為 GSHTesterTing000 ~ GSHTesterTing999
            String idx = String.format("%03d",(int)(Math.random()*1000));
            __strRegisterName += idx;
            __strFilePath = fnFileEvent.getPath(__strFileActTxt);

            //判斷帳號是否存在過
            boolean isExist = false;
            FileInputStream fis = new FileInputStream(__strFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
            while (br.ready()){
                if (__strRegisterName.equals(br.readLine())){
                    //帳號設為初值重新抽取流水號
                    __strRegisterName = (String) alData.get(1);
                    isExist = true;
                    break;
                }
            }
            //帳號存在則重新迴圈
            //帳號不存在 將帳號寫入檔案 跳出迴圈
            if (isExist){
                continue;
            } else{
                break;
            }
        }

        nrRegister(__strSN,__strRegisterName,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        //if Register success will display message for Privacy policy and terms of use to click OK
        //and then check e-mail to find the Register mail and to verify Account
        FnMailEvent fnMailEvent = new FnMailEvent();
        if (adDriver.findElement(By.id(_strRegisterMsg2ID)).getText().equals(__strENRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();

            //System.out.println(_strRegisterName+","+_strRegisterPwd);
            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(__strRegisterName+"\r\n");
            bw.write(__strRegisterPwd+"\r\n");
            bw.flush();
            bw.close();

            /*
            //計算註冊時的時間
            StartLoadTime = System.currentTimeMillis();
            adDriver.findElement(By.id(_strButton1ID)).click();
            WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));
            EndLoadTime = System.currentTimeMillis();
            loadTime = (EndLoadTime - StartLoadTime)/1000.0;
            */


            //等待120秒確保驗證信寄到信箱
            Thread.sleep(120000);
            fnMailEvent.getVerification();

            logger.info(strPassMsg);
        }

        else if (adDriver.findElement(By.id(_strRegisterMsg2ID)).getText().equals(__strCHRegisterMsg)){
            adDriver.findElement(By.id(_strButton1ID)).click();

            //System.out.println(_strRegisterName+","+_strRegisterPwd);
            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(__strRegisterName+"\r\n");
            bw.write(__strRegisterPwd+"\r\n");
            bw.flush();
            bw.close();

            fnMailEvent.getVerification();
            logger.info(strPassMsg);
        }
        else{
            adDriver.findElement(By.id(_strButton1ID)).click();
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            logger.warn(strFailMsg);
        }

        //click register complete message
        adDriver.findElement(By.id(_strButton1ID)).click();
    }


}
