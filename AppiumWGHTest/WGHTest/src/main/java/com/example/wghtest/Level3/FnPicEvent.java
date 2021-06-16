package com.example.wghtest.Level3;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import static com.example.wghtest.WGHTestBase.adDriver;
import static com.example.wghtest.WGHTestBase.appSizeX;
import static com.example.wghtest.WGHTestBase.appSizeY;
import static com.example.wghtest.WGHTestBase.appStartX;
import static com.example.wghtest.WGHTestBase.appStartY;


public class FnPicEvent {
    Logger logger = LoggerFactory.getLogger(FnPicEvent.class);

    public void screenShot() throws InterruptedException {
        File tstPic = new File("D:\\TestData\\TestPic\\test.png");
        Thread.sleep(3000);
        File screenshot = adDriver.getScreenshotAs(OutputType.FILE);
        try{
            FileHandler.copy(screenshot,tstPic);
        } catch (Exception eNoFindFile){
            logger.debug("Can't find the picture path.");
        }
    }

    public void cutImage(){
        File tstPic = new File("D:\\TestData\\TestPic\\test.png");
        try{
            BufferedImage imgTest = ImageIO.read(tstPic);
            imgTest = imgTest.getSubimage(appStartX,appStartY,appSizeX,appSizeY);
            ImageIO.write(imgTest,"png",tstPic);
        } catch (IOException eNoFindFile){
            logger.debug("Can't find the picture path.");
        }
    }

    public boolean compareImages(BufferedImage imgSample) throws IOException {
        int sameNum = 0 ;
        double totalPixel = 0, similarity = 0;
        //imgSample = ImageIO.read(new File("E:\\TestData\\TestPic\\DataMean1.png"));
        BufferedImage imgTest = ImageIO.read(new File("D:\\TestData\\TestPic\\test.png"));
        //get picture's width and height
        int[][] pxSample = new int[imgSample.getWidth()][imgSample.getHeight()];
        int[][] pxTest = new int[imgTest.getWidth()][imgTest.getHeight()];
        int[][] pxResult = new int[imgSample.getWidth()][imgSample.getHeight()];
        //if two image's size is different , the images are different
        if (imgSample.getWidth() != imgTest.getWidth() || imgSample.getHeight() != imgTest.getHeight()){
            //loger.debug("The image's size is different , so the images can't compare");
            return false;
        }
        //put each pixel into the array
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight(); y++){
                pxSample[x][y] = imgSample.getRGB(x,y);
            }
        }
        for (int x=0; x<imgTest.getWidth(); x++){
            for (int y=0; y<imgTest.getHeight(); y++){
                pxTest[x][y] = imgTest.getRGB(x,y);
            }
        }
        //compare two images , and put the result into array .
        //if images are the same , the result are all 0
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight(); y++){
                pxResult[x][y] = pxSample[x][y] - pxTest[x][y];
            }
        }
        //count the same number
        for (int x=0; x<imgSample.getWidth(); x++){
            for (int y=0; y<imgSample.getHeight(); y++){
                if (pxResult[x][y] == 0){
                    sameNum += 1;
                }
            }
        }
        totalPixel = imgSample.getWidth() * imgSample.getHeight();
        DecimalFormat df = new DecimalFormat("#.#####");
        similarity = (sameNum / totalPixel) * 100;

        if (similarity == 100.0){
            return true;
        }else {
            //System.out.println("Images are different , the similarity : " + df.format(similarity) + " %");
            return false;
        }

    }

}
