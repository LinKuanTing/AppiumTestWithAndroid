package com.example.wghtest.Level3.Ouendan;

import com.example.wghtest.other.FnFileEvent;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.wghtest.other.FnLogEvent.strFailMsg;
import static com.example.wghtest.other.FnLogEvent.strPassMsg;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static com.example.wghtest.WGHTestBase.adDriver;

public class TestCaseOuendan extends WDFnL3Ouendan{

    protected static String _strName,_strCode,_strPassword;

    private static String __strEncourageContent = "Encourage test ";
    private static String __strReplyContent = "Reply test ";

    private static String __strFileName;
    private static String __strFilePath;

    Logger logger = LoggerFactory.getLogger(TestCaseOuendan.class);

    public void CreateOuendan(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String strDate = sdFormat.format(date);

        _strName = "Ting" + strDate;
        clickOptionCreate();
        create(_strName);
        char[] charArray = getCreateMsg().toCharArray();
        adDriver.findElement(By.id(_strButton2ID)).click();


        ArrayList infoOuendan = new ArrayList();
        String strInfo = "";

        //取得 團名 代碼 密碼
        for (int i=0; i<charArray.length; i++){
            if (charArray[i] == '「') {
                for (int j=i+1; ;j++){

                    if (charArray[j] == '」'){
                        infoOuendan.add(strInfo);
                        strInfo = "";
                        break;
                    }
                    else {
                        strInfo += charArray[j];
                    }

                }
            }
        }
        //idx[0]->團名  idx[1]->團代碼  idx[2]->團密碼
        _strName = infoOuendan.get(0).toString();
        _strCode = infoOuendan.get(1).toString();
        _strPassword = infoOuendan.get(2).toString();

        //判斷團名是否正確出現在已加入油團中
        ArrayList alOuendan = getOuendan();
        for (int i=0; i<alOuendan.size(); i++){
            if (_strName.equals(alOuendan.get(i))){
                logger.info(strPassMsg);
                return;
            }
            if (i == alOuendan.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: create failed which can't find Ouendan.");
            }
        }


    }

    public void QuitOuendan() throws InterruptedException {
        clickOuendan(_strName);
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.quit();

        ArrayList alOuendan = getOuendan();
        if (alOuendan.size() == 0){
            logger.info(strPassMsg);
            return;
        }

        for (int i=0; i<alOuendan.size(); i++){
            if (alOuendan.get(i).toString().equals(_strName)){
                logger.warn(strFailMsg);
                logger.debug("Reason: quit ouendan fail, ouendan listview can find the ouendan.");
                return;
            }

            if (i == alOuendan.size()-1){
                logger.info(strPassMsg);
            }else {
                logger.warn(strFailMsg);
                logger.debug("Reason: Quit fail");
                System.out.println(alOuendan.size());
            }
        }
    }

    public void JoinOuendan(){
        clickOptionJoin();
        join(_strCode,_strPassword);
        char[] charArray = getCreateMsg().toCharArray();
        adDriver.findElement(By.id(_strButton2ID)).click();

        String strName = "";
        for (int i =0; i<charArray.length; i++){
            if (charArray[i] == '「') {
                for (int j=i+1; ;j++){
                    if (charArray[j] == '」'){
                        break;
                    } else {
                        strName += charArray[j];
                    }
                }
            }
        }

        if (!strName.equals(_strName)){
            logger.warn(strFailMsg);
            logger.debug("Reason: ouendan name is different with this code and password");
        }

        //判斷團名是否正確出現在已加入油團中
        ArrayList alOuendan = getOuendan();
        for (int i=0; i<alOuendan.size(); i++){
            if (strName.equals(alOuendan.get(i))){
                logger.info(strPassMsg);
                return;
            }
            if (i == alOuendan.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: join failed which can't find Ouendan.");
            }
        }

    }

