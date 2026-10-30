import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class inventory {
    WebDriver driver;
    WebDriverWait ex;
    SoftAssert sf=new SoftAssert();

    @BeforeMethod
    public void initial_setup(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        ex=new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");


        WebElement user=ex.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        user.sendKeys("standard_user");
        WebElement pass=ex.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        pass.sendKeys("secret_sauce");
        WebElement loginbutton=ex.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginbutton.click();
    }

    @Test
    public void display_inventory_items(){
        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        List<WebElement>items=driver.findElements(By.cssSelector("[data-test='inventory-item']"));
        sf.assertEquals(items.size(),6,"all items are here");
        sf.assertAll();

        for(int i=0;i<items.size();i++){
            sf.assertTrue(items.get(i).isDisplayed(),"that is "+i+" not show");
            System.out.println("inventory item "+(i+1)+" displayed");

            sf.assertAll();
        }
        System.out.println("displayed items "+items.size());
    }

    @Test
    public void add_to_cart_button(){

        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        By addButton = By.xpath("(//div[@data-test='inventory-item'])[1]//button[contains(text(),'Add to cart')]");
        driver.findElement(addButton).click();
        By removeButton = By.xpath("(//div[@data-test='inventory-item'])[1]//button[contains(text(),'Remove')]");
        ex.until(ExpectedConditions.visibilityOfElementLocated(removeButton));
        boolean removeDisplayed = driver.findElement(removeButton).isDisplayed();
        sf.assertTrue(removeDisplayed, "Remove button is not displayed after adding product");

        driver.findElement(removeButton).click();
        ex.until(ExpectedConditions.visibilityOfElementLocated(addButton));
        boolean addDisplayed = driver.findElement(addButton).isDisplayed();
        sf.assertTrue(addDisplayed, "Add to cart button is not displayed after removing product");
        sf.assertAll();


    }

    @Test
    public void serch_function_01(){
        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        By product_sc=By.xpath("//select[@data-test='product-sort-container']");
        ex.until(ExpectedConditions.elementToBeClickable(product_sc)).click();
        By first = By.xpath("(//select[@data-test='product-sort-container']/option)[1]");
        ex.until(ExpectedConditions.elementToBeClickable(first)).click();

        WebElement first_item=ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-test='inventory-item'])[1]//div[@data-test='inventory-item-name']")));
        String product_name=first_item.getText();
        sf.assertEquals(product_name,"Sauce Labs Backpack","sort is incorect");
        sf.assertAll();

    }

    @Test
    public void serch_function_02(){
        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        By product_sc=By.xpath("//select[@data-test='product-sort-container']");
        ex.until(ExpectedConditions.elementToBeClickable(product_sc)).click();
        By second = By.xpath("(//select[@data-test='product-sort-container']/option)[2]");
        ex.until(ExpectedConditions.elementToBeClickable(second)).click();

        //
        WebElement first_item=ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-test='inventory-item'])[1]//div[@data-test='inventory-item-name']")));
        String product_name=first_item.getText();
        sf.assertEquals(product_name,"Test.allTheThings() T-Shirt (Red)","sort is incorect");
        sf.assertAll();


    }

    @Test
    public void serch_function_03(){
        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        By product_sc=By.xpath("//select[@data-test='product-sort-container']");
        ex.until(ExpectedConditions.elementToBeClickable(product_sc)).click();
        By third = By.xpath("(//select[@data-test='product-sort-container']/option)[3]");
        ex.until(ExpectedConditions.elementToBeClickable(third)).click();


        WebElement first_item=ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-test='inventory-item'])[1]//div[@data-test='inventory-item-name']")));
        String product_name=first_item.getText();
        sf.assertEquals(product_name,"Sauce Labs Onesie","sort is incorect");
        sf.assertAll();

    }

    @Test
    public void serch_func_04(){
        ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        By product_sc=By.xpath("//select[@data-test='product-sort-container']");
        ex.until(ExpectedConditions.elementToBeClickable(product_sc)).click();
        By forth = By.xpath("(//select[@data-test='product-sort-container']/option)[4]");
        ex.until(ExpectedConditions.elementToBeClickable(forth)).click();

        WebElement first_item=ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-test='inventory-item'])[1]//div[@data-test='inventory-item-name']")));
        String product_name=first_item.getText();
        sf.assertEquals(product_name,"Sauce Labs Fleece Jacket","sort is incorect");
        sf.assertAll();

    }




    @AfterClass
    public void test_close(){
        driver.quit();
    }
}
