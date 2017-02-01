import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart {
  private WebDriver wd;
  private WebDriverWait wait;

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new ChromeDriver(); //FirefoxDriver(); //ChromeDriver()
    wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(wd, 10); //sel3_l2_m2
  }

  @Test
  public void web() {
    wd.get("http://localhost/litecart/admin/");

    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();

    wd.findElement(By.name("login")).click();

    wait.until(titleIs("My Store"));
  }

  @AfterMethod
  public void stop() {
    wd.quit();
    wd = null;
  }
}
