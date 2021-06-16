package com.example.wghtest.Level3.Weight;

import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;

import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static com.example.wghtest.Level1.Register.WDFnL1Register.width;
import static com.example.wghtest.Level1.Register.WDFnL1Register.height;
import static com.example.wghtest.WGHTestBase.adDriver;
import static java.lang.Thread.sleep;

public class WDFnL3Weight extends WDFnBaseMeasureMenuItems {
    protected static AndroidElement _btnOff;
    protected AndroidElement _tvTips;
    protected AndroidElement _imgWeight,_imgBodyFat,_imgWater,_imgMuscleMass,_imgBone,_imgVFL;
    protected AndroidElement _btnPickChart,_btn30Days,_btn60Days,_btn90Days;

    protected AndroidElement _tvBodyfatNote,_tvWeightNote;

    protected static String _strBluetoothID = "tw.com.wgh3h:id/bt_wgt";
    protected static String _strTimeID = "tw.com.wgh3h:id/tv_wgtAddHeader";
    private static String __strTipsID = "tw.com.wgh3h:id/tvInfo";
    private static String __strWeightID = "tw.com.wgh3h:id/image_m_up";
    private static String __strBodyfatID = "tw.com.wgh3h:id/image_m_down";
    private static String __strWaterID = "tw.com.wgh3h:id/image_m_water";
    private static String __strMuscleMassID = "tw.com.wgh3h:id/image_m_muscle";
    private static String __strBoneID = "tw.com.wgh3h:id/image_m_bone";
    private static String __strVFLID = "tw.com.wgh3h:id/image_m_vfat_level";

    private static String __strNumberWeightID = "tw.com.wgh3h:id/tx_wgtWeight";
    private static String __strNumbleBMIID = "tw.com.wgh3h:id/tx_wgtbmi";
    private static String __strNumberBodyfatID = "tw.com.wgh3h:id/tx_wgtBodyFat";

    private static String __strPickChartID = "tw.com.wgh3h:id/btnTitle";
    private static String __str30DaysID = "tw.com.wgh3h:id/btnWeekMode";
    private static String __str60DaysID = "tw.com.wgh3h:id/btnDoubleWeekMode";
    private static String __str90DaysID = "tw.com.wgh3h:id/btnMonthMode";

    protected String _strBodyfatNoteID = "tw.com.wgh3h:id/tx_bodyFatWarningMessage";
    protected String _strWeightNoteID = "tw.com.wgh3h:id/tx_wgtWarningMessage";

    protected static String _strOffID = "tw.com.wgh3h:id/btnTitle";
    private static String __strFirstOffXpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.RelativeLayout/android.widget.Button";

    public WDFnL3Weight() {
        try{
            Thread.sleep(3000);
            this._btnOff = (AndroidElement) adDriver.findElement(By.xpath(__strFirstOffXpath));
            if (this._btnOff.getAttribute("resourceId").equals(_strOffID)){
                this._btnOff.click();
            }
        } catch (Exception eNoFindElement) { }
        super._strDateTimeID = this._strTimeID;
    }

