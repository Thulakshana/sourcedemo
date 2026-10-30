import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class User_Login {
    WebDriver driver;
    WebDriverWait ex;
    SoftAssert sf=new SoftAssert();

    @BeforeMethod
    public void initial_setup(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        ex=new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name="login_details")
    public Object[][] getlogindata(){
        return new Object[][]{
                {"standard_user","secret_sauce"},
                {"locked_out_user","secret_sauce"},
                {"problem_user","secret_sauce"},
                {"performance_glitch_user","secret_sauce"},
                {"error_user","secret_sauce"},
                {"visual_user","secret_sauce"}

        };
    }

    @Test(dataProvider = "login_details",priority = 0)
    public void login_test_with_valid(String username,String password){

        WebElement user=ex.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        user.sendKeys(username);
        WebElement pass=ex.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        pass.sendKeys(password);
        WebElement loginbutton=ex.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginbutton.click();

        String expected_url="https://www.saucedemo.com/inventory.html";
        String actual_url=driver.getCurrentUrl();


        sf.assertEquals(expected_url,actual_url);
        sf.assertAll();



    }

    @Test(priority = 1)
    public void invalid_creadential_login(){
        WebElement user_name=ex.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        user_name.sendKeys("problem_user");
        WebElement pass_word=ex.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        pass_word.sendKeys("sihara");
        WebElement loginbutton=ex.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginbutton.click();

        boolean expected_val=ex.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='error-message-container error']//h3"))).isDisplayed();
        sf.assertTrue(expected_val);


    }



    @AfterClass
    public void test_close(){
        driver.quit();
    }

}
