import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.json.JsonInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class cart {
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
    public void cart_01() throws InterruptedException {




            ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
            By addButton_01 = By.xpath("(//div[@data-test='inventory-item'])[1]//button[contains(text(),'Add to cart')]");
            ex.until(ExpectedConditions.elementToBeClickable(addButton_01)).click();
            By addButton_02 = By.xpath("(//div[@data-test='inventory-item'])[2]//button[contains(text(),'Add to cart')]");

            ex.until(ExpectedConditions.elementToBeClickable(addButton_02)).click();
            By addButton_03 = By.xpath("(//div[@data-test='inventory-item'])[3]//button[contains(text(),'Add to cart')]");
            ex.until(ExpectedConditions.elementToBeClickable(addButton_03)).click();
            By cartIcon = By.xpath("//a[@data-test='shopping-cart-link']");
            ex.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();

            String url = driver.getCurrentUrl();
            System.out.println("Cart URL: " + url);
            sf.assertEquals(url, "https://www.saucedemo.com/cart.html", "Not redirected to cart page");
            ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test='cart-list']")));
            By backpack = By.xpath("//div[@data-test='inventory-item'][.//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']]");
            boolean before_rem = !driver.findElements(backpack).isEmpty();
            sf.assertTrue(before_rem, "Backpack is not displayed in cart");
            By removeBackpack = By.xpath("//div[@data-test='inventory-item'][.//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']]//button[contains(text(),'Remove')]");
            ex.until(ExpectedConditions.elementToBeClickable(removeBackpack)).click();

            ex.until(ExpectedConditions.invisibilityOfElementLocated(backpack));
            boolean after_rem = driver.findElements(backpack).isEmpty();
            sf.assertTrue(after_rem, "Backpack was not removed from cart");

            WebElement ch_button = ex.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
            ch_button.click();

            ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_info_container")));
            String url2 = driver.getCurrentUrl();
            System.out.println("Checkout URL: " + url2);
            sf.assertEquals(url2, "https://www.saucedemo.com/checkout-step-one.html", "Not redirected to checkout page");


            WebElement first_name = ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            first_name.sendKeys("Thilini");

            WebElement last_name = driver.findElement(By.id("last-name"));
            last_name.sendKeys("Nadeesha");
            WebElement postal = driver.findElement(By.id("postal-code"));
            postal.sendKeys("60000");

            WebElement con = driver.findElement(By.id("continue"));
            con.click();

            ex.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_summary_container")));

            WebElement price = driver.findElement(By.xpath("//div[@data-test='total-label']"));
            String value = price.getText();
            System.out.println("Checkout Total: " + value);
            sf.assertEquals(value, "Total: $28.06", "Total price calculation error");


            WebElement finish_btn = ex.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
            finish_btn.click();

            WebElement message = ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-test='complete-header']")));
            String actual_message = message.getText();
            System.out.println("Order Status: " + actual_message);
            sf.assertEquals(actual_message, "Thank you for your order!", "Order confirmation message is incorrect");
            sf.assertAll();

            WebElement download_btn= driver.findElement(By.id("generate-pdf-order"));
            download_btn.click();
            










    }



    @AfterClass
    public void test_close(){
        driver.quit();
    }
}
