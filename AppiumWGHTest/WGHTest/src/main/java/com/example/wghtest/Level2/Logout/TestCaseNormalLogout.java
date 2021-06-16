package com.example.wghtest.Level2.Logout;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseNormalLogout extends WDFnL2Logout{

    private String __strLoginMainPageID = "tw.com.wgh3h:id/rlLoginMain";

    public void execute() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseNormalLogout.class);
        logout();
        try {
            adDriver.findElement(By.id(__strLoginMainPageID));
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

    }
}
