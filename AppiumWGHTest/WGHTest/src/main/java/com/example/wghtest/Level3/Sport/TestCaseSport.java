package com.example.wghtest.Level3.Sport;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class TestCaseSport extends WDFnL3Sport{

    private static String __strFileName;
    private static String __strFilePath;

    private static String __strRecordImgID = "tw.com.wgh3h:id/date_icon";
    private static String __strRecordDownloadMsgID = "android:id/parentPanel";

    Logger logger = LoggerFactory.getLogger(TestCaseSport.class);

    public void LoadRecord(){
        try {
            int time = 0;
            while (true){
                clickRecord();
                sleep(8000);
                try{
                    adDriver.findElement(By.id(__strRecordDownloadMsgID));
                    adDriver.findElement(By.id(_strButton1ID)).click();
                    sleep(3000);
                    try {
                        adDriver.findElement(By.id(__strRecordImgID));
                    }catch (Exception eWeakNetWork){
                        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                        continue;
                        //time++;
                    }
                    break;
                }catch (Exception eNoFindElement){
                    //no show download record message
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    time++;
                }

                if (time == 3){
                    break;
                }
            }


            int timeClickPrevious = 0;
            while(true){
                try {
                    //adDriver.findElement(By.xpath("//*[@resource-id=\"tw.com.wgh3h:id/date_icon\"]"));
                    adDriver.findElement(By.id(__strRecordImgID));
                    logger.info(strPassMsg);
                    break;
                }catch (Exception eNoFindElement){
                    if (timeClickPrevious == 3){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: can't find the record which over 90 days");
                        break;
                    }
                    new WDFnL3SportRecord().clickPreviousYear();
                    timeClickPrevious++;
                    try{
                        //多判斷一次
                        //原因: 可能進來時在月初 尚未新增紀錄 因此沒有跳出下載紀錄的訊息
                        //      所以當點擊上一個月份 此時會跳出下載紀錄的訊息
                        adDriver.findElement(By.id(__strRecordDownloadMsgID));
                        adDriver.findElement(By.id(_strButton1ID)).click();
                    }catch (Exception e){ }

                }
            }

            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }catch (Exception eTimeOut){
            eTimeOut.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: load record failed or no data in three months");
        }

    }

    public void AddRecord(){
        ArrayList<String> alAddRecord = new ArrayList<>();
        try{

            setValue();
            alAddRecord.add(_strAddSportContent);
            setPedometerValue();
            alAddRecord.add(_strAddSportContent);


            clickRecord();

            WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
            wdFnL3SportRecord.clickToday();
            sleep(3000);
            ArrayList alDateRecord = wdFnL3SportRecord.getDateRecord();


            if (wdFnL3SportRecord.findRecord(alAddRecord,alDateRecord)){
                logger.info(strPassMsg);
            }
            else{
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find add data from record");
                logger.debug("add  record: " + alAddRecord);
                logger.debug("read record: " + alDateRecord);
            }
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        }catch (Exception eWeakNetWork){
            eWeakNetWork.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: by weak network");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }
    }

    public void UpdateRecord(){
        try{
            WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
            wdFnL3SportRecord.update();
            logger.info(strPassMsg);


            //更新運動檔案
            __strFileName = "SportRecord";
            wdFnL3SportRecord.clickToday();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String[] strTodayDate = dateFormat.format(Calendar.getInstance().getTime()).split("/");
            String strRecordDate = wdFnL3SportRecord.getTitleMonthYear() +"/"+ strTodayDate[2];

            ArrayList alRecordSport = wdFnL3SportRecord.getDateRecord();
            String strUpdateRecord = strRecordDate + "/" + alRecordSport.get(0);
            String strAddRecord = strRecordDate +"/" + alRecordSport.get(1);
            ArrayList alNewSport = new ArrayList();
            alNewSport.add(strUpdateRecord);
            alNewSport.add(strAddRecord);



            FnFileEvent fnFileEvent = new FnFileEvent();
            __strFilePath = fnFileEvent.getPath(__strFileName);
            FileOutputStream fos = new FileOutputStream(__strFilePath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "Big5"));
            for (int i=0; i<alNewSport.size(); ++i){
                bw.write(alNewSport.get(i)+"\r\n");
            }
            bw.flush();
            bw.close();

        }catch (Exception eNoFindElement){
            eNoFindElement.printStackTrace();
            logger.warn(strFailMsg);
        }
    }

    public void CompareRecord() throws IOException {
        //取得本週運動總時間 寫入檔案  之後測試會使用到
        __strFileName = "AccountInfo";
        String strExerciseTime = String.valueOf(new WDFnL3SportRecord().getWeeklySportTime());
        __strFilePath = new FnFileEvent().getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###Weekly exercise time\r\n");
        bw.write(strExerciseTime+"\r\n");
        bw.flush();
        bw.close();


        __strFileName = "SportRecord";

        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alReadData = fnFileEvent.getContent(__strFilePath);

        WDFnL3SportRecord wdFnL3SportRecord = new WDFnL3SportRecord();
        if (wdFnL3SportRecord.findRecord(alReadData)){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
        }
    }

    public void CheckNotUploadMsg() throws InterruptedException {
        saveAndUploadLater();
        String strNotUploadMsg = getNotUploadTitleMsg();
        int index = strNotUploadMsg.indexOf(":")+1;

        if (strNotUploadMsg.charAt(index) != '0'){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: can't find record of upload later" );
        }

    }
}
