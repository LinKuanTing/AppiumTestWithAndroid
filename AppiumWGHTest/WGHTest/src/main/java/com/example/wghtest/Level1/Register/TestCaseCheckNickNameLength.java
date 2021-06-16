package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseCheckNickNameLength extends WDFnL1Register {
    private static String __strRegisterName,__strSN,__strNickName,__strMail,__strPhone,__strRegisterPwd,__strRegisterPwdCfm;

    private static String __strFileName = "TestDataCheckNickNameLength";
    private static String __strFilePath;

    public void execute() throws IOException {
        Logger logger = LoggerFactory.getLogger(TestCaseCheckNickNameLength.class);
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        __strNickName = (String) alData.get(2);

        _etNickName.sendKeys(__strNickName);

        String strRead = _etNickName.getText();

        if (strRead.length() == 20){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Get nick name length " + strRead.length());
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
