package com.example.wghtest.Level3.Progress;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseProgress extends WDFnL3Progress{

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger = LoggerFactory.getLogger(TestCaseProgress.class);

    public void CheckContentData() throws IOException {
        String strHtmlSource;
        try{
            contextWebview();
            strHtmlSource = adDriver.getPageSource();
            contextNative();
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            return;
        }


        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        //取得比較的預期資料
        __strFileName = "AccountInfo";
        String strNickName = "",strCurrentWeight = "",strCurrentBMI = "",strTargetWeight = "",strRemainderWeight = "",strWeeklyExercise = "";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("Nick Name")){
                strNickName = br.readLine();
            }else if (strReadLine.contains("CurrentWeight")){
                strCurrentWeight = br.readLine();
            }else if (strReadLine.contains("BMI")){
                strCurrentBMI = br.readLine();
            }else if(strReadLine.contains("TargetWeight")){
                strTargetWeight = br.readLine();
            }else if (strReadLine.contains("exercise time")){
                strWeeklyExercise = br.readLine();
            }
        }

        if (strNickName.equals("") || strCurrentWeight.equals("") || strCurrentBMI.equals("") || strTargetWeight.equals("") || strWeeklyExercise.equals("")){
            logger.warn(strFailMsg);
            logger.debug("Reason: can't find compare data");
            return;
        }


        Elements blkPickup;
        while(true){
            DecimalFormat decimalFormat = new DecimalFormat("##.0");
            strRemainderWeight = String.valueOf(decimalFormat.format(Double.parseDouble(strCurrentWeight) - Double.parseDouble(strTargetWeight)));


            Document doc = Jsoup.parse(strHtmlSource);
            blkPickup = doc.select("span[class=word_bu]");

            //idx[0] 為空白因此移除
            blkPickup.remove(0);

            //檢查是否有抓取到progress資料
            int wrongNum = 0;
            for (int i=0; i<blkPickup.size(); i++){
                String strData = blkPickup.get(i).text();
                if (strData.equals("－－")){
                    wrongNum++;
                }
            }
            if (wrongNum == blkPickup.size()){
                new WDFnL2TitleMenuTabbar().clickTabbarMenu();
                new WDFnL2TitleMenuTabbar().clickTabbarProgress();
            }else {
                break;
            }
        }



        //idx[0]->計畫倒數天數 [1]->Nickname [2]->目前體重 [3]->目前BMI [4]->與目標體重差 [5]->本週運動時間 [6]->飲食定時比例
        //比較[1] [2] [3] [4] [5] 是否正確


        int theSameNum = 0;
        for (int i=0; i<blkPickup.size(); i++){
            String strProgressData = blkPickup.get(i).text();
            switch (i){
                case 1:
                    if (strProgressData.equals(strNickName)){
                        theSameNum++;
                    }else {
                        System.out.println(" Read : " +strProgressData);
                        System.out.println("Expect: " +strNickName);
                    }
                    break;
                case 2:
                    if (strProgressData.equals(strCurrentWeight)){
                        theSameNum++;
                    }else {
                        System.out.println(" Read : " +strProgressData);
                        System.out.println("Expect: " +strCurrentWeight);
                    }
                    break;
                case 3:
                    if (strProgressData.equals(strCurrentBMI)){
                        theSameNum++;
                    }else {
                        System.out.println(" Read : " +strProgressData);
                        System.out.println("Expect: " +strCurrentBMI);
                    }
                    break;
                case 4:
                    if (strProgressData.equals(strRemainderWeight)){
                        theSameNum++;
                    }else {
                        System.out.println(" Read : " +strProgressData);
                        System.out.println("Expect: " +strRemainderWeight);
                    }
                    break;
                case 5:
                    if (strProgressData.equals(strWeeklyExercise)){
                        theSameNum++;
                    }else {
                        System.out.println(" Read : " +strProgressData);
                        System.out.println("Expect: " +strWeeklyExercise);
                    }
                    break;
                default:
                    break;
            }
        }


        if (theSameNum == 5){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: progress data is different with expect data");

        }
    }

    public void CheckAwardsDescription() throws IOException, InterruptedException {
        try {
            clickAwards();
        }catch (Exception eNoFindWebview){
            //因為元素在webview上
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            return;
        }

        clickInfo();

        ArrayList alRead = getDescription();

        __strFileName = "AwardsDescription";
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF8"));

        ArrayList alExpect= new ArrayList();
        while (br.ready()){
            String str = br.readLine();
            alExpect.add(str);
        }

        //System.out.println(alExpect);
        //System.out.println(alRead);

        int sameNum = 0;
        for (int i=0; i<alRead.size(); i++){
            for  (int j=0; j<alExpect.size(); j++){
                if (alExpect.get(j).toString().contains(alRead.get(i).toString())){
                    sameNum++;
                }

            }
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        if (sameNum == 12){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: some awards description is wrong.");
            System.out.println(alRead);
        }

        Thread.sleep(3000);
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
