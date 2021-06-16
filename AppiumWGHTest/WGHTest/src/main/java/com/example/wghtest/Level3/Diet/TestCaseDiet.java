package com.example.wghtest.Level3.Diet;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.Level3.Weight.WDFnL3Weight;
import com.example.wghtest.Level3.Weight.WDFnL3WeightRecord;
import com.example.wghtest.other.FnFileEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.tmoutLoading;

public class TestCaseDiet extends WDFnL3Diet{

    private static String __strFileName;
    private static String __strFilePath;
    private static String[] __strDateMsg = {"not regular meal time","irregular","Fasting","非正餐","禁食"};

    private static String __strRecordDownloadMsgID = "android:id/parentPanel";
    private static String __strWebviewID = "android:id/content";

    Logger logger = LoggerFactory.getLogger(TestCaseDiet.class);

    public TestCaseDiet() throws Exception {

        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
        //在進行Diet測試項目時  會檢查儲存帳號資料是否齊全 以方便進行接下來的測試
        //主要是重新存取當前體重資料
        __strFileName = "AccountInfo";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        boolean isExistWeightData = false;
        while (br.ready()) {
            String strReadLine = br.readLine();
            if (strReadLine.contains("CurrentWeight")) {
                isExistWeightData = true;
                break;
            }
        }

        String strWeight = "";
        if (!isExistWeightData){
            new WDFnL2TitleMenuTabbar().clickWeight();

            //此可能發生下載歷史紀錄失敗  因此重複近來直到下載成功
            while (true){
                new WDFnL3Weight().clickRecord();
                try {
                    strWeight = new WDFnL3WeightRecord(1).getWeight();
                    break;
                }catch (Exception eWeekNetWork){
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    continue;
                }
            }

            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }

        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###CurrentWeight\r\n");
        bw.write(strWeight+"\r\n");
        bw.flush();
        bw.close();


        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
        new WDFnL2TitleMenuTabbar().clickDiet();

    }


