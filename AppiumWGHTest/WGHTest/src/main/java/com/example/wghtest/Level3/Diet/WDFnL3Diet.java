package com.example.wghtest.Level3.Diet;

import com.example.wghtest.other.FnFileEvent;
import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import io.appium.java_client.android.AndroidElement;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Diet extends WDFnBaseMeasureMenuItems {

    protected AndroidElement _btnMealSuggestion, _btnCategory, _btnPhoto, _etMemo;

    static int indexCategory = 0;
    protected static String _strAddDietContent;

    private static String __strSuggestionID = "tw.com.wgh3h:id/btmealsuggest";
    private static String __strCategoryID = "tw.com.wgh3h:id/textView2";
    protected static String _strCategoryListViewCN = "android.widget.CheckedTextView";
    private static String __strDateMsgID = "tw.com.wgh3h:id/textView3";
    private static String __strTimeID = "tw.com.wgh3h:id/textView4";
    private static String __strPhotoXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout[3]/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.GridView/android.widget.ImageView[1]";
    private static String __strMemoID = "tw.com.wgh3h:id/etMemo";

    private static String __strNotUploadedTitleID = "tw.com.wgh3h:id/tvHeader";
    private static String __strSaveUpload = "tw.com.wgh3h:id/btnSaveAndUpload";
    private static String __strSaveUploadLater = "tw.com.wgh3h:id/btnSaveAndUploadLater";

    private static String __strWeightMsg1 = "weight_under";
    private static String __strWeightMsg2 = "weight_good";
    private static String __strWeightMsg3 = "weight_over";
    private static String __strWeightMsg4 = "weight_obesity";

    private static String __strFileName;
    private static String __strFilePath;

    public WDFnL3Diet(){
        _tvTime = (AndroidElement) adDriver.findElement(By.id(__strTimeID));
        this._btnMealSuggestion = (AndroidElement) adDriver.findElement(By.id(__strSuggestionID));
        this._btnCategory = (AndroidElement) adDriver.findElement(By.id(__strCategoryID));
        this._btnPhoto = (AndroidElement) adDriver.findElement(By.xpath(__strPhotoXpath));
        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
    }


    public void setValue(String strCategory) throws InterruptedException {
        _btnCategory.click();
        List<AndroidElement> listCategoryOption = adDriver.findElementsByClassName(_strCategoryListViewCN);

        switch (strCategory){
            case "Breakfast":
            case "早餐":
                indexCategory = 0;
                break;
            case "Lunch":
            case "午餐":
                indexCategory = 1;
                break;
            case "Dinner":
            case "晚餐":
                indexCategory = 2;
                break;
            case "Beverage":
            case "含糖飲料":
                indexCategory = 3;
                break;
            case "Dessert":
            case "點心":
                indexCategory = 4;
                break;
        }


        listCategoryOption.get(indexCategory).click();
        //類別已存在
        try {
            adDriver.findElement(By.id(_strButton1ID)).click();
        }catch (Exception eNoFindElement){}


        setTime();
        String[] strDate = _tvTime.getText().split(" "); //[0]:date [1]:time
        _strAddDietContent = strDate[0] + "," + strDate[1] + " " + _btnCategory.getText();

        _etMemo.setValue("testDemo");
        clickSave();

        adDriver.findElement(By.id(__strSaveUpload)).click();
        adDriver.findElement(By.id(_strButton1ID)).click();



    }
/*
    public void setNotMainMealValue() throws InterruptedException {
        _btnCategory.click();
        List<AndroidElement> listCategoryOption = adDriver.findElementsByClassName(_strCategoryListViewCN);

        //飲食類別設定為 含糖飲料
        indexCategory = 3;

        listCategoryOption.get(indexCategory).click();
        adDriver.findElement(By.id(_strButton1ID)).click();

        setTime();
        String[] strDate = _tvTime.getText().split(" "); //[0]:date [1]:time
        _strAddDietContent = strDate[0] + "," + strDate[1] + " " + _btnCategory.getText();

        _etMemo.setValue("testDemo");

        clickSave();
        adDriver.findElement(By.id(__strSaveUpload)).click();
        adDriver.findElement(By.id(_strButton1ID)).click();
    }
*/
    public void saveAndUploadLater(){
        _btnCategory.click();
        List<AndroidElement> listCategoryOption = adDriver.findElementsByClassName(_strCategoryListViewCN);
        listCategoryOption.get(4).click();
        adDriver.findElement(By.id(_strButton1ID)).click();
        _etMemo.setValue("testDemo");
        clickSave();
        adDriver.findElement(By.id(__strSaveUploadLater)).click();
    }

    public String getDateMsg(){
        return adDriver.findElement(By.id(__strDateMsgID)).getText();
    }

    public String getNotUploadTitleMsg(){
        return adDriver.findElement(By.id(__strNotUploadedTitleID)).getText();
    }

    public void clickSuggestions(){
        _btnMealSuggestion.click();
    }

    public int getDailyCal() throws IOException {
        __strFileName = "AccountInfo";
        int age = -1, gender = -1, exerciseNo = -1, reduceCal = -1;
        double bmi, height = -1, weight = -1;

        FnFileEvent fnFileEvent = new FnFileEvent();
        __strFilePath = fnFileEvent.getPath(__strFileName);
        FileInputStream fis = new FileInputStream(__strFilePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        while (br.ready()) {
            String strReadLine = br.readLine();
            if (strReadLine.contains("Age")) {
                age = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Gender")) {
                gender = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Height")) {
                height = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("CurrentWeight")) {
                weight = Double.parseDouble(br.readLine());
            } else if (strReadLine.contains("ExerciseNo")) {
                exerciseNo = Integer.parseInt(br.readLine());
            } else if (strReadLine.contains("Reduce")) {
                reduceCal = Integer.parseInt(br.readLine());
            }
        }

        if (age == -1 || gender == -1 || exerciseNo == -1 || reduceCal == -1 || height == -1 || weight == -1){
            return -1;
        }

        height /= 100;
        bmi = Double.parseDouble(new DecimalFormat("#.#").format(weight / (height * height)));
        //將計算的BMI許入檔案  在之後的測試會用到
        FileWriter fw = new FileWriter(__strFilePath,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("###BMI"+"\r\n");
        bw.write(bmi+"\r\n");
        bw.flush();
        bw.close();

        String bmiMessage = "";
        //取得體重BMI範圍
        if (age >= 18) {
            if (bmi < 18.5) {
                bmiMessage += __strWeightMsg1;
            } else if (bmi >= 18.5 && bmi < 24.0) {
                bmiMessage += __strWeightMsg2;
            } else if (bmi >= 24.0 && bmi < 27.0) {
                bmiMessage += __strWeightMsg3;
            } else if (bmi >= 27.0) {
                bmiMessage += __strWeightMsg4;
            }
        } else {
            if (gender == 1) { //未滿18歲男生
                switch (age) {
                    case 2:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 3:
                        if (bmi < 14.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.8 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 4:
                        if (bmi < 14.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.4 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 5:
                        if (bmi < 14.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.0 && bmi < 17.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.7 && bmi < 19.4) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.4) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 6:
                        if (bmi < 13.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.9 && bmi < 17.9) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.9 && bmi < 19.7) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.7) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 7:
                        if (bmi < 14.7) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.7 && bmi < 18.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.6 && bmi < 21.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 8:
                        if (bmi < 15.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.0 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.3 && bmi < 22.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 9:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 19.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.7 && bmi < 22.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 10:
                        if (bmi < 15.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.4 && bmi < 20.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.3 && bmi < 22.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 11:
                        if (bmi < 15.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.8 && bmi < 21.0) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.0 && bmi < 23.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 12:
                        if (bmi < 16.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 16.4 && bmi < 21.5) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.5 && bmi < 24.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 13:
                        if (bmi < 17.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.0 && bmi < 22.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.2 && bmi < 24.8) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.8) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 14:
                        if (bmi < 17.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.6 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.2) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.2) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 15:
                        if (bmi < 18.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.2 && bmi < 23.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.1 && bmi < 25.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 16:
                        if (bmi < 18.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.6 && bmi < 23.4) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.4 && bmi < 25.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 17:
                        if (bmi < 19.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 19.0 && bmi < 23.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 23.6 && bmi < 25.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    default:
                        break;
                }
            } else {// 未滿18歲女生
                switch (age) {
                    case 2:
                        if (bmi < 14.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.9 && bmi < 17.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.3 && bmi < 18.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 3:
                        if (bmi < 14.5) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.5 && bmi < 17.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.2 && bmi < 18.5) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.5) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 4:
                        if (bmi < 14.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.2 && bmi < 17.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.1 && bmi < 18.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 5:
                        if (bmi < 13.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.9 && bmi < 17.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.1 && bmi < 18.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 18.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 6:
                        if (bmi < 13.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 13.6 && bmi < 17.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 17.2 && bmi < 19.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 19.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 7:
                        if (bmi < 14.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.4 && bmi < 18.0) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.0 && bmi < 20.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 20.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 8:
                        if (bmi < 14.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.6 && bmi < 18.8) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 18.8 && bmi < 21.0) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.0) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 9:
                        if (bmi < 14.9) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 14.9 && bmi < 19.3) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 19.3 && bmi < 21.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 21.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 10:
                        if (bmi < 15.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.2 && bmi < 20.1) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.1 && bmi < 22.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 22.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 11:
                        if (bmi < 15.8) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 15.8 && bmi < 20.9) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 20.9 && bmi < 23.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 12:
                        if (bmi < 16.4) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 16.4 && bmi < 21.6) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 21.6 && bmi < 23.9) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 23.9) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 13:
                        if (bmi < 17.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.0 && bmi < 22.2) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.2 && bmi < 24.6) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 24.6) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 14:
                        if (bmi < 17.6) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 17.6 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.1) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.1) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 15:
                        if (bmi < 18.0) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.0 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 16:
                        if (bmi < 18.2) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.2 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    case 17:
                        if (bmi < 18.3) {
                            bmiMessage += __strWeightMsg1;
                        } else if (bmi >= 18.3 && bmi < 22.7) {
                            bmiMessage += __strWeightMsg2;
                        } else if (bmi >= 22.7 && bmi < 25.3) {
                            bmiMessage += __strWeightMsg3;
                        } else if (bmi >= 25.3) {
                            bmiMessage += __strWeightMsg4;
                        }
                        break;
                    default:
                        break;

                }
            }
        }


        int dayneedkal = 1200;
        int mHealthkal;

        if (bmiMessage.equals(__strWeightMsg1)){
            switch (exerciseNo){
                case 1:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((40 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((45 * weight) / 100) * 100;
                    break;
            }
        }else if (bmiMessage.equals(__strWeightMsg2)) {
            switch (exerciseNo) {
                case 1:
                    dayneedkal = (int) Math.round((30 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((40 * weight) / 100) * 100;
                    break;
            }
        }else if(bmiMessage.equals(__strWeightMsg3) || bmiMessage.equals(__strWeightMsg4)){
            switch (exerciseNo) {
                case 1:
                    dayneedkal = (int) Math.round((25 * weight) / 100) * 100;
                    break;
                case 2:
                    dayneedkal = (int) Math.round((30 * weight) / 100) * 100;
                    break;
                case 3:
                    dayneedkal = (int) Math.round((35 * weight) / 100) * 100;
                    break;
            }
        }else {
            System.out.println("unknown error");
            System.out.println("BMI Message: "+ bmiMessage);
            System.out.println("Daily exerciseNo: " + exerciseNo);
        }

        if (dayneedkal < 1200){
            dayneedkal = 1200;
        }
        mHealthkal = dayneedkal - reduceCal;
        if (mHealthkal < 1200){
            mHealthkal = 1200;
        }

        return mHealthkal;

    }



}
