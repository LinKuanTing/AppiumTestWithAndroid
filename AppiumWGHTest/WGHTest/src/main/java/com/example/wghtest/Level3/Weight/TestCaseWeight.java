package com.example.wghtest.Level3.Weight;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.Advice.WDFnL3Advice;
import com.example.wghtest.Level3.FnPicEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.Level1.Register.WDFnL1Register.height;
import static com.example.wghtest.Level1.Register.WDFnL1Register.width;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class TestCaseWeight extends WDFnL3Weight {

    private static String __strFileName;
    private static String __strFilePath;

    protected int _intGender,_intAge;
    protected Double _dbTopBMI;
    protected Double _dbYoungMaleTopBodyfat,_dbOldMaleTopBodyfat;
    protected Double _dbYoungFemaleTopBodyfat,_dbOldFemaleTopBodyfat;
    protected static String _strENBodyfatGoodNote,_strENBodyfatBadNote,_strENWeightGoodNote,_strENWeightBadNote;
    protected static String _strCHBodyfatGoodNote,_strCHBodyfatBadNote,_strCHWeightGoodNote,_strCHWeightBadNote;

    private static String __strCHMessage = "儲存成功";
    private static String __strENMessage = "Successfully saved";

    private static String __imgTips0Path = "D:\\TestData\\TestPic\\Tips1.png";
    private static String __imgTips1Path = "D:\\TestData\\TestPic\\Tips2.png";
    private static String __imgTips2Path = "D:\\TestData\\TestPic\\Tips3.png";
    private static String __imgTips3Path = "D:\\TestData\\TestPic\\Tips4.png";

    protected AndroidElement _lvRecord;

    Logger logger = LoggerFactory.getLogger(TestCaseWeight.class);

    public TestCaseWeight(){
        this._strENBodyfatGoodNote = "Bodyfat good";
        this._strENBodyfatBadNote = "Bodyfat over";
        this._strENWeightGoodNote = ",weight and BMI good";
        this._strENWeightBadNote = ",weight and BMI over";
        this._strCHBodyfatGoodNote = "體脂符合標準";
        this._strCHBodyfatBadNote = "體脂超標";
        this._strCHWeightGoodNote = "體重和BMI符合標準";
        this._strCHWeightBadNote = "體重和BMI超標";
    }

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
            sleep(20000);
            try {
                String strMessage = adDriver.findElement(By.id(_strMessageID)).getText();
                if (strMessage.equals(__strCHMessage) || strMessage.equals(__strENMessage)){
                    adDriver.findElement(By.id(_strButton1ID)).click();
                    logger.info(strPassMsg);
                    break;
                }
            }catch (NoSuchElementException e){
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
            for (int i=1; i<=10; i++){
                clickRecord();
                try{
                    new WDFnL3WeightRecord(1);
                    logger.info(strPassMsg);
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    return;
                }catch(Exception eWeakNetwork){
                    if (i == 10){
                        logger.warn(strFailMsg);
                        logger.debug("Reason: repeat click record to loading with fail for 10 times");
                    }
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                }
            }
        }catch (Exception eTimeOut){
            logger.warn(strFailMsg);
            logger.debug("Reason: load record time out for 15 seconds");
        }

    }

    public void AddRecord(){
        String strDate = getDate();
        //測試推播所需要的資料
        new WDFnL3Advice().alMeasureTime.add(strDate+",體重");
        try{
            setValue();
            sleep(15000); //新增紀錄後會跳通知 因此等待10秒再繼續動作
            clickRecord();
            if (strDate.equals(new WDFnL3WeightRecord(1).getDate())){
                logger.info(strPassMsg);
            }
            else{
                logger.warn(strFailMsg);
                logger.debug("Reason: add's record is different with first record");
            }
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: fail by weak network");
            //判斷是否在Weight頁面
            try {
                adDriver.findElement(By.id(_strTimeID));
            }catch (Exception eNoFindElement){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
        }

    }

    public void UpdateRecord(){

        try {
            WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
            wdFnL3WeightRecord.update();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

    }

    public void DeleteRecord(){

        try {
            WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
            wdFnL3WeightRecord.delete();
            logger.info(strPassMsg);
        }catch (Exception eNoFindElement){
            logger.warn(strFailMsg);
        }

    }

    public void CompareRecord() throws Exception {

        __strFileName = "WeightRecord";
        //取剛新增的紀錄比較
        ArrayList alWeight = new ArrayList<>();
        try{

            for (int i=1; i<=1; ++i){
                WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(i);
                String recordData = "";
                recordData += wdFnL3WeightRecord.getDate() + "/" + wdFnL3WeightRecord.getWeight() ;
                alWeight.add(recordData);
            }
        }catch (Exception eWeakNetwork){
            if (alWeight.size() == 0){
                eWeakNetwork.printStackTrace();
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find record by weak network(1)");
            }
            return;
        }


        //儲存記錄前1筆資料
        WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord();
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i=0; i<1; ++i){
            if (i==0) {
                bw.write(alWeight.get(i) + "\r\n");
                continue;
            }
        }
        bw.flush();
        bw.close();

        //登出切換帳號
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));  //返回 從紀錄列表離開至體重頁面
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();  //點擊首頁
        DeleteLocalDataBase();  //切換帳號刪除DB在切回原帳號

        new WDFnL2TitleMenuTabbar().clickWeight();   //進入體重
        adDriver.findElement(By.id(_strOffID)).click();


        //進入紀錄列表 有時可能下載歷史紀錄失敗 沒有任何紀錄顯示
        while (true){
            clickRecord();  //進入紀錄
            try {
                new WDFnL3WeightRecord(1);
                break;
            }catch (Exception eWeekNetWork){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                continue;
            }
        }

        //重新進入紀錄下載資料取第一筆資料比較
        sleep(3000);
        ArrayList alWeightNew = new ArrayList<>();
        try{

            for (int i=1; i<=5; i++) {
                if (i!=1){
                    clickRecord();
                }
                try {
                    new WDFnL3WeightRecord(1);
                    break;
                } catch (Exception eWeakNetwork) {
                    if (i == 5) {
                        logger.warn(strFailMsg);
                        logger.debug("Reason: repeat click record to loading with fail for 5 times");
                    }
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                }
            }


            for (int i=1; i<=1; ++i){
                WDFnL3WeightRecord wdFnL3WeightRecordNew = new WDFnL3WeightRecord(i);
                String recordData = "";
                recordData += wdFnL3WeightRecordNew.getDate() + "/" + wdFnL3WeightRecordNew.getWeight() ;
                alWeightNew.add(recordData);
            }
        }catch (Exception eWeakNetwork){
            if (alWeightNew.size() == 0){
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find record by weak network(2)");

            }
            return;
        }

        //呼叫方法比較 預期資料idx[0] 比較 紀錄資料[0]
        if(wdFnL3WeightRecord.compare(alWeightNew)){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Expect data :" + wdFnL3WeightRecord.alReadLine);
            logger.debug("Read  data  :" + alWeightNew);
        }

        //取前幾筆資料比較
        /*
        WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord();
        _lvRecord = (AndroidElement) adDriver.findElementByXPath(wdFnL3WeightRecord._strOneRecord);
        int startX = _lvRecord.getLocation().getX()+_lvRecord.getSize().getWidth()/2;
        int startY = _lvRecord.getLocation().getY()+_lvRecord.getSize().getHeight();
        int endX = startX;
        int endY = startY-_lvRecord.getSize().getHeight();


        ArrayList alWeight = new ArrayList<>();

        try{
            //取得前3筆體重資料
            A:for (int i=1; i<=3; i++){
                WDFnL3WeightRecord wdFnL3WeightRecordidx = new WDFnL3WeightRecord(1);
                alWeight.add(wdFnL3WeightRecordidx.getWeight());

                wdFnL3WeightRecord.tchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY-10)).release().perform();

                while (true){
                    String strPageSource = adDriver.getPageSource();
                    if (_lvRecord.getLocation().getY() <= adDriver.findElement(By.id("tw.com.wgh3h:id/llallGroupItems")).getLocation().getY()){
                        Thread.sleep(3000);
                        wdFnL3WeightRecord.tchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(startX,startY-40)).release().perform();
                        //滑到最底部
                        if (strPageSource.equals(adDriver.getPageSource())){
                            alWeight.add(new WDFnL3WeightRecord(2).getWeight());
                            break A;
                        }
                    }else {
                        break;
                    }
                }
            }
        }catch (Exception eWeakNetwork){
            logger.warn(strFailMsg);
            logger.debug("Reason: can't find record by weak network");
            return;
        }


        if(wdFnL3WeightRecord.compare(alWeight)){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Expect data :" + wdFnL3WeightRecord.alReadLine);
            logger.debug("Read  data  :" + alWeight);
        }

        __strFileName = "AccountInfo";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###CurrentWeight\r\n");
        bw.write(alWeight.get(0)+"\r\n");
        bw.flush();
        bw.close();
*/

    }

    public void CheckWarningMsg() throws IOException, InterruptedException {
        sleep(5000);

        this._tvBodyfatNote = (AndroidElement) adDriver.findElement(By.id(_strBodyfatNoteID));
        this._tvWeightNote = (AndroidElement) adDriver.findElement(By.id(_strWeightNoteID));

        __strFileName = "TestDataWeightCheckWarningMsg";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        _dbTopBMI = Double.parseDouble((String) alData.get(0));
        _dbYoungMaleTopBodyfat = Double.parseDouble((String) alData.get(1));
        _dbOldMaleTopBodyfat = Double.parseDouble((String) alData.get(2));
        _dbYoungFemaleTopBodyfat = Double.parseDouble((String) alData.get(3));
        _dbOldFemaleTopBodyfat = Double.parseDouble((String) alData.get(4));

        __strFileName = "AccountInfo";
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"Big5"));
        while (br.ready()){
            String strReadLine = br.readLine();
            if (strReadLine.indexOf("Gender")!=-1 || strReadLine.indexOf("Age")!=-1){
                alData.add(br.readLine());
            }
        }
        _intGender = Integer.parseInt((String) alData.get(5));
        _intAge = Integer.parseInt((String) alData.get(6));


        while (true){
            if (getBMI() >= _dbTopBMI){
                if (_tvWeightNote.getText().equals(_strENWeightBadNote) || _tvWeightNote.getText().equals(_strCHWeightBadNote)){
                    break;
                }
                else {
                    logger.warn(strFailMsg);
                    logger.debug("Reason:BMI note display wrong message1.");
                    return;
                }
            }
            else if (getBMI() < _dbTopBMI){
                if (!(_tvWeightNote.getText().equals(_strENWeightGoodNote) || _tvWeightNote.getText().equals(_strCHWeightGoodNote))){
                    logger.warn(strFailMsg);
                    logger.debug("Reason:BMI note display wrong message2.");
                    return;
                }
            }
            weightSwipeLeft();
            sleep(3000);
        }

        while (true){
            if (_intGender == 1){
                if (_intAge < 30){
                    if (getBodyfat() >= _dbYoungMaleTopBodyfat){
                        if (_tvBodyfatNote.getText().equals(_strENBodyfatBadNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatBadNote)) {
                            logger.info(strPassMsg);
                            break;
                        }
                        else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message1.");
                            return;
                        }
                    }
                    else if (getBodyfat() < _dbYoungMaleTopBodyfat){
                        if (!(_tvBodyfatNote.getText().equals(_strENBodyfatGoodNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message2.");
                            return;
                        }
                    }
                    bodyfatSwipeLeft();
                    sleep(3000);
                }
                // Age >= 30
                else{
                    if (getBodyfat() >= _dbOldMaleTopBodyfat){
                        if (_tvBodyfatNote.getText().equals(_strENBodyfatBadNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatBadNote)) {
                            logger.info(strPassMsg);
                            break;
                        }
                        else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message3.");
                            return;
                        }
                    }
                    else if (getBodyfat() < _dbOldMaleTopBodyfat){
                        if (!(_tvBodyfatNote.getText().equals(_strENBodyfatGoodNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message4.");
                            return;
                        }
                    }
                    bodyfatSwipeLeft();
                    sleep(3000);
                }
            }
            // Gender == 0
            else{
                if (_intAge < 30){
                    if (getBodyfat() >= _dbYoungFemaleTopBodyfat){
                        if (_tvBodyfatNote.getText().equals(_strENBodyfatBadNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatBadNote)) {
                            logger.info(strPassMsg);
                            break;
                        }
                        else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message5.");
                            return;
                        }
                    }
                    else if (getBodyfat() < _dbYoungFemaleTopBodyfat){
                        if (!(_tvBodyfatNote.getText().equals(_strENBodyfatGoodNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message6.");
                            return;
                        }
                    }
                    bodyfatSwipeLeft();
                    sleep(3000);
                }
                //Age >= 30
                else {
                    if (getBodyfat() >= _dbOldFemaleTopBodyfat){
                        if (_tvBodyfatNote.getText().equals(_strENBodyfatBadNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatBadNote)) {
                            logger.info(strPassMsg);
                            break;
                        }
                        else {
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message7.");
                            return;
                        }
                    }
                    else if (getBodyfat() < _dbOldFemaleTopBodyfat){
                        if (!(_tvBodyfatNote.getText().equals(_strENBodyfatGoodNote) || _tvBodyfatNote.getText().equals(_strCHBodyfatGoodNote))){
                            logger.warn(strFailMsg);
                            logger.debug("Reason: bodyfat note display wrong message8.");
                            return;
                        }
                    }
                    bodyfatSwipeLeft();
                    sleep(3000);
                }
            }
        }

    }

    public void CheckDataMean() throws InterruptedException, IOException {

        __strFileName = "AccountInfo";
        FnFileEvent fnFileEvent =  new FnFileEvent();
        __strFilePath =  fnFileEvent.getPath(__strFileName);
        ArrayList alData = fnFileEvent.getContent(__strFilePath);

        int intGender = Integer.parseInt(String.valueOf(alData.get(2)));
        int intAge = Integer.parseInt(String.valueOf(alData.get(3)));
        double dbHeight = Double.parseDouble(String.valueOf(alData.get(4)));


        //click menu Weight Record
        clickRecord();
        //swipe to down to refresh record
        sleep(5000);
        touchAction.press(PointOption.point(width/2,height/3)).waitAction().moveTo(PointOption.point(width/2,height*2/3)).release().perform();
        sleep(8000);
        //get the recent record
        WDFnL3WeightRecord wdFnL3WeightRecord = new WDFnL3WeightRecord(1);
        double dbWeight = Double.parseDouble(wdFnL3WeightRecord.getWeight());
        double dbBodyfat = Double.parseDouble(wdFnL3WeightRecord.getBodyfat());
        double dbBMI = Double.parseDouble(wdFnL3WeightRecord.getBMI());
        double dbWater = Double.parseDouble(wdFnL3WeightRecord.getWater());
        double dbMM = Double.parseDouble(wdFnL3WeightRecord.getMM());
        double dbBone = Double.parseDouble(wdFnL3WeightRecord.getBone());
        double dbBMR = Double.parseDouble(wdFnL3WeightRecord.getBMR());
        double dbVFL = Double.parseDouble(wdFnL3WeightRecord.getVFL());
        double dbProtein = Double.parseDouble(wdFnL3WeightRecord.getProtein());
        double dbMP = Double.parseDouble(wdFnL3WeightRecord.getMP());
        adDriver.pressKeyCode(4);

        //find the window of webview
        clickDataMean();
        Set contextWeight = adDriver.getContextHandles();
        //System.out.println(contextWeight);
        //switch to Webview and get html
        String strHTMLsource;
        try{
            adDriver.context((String) contextWeight.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) contextWeight.toArray()[0]);
        }catch (Exception eNoFindWebview){
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));return;
        }

        Document doc = Jsoup.parse(strHTMLsource);

        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

        //find the colored grids
        Elements blkPickup = doc.select("table").select("tbody").select("tr").select("td[bgcolor=\"#0AC985\"]");
        //save the colored grids to the array
        String[] strblkPickup = new String[blkPickup.size()];
        for (int i=0; i<blkPickup.size(); i++){
            strblkPickup[i] = String.valueOf(blkPickup.get(i).id());
        }

        //webview data
        Elements wvBMR = doc.select("font[id=\"1_1_0\"]");
        Elements wvMP = doc.select("font[id=\"9_1_0\"]");
        Elements wvBodyfat = doc.select("font[id=\"2_1_0\"]");
        Elements wvWater = doc.select("font[id=\"3_1_0\"]");
        Elements wvMM = doc.select("font[id=\"4_1_0\"]");
        Elements wvBone = doc.select("font[id=\"5_1_0\"]");
        Elements wvBMI = doc.select("font[id=\"6_1_0\"]");
        Elements wvVFL = doc.select("font[id=\"7_1_0\"]");
        Elements wvProtein = doc.select("font[id=\"8_1_0\"]");

        //compare webview data and record data result which is the same
        if (dbBMR == Double.parseDouble(wvBMR.get(0).text()) &&
                dbMP == Double.parseDouble(wvMP.get(0).text()) &&
                dbBodyfat == Double.parseDouble(wvBodyfat.get(0).text()) &&
                dbWater == Double.parseDouble(wvWater.get(0).text()) &&
                dbMM == Double.parseDouble(wvMM.get(0).text()) &&
                dbBone == Double.parseDouble(wvBone.get(0).text()) &&
                dbBMI == Double.parseDouble(wvBMI.get(0).text()) &&
                dbVFL == Double.parseDouble(wvVFL.get(0).text()) &&
                dbProtein == Double.parseDouble(wvProtein.get(0).text()) ){ }
        else {
            logger.warn(strFailMsg);
            logger.debug("Reason: Webview data is different from record first data");
            return;
        }

        boolean isPass = true;
        //determine the colored grids if are on the correct grids
        for (String str : strblkPickup){
            switch (str.charAt(0)){
                case '1':
                case '9':
                    switch (str.charAt(2)) {
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of metabolism");
                                isPass = false; }
                            break;
                        case '2':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of metabolism");
                                isPass = false;}
                            break;
                    }
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(intAge >= 18 && intAge < 30) ){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(intAge >= 30 && intAge < 49) ){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(intAge >= 50 && intAge < 69) ){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                        case '4':
                            if ( !(intAge >= 70) ){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of metabolism");
                                isPass = false;}
                            break;
                    }
                    break;
                case '2':
                    switch (str.charAt(2)){
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(intAge < 30)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 20) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 20 && dbBodyfat < 25) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 25) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(intAge >= 30)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 25) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 25 && dbBodyfat < 30) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 30) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '3':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(intAge < 30)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 25) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 25 && dbBodyfat < 30) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 30) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '4':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of fat ratio");
                                isPass = false;
                            }
                            if (!(intAge >= 30)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by age of fat ratio");
                                isPass = false;
                            }
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbBodyfat < 30) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbBodyfat >= 30 && dbBodyfat < 35) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbBodyfat >= 35) ){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of fat ratio");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '3':
                    switch (str.charAt(4)){
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of water ratio");
                                isPass = false;}
                            break;
                        case '2':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of water ratio");
                                isPass = false;}
                            break;
                    }
                    break;
                case '4':
                    switch (str.charAt(2)){
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of muscle mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbHeight < 160)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbHeight >= 160 && dbWeight < 170)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbHeight >= 170)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of muscle mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbHeight < 150)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbHeight >= 150 && dbWeight < 160)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbHeight >= 160)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by height of muscle mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '5':
                    switch (str.charAt(2)){
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of bone mineral mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbWeight < 60)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbWeight >= 60 && dbWeight < 75)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbWeight >= 75)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of bone mineral mass");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbWeight < 45)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbWeight >= 45 && dbWeight < 60)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbWeight >= 60)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by weight of bone mineral mass");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }
                    break;
                case '6':
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(dbBMI < 18.5)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(dbBMI >= 18.5 && dbBMI < 24)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(dbBMI >= 24 && dbBMI < 27)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                        case '4':
                            if ( !( dbBMI <= 27)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of BMI");
                                isPass = false;}
                            break;
                    }
                    break;
                case '7':
                    switch (str.charAt(4)){
                        case '1':
                            if ( !(dbVFL >= 0 && dbVFL < 10)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                        case '2':
                            if ( !(dbVFL >= 10 && dbVFL < 15)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                        case '3':
                            if ( !(dbVFL >= 15)){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by range of VFL");
                                isPass = false;}
                            break;
                    }
                    break;
                case '8':
                    switch (str.charAt(2)) {
                        case '1':
                            if (intGender != 1){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of Protein");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbProtein < 20) || dbProtein == 0){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbProtein >= 20 && dbProtein < 30) && dbProtein != 0){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbProtein >= 30)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                            }
                            break;
                        case '2':
                            if (intGender != 0){
                                if(isPass){
                                    logger.warn(strFailMsg);
                                }
                                logger.debug("Reason: data compare fail by gender of Protein");
                                isPass = false;}
                            switch (str.charAt(4)){
                                case '1':
                                    if ( !(dbProtein < 15)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '2':
                                    if ( !(dbProtein >= 15 && dbProtein < 30) && dbProtein != 0){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                                case '3':
                                    if ( !(dbProtein >= 30)){
                                        if(isPass){
                                            logger.warn(strFailMsg);
                                        }
                                        logger.debug("Reason: data compare fail by range of Protein");
                                        isPass = false;}
                                    break;
                            }
                            break;
                    }

                    break;

            }
        }

        if (isPass){
            logger.info(strPassMsg);
        }
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void CheckTips() throws InterruptedException, IOException {

        clickTips();

        int countPass = 0;
        FnPicEvent fnPicEvent = new FnPicEvent();
        ArrayList<BufferedImage> reality = new ArrayList<>();
        reality.add(ImageIO.read(new File(__imgTips0Path)));
        reality.add(ImageIO.read(new File(__imgTips1Path)));
        reality.add(ImageIO.read(new File(__imgTips2Path)));
        reality.add(ImageIO.read(new File(__imgTips3Path)));
        for (int i=0; i<=3; i++) {
            fnPicEvent.screenShot();
            fnPicEvent.cutImage();
            if (fnPicEvent.compareImages(reality.get(i))){
                countPass ++;
            }else {
                System.out.println("different Idx: " +i);
            }
            touchAction.press(PointOption.point(width * 2 / 3, height / 2)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))).moveTo(PointOption.point(width / 3, height / 2)).release().perform();
            sleep(2000);
        }
        if(countPass == 4){
            logger.info(strPassMsg);
        } else{
            logger.warn(strFailMsg);
            logger.debug("Reason: pictures are different");
        }
        adDriver.findElement(By.id(_strOffID)).click();
    }
}