    public void LoadRecord(){
        try {
            clickRecord();
            try {
                adDriver.findElement(By.id(__strRecordDownloadMsgID));
                adDriver.findElement(By.id(_strButton1ID)).click();
            }catch (Exception eNoFindElement){ }

            try{
                new WDFnL3DietRecord(1);
                logger.info(strPassMsg);
            }catch (Exception eNoFindElement){
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find the record");
            }

        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: loading record time out for 15 seconds");
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

    }

    public void CheckEatingTimeMsg() throws IOException {
        __strFileName = "AccountInfo";
        ArrayList alEatingTime = new ArrayList();

        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        while(br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.contains("mealtime")){
                alEatingTime.add(br.readLine());
            }
        }


        //將時間轉換成分鐘
        String strMealTimeByMinutes = "";
        for(int i=0; i<alEatingTime.size(); i++){
            String strMealTime[] = alEatingTime.get(i).toString().split("~");
            // strMealTime[0]開始時間 strMealTime[1]結束時間
            for(int j=0; j<strMealTime.length; j++){
                String[] strTime = strMealTime[j].split(":");
                //strTime[0]小時 strTime[1]分鐘
                int timeMinutes = Integer.parseInt(strTime[0])*60+Integer.parseInt(strTime[1]);
                strMealTimeByMinutes += timeMinutes ;
                if( j == 0){
                    strMealTimeByMinutes += "~";
                }
                else if ( j!=0 && i!=alEatingTime.size()-1){
                    strMealTimeByMinutes += ",";
                }

            }
        }
        /*
        //處理禁食時間到凌晨問題
        String strDuringMeal[] = strMealTimeByMinutes.split(",");
        //[0]早餐 [1]中餐 [2]晚餐 [3]禁食
        String strFasting[] = strDuringMeal[3].split("~");
        //[0]禁食開始時間 [1]禁食結束時間
        if (Integer.parseInt(strFasting[0])>Integer.parseInt(strFasting[1])){
            strMealTimeByMinutes = strDuringMeal[1]+","+strDuringMeal[2]+","+strDuringMeal[3]+","+strFasting[0]+"~"+24*60+","+0+"~"+strFasting[1];
        }
        */

        String strMealTime[] = strMealTimeByMinutes.split(",");
        String[] timeBreakfast = strMealTime[0].split("~");
        String[] timeLunch = strMealTime[1].split("~");
        String[] timeDinner = strMealTime[2].split("~");
        String[] timeFasting = strMealTime[3].split("~");

        _btnCategory.click();
        List<AndroidElement> listCategoryOption = adDriver.findElementsByClassName(_strCategoryListViewCN);
        for(int i=0; i<listCategoryOption.size(); i++){
            listCategoryOption.get(i).click();
            adDriver.findElement(By.id(_strButton1ID)).click();

            try {
                //跳出重複三餐的訊息
                adDriver.findElement(By.id(_strButton1ID)).click();
            }catch (Exception eNoFindElement){}

            String[] strDateTime = getDate().split(" ");
            //[0]日期 [1]時間
            String[] strDate = strDateTime[1].split(":");
            //[0]時 [1]分
            int time = Integer.parseInt(strDate[0])*60 + Integer.parseInt(strDate[1]);
            switch(i){
                case 0:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateMsg().contains(__strDateMsg[1]) || getDateMsg().contains(__strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is fail");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeBreakfast[0]) && time < Integer.parseInt(timeBreakfast[1])){
                        if ( (getDateMsg().contains(__strDateMsg[0]) || getDateMsg().contains(__strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Breakfast time message is fail");
                            return;
                        }
                    }
                    break;
                case 1:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateMsg().contains(__strDateMsg[1]) || getDateMsg().contains(__strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is fail");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeLunch[0]) && time < Integer.parseInt(timeLunch[1])){
                        if ( (getDateMsg().contains(__strDateMsg[0]) || getDateMsg().contains(__strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Lunch time message is fail");
                            return;
                        }
                    }
                    break;
                case 2:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])){
                        if ( !(getDateMsg().contains(__strDateMsg[1]) || getDateMsg().contains(__strDateMsg[3])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is fail");
                            return;
                        }
                    }else if(time > Integer.parseInt(timeDinner[0]) && time < Integer.parseInt(timeDinner[1])){
                        if ( (getDateMsg().contains(__strDateMsg[0]) || getDateMsg().contains(__strDateMsg[2])) ){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Dinner time message is fail");
                            return;
                        }
                    }
                    break;
                default:
                    if (time > Integer.parseInt(timeFasting[0]) && time < Integer.parseInt(timeFasting[1])) {
                        if (!(getDateMsg().contains(__strDateMsg[1]) || getDateMsg().contains(__strDateMsg[3]))) {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: Fasting time message is fail");
                            return;
                        }
                    }
            }

            if (i!=listCategoryOption.size()-1){
                _btnCategory.click();
            }else {
                logger.info(strPassMsg);
            }


        }


    }

    public void AddRecord(){
        ArrayList alAddRecord = new ArrayList();
        try{

            try {
                setValue("Breakfast");
                alAddRecord.add(_strAddDietContent);
            }catch (Exception eNoFindElement){
                //重複用餐類別訊息
                adDriver.findElement(By.id(_strButton1ID)).click();
            }

            setValue("Beverage");
            alAddRecord.add(_strAddDietContent);

            clickRecord();

            //抓取今日日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String strTodayDate = dateFormat.format(Calendar.getInstance().getTime());
            //抓取日期內的紀錄
            WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord();
            ArrayList alRecord = wdFnL3DietRecord.getDateRecord(strTodayDate);

            //新增的資料與日期內比較是否相同
            int sameNum = 0;
            for (int i=0; i<alAddRecord.size(); i++){
                for (int j=1; j<alRecord.size(); j++){
                    String strAddRecord[] = alAddRecord.get(i).toString().split(",");
                    if (alRecord.get(0).toString().contains(strAddRecord[0]) && alRecord.get(j).toString().contains(strAddRecord[1])){
                        sameNum++;
                        break;
                    }
                }
            }
            if (sameNum == alAddRecord.size()){
                logger.info(strPassMsg);

            }
            else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Can't find add data");
                logger.debug("add data: " + alAddRecord);
                logger.debug(" record : " + alRecord);
            }

            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        }catch (Exception eWeakNetWork){
            logger.warn(strFailMsg);
            logger.debug("Reason: fail by weak network");
        }

    }

    public void CheckExistMainMealMsg() throws InterruptedException {

        try {
            setValue("Lunch");
        }catch (Exception eNoFindElement){
            //紀錄裡已有重複類別
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }

        try {
            setValue("Lunch");
            logger.warn(strFailMsg);
        }catch (Exception eNoFindElement){
            adDriver.findElement(By.id(_strButton1ID)).click();
            logger.info(strPassMsg);
        }

    }

    public void UpdateRecord(){
        try{
            //index[1] 為日期紀錄 index[2]為用餐紀錄
            clickRecord();
            WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord(2);
            wdFnL3DietRecord.update();
            logger.info(strPassMsg);


            //更新檔案紀錄
            __strFileName = "DietRecord";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String strTodayDate = dateFormat.format(Calendar.getInstance().getTime());
            ArrayList alTodayRecord =  new WDFnL3DietRecord().getDateRecord(strTodayDate);
            String strUpdateRecord = strTodayDate +","+ alTodayRecord.get(alTodayRecord.size()-1);
            String strAddRecord = strTodayDate +","+ alTodayRecord.get(alTodayRecord.size()-2);


            ArrayList alNewDied = new ArrayList();
            alNewDied.add(strUpdateRecord);
            alNewDied.add(strAddRecord);


            FnFileEvent fnFileEvent = new FnFileEvent();
            __strFilePath = fnFileEvent.getPath(__strFileName);
            FileWriter fw = new FileWriter(__strFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i=0; i<alNewDied.size(); ++i){
                bw.write(alNewDied.get(i)+"\r\n");
            }
            bw.flush();
            bw.close();



        }catch (Exception eNoFindElement){
            eNoFindElement.printStackTrace();
            logger.warn(strFailMsg);
        }
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void CompareRecord() throws IOException, InterruptedException {
        __strFileName = "DietRecord";

        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alReadText = fnFileEvent.getContent(__strFilePath);

        clickRecord();
        int sameNum = 0;
        WDFnL3DietRecord wdFnL3DietRecord = new WDFnL3DietRecord();
        for(int i=0; i<alReadText.size(); i++){
            String[] strReadData = alReadText.get(i).toString().split(",");
            //[0]日期資料 [1]用餐資料
            ArrayList alDateRecord = wdFnL3DietRecord.getDateRecord(strReadData[0]);
            //System.out.println(strReadData[0]+","+strReadData[1]);
            //System.out.println(alDateRecord);
            for(int j=1; j<alDateRecord.size(); j++){
                if (alDateRecord.get(0).toString().contains(strReadData[0]) && alDateRecord.get(j).toString().contains(strReadData[1])){
                    sameNum++;
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    break;
                }else {
                    if (j==alDateRecord.size()-1){
                        System.out.println("File text data is wrong, can't be found.");
                        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    }
                }
            }

            if (i != alReadText.size()-1){
                new WDFnL3Diet().clickRecord();
            }

        }

        if (sameNum == alReadText.size()){
            logger.info(strPassMsg);
        }else {
            System.out.println("find record: " + sameNum);
            logger.warn(strFailMsg);
        }

    }

    public void CheckNotUploadMsg(){
        saveAndUploadLater();
        String strNotUploadedMsg = getNotUploadTitleMsg();
        int index = strNotUploadedMsg.indexOf(":")+1;

        if (strNotUploadedMsg.charAt(index) != '0'){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: can't find record of upload later" );
        }
    }

    public void CheckDataMean() throws IOException {
        __strFileName = "DietSuggestionData";

        clickSuggestions();
        WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(__strWebviewID)));

        //取得 Native APP 與 Webview
        Set contextSuggestion = adDriver.getContextHandles();

        //切換到Webview
        String strHTMLsource;
        try{
            adDriver.context((String) contextSuggestion.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) contextSuggestion.toArray()[0]);
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }

        Document docRead = Jsoup.parse(strHTMLsource);
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        Elements category = docRead.select("span[class =\"word\"]");

        String strSuggestionsData = "";
        for (int i=0; i<category.size(); i++){
            if (i != category.size()-1){
                strSuggestionsData += category.get(i).text() + "/";
            } else {
                strSuggestionsData += category.get(i).text();
            }
        }

        //計算當前體重每日所需熱量
        String strDailyCal = String.valueOf(getDailyCal());
        if (strDailyCal.equals("-1")){
            logger.warn(strFailMsg);
            logger.debug("Reason: some data in need can't get");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }

        //讀取檔案
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        String strExpectData = "";
        while(br.ready()) {
            strExpectData = br.readLine();
            if (strExpectData.contains(strDailyCal)){
                break;
            }
        }

        if (!strSuggestionsData.equals(strExpectData)){
            logger.warn(strFailMsg);
            logger.debug("Expect daily cal: " + strExpectData);
            logger.debug(" Read  daily cal: " + strSuggestionsData);
        }else {
            logger.info(strPassMsg);
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

    }



}
