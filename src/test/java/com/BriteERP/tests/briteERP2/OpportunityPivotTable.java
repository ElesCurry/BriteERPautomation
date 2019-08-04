package com.BriteERP.tests.briteERP2;


import com.BriteERP.utilities.BriteERPUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpportunityPivotTable {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//created only once for rest of the whole code
        driver.manage().window().maximize();
    }
    @AfterMethod
    public void closeDown() throws InterruptedException {
        Thread.sleep(2000);
        //  driver.quit();
    }
    @Test
    public void pivotTable() throws InterruptedException {
        BriteERPUtilities.login(driver, "eventscrmmanager52@info.com", "eventscrmmanager");
//click CRM
        driver.findElement(By.xpath("//span[@class='oe_menu_text'][contains(text(), 'CRM')]")).click();

        driver.findElement(By.xpath("//button[@type='button' and @aria-label='pivot']")).click();

        driver.findElement(By.cssSelector("td[class='o_pivot_header_cell_closed']")).click();
        driver.findElement(By.linkText("Opportunity")).click();
        String thirdcell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]")).getText();
        // System.out.println(thirdcell);

        driver.findElement(By.xpath("//button[@type='button' and @aria-label='list']")).click();
        Thread.sleep(4000);
        List<WebElement> opportunity = driver.findElements(By.xpath("//table/tbody/tr/td[3]"));
        List<String> OpportunityStr = new ArrayList<>();

        //opportunity.forEach(column -> System.out.println(column.getText()));
        //System.out.println(opportunity.get(0).getText());
        int index = 0;
        for (int i = 0; i < opportunity.size(); i++) {
            if(opportunity.get(i).getText().equals("Printer")){
                index = i+1;
            }
        }
        Thread.sleep(4000);
        String revenue = driver.findElement(By.xpath("//table/tbody/tr["+index+"]/td[9]")).getText();
        System.out.println(revenue);
    }

    @Test
    public void sumOfTheRevenue() throws InterruptedException {
        //2. Verify that on the pivot table, the total expected revenue should be the sumof all opportunities’ expected revenue.
        BriteERPUtilities.login(driver, "eventscrmmanager52@info.com", "eventscrmmanager");
//click CRM
        driver.findElement(By.xpath("//span[@class='oe_menu_text'][contains(text(), 'CRM')]")).click();

        driver.findElement(By.xpath("//button[@type='button' and @aria-label='pivot']")).click();

        String totalRevenue = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
        System.out.println(totalRevenue);
        Double totalRevdouble = Double.parseDouble(totalRevenue.substring(0,totalRevenue.length()-1).replace(",",""));

        String totalRevenue2 = driver.findElement(By.xpath("//tbody/tr[2]/td[2]")).getText();
        Double totalRevdouble2 = Double.parseDouble(totalRevenue2.substring(0,totalRevenue2.length()-1).replace(",",""));

        driver.findElement(By.cssSelector("td[class='o_pivot_header_cell_closed']")).click();
        driver.findElement(By.linkText("Opportunity")).click();
        Thread.sleep(2000);
        List<String> totalRevenueStr = new ArrayList<>();
        List<WebElement> totalRevenuList = driver.findElements(By.xpath("//tbody/tr/td[2]"));

        Thread.sleep(2000);
        for (WebElement w : totalRevenuList) {
            totalRevenueStr.add(w.getText());
            // System.out.println(w.getText());
        }

        List<Double> totalRevenuDouble = new ArrayList<>();
        for (String s : totalRevenueStr) {
            totalRevenuDouble.add(Double.parseDouble(s.substring(0, s.length()-2).replace(",","")));
        }

        double sum = 0;
        for (Double td:totalRevenuDouble) {
            sum+=td;
        }

        double lastSum = sum - (totalRevdouble+totalRevdouble2);
        Assert.assertTrue(lastSum==totalRevdouble);


    }
}