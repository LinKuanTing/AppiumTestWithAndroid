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

public class TestCaseFailLoginByWrongAct extends WDFnL1Login {
    private static String __strUserName,__strUserPwd;

    private static String __strENMssage = "Account does not exist";
    private static String __strCHMssage = "此會員帳號不存在，請確認後再重新登入";

    private static String __strFileName = "TestDataFailLoginByWrongAct";
    private static String __strFilePath;

    public void execute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailLoginByWrongAct.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strUserName = (String) alData.get(0);
        __strUserPwd = (String) alData.get(1);

        /*
		Description:
			This case is to test Login with non-existent account.
		*/
        // This test Login with a non-existent account.
        // Result: display message : "Account does not exist"
        //                         | "此會員帳號不存在，請確認後再重新登入"
        login(__strUserName,__strUserPwd);

        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        if (adDriver.findElement(By.id(_strMsgID)).getText().equals(__strENMssage)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else if (adDriver.findElement(By.id(_strMsgID)).getText().equals(__strCHMssage)){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }
        else {
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            logger.warn(strFailMsg);
        }
    }
}
