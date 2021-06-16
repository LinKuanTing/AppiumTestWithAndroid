package com.example.wghtest.Level3.Sport;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import java.util.Locale;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.WGHTestBase.adDriver;

public class WDFnL3Sport extends WDFnBaseMeasureMenuItems {
    protected AndroidElement _tvSport,_tvDuration,_tvStep,_tvPhoto,_etMemo,_tvCalorie;

    protected String _strDate;
    protected String _strAddSportContent;

    protected AndroidElement _lvSportType,_lvSportItem;

    private static String __strTimeID = "tw.com.wgh3h:id/imageView1";
    private static String __strSportID = "tw.com.wgh3h:id/imageView2";
    private static String __strDurationID = "tw.com.wgh3h:id/imageView4";
    private static String __strNumStepID = "tw.com.wgh3h:id/stepnum1";
    private static String __strCalorieID = "tw.com.wgh3h:id/textView8";
    private static String __strPhotoID = "tw.com.wgh3h:id/imageView5";
    private static String __strMemoID = "tw.com.wgh3h:id/etMemo";

    private static String __strSportTextID = "tw.com.wgh3h:id/textView4";
    private static String __strDurationTextID = "tw.com.wgh3h:id/textView6";
    private static String __strCalorieTextID = "tw.com.wgh3h:id/textView8";

    private static String __strSportTypeID = "tw.com.wgh3h:id/wv_class";
    private static String __strSportItemID = "tw.com.wgh3h:id/wv_item";

    private static String __strNotUploadedTitleID = "tw.com.wgh3h:id/tvHeader";
    private static String __strSaveUpload = "tw.com.wgh3h:id/btnSaveAndUpload";
    private static String __strSaveUploadLater = "tw.com.wgh3h:id/btnSaveAndUploadLater";

    public WDFnL3Sport(){
        super._strDateTimeID = this.__strTimeID;
    }

    public void setValue() throws InterruptedException {
        setTime();
        this._tvSport = (AndroidElement) adDriver.findElement(By.id(__strSportID));
        _tvSport.click();
        _lvSportType = (AndroidElement) adDriver.findElement(By.id(__strSportTypeID));
        _lvSportItem = (AndroidElement) adDriver.findElement(By.id(__strSportItemID));
        int Type_X = _lvSportType.getLocation().getX() + _lvSportType.getSize().getWidth()/2;
        int Type_Y = _lvSportType.getLocation().getY() + _lvSportType.getSize().getHeight()-1;
        touchAction.press(PointOption.point(Type_X,Type_Y)).waitAction().moveTo(PointOption.point(Type_X,Type_Y - (int)(Math.random()*450) -100)).release().perform();
        int Item_X = _lvSportItem.getLocation().getX() + _lvSportItem.getSize().getWidth()/2;
        int Item_Y = _lvSportItem.getLocation().getY() + _lvSportItem.getSize().getHeight()-1;
        touchAction.press(PointOption.point(Item_X,Item_Y)).waitAction().moveTo(PointOption.point(Item_X,Item_Y - (int)(Math.random()*1200) +600)).release().perform();
        adDriver.findElement(By.id(_strButton1ID)).click();
        _strAddSportContent = adDriver.findElementById(__strSportTextID).getText();
        while (true){
            this._tvDuration = (AndroidElement) adDriver.findElement(By.id(__strDurationID));
            _tvDuration.click();
            _lvHours = (AndroidElement) adDriver.findElement(By.id(_strHoursID));
            _lvMinutes = (AndroidElement) adDriver.findElement(By.id(_strMinutesID));
            int hourX =_lvHours.getLocation().getX()+_lvHours.getSize().getWidth()/2;
            int hourY =_lvHours.getLocation().getY()+_lvHours.getSize().getHeight()/2;
            touchAction.press(PointOption.point(hourX,hourY)).waitAction().moveTo(PointOption.point(hourX, (int) (hourY-Math.random()*300))).release().perform();
            int minX =_lvMinutes.getLocation().getX()+_lvMinutes.getSize().getWidth()/2;
            int minY =_lvMinutes.getLocation().getY()+_lvMinutes.getSize().getHeight()/2;
            touchAction.press(PointOption.point(minX,minY)).waitAction().moveTo(PointOption.point(minX, (int) (minY-Math.random()*300))).release().perform();
            adDriver.findElement(By.id(_strButton1ID)).click();
            if(Locale.getDefault().getLanguage().equals("en")){
                _strAddSportContent += "/"+ adDriver.findElement(By.id(__strDurationTextID)).getText().replaceAll("hour0minute","hour");
            }else if (Locale.getDefault().getLanguage().equals("zh")){
                _strAddSportContent += "/"+ adDriver.findElement(By.id(__strDurationTextID)).getText().replaceAll("小時0分鐘","小時");
            }
            this._tvCalorie = (AndroidElement) adDriver.findElement(By.id(__strCalorieID));
            _strAddSportContent += "/"+ _tvCalorie.getText();
            if (_tvCalorie.getText().indexOf("0") == 0){
                continue;
            }
            else { break; }
        }
        this._tvPhoto = (AndroidElement) adDriver.findElement(By.id(__strPhotoID));
        _tvPhoto.click();
        adDriver.findElement(By.id(_strButton2ID)).click();
        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        _etMemo.setValue("testDemo");
        clickSave();
        adDriver.findElement(By.id(__strSaveUpload)).click();
        adDriver.findElement(By.id(_strButton1ID)).click();
    }

