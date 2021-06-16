package com.example.wghtest.Level2.Tabbar;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseTabbarPoints extends WDFnL2TabbarPoints{

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger = LoggerFactory.getLogger(TestCaseTabbarPoints.class);

    public void CheckTermsOfUse() throws IOException, InterruptedException {
        clickTerms();

        //get ReadContent
        Thread.sleep(5000);

        Set contextPoints = adDriver.getContextHandles();
        String strHTMLsource;
        try{
            adDriver.context((String) contextPoints.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) contextPoints.toArray()[0]);
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }

        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");


        //get ExpectContent
        __strFileName = "points_en";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        String strExpectContent = "";
        while (br.ready()){
            String strReadLine = br.readLine();
            strExpectContent += strReadLine;
        }

        String strReadContent = strHTMLsource;
        Document docRead = Jsoup.parse(strReadContent);
        Document docExpect = Jsoup.parse(strExpectContent);

        strExpectContent = docExpect.text().trim();
        strReadContent = docRead.text().trim();


        if(strExpectContent.equals(strReadContent)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Terms of Use is different between expect data and read data");
            System.out.println(strExpectContent.replaceAll("\n",""));
            System.out.println("~~~~~~~~~~~~~~~~~~~~");
            System.out.println(strReadContent.replaceAll("\n",""));
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void CheckWowPoints() throws IOException {
        int wowPoints;
        int earnedPoints;
        int usedPoints;

        while (true){
            try{
                wowPoints = getWowPoints();
                earnedPoints = getEarnedPoints();
                usedPoints = getUsedPoints();
                break;
            }catch (Exception eWeakNetwork){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                new WDFnL2TitleMenuTabbar().clickTarbarPoints();
            }
        }


        if (wowPoints == (earnedPoints + usedPoints)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Points show out is wrong");
            logger.debug("Wow Points: " + wowPoints);
            logger.debug("Earned points cut Used points is : " + (earnedPoints - usedPoints));
        }

        __strFileName = "AccountInfo";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###Wow Points\r\n");
        bw.write(wowPoints+"\r\n");
        bw.flush();
        bw.close();

    }

    public void CheckEarnedPoints() throws InterruptedException {
        clickEarned();
        int titleEarnedPoints = getEarnedPoints();
        int earnedPoints = getEarnedTotalPoints();

        if (titleEarnedPoints == earnedPoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: data is different");
            logger.debug("Title Earned Points: " + titleEarnedPoints);
            logger.debug("ScrollView  Points : " + earnedPoints);
        }
    }

    public void CheckUsedPoints(){
        clickUsed();
        int titleUsedPoints = getUsedPoints();
        int usedPoints = getUsedTotalPoints();

        if (titleUsedPoints == usedPoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: data is different");
            logger.debug("Title Used Points: " + titleUsedPoints);
            logger.debug("ScrollView Points: " + usedPoints);
        }
    }

    public void CheckDeadlinePoints(){
        clickDeadline();
        int titleTotalPoints = getWowPoints();
        int deadlinePoints = getDeadlineTotalPoints();

        if (titleTotalPoints == deadlinePoints){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: data is different");
            logger.debug("WowGoHealth Points: " + titleTotalPoints);
            logger.debug(" Deadline   Points: " + deadlinePoints);
        }

    }


}
