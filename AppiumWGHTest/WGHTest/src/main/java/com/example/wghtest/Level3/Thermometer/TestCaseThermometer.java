package com.example.wghtest.Level3.Thermometer;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.Advice.WDFnL3Advice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class TestCaseThermometer extends WDFnL3Thermometer {

    private static String __strFileName;
    private static String __strFilePath;

    private static String __strCHMessage = "儲存成功";
    private static String __strENMessage = "Successfully saved";

    Logger logger = LoggerFactory.getLogger(TestCaseThermometer.class);

    public TestCaseThermometer(){ }

    public void BluetoothMeasure() throws InterruptedException {
        scanBle();

        sleep(5000);

        //檢查點擊藍牙後 是否跳出搜尋中訊息
        try {
            adDriver.findElement(By.id(_strProcessID));
        }catch (Exception eNoFindElement){
            //System.out.println("No find scan device message.");
        }



        //收到量測資料後 跳出上傳成功訊息 點擊確認
        int scanTime = 0;
        while (true){
            sleep(10000);
            try {
                String strMessage = adDriver.findElement(By.id(_strBLEMessageID)).getText();
                if (strMessage.equals(__strCHMessage) || strMessage.equals(__strENMessage)){
                    adDriver.findElement(By.id(_strButton1ID)).click();
                    logger.info(strPassMsg);
                    break;
                }
            }catch (Exception e){
                try {
                    adDriver.findElement(By.id(_strProcessID));
                    if (scanTime > 10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: Can't get measure data.");
                        adDriver.findElement(By.id(_strBleCancelID)).click();
                        break;
                    }else{
                        scanTime++;
                    }
                    continue;
                }catch (Exception eNoFindElement){
                    logger.warn(strFailMsg);
                    logger.debug("Reason: Can't get measure data.");
                    break;
                }
            }
        }


    }


    public void LoadRecord(){

        try{
            for(int i=0; i<10; ++i){
                clickRecord();
                try{
                    new WDFnL3ThermometerRecord(1);
                    logger.info(strPassMsg);
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    return;
                }catch (Exception eWeakNetwork){
                    if (i == 10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: repeat click record to loading with fail for 10 times");
                    }
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                }
            }
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
            logger.debug("Reason: load record time out for 15 seconds");
        }

    }

    public void AddRecord(){
        String strDate = getDate();
        //測試推播所需要的資料
        new WDFnL3Advice().alMeasureTime.add(strDate+",體溫");
        try{
            setValue();
            sleep(20000);
            clickRecord();
            if (strDate.equals(new WDFnL3ThermometerRecord(1).getDate())){
                logger.info(strPassMsg);
            }
            else{
                logger.warn(strFailMsg);
                logger.debug("Reason: Add's record is different with first record");
            }
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: Saved error");
            //判斷是否在Weight頁面
            try {
                adDriver.findElement(By.id(_strTimeID));
            }catch (Exception eNoFindElement){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
        }
    }

    public void UpdateRecord(){

        try{
            WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord(1);
            wdFnL3ThermometerRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            eNoFindElement.printStackTrace();
            logger.warn(strFailMsg);
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }

    }

    public void CompareRecord() throws Exception {
        __strFileName = "ThermometerRecord";

        //取記錄前1筆資料
        ArrayList alTemp = new ArrayList();
        try{
            for (int i=1; i<=1; i++){
                WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord(i);
                String recordData = "";
                recordData += wdFnL3ThermometerRecord.getType() + "/" + wdFnL3ThermometerRecord.getTemp().substring(0,4);
                alTemp.add(recordData);
            }
        }catch (Exception eWeakNetwork){
            if (alTemp.size() == 0) {
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find record by weak network");
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
            return;
        }


        //儲存記錄前1筆資料
        WDFnL3ThermometerRecord wdFnL3ThermometerRecord = new WDFnL3ThermometerRecord();
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileOutputStream fos = new FileOutputStream(__strFilePath);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "Big5"));
        for (int i=0; i<1; ++i){
            if (i==0) {
                bw.write(alTemp.get(i) + "\r\n");
                continue;
            }
        }
        bw.flush();
        bw.close();


        //登出切換帳號
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));  //返回 從紀錄列表離開至血壓頁面
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();  //點擊首頁
        DeleteLocalDataBase();  //切換帳號刪除DB在切回原帳號

        new WDFnL2TitleMenuTabbar().clickThermometer();
        //進入紀錄列表 有時可能下載歷史紀錄失敗 沒有任何紀錄顯示
        while (true){
            clickRecord();  //進入紀錄
            try {
                new WDFnL3ThermometerRecord(1);
                break;
            }catch (Exception eWeekNetWork){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                continue;
            }
        }


        sleep(3000);
        ArrayList alTempNew = new ArrayList();
        try{
            for (int i=1; i<=1; i++){
                WDFnL3ThermometerRecord wdFnL3ThermometerRecordNew = new WDFnL3ThermometerRecord(i);
                String recordData = "";
                recordData += wdFnL3ThermometerRecordNew.getType() + "/" + wdFnL3ThermometerRecordNew.getTemp().substring(0,4);
                alTempNew.add(recordData);
            }
        }catch (Exception eWeakNetwork){
            if (alTempNew.size() == 0) {
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find record by weak network");
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
            return;
        }



        //呼叫方法比較 預期資料idx[0] 比較 紀錄資料[0]
        if (wdFnL3ThermometerRecord.compare(alTempNew)){
            logger.info(strPassMsg);
        }
        else{
            logger.warn(strFailMsg);
            logger.debug("Expect data: " + wdFnL3ThermometerRecord.alReadLine);
            logger.debug("Read  data : " + alTempNew);
        }


    }

    public void CheckDataMean() throws IOException {
        __strFileName = "temp_colormean_en";

        clickDataMean();

        Set contextTemp = adDriver.getContextHandles();
        //System.out.println(contextTemp);

        //get app webview content
        String strHTMLsource;
        try{
            adDriver.context((String) contextTemp.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) contextTemp.toArray()[0]);
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            return;
        }

        Document docRead = Jsoup.parse(strHTMLsource);
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        //get file webview content
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);

        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String txtExpectData = "";
        while(br.ready()){
            String strReadLine = br.readLine();
            txtExpectData = txtExpectData + strReadLine;
        }
        Document docExpect = Jsoup.parse(txtExpectData);

        //去掉開頭空格
        String appWebview = docRead.text().trim();
        String fileWebview = docExpect.text().trim();

        if (appWebview.equals(fileWebview)){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Webview's contents aren't the same");
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
}