    public void setPedometerValue() throws InterruptedException {
        setTime();
        this._tvSport = (AndroidElement) adDriver.findElement(By.id(__strSportID));
        _tvSport.click();
        adDriver.findElement(By.id(_strButton1ID)).click();
        _strAddSportContent = adDriver.findElementById(__strSportTextID).getText();
        _tvStep = (AndroidElement) adDriver.findElement(By.id(__strNumStepID));
        _tvStep.sendKeys(String.valueOf( (int)(Math.random()*2000+1000) ));
        if(Locale.getDefault().getLanguage().equals("en")){
            _strAddSportContent += "/"+_tvStep.getText()+"steps";
        }else if (Locale.getDefault().getLanguage().equals("zh")){
            _strAddSportContent += "/"+_tvStep.getText()+"步";
        }
        _strAddSportContent += "/"+adDriver.findElement(By.id(__strCalorieTextID)).getText();
        this._tvPhoto = (AndroidElement) adDriver.findElement(By.id(__strPhotoID));
        _tvPhoto.click();
        adDriver.findElement(By.id(_strButton2ID)).click();
        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        _etMemo.setValue("testDemo");
        clickSave();
        adDriver.findElement(By.id(__strSaveUpload)).click();
        adDriver.findElement(By.id(_strButton1ID)).click();
    }

    public void saveAndUploadLater() throws InterruptedException {
        setTime();
        this._tvSport = (AndroidElement) adDriver.findElement(By.id(__strSportID));
        _tvSport.click();
        adDriver.findElement(By.id(_strButton1ID)).click();
        _strAddSportContent = adDriver.findElementById(__strSportTextID).getText();
        _tvStep = (AndroidElement) adDriver.findElement(By.id(__strNumStepID));
        _tvStep.sendKeys(String.valueOf( (int)(Math.random()*2000+1000) ));
        if(Locale.getDefault().getLanguage().equals("en")){
            _strAddSportContent += "/"+_tvStep.getText()+"steps";
        }else if (Locale.getDefault().getLanguage().equals("zh")){
            _strAddSportContent += "/"+_tvStep.getText()+"步";
        }
        _strAddSportContent += "/"+adDriver.findElement(By.id(__strCalorieTextID)).getText();
        this._tvPhoto = (AndroidElement) adDriver.findElement(By.id(__strPhotoID));
        _tvPhoto.click();
        adDriver.findElement(By.id(_strButton2ID)).click();
        this._etMemo = (AndroidElement) adDriver.findElement(By.id(__strMemoID));
        _etMemo.setValue("testDemo");
        clickSave();
        adDriver.findElement(By.id(__strSaveUploadLater)).click();

    }

    public String getDate(){
        this._tvTime = (AndroidElement) adDriver.findElement(By.id(__strTimeID));
        _strDate = _tvTime.getText();
        return _strDate;
    }

    public String getNotUploadTitleMsg(){
        return adDriver.findElement(By.id(__strNotUploadedTitleID)).getText();
    }
}