    public void LatestNewsEncourage(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String strDate = sdFormat.format(date);

        clickOuendan(_strName);
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.ckickLatestNews();

        __strEncourageContent += strDate;
        wdFnL3InOuendan.encourage(__strEncourageContent);

        ArrayList alContent = wdFnL3InOuendan.getLatestNewsContent();
        for (int i=0; i<alContent.size(); i++){
            if (alContent.get(i).toString().equals(__strEncourageContent)){
                logger.info(strPassMsg);
                return;
            }
            if (i == alContent.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find encourage message");
            }
        }
    }

    public void LatestNewsClickGoodAndBad(){
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickImgGoodAndBad();
        wdFnL3InOuendan.clickImgReply();
        ArrayList alReplyContent = wdFnL3InOuendan.getReplyContent();

        boolean isFindGoodMsg = false;
        boolean isFindBadMsg = false;
        for (int i=0; i<alReplyContent.size(); i++){
            if (alReplyContent.get(i).toString().contains("讚")){
                isFindGoodMsg = true;
            }
            else if (alReplyContent.get(i).toString().contains("噓")){
                isFindBadMsg = true;
            }
        }

        if (isFindGoodMsg && isFindBadMsg){
            logger.info(strPassMsg);
        }
        else{
            logger.warn(strFailMsg);
            logger.debug("Reason: can't find good or bad message");
        }

    }

    public void LatestNewsReply(){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String strDate = sdFormat.format(date);

        __strReplyContent += strDate;
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickImgReply();
        wdFnL3InOuendan.reply(__strReplyContent);

        ArrayList alReplyContent = wdFnL3InOuendan.getReplyContent();

        for (int i=0; i<alReplyContent.size(); i++){
            if (alReplyContent.get(i).toString().equals(__strReplyContent)){
                logger.info(strPassMsg);
                break;
            }
            if (i == alReplyContent.size()-1){
                logger.warn(strFailMsg);
                logger.debug("Reason: can't find reply message");
            }
        }

        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void MyPartnerCheckOuendanInfo(){
        WDFnL3InOuendan wdFnL3InOuendan = new WDFnL3InOuendan();
        wdFnL3InOuendan.clickMyPartners();
        ArrayList alReadOuendanInfo = wdFnL3InOuendan.getOuendanInfo();
        //[0]->name  [1]->code  [2]->password
        ArrayList alExpectOuendanInfo = new ArrayList();
        alExpectOuendanInfo.add(_strName);
        alExpectOuendanInfo.add(_strCode);
        alExpectOuendanInfo.add(_strPassword);

        int sameNum = 0;
        for (int i=0; i<alReadOuendanInfo.size(); i++){
            if (alReadOuendanInfo.get(i).equals(alExpectOuendanInfo.get(i).toString())){
                sameNum++;
            }
        }

        if (sameNum == 3){
            logger.info(strPassMsg);
        }
        else {
            logger.warn(strFailMsg);
            logger.debug("Expect data:" + alExpectOuendanInfo);
            logger.debug("Read  data :" + alReadOuendanInfo);
        }
    }

    public void MyPartnerCheckPersonalInfo() throws IOException, InterruptedException {
        String strPersonalContent = new WDFnL3InOuendan().getPersonalContent();
        String strPersonalName = new WDFnL3InOuendan().getPersonalName();

        String strNickName = "";
        double dbInitialWeight = -1.0, dbTargetWeight = -1.0, dbCurrentWeight = -1.0;
        double dbBPAchieved = -1.0, dbBGAchieved = -1.0;

        __strFileName = "AccountInfo";
        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "Big5"));
        while (br.ready()) {
            String strReadLine = br.readLine();
            if (strReadLine.contains("Nick Name")) {
                strNickName = br.readLine();
            } else if (strReadLine.contains("InitialWeight")) {
                dbInitialWeight = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("TargetWeight")) {
                dbTargetWeight = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("CurrentWeight")) {
                dbCurrentWeight = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("BP achieved")) {
                dbBPAchieved = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("BG achieved")) {
                dbBGAchieved = Double.parseDouble(br.readLine());
            }
        }

