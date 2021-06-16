package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;

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

public class TestCaseFailRegisterByChineseAct extends WDFnL1Register {
    private static String __strRegisterName,__strSN,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm;

    private static String __strCHRegisterMsg = "*資料填寫不完整請確認\n" + "*帳號至少要1個英數字\n";
    private static String __strENRegisterMsg = "Data incomplete\n" + "Need Valid Account\n";

    private static String __strFileName = "TestDataFailRegisterByChineseAct";
    private static String __strFilePath;

    public void execute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseFailRegisterByChineseAct.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strSN = (String) alData.get(0);
        __strRegisterName = (String) alData.get(1);

        _etSN.sendKeys(__strSN);
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);
        strPrefixWord = _etRegisterName.getText();
        _etRegisterName.sendKeys(__strRegisterName);

        Thread.sleep(3000);
        if (_etRegisterName.getText().equals(strPrefixWord)){
            logger.info(strPassMsg);
        }else{
            logger.warn(strFailMsg);
            System.out.println("Read :  "+_etRegisterName.getText());
            System.out.println("Expect: "+strPrefixWord);
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
