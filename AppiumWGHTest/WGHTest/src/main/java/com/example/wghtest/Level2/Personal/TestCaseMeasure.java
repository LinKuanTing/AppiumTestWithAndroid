package com.example.wghtest.Level2.Personal;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.appSizeX;
import static com.example.wghtest.WGHTestBase.appSizeY;

public class TestCaseMeasure extends WDFnL2Measure {
    private String __strFilePath;
    private String __strFileName = "AccountInfo";

    private String __strBGUnitsID = "tw.com.wgh3h:id/tvUnit";
    private String __strTitleSessionID = "tw.com.wgh3h:id/tvFoodName";

    Logger logger;

    public TestCaseMeasure(){
        logger = LoggerFactory.getLogger(TestCaseMeasure.class);
    }

    public void SetInfo() throws IOException, InterruptedException {

        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);

        new WDFnL2Personal().clickMeasure();

        setMeasurements();

        if (checkSaveSuccessfullyMsg()){
            logger.info(strPassMsg);

            Thread.sleep(3000);
            FileWriter fw = new FileWriter(__strFilePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("###BP measurements\r\n");
            bw.write(getBPMeasurements()+"\r\n");
            bw.write("###BG measurements\r\n");
            bw.write(getBGMeasurements()+"\r\n");
            bw.flush();
            bw.close();
        }else {
            logger.warn(strFailMsg);
        }
    }

    public void ChangeBGUnits() throws InterruptedException {
        new WDFnL2Personal().clickMeasure();

        setBGUnits();
        if (checkSaveSuccessfullyMsg()){
            Thread.sleep(3000);
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

            new WDFnL2TitleMenuTabbar().clickBloodGlucose();
            Thread.sleep(3000);

            int pointY = adDriver.findElement(By.id(__strTitleSessionID)).getLocation().getY() +5;
            touchAction.press(PointOption.point(appSizeX/2,pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(appSizeX/2,appSizeY/8)).release().perform();
            String strReadUnit = adDriver.findElement(By.id(__strBGUnitsID)).getText();
            String strExpect = getBGUnits() == 0 ? "mg/dL":"mmol/L";

            if (strReadUnit.equals(strExpect)){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: BG unit is wrong.");
                System.out.println(" Read : "+strReadUnit);
                System.out.println("Expect: "+strExpect);
            }
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

            if (strReadUnit.equals("mmol/L")){
                new WDFnL2TitleMenuTabbar().clickTitlePersonal();
                new WDFnL2Personal().clickMeasure();
                clickMgdL();
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

            }


        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: No find save successfully message");
        }
    }
}
