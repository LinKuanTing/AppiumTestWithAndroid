package com.example.wghtest.Level3.BloodGlucose;

import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.Advice.WDFnL3Advice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class TestCaseBloodGlucose extends WDFnL3BloodGlucose {

    private static String __strFileName;
    private static String __strFilePath;

    protected AndroidElement _lvRecord,_tvAchieve;
    protected double _dbAchieve;

    protected static String _strENAchieved;
    protected static String _strCHAchieved;

    private static String __strCHMessage = "儲存成功" ;
    private static String __strENMessage = "Successfully saved";

    private static String __strOneRecordXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]";
    private static String __strAchieveID = "tw.com.wgh3h:id/textView_reachedRate";

    Logger logger = LoggerFactory.getLogger(TestCaseBloodGlucose.class);

    public TestCaseBloodGlucose(){
        this._dbAchieve = 0.0;
        this._strENAchieved = "";
        this._strCHAchieved = "";
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
            sleep(10000);
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
            for (int i=1; i<=10; i++) {
                clickRecord();
                try {
                    new WDFnL3BloodGlucoseRecord(1);
                    logger.info(strPassMsg);
                    adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                    return;
                } catch (Exception eWeakNetWork) {
                    if (i == 10) {
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
        new WDFnL3Advice().alMeasureTime.add(strDate+",血糖");
        try{
            setValue();
            sleep(20000);

            clickRecord();
            if (strDate.equals(new WDFnL3BloodGlucoseRecord(1).getDate())){
                logger.info(strPassMsg);
            }
            else{
                logger.warn(strFailMsg);
                logger.debug("Reason: add's record is different with first record");
            }
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }catch (Exception eWeakNetwork){
            eWeakNetwork.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: can't test by weak network");
            //判斷是否在BG頁面
            try {
                adDriver.findElement(By.id(_strTimeID));
            }catch (Exception eNoFindElement){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
        }

    }

    public void UpdateRecord() {

        try {
            WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord(1);
            wdFnL3BloodGlucoseRecord.update();
            logger.info(strPassMsg);
        } catch (Exception eNoFindElement) {
            logger.warn(strFailMsg);
        }
    }

    public void CompareRecord() throws Exception {
        __strFileName = "BloodGlucoseRecord";

        //取記錄前1筆資料
        ArrayList alBG = new ArrayList();
        try{
            for (int i=1; i<=1; ++i){
                WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord(i);
                String recordData = "";
                recordData += wdFnL3BloodGlucoseRecord.getSession() + "/" + wdFnL3BloodGlucoseRecord.getGLU();
                alBG.add(recordData);
            }
        }catch (Exception eWeakNetWork){
            eWeakNetWork.printStackTrace();
            if (alBG.size() == 0){
                logger.warn(strFailMsg);
                logger.debug("Result: can't find record by weak network");
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            }
            return;
        }



        //儲存紀錄前1筆資料(剛新增的資料)
        WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecord = new WDFnL3BloodGlucoseRecord();
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileOutputStream fos = new FileOutputStream(__strFilePath);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "Big5"));
        for (int i=0; i<1; i++){
            if(i==0){
                bw.write(alBG.get(i)+"\r\n");
                continue;
            }
        }
        bw.flush();
        bw.close();



        //登出切換帳號
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));  //返回 從紀錄列表離開至血壓頁面
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();  //點擊首頁
        DeleteLocalDataBase();  //切換帳號刪除DB在切回原帳號

        new WDFnL2TitleMenuTabbar().clickBloodGlucose();   //進入血糖


        //進入紀錄列表 有時可能下載歷史紀錄失敗 沒有任何紀錄顯示
        while (true){
            clickRecord();  //進入紀錄
            try {
                new WDFnL3BloodGlucoseRecord(1);
                break;
            }catch (Exception eWeekNetWork){
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
                continue;
            }
        }

        ArrayList alBGNew = new ArrayList();
        try{
            for (int i=1; i<=1; ++i){
                WDFnL3BloodGlucoseRecord wdFnL3BloodGlucoseRecordNew = new WDFnL3BloodGlucoseRecord(i);
                String recordData = "";
                recordData += wdFnL3BloodGlucoseRecordNew.getSession() + "/" + wdFnL3BloodGlucoseRecordNew.getGLU();
                alBGNew.add(recordData);
            }
        }catch (Exception eWeakNetWork){
            eWeakNetWork.printStackTrace();
            if (alBG.size() == 0){
                logger.warn(strFailMsg);
                logger.debug("Result: can't find record by weak network");
                adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            };
            return;
        }




        //呼叫方法比較 預期資料idx[0] 比較 紀錄資料[0]
        if (wdFnL3BloodGlucoseRecord.compare(alBGNew)){
            logger.info(strPassMsg);
        }
        else{
            logger.warn(strFailMsg);
            logger.debug("Expect data:"+wdFnL3BloodGlucoseRecord.alReadLine);
            logger.debug("Read  data :"+alBGNew);
        }




    }

    public void CheckDataMean() throws IOException {
        __strFileName = "glucose_colormean_en";

        clickDataMean();

        Set contextBG = adDriver.getContextHandles();
        //System.out.println(contextBG);

        //get app webview content
        String strHTMLsource;
        try{
            adDriver.context((String) contextBG.toArray()[1]);
            strHTMLsource = adDriver.getPageSource();
            adDriver.context((String) contextBG.toArray()[0]);
        }catch (Exception eNoFindWebview){
            //eNoFindWebview.printStackTrace();
            logger.warn(strFailMsg);
            logger.debug("Reason: developer don't enable debug mode for test");
            adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));return;
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
            txtExpectData += strReadLine;
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

    public void CheckAchieved() throws InterruptedException, IOException {
        __strFileName = "AccountInfo";

        //日期處理
        //格式固定
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //抓取今天日期
        Calendar cal = Calendar.getInstance();
        Date TodayDate = cal.getTime();
        //抓取星期 0~6
        int week = Integer.parseInt(new SimpleDateFormat("u").format(TodayDate)); //u 為取星期
        //推算當週 日~六 日期
        ArrayList alWeekDate = new ArrayList();
        cal.add(Calendar.DATE,-week);
        for (int i=0; i<7; i++){
            cal.add(Calendar.DATE,+i);
            alWeekDate.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,-i);
        }

        //紀錄下滑4個Record高度
        clickRecord();
        this._lvRecord = (AndroidElement) adDriver.findElement(By.xpath(__strOneRecordXpath));
        int startX = _lvRecord.getLocation().getX() + _lvRecord.getSize().getWidth()/2;
        int startY = _lvRecord.getLocation().getY() + _lvRecord.getSize().getHeight()*4;
        int endX = startX;
        int endY = startY - _lvRecord.getSize().getHeight()*4;

        //儲存在範圍內的日期紀錄
        ArrayList RecordDate = new ArrayList();
        A:while(true) {
            //判斷是否重複存取
            for (int i=0; i<RecordDate.size(); i++){
                try{
                    if (new WDFnL3BloodGlucoseRecord(1).getDate().equals(RecordDate.get(i))){
                        break A;
                    }
                }catch (Exception eNoFindElement){
                    //touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(startX,startY-20)).release().perform();
                    continue ;
                }
            }

            boolean isFind = true;
            try{
                B:for (int i=1; i<=4; i++){
                    String strDate = new WDFnL3BloodGlucoseRecord(i).getDate();
                    for (int j=0; j<alWeekDate.size(); j++){
                        String[] splitDate = strDate.split(" ");
                        if (splitDate[0].equals(alWeekDate.get(j))){
                            RecordDate.add(strDate);
                            isFind = true;
                            break ;
                        }else {
                            isFind = false;
                            if (j == alWeekDate.size()-1){
                                break B;
                            }
                        }
                    }
                }
            }catch (Exception eNoFindElement){}

            if(!isFind){
                break ;
            }

            //滑動4個紀錄高度
            touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(endX,endY-35)).release().perform();
            //沒有滑完全 第一筆紀錄被切割時  再次滑動
            while (true){
                if (_lvRecord.getLocation().getY() <= adDriver.findElement(By.id("tw.com.wgh3h:id/llallGroupItems")).getLocation().getY()){
                    sleep(3000);
                    int pointY =_lvRecord.getLocation().getY();
                    touchAction.press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(startX,startY-40)).release().perform();
                    int newPointY = _lvRecord.getLocation().getY();
                    //判斷滑到底部  但最上面資料仍被切一半時
                    if (pointY == newPointY){
                        break ;
                    }
                }
                else{
                    break;
                }
            }
        }
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        //讀檔抓measure資料
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        int BGmeasure = -1;
        while(br.ready()){
            if (br.readLine().indexOf("BG measurements") != -1){
                BGmeasure = Integer.parseInt(br.readLine());
                break;
            }
        }

        if (BGmeasure != -1){
            DecimalFormat df = new DecimalFormat("##.0");
            this._dbAchieve = RecordDate.size()*1.0 / BGmeasure*1.0 * 100.0;

            _dbAchieve = Double.parseDouble(df.format(_dbAchieve));
            if (_dbAchieve >= 100.0){
                _dbAchieve = 100.0;
            }
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: can't get txtFile info");
        }

        /*須進入量測設定載入量測次數*/
        this._tvAchieve = (AndroidElement) adDriver.findElement(By.id(__strAchieveID));
        String strAppAchieve = _tvAchieve.getText();
        _strCHAchieved = "本週量測達成率" + _dbAchieve + "%";
        _strENAchieved = "Achieved" + _dbAchieve + "%";


        if (strAppAchieve.equals(_strCHAchieved) || strAppAchieve.equals(_strENAchieved)){
            logger.info(strPassMsg);
        }
        else if(strAppAchieve.equals("Achieved0.0%") && _dbAchieve != 0.0){
            logger.warn(strFailMsg);
            logger.debug("Reason: doesn't get measure times yet, need to go Personal setting to load measure data");
        }
        else if(strAppAchieve.equals("本週量測達成率0.0%") && _dbAchieve != 0.0){
            logger.warn(strFailMsg);
            logger.debug("Reason: doesn't get measure times yet, need to go Personal setting to load measure data");
        }
        else{
            logger.warn(strFailMsg);
            logger.debug("Except: " + (strAppAchieve.indexOf("Achieved")==-1?_strCHAchieved:_strENAchieved));
            logger.debug(" Read : " + strAppAchieve);
        }

        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###BG achieved (%)\r\n");
        bw.write(_dbAchieve+"\r\n");
        bw.flush();
        bw.close();

    }
}
