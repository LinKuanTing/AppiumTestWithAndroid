package com.example.wghtest.Level1.Login;

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

public class TestCaseFailLoginByWrongPwd extends WDFnL1Login {
    private static String __strUserName,__strUserPwd;

    private static String __strENMessage = "Wrong password. Please login again.\n" + " (prompt：If you entered wrong password 5 times, your account will be locked)";
    private static String __strCHMessage = "密碼錯誤，請重新輸入\n" + "(提示:若輸入密碼錯誤5次,系統將自動鎖定)";

    private static String __strFileName = "TestDataFailLoginByWrongPwd";
    private static String __strFilePath;

    public void execute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailLoginByWrongPwd.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strUserName = (String) alData.get(0);
        __strUserPwd = (String) alData.get(1);

        /*
		Description:
			This case is to test Login with wrong password.
		*/
        // This test Login with a wrong password.
        // Result: display message : "Wrong password. Please login again.\n" + " (prompt：If you entered wrong password 5 times, your account will be locked)"
        //                         | "密碼錯誤，請重新輸入\n" + "(提示:若輸入密碼錯誤5次,系統將自動鎖定)"
        login(__strUserName,__strUserPwd);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        if (adDriver.findElement(By.id(_strMsgID)).getText().equals(__strENMessage)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else if (adDriver.findElement(By.id(_strMsgID)).getText().equals(__strCHMessage)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else {
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            logger.warn(strFailMsg);
        }
    }
}
