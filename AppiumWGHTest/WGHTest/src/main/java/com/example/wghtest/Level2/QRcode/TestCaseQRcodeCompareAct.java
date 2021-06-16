package com.example.wghtest.Level2.QRcode;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;

public class TestCaseQRcodeCompareAct extends WDFnL2QRcode{

    private static String __strFileName = "AccountInfo";
    private static String __strFilePath;

    Logger logger;

    public TestCaseQRcodeCompareAct(){
        logger = LoggerFactory.getLogger(TestCaseQRcodeCompareAct.class);
    }

    public void execute() throws IOException {
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        new WDFnL2TitleMenuTabbar().clickTitleQRcode();

        String strExpectAct = (String) fnFileEvent.getContent(__strFilePath).get(0);
        String strReadAct = getAccount();

        if (strExpectAct.equals(strReadAct)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Expect: " + strExpectAct);
            logger.debug(" Read : " + strReadAct);
        }

        clickOk();
    }
}