    public void setValue() throws InterruptedException {
        setTime();
        this._imgWeight = (AndroidElement) adDriver.findElement(By.id(__strWeightID));
        int WeightX = (_imgWeight.getLocation().getX()+_imgWeight.getSize().getWidth());
        int WeightY = (_imgWeight.getLocation().getY()+_imgWeight.getSize().getHeight()/2);
        touchAction.press(PointOption.point(WeightX,WeightY)).waitAction().moveTo(PointOption.point((int) (WeightX-(Math.random()*840)),WeightY)).release().perform();
        this._imgBodyFat = (AndroidElement) adDriver.findElement(By.id(__strBodyfatID));
        int BodyFatX = (_imgBodyFat.getLocation().getX()+_imgBodyFat.getSize().getWidth());
        int BodyFatY = (_imgBodyFat.getLocation().getY()+_imgBodyFat.getSize().getHeight()/2);
        touchAction.press(PointOption.point(BodyFatX,BodyFatY)).waitAction().moveTo(PointOption.point((int) (BodyFatX-(Math.random()*840)),BodyFatY)).release().perform();
        sleep(_timeSleep);
        touchAction.press(PointOption.point(width/2,height*3/4)).waitAction().moveTo(PointOption.point(width/2,height/4)).release().perform();
        this._imgWater = (AndroidElement) adDriver.findElement(By.id(__strWaterID));
        int waterX = (_imgWater.getLocation().getX()+_imgWater.getSize().getWidth());
        int waterY = (_imgWater.getLocation().getY()+_imgWater.getSize().getHeight()/2);
        touchAction.press(PointOption.point(waterX,waterY)).waitAction().moveTo(PointOption.point((int) (waterX-(Math.random()*840)),waterY)).release().perform();
        this._imgMuscleMass = (AndroidElement) adDriver.findElement(By.id(__strMuscleMassID));
        int MuscleMassX = (_imgMuscleMass.getLocation().getX()+_imgMuscleMass.getSize().getWidth());
        int MuscleMassY = (_imgMuscleMass.getLocation().getY()+_imgMuscleMass.getSize().getHeight()/2);
        touchAction.press(PointOption.point(MuscleMassX,MuscleMassY)).waitAction().moveTo(PointOption.point((int) (MuscleMassX-(Math.random()*840)),MuscleMassY)).release().perform();
        sleep(_timeSleep);
        touchAction.press(PointOption.point(width/2,height*3/4)).waitAction().moveTo(PointOption.point(width/2,height/4)).release().perform();
        this._imgBone = (AndroidElement) adDriver.findElement(By.id(__strBoneID));
        int BoneX = (_imgBone.getLocation().getX()+_imgBone.getSize().getWidth());
        int BoneY = (_imgBone.getLocation().getY()+_imgBone.getSize().getHeight()/2);
        touchAction.press(PointOption.point(BoneX,BoneY)).waitAction().moveTo(PointOption.point((int) (BoneX-(Math.random()*840)),BoneY)).release().perform();
        this._imgVFL = (AndroidElement) adDriver.findElement(By.id(__strVFLID));
        int VFLX = (_imgVFL.getLocation().getX()+_imgVFL.getSize().getWidth());
        int VFLY = (_imgVFL.getLocation().getY()+_imgVFL.getSize().getHeight()/2);
        touchAction.press(PointOption.point(VFLX,VFLY)).waitAction().moveTo(PointOption.point((int) (VFLX-(Math.random()*840)),VFLY)).release().perform();
        clickSave();
        adDriver.findElement(By.id(_strButton1ID)).click();
    }

    public void weightSwipeLeft() throws InterruptedException {
        this._imgWeight = (AndroidElement) adDriver.findElement(By.id(__strWeightID));
        int WeightX = (_imgWeight.getLocation().getX()+_imgWeight.getSize().getWidth());
        int WeightY = (_imgWeight.getLocation().getY()+_imgWeight.getSize().getHeight()/2);
        touchAction.press(PointOption.point(WeightX,WeightY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(WeightX-_imgWeight.getSize().getWidth(),WeightY)).release().perform();
        sleep(_timeSleep);
    }

    public void bodyfatSwipeLeft() throws InterruptedException {
        this._imgBodyFat = (AndroidElement) adDriver.findElement(By.id(__strBodyfatID));
        int BodyFatX = (_imgBodyFat.getLocation().getX()+_imgBodyFat.getSize().getWidth());
        int BodyFatY = (_imgBodyFat.getLocation().getY()+_imgBodyFat.getSize().getHeight()/2);
        touchAction.press(PointOption.point(BodyFatX,BodyFatY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(BodyFatX-_imgBodyFat.getSize().getWidth(),BodyFatY)).release().perform();
        sleep(_timeSleep);
    }

    public double getWeight(){
        return Double.parseDouble(adDriver.findElement(By.id(__strNumberWeightID)).getText());
    }

    public double getBMI(){
        String strMsg[] = adDriver.findElement(By.id(__strNumbleBMIID)).getText().split(":");
        String strBMI[] = strMsg[1].split(",");
        return Double.parseDouble(strBMI[0]);
    }

    public double getBodyfat(){
        return Double.parseDouble(adDriver.findElement(By.id(__strNumberBodyfatID)).getText());
    }

    public void checkChart() throws InterruptedException {
        clickChart();
        adDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _btn30Days = (AndroidElement) adDriver.findElement(By.id(__str30DaysID));
        _btn60Days = (AndroidElement) adDriver.findElement(By.id(__str60DaysID));
        _btn90Days = (AndroidElement) adDriver.findElement(By.id(__str90DaysID));
        _btnPickChart = (AndroidElement) adDriver.findElement(By.id(__strPickChartID));
        _btnPickChart.click();
        List<AndroidElement> listChartOption = adDriver.findElementsByClassName("android.widget.Button");
        for (int i=0; i<listChartOption.size(); ++i){
            listChartOption.get(i).click();
            _btn30Days.click();
            sleep(3000);
            _btn60Days.click();
            sleep(3000);
            _btn90Days.click();
            sleep(3000);
            if(i == listChartOption.size()-1){
                adDriver.pressKeyCode(4);
                break;
            }
            else { _btnPickChart.click(); }
        }
    }

    public void clickTips() {
        this._tvTips = (AndroidElement) adDriver.findElement(By.id(__strTipsID));
        _tvTips.click();
    }

    public void scanBle() throws InterruptedException {
        clickBLE(_strBluetoothID);
    }
}
