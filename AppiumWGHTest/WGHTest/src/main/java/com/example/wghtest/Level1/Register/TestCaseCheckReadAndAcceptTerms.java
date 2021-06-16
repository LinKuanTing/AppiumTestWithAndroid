package com.example.wghtest.Level1.Register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static java.lang.Thread.sleep;

public class TestCaseCheckReadAndAcceptTerms extends WDFnL1Register {

    public void execute() throws Exception{
        Logger logger = LoggerFactory.getLogger(TestCaseCheckReadAndAcceptTerms.class);

        touchAction.press(PointOption.point(width/2,height*4/5)).waitAction().moveTo(PointOption.point(width/2,height/5)).release().perform();
        clickReadAndAccept();

        boolean isElementEnable = _btnTitleRegister.isEnabled();
        if (isElementEnable){


            clickReadAndAccept();
            isElementEnable = _btnTitleRegister.isEnabled();
            if (!isElementEnable){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: button Register should be false for attributes of enable.");
            }


        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: button Register should be enable.");
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }


}