        if (strNickName.equals("") || dbInitialWeight<0 || dbTargetWeight<0 || dbCurrentWeight<0 || dbBPAchieved<0 || dbBGAchieved<0){
            logger.warn(strFailMsg);
            logger.debug("Reason: can't get enough data to compare personal information");
            new WDFnL3InOuendan().quit();
            return;
        }

        if (!strNickName.equals(strPersonalName)){
            logger.warn(strFailMsg);
            logger.debug("Reason: nickname is different");
            logger.debug("Expect: " + strNickName);
            logger.debug(" Read : " + strPersonalName);
            new WDFnL3InOuendan().quit();
            return;
        }

        double dbRemainder = dbCurrentWeight - dbInitialWeight;
        double dbReachingRate = Math.abs(dbRemainder) / Math.abs(dbTargetWeight - dbInitialWeight) * 100;
        DecimalFormat df = new DecimalFormat("#.#");
        dbReachingRate = Double.parseDouble(df.format(dbReachingRate));

        String[] str = strPersonalContent.split("\n");
        ArrayList al = new ArrayList();
        //[0]->體重差與達成度  [1]->今天步數與達成度  [2]->這週步數(平均)與達成度  [3]->這週血壓達成度  [4]->這週血糖達成度
        for (int i=0; i<str.length; i++){
            al.add(str[i]);
        }

        int theSameNum = 0;
        for (int i=0; i<al.size(); i++){
            switch (i){
                case 0:
                    String strUpDownMsg = "";
                    if (dbRemainder<0){
                        strUpDownMsg = "decreased";
                    }else {
                        strUpDownMsg = "increased";
                    }

                    //當體重增減數值為整數 ex: 8 時  內容為8 而不是8.0 因此要處理
                    DecimalFormat decimalFormat = new DecimalFormat("##.0");
                    String strRemainder = String.valueOf(decimalFormat.format(Math.abs(dbRemainder)));

                    if (strRemainder.contains(".0")){
                        int intRemainder = (int) dbRemainder;
                        strRemainder = String.valueOf(Math.abs(intRemainder));
                        strRemainder += "Kg";
                    }


                    //System.out.println(al.get(i).toString() +"     "+ strUpDownMsg + "," + strRemainder + " , " + dbReachingRate + " , ");

                    if (al.get(i).toString().contains(strUpDownMsg) && al.get(i).toString().contains(strRemainder) && al.get(i).toString().contains(String.valueOf(dbReachingRate))){
                        theSameNum++;
                    }else {
                        System.out.println("Expect: "+strUpDownMsg +"/"+ strRemainder +"/"+ dbReachingRate);
                        System.out.println("Read  : "+al.get(i));
                    }
                    break;
                case 1:
                case 2:
                    break;
                case 3:
                    //待APP修改  加油團個人資料的達成度為無條件捨去取到整數
                    String strBP;
                    df = new DecimalFormat("#");
                    if (String.valueOf(dbBPAchieved).contains(".5")){
                        strBP = String.valueOf(Math.round(dbBPAchieved));
                    }else {
                        strBP = df.format(dbBPAchieved);
                    }

                    if (al.get(i).toString().contains(strBP)){
                        theSameNum++;
                    }else {
                        System.out.println("Expect: " + strBP);
                        System.out.println("Read  : " + al.get(i));
                    }
                    break;
                case 4:
                    //待APP修改  加油團個人資料的達成度為取到整數
                    String strBG;
                    df = new DecimalFormat("#");
                    if (String.valueOf(dbBGAchieved).contains(".5")){
                        strBG = String.valueOf(Math.round(dbBGAchieved));
                    }else {
                        strBG = df.format(dbBGAchieved);
                    }

                    if (al.get(i).toString().contains(strBG)){
                        theSameNum++;
                    }else {
                        System.out.println("Expect: " + strBG);
                        System.out.println("Read  : " + al.get(i));
                    }
                    break;
                default:
                    break;
            }
        }


        if (theSameNum == 3){
            logger.info(strPassMsg);
        }else {
            logger.warn(strFailMsg);
            logger.debug("Reason: the content data is different");
        }

        new WDFnL3InOuendan().quit();
    }

    public void CheckRank(){}

}
