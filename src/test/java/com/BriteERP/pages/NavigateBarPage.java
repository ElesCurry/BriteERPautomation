package com.BriteERP.pages;

import com.BriteERP.tests.TestBase;
import com.BriteERP.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class NavigateBarPage extends TestBase {
    public NavigateBarPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    public void navigateToMenu(String menuName) {
        WebElement menuBar = Driver.get().findElement(By.xpath("//span[@class='oe_menu_text'][contains(text(), '" + menuName + "')]"));
        menuBar.click();
    }
}