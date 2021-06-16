package com.example.wghtest;

import com.example.wghtest.Level1.ForgetPwd.TestCaseFailForgetPwdByWrongFormat;
import com.example.wghtest.Level1.ForgetPwd.TestCaseFailForgetPwdByWrongMail;
import com.example.wghtest.Level1.ForgetPwd.TestCaseNormalForgetPwd;
import com.example.wghtest.Level1.Login.TestCaseDifferentActLogin;
import com.example.wghtest.Level1.Login.TestCaseFailLoginByWrongAct;
import com.example.wghtest.Level1.Login.TestCaseFailLoginByWrongPwd;
import com.example.wghtest.Level1.Login.TestCaseNormalLogin;
import com.example.wghtest.Level1.Login.TestCaseRegisterLogin;
import com.example.wghtest.Level1.Login.WDFnL1Login;
import com.example.wghtest.Level1.Register.TestCaseCheckNickNameLength;
import com.example.wghtest.Level1.Register.TestCaseCheckReadAndAcceptTerms;
import com.example.wghtest.Level1.Register.TestCaseCompareRegisterTerms;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByCharNickName;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByCharacterAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByChineseAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByExistAct;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongMail;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongPwd;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongPwdCfm;
import com.example.wghtest.Level1.Register.TestCaseFailRegisterByWrongSN;
import com.example.wghtest.Level2.Logout.TestCaseFailLogoutByClickCancel;
import com.example.wghtest.Level2.Logout.TestCaseNormalLogout;
import com.example.wghtest.Level2.Personal.TestCaseHealthPlan;
import com.example.wghtest.Level2.Personal.TestCaseMeasure;
import com.example.wghtest.Level2.Personal.TestCasePersonal;
import com.example.wghtest.Level2.QRcode.TestCaseQRcodeCompareAct;
import com.example.wghtest.Level2.Tabbar.TestCaseTabbarPoints;
import com.example.wghtest.Level2.WDFnL2TitleMenuTabbar;
import com.example.wghtest.Level3.Advice.TestCaseAdvice;
import com.example.wghtest.Level3.Advice.WDFnL3Advice;
import com.example.wghtest.Level3.BloodGlucose.TestCaseBloodGlucose;
import com.example.wghtest.Level3.BloodGlucose.WDFnL3BloodGlucose;
import com.example.wghtest.Level3.BloodPressure.TestCaseBloodPressure;
import com.example.wghtest.Level3.BloodPressure.WDFnL3BloodPressure;
import com.example.wghtest.Level3.Diet.TestCaseDiet;
import com.example.wghtest.Level3.Ouendan.TestCaseOuendan;
import com.example.wghtest.Level3.Progress.TestCaseProgress;
import com.example.wghtest.Level3.Shop.TestCaseShop;
import com.example.wghtest.Level3.Sport.TestCaseSport;
import com.example.wghtest.Level3.Sport.WDFnL3Sport;
import com.example.wghtest.Level3.StressMood.TestCaseStressMood;
import com.example.wghtest.Level3.Thermometer.TestCaseThermometer;
import com.example.wghtest.Level3.Thermometer.WDFnL3Thermometer;
import com.example.wghtest.Level3.WDFnBaseMeasureMenuItems;
import com.example.wghtest.Level3.Weight.TestCaseWeight;
import com.example.wghtest.Level3.Weight.WDFnL3Weight;
import com.example.wghtest.other.FnLogEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static java.lang.Thread.sleep;


public class WGHTestManager extends WGHTestBase{

    public WGHTestManager() throws IOException { }

