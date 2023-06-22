package base;

import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import utils.EventReporter;
import utils.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class BaseTests {

    private EventFiringWebDriver driver;
    protected HomePage homePage;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        driver = new EventFiringWebDriver(new ChromeDriver(co));
        driver.register(new EventReporter());
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        goHome();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        setCookie();
    }

    @BeforeMethod
    public void goHome(){
        driver.get("https://the-internet.herokuapp.com/");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()){
            var camera = (TakesScreenshot)driver;

            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("resources/screenshots/" + result.getName() + ".png"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public WindowManager getWindowManager(){
        return new WindowManager(driver);
    }

    private void setCookie(){
        Cookie cookie = new Cookie.Builder("tau","123")
                .domain("the-internet.herokuapp.com")
                .build();
        driver.manage().addCookie(cookie);
    }
}
