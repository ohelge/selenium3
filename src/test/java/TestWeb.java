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
 * Created by OL A546902 on 2017-01-27.
 */
public class TestWeb {
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
    wd.get("http://google.com"); //ili wd.navigate().to("http://google.com");
    wd.findElement(By.id("gs_ok0")).click(); //otkrivaem klaviaturu
    for (int i = 1; i < 30; i++) {
      wd.findElement(By.id("K32")).click(); // probel
    }
    WebElement q = wd.findElement(By.name("q")); //ili  wd.findElement(By.name("q")).sendKeys("webdriver");
    // wd.navigate().refresh(); // esli obnovim stranicu to polu4im "stale exception" (stale element reference: element is not attached to the page document) takogo elementa net!
    q.sendKeys("webdriver");
    wd.findElement(By.name("btnG")).click();
    wait.until(titleIs("webdriver - Sök på Google"));
  }

  @AfterMethod
  public void stop() {
    wd.quit();
    wd = null;
  }


}
