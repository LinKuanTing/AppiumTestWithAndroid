package com.example.wghtest.Level3.Advice;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseAdvice extends WDFnL3Advice{

    private static String __strFileName;
    private static String __strFilePath;


    Logger logger = LoggerFactory.getLogger(TestCaseAdvice.class);

    public TestCaseAdvice(){
        getBrowseLocation();
    }

    public void CheckBrowseAI() throws InterruptedException, ParseException {
        Thread.sleep(30000);

        while (true){
            try{
                WebDriverWait wait = new WebDriverWait(adDriver,120);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(_strBrowseFirst)));
                break;
            }catch (Exception eWeakNetwork){
                eWeakNetwork.printStackTrace();
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                new WDFnL2TitleMenuTabbar().clickAdvice();
            }
        }

        if (alMeasureTime.size() == 0){
            logger.warn(strFailMsg);
            logger.debug("Reason: get measure time fail");
        }


        int sameNum = 0;
        for (int i=0; i<alMeasureTime.size(); i++){
            //[0]->yyyy/MM/dd hh:mm    [1]->(item)
            String strData[] = alMeasureTime.get(i).toString().split(",");

            strData[0] = strData[0].replaceAll("/","-");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = sdf.parse(strData[0]);
            strData[0] = dateFormat.format(date);
            String strDate[] = strData[0].split(" ");



            for (int j=1; j<=alMeasureTime.size(); j++){
                WDFnL3Advice wdFnL3Advice;
                try {
                    wdFnL3Advice = new WDFnL3Advice(j);
                }catch (Exception eNoFindElement){
                    //當取第4筆AI紀錄時  必須下滑才能抓到
                    touchAction.press(PointOption.point(_pointX,_pointY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(_pointX,_pointY-_sizeY)).release().perform();
                    wdFnL3Advice = new WDFnL3Advice(j);
                }

                String strBrowseDate = wdFnL3Advice.getDate();
                String strBrowseContent = wdFnL3Advice.getContent();



                if ( !(strBrowseDate.contains(strDate[0]))){
                    continue;
                }

                if (strBrowseContent.contains(strDate[1]) && strBrowseContent.contains(strData[1])){
                    sameNum++;
                    break;
                }
            }
        }

        if(sameNum == alMeasureTime.size()){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: total measure have " +alMeasureTime.size() + " records, but only find " + sameNum +" records");
        }

    }

    public void  CheckStarComment(){
        String strType = new WDFnL3Advice(1).getType();
        String strDate = new WDFnL3Advice(1).getDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strTodayDate = dateFormat.format(Calendar.getInstance().getTime());

        if (!(strType.contains("ai") && strDate.contains(strTodayDate))){
            logger.warn(strFailMsg);
            logger.debug("Reason: First index not a ai browse or date not today.");
            System.out.println(strType+","+strDate);
            System.out.println(strTodayDate);
            return;
        }

        new WDFnL3Advice(1).clickBrowseIdx();

        clickBtnStar();
        try{
            clickMsgSend();
        }catch (Exception e){
            logger.warn(strFailMsg);
            logger.debug("Reason: No case to test or already commented");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }


        clickBackToAdvice();

        String strStarLevel = new WDFnL3Advice(1).getStarText();
        if (strStarLevel.equals("5")){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Fail to click star comment");
            System.out.println(strStarLevel);
        }

    }

    public void SubmitQuestion() throws InterruptedException {
        String strContent = "test";
        sendQuestion(strContent);
        Thread.sleep(5000);

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        new WDFnL2TitleMenuTabbar().clickAdvice();
        Thread.sleep(8000);

        int winX = adDriver.manage().window().getSize().width;
        int winY = adDriver.manage().window().getSize().height;
        touchAction.press(PointOption.point(winX/2,winY/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(winX/2,winY*4/5)).release().perform();
        Thread.sleep(15000);


        WDFnL3Advice wdFnL3Advice = new WDFnL3Advice(1);
        String strReadContent = wdFnL3Advice.getContent();

        if (strContent.equals(strReadContent)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Can't find question content.");
            System.out.println("Read: "+strReadContent);
        }

    }


    public void CheckServices() throws IOException, InterruptedException {
        __strFileName = "services_en";

        clickServices();
        clickServicesGSH();

        Set context = adDriver.getContextHandles();
        //System.out.println(context);

        //get app webview content
        String strHTMLsource;
        try{
            adDriver.context((String) context.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) context.toArray()[0]);
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }


        Document docRead = Jsoup.parse(strHTMLsource);
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        //get file webview content
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String txtExpectData = "";
        while(br.ready()){
            txtExpectData += br.readLine();
        }
        Document docExpect = Jsoup.parse(txtExpectData);

        if (docRead.text().equals(docExpect.text())){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: content is different");
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        Thread.sleep(5000);

    }

}