    public static void TestLevel1() throws Exception {
        //wait for loading Login main page
        WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));

        System.out.println("    Test for Register");
        //Test Case of Register
        TestCaseCompareRegisterTerms tcCompareRegisterTerms = new TestCaseCompareRegisterTerms();
        tcCompareRegisterTerms.execute();

        TestCaseCheckReadAndAcceptTerms tcCheckReadAndAcceptTerms = new TestCaseCheckReadAndAcceptTerms();
        tcCheckReadAndAcceptTerms.execute();

        TestCaseFailRegisterByWrongSN tcFailRegisterByWrongSN = new TestCaseFailRegisterByWrongSN();
        tcFailRegisterByWrongSN.execute();

        TestCaseFailRegisterByCharacterAct tcFailRegisterByCharacterAct = new TestCaseFailRegisterByCharacterAct();
        tcFailRegisterByCharacterAct.execute();

        TestCaseFailRegisterByChineseAct tcFailRegisterByChineseAct = new TestCaseFailRegisterByChineseAct();
        tcFailRegisterByChineseAct.execute();

        TestCaseFailRegisterByExistAct tcFailRegisterByExistAct = new TestCaseFailRegisterByExistAct();
        tcFailRegisterByExistAct.execute();

        TestCaseFailRegisterByCharNickName tcFailRegisterByCharNickName = new TestCaseFailRegisterByCharNickName();
        tcFailRegisterByCharNickName.execute();

        TestCaseCheckNickNameLength tcCheckNickNameLength =  new TestCaseCheckNickNameLength();
        tcCheckNickNameLength.execute();

        TestCaseFailRegisterByWrongMail tcFailRegisterByWrongMail = new TestCaseFailRegisterByWrongMail();
        tcFailRegisterByWrongMail.execute();

        TestCaseFailRegisterByWrongPwd tcFailRegisterByWrongPwd = new TestCaseFailRegisterByWrongPwd();
        tcFailRegisterByWrongPwd.execute();

        TestCaseFailRegisterByWrongPwdCfm tcRegisterByWrongPwdCfm = new TestCaseFailRegisterByWrongPwdCfm();
        tcRegisterByWrongPwdCfm.execute();
