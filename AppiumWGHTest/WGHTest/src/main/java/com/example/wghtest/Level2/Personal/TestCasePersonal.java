package com.example.wghtest.Level2.Personal;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCasePersonal extends WDFnL2Personal {

    private String __strFilePath;
    private String __strFileName = "AccountInfo";

    private static String __strActID = "tw.com.wgh3h:id/tvAccount";
    private static String __strNickName = "tw.com.wgh3h:id/etNickname";

    Logger logger;

    public TestCasePersonal(){
        logger = LoggerFactory.getLogger(TestCasePersonal.class);
    }

    public void SetInfo() throws IOException {
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);

        clickPersonal();

        String strAccount = adDriver.findElement(By.id(__strActID)).getText();
        String strNickName = adDriver.findElement(By.id(__strNickName)).getText();

        setPersonalInfo();


        if (checkSaveSuccessfullyMsg()){
            logger.info(strPassMsg);

            FileWriter fw = new FileWriter(__strFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###Account\r\n");
            bw.write(strAccount+"\r\n");
            bw.write("###Nick Name\r\n");
            bw.write(strNickName+"\r\n");
            bw.write("###Gender\r\n");
            bw.write(_intGender+"\r\n");
            bw.flush();
            bw.close();
        }else {
            logger.warn(strFailMsg);
        }

    }
}
