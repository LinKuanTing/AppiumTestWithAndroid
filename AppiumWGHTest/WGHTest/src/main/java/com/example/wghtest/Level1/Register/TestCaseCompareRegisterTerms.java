package com.example.wghtest.Level1.Register;

import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Locale;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;


public class TestCaseCompareRegisterTerms extends WDFnL1Register {

    private static String __strFileNameEN = "conditions_en-us"; // User Terms .htm
    private static String __strFileNameZH = "conditions_zh-tw";
    private static String __strFilePath;

    public void execute() throws IOException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestCaseCompareRegisterTerms.class);

        clickUseTerms();

        sleep(5000);
        Set contextRegister = adDriver.getContextHandles();
        //System.out.println(contextRegister);


        //檢查是否能抓到webview context
        String strHTMLTerms;
        try{
            adDriver.context((String) contextRegister.toArray()[1]);
            strHTMLTerms = adDriver.getPageSource();
            adDriver.context((String) contextRegister.toArray()[0]);
        }catch(Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));//離開條款頁面
            sleep(3000);
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));//離開註冊頁面
            return;
        }



        String strLanguage = Locale.getDefault().getLanguage();
        System.out.println(strLanguage);

        Document doc = Jsoup.parse(strHTMLTerms);
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        String txtWebview = doc.text().replace(" ","");

        FnFileEvent fnFileEvent =  new FnFileEvent();

        __strFilePath =  fnFileEvent.getPath(__strFileNameEN);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String txtExpectData = "";
        while (br.ready()){
            txtExpectData += br.readLine();
        }
        doc = Jsoup.parse(txtExpectData);
        txtExpectData =  doc.text().replace(" ","");


        if (txtWebview.equals(txtExpectData)){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Webview content is different with expect data");
        }

        //返回註冊頁面
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        sleep(3000);

        //返回到登入頁面
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