/*
        //Test case for Normal Register
        TestCaseNormalRegister tcNormalRegister = new TestCaseNormalRegister();
        tcNormalRegister.execute();
*/
        System.out.println("    Test for Login");
        //Test Case of Login
        TestCaseFailLoginByWrongAct tcFailLoginByWrongAct = new TestCaseFailLoginByWrongAct();
        tcFailLoginByWrongAct.execute();

        TestCaseFailLoginByWrongPwd tcFailLoginByWrongPwd = new TestCaseFailLoginByWrongPwd();
        tcFailLoginByWrongPwd.execute();

        TestCaseRegisterLogin tcRegisterLogin = new TestCaseRegisterLogin();
        tcRegisterLogin.execute();

        TestCaseDifferentActLogin tcDifferentActLogin = new TestCaseDifferentActLogin();
        tcDifferentActLogin.execute();

        System.out.println("    Test for ForgetPassword");
        //Test Case of Forget Password
        new WDFnL1Login().clickForgetPwd();
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        TestCaseFailForgetPwdByWrongFormat tcFailForgetPwdByWrongFormat = new TestCaseFailForgetPwdByWrongFormat();
        tcFailForgetPwdByWrongFormat.execute();

        TestCaseFailForgetPwdByWrongMail tcFailForgetPwdByWrongMail = new TestCaseFailForgetPwdByWrongMail();
        tcFailForgetPwdByWrongMail.execute();

        TestCaseNormalForgetPwd tcNormalForgetPwd = new TestCaseNormalForgetPwd();
        tcNormalForgetPwd.execute();

    }

    public static void TestLevel2() throws Exception {
        //wait for loading Login main page
        WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));

        //Test case for Login with correct account and password
        TestCaseNormalLogin tcNormalLogin = new TestCaseNormalLogin();
        tcNormalLogin.execute();
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        System.out.println("    Test for Personal");
        //Personal Test Case
        new WDFnL2TitleMenuTabbar().clickTitlePersonal();

        TestCasePersonal tcPersonal = new TestCasePersonal();
        tcPersonal.SetInfo();

        TestCaseHealthPlan tcHealthPlan = new TestCaseHealthPlan();
        tcHealthPlan.CheckHeightRange();
        tcHealthPlan.SetInfo();

        TestCaseMeasure tcMeasure = new TestCaseMeasure();
        tcMeasure.SetInfo();
        tcMeasure.ChangeBGUnits();


        System.out.println("    Test for QRcode");
        //QRcode Test Case
        TestCaseQRcodeCompareAct tcQRcodeCompareAct = new TestCaseQRcodeCompareAct();
        tcQRcodeCompareAct.execute();

        System.out.println("    Test for Logout");
        //Logout Test Case
        TestCaseFailLogoutByClickCancel tcFailLogoutByClickCancel = new TestCaseFailLogoutByClickCancel();
        tcFailLogoutByClickCancel.execute();

        TestCaseNormalLogout tcNormalLogout = new TestCaseNormalLogout();
        tcNormalLogout.execute();

    }

    public static void TestL3Weight() throws Exception {
        System.out.println("    Test for Weight");

        new WDFnL2TitleMenuTabbar().clickWeight();
        TestCaseWeight tcWeight = new TestCaseWeight();
        tcWeight.BluetoothMeasure();
        tcWeight.LoadRecord();
        tcWeight.AddRecord();
        new WDFnL3Weight().clickRecord();
        tcWeight.UpdateRecord();
        tcWeight.CompareRecord();
        tcWeight.DeleteRecord();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        tcWeight.CheckWarningMsg();
        tcWeight.CheckDataMean();
        tcWeight.CheckTips();

        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3BloodPressure() throws Exception {
        System.out.println("    Test for BloodPressure");

        new WDFnL2TitleMenuTabbar().clickBloodPressure();
        TestCaseBloodPressure tcBloodPressure = new TestCaseBloodPressure();
        tcBloodPressure.BluetoothMeasure();
        tcBloodPressure.LoadRecord();
        tcBloodPressure.AddRecord();
        tcBloodPressure.CheckAchieved();
        new WDFnL3BloodPressure().clickRecord();
        tcBloodPressure.UpdateRecord();
        tcBloodPressure.CompareRecord();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        tcBloodPressure.CheckDataMean();

        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3BloodGlucose() throws Exception {
        System.out.println("    Test for BloodGlucose");

        new WDFnL2TitleMenuTabbar().clickBloodGlucose();
        TestCaseBloodGlucose tcBloodGlucose = new TestCaseBloodGlucose();
        tcBloodGlucose.BluetoothMeasure();
        tcBloodGlucose.LoadRecord();
        tcBloodGlucose.AddRecord();
        tcBloodGlucose.CheckAchieved();
        new WDFnL3BloodGlucose().clickRecord();
        tcBloodGlucose.UpdateRecord();
        tcBloodGlucose.CompareRecord();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        tcBloodGlucose.CheckDataMean();

        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Thermometer() throws Exception {
        System.out.println("    Test for Thermometer");

        new WDFnL2TitleMenuTabbar().clickThermometer();
        TestCaseThermometer tcThermometer = new TestCaseThermometer();
        tcThermometer.BluetoothMeasure();
        tcThermometer.LoadRecord();
        tcThermometer.AddRecord();
        new WDFnL3Thermometer().clickRecord();
        tcThermometer.UpdateRecord();
        tcThermometer.CompareRecord();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        tcThermometer.CheckDataMean();

        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Sport() throws Exception {
        System.out.println("    Test for Sport");

        new WDFnL2TitleMenuTabbar().clickSport();
        TestCaseSport tcSport = new TestCaseSport();
        tcSport.LoadRecord();
        tcSport.AddRecord();
        new WDFnL3Sport().clickRecord();
        tcSport.UpdateRecord();
        tcSport.CompareRecord();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        tcSport.CheckNotUploadMsg();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Diet() throws Exception {
        System.out.println("    Test for Diet");

        new WDFnL2TitleMenuTabbar().clickDiet();
        TestCaseDiet tcDiet = new TestCaseDiet();
        tcDiet.LoadRecord();
        tcDiet.CheckEatingTimeMsg();
        tcDiet.AddRecord();
        tcDiet.CheckExistMainMealMsg();
        tcDiet.UpdateRecord();
        tcDiet.CompareRecord();
        tcDiet.CheckNotUploadMsg();
        tcDiet.CheckDataMean();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3StressMood() throws Exception {
        System.out.println("    Test for StressMood");

        new WDFnL2TitleMenuTabbar().clickStressMood();
        TestCaseStressMood tcStressMood = new TestCaseStressMood();
        tcStressMood.LoadRecord();
        tcStressMood.CompareRecord();
        tcStressMood.LoadBreathTrainRecord();
        tcStressMood.CompareBreathTrainRecord();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Advice() throws Exception {
        System.out.println("    Test for Advice");

        new WDFnL2TitleMenuTabbar().clickAdvice();
        TestCaseAdvice tcAdvice = new TestCaseAdvice();
        tcAdvice.CheckBrowseAI();
        tcAdvice.CheckStarComment();
        tcAdvice.SubmitQuestion();
        tcAdvice.CheckServices();
        new WDFnL3Advice().clickBackToMenu();
    }

    public static void TestL3Ouendan() throws Exception {
        System.out.println("    Test for Ouendan");

        new WDFnL2TitleMenuTabbar().clickOuendan();
        TestCaseOuendan tcOuendan = new TestCaseOuendan();
        tcOuendan.CreateOuendan();
        tcOuendan.QuitOuendan();
        tcOuendan.JoinOuendan();
        tcOuendan.LatestNewsEncourage();
        tcOuendan.LatestNewsClickGoodAndBad();
        tcOuendan.LatestNewsReply();
        tcOuendan.MyPartnerCheckOuendanInfo();
        tcOuendan.MyPartnerCheckPersonalInfo();
        adDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public static void TestL3Progress() throws Exception {
        System.out.println("    Test for Progress");

        new WDFnL2TitleMenuTabbar().clickProgress();
        TestCaseProgress tcProgress = new TestCaseProgress();
        tcProgress.CheckContentData();
        tcProgress.CheckAwardsDescription();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3WowPoints() throws Exception {
        System.out.println("    Test for WowPoints");

        new WDFnL2TitleMenuTabbar().clickTarbarPoints();
        TestCaseTabbarPoints tcTabbarPoints = new TestCaseTabbarPoints();
        tcTabbarPoints.CheckTermsOfUse();
        tcTabbarPoints.CheckWowPoints();
        //tcTabbarPoints.CheckEarnedPoints();
        tcTabbarPoints.CheckUsedPoints();
        tcTabbarPoints.CheckDeadlinePoints();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestL3Shop() throws Exception {
        System.out.println("    Test for Shop");

        new WDFnL2TitleMenuTabbar().clickShop();
        TestCaseShop tcShop = new TestCaseShop();
        tcShop.CheckShopNickName();
        tcShop.CheckShopPoints();
        tcShop.AddShoppingCart();
        tcShop.RemoveShoppingCart();
        tcShop.SearchCommodity();
        new WDFnL2TitleMenuTabbar().clickTabbarMenu();
    }

    public static void TestLevel3() throws Exception {


        //wait for loading Login main page
        WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));

        //Test case for Login with correct account and password
        TestCaseNormalLogin tcNormalLogin = new TestCaseNormalLogin();
        tcNormalLogin.execute();
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);


/**/
        TestL3Weight();

        TestL3BloodPressure();

        TestL3BloodGlucose();

        TestL3Thermometer();

//
        TestL3Sport();

        TestL3Diet();

        TestL3StressMood();

        TestL3Advice();

        TestL3Ouendan();

        TestL3Progress();

        TestL3WowPoints();

        TestL3Shop();
/**/

    }

    public static void End() throws Exception {
        FnLogEvent fnLogEvent = new FnLogEvent();
        fnLogEvent.logFormat();
        adDriver.quit();
    }


    public static void main(String[] args) throws Exception{

        new WGHTestManager();
//
/*
        //用於檢查logback.xml 應該存放的classpath
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
        // 获取classpath路径
        String s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("classpath => " + s );
        // 获取classpath路径
        String path = WGHTestManager.class.getResource("/").toString();
        System.out.println("classpath => " + path);

*/

/*
        TestLevel1();

        TestLevel2();

        TestLevel3();

        End();

*/

/**/
        TestLevel2();


         //wait for loading Login main page
        WebDriverWait wait = new WebDriverWait(adDriver,tmoutLoading);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(strLoginMainPageID)));

        //Test case for Login with correct account and password
        new WDFnL1Login().login("test777","1234567");
        adDriver.manage().timeouts().implicitlyWait(tmoutLoading, TimeUnit.SECONDS);

        //TestL3Weight();
        //TestL3BloodPressure();
        //TestL3BloodGlucose();
        TestL3Thermometer();
        TestL3Sport();

    }
}
