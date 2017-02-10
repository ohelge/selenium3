package ru.stqa.ol.sel3.litecart;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by A546902 on 2017-02-02.
 */
public class TestBase {
  //public static WebDriver wd; //s3_l3_m12 delaem static wd i wait dlq proverki esli uzhe brauzer zapuwen.
  public WebDriver wd;
  public WebDriverWait wait;
  public final static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
  //l3_m12 Delaem Thread dlq zapuska neskol'kih brauzerov odnovremenne. Togda ubiraem static pered wd, t.k. ona budet inicializirovat'sq pered kazhdim testovim metodom @Test

  public boolean isElementPresent(By locator) { //l4_m8 delaem 2 metoda
    try {
      wd.findElement(locator);
      return true;
    } catch (InvalidSelectorException ex) { //l4_m8  4tobi test padal na "div[" nado dobavit' catch InvalidSelectorException
      throw ex;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public boolean areElementsPresent(By locator) { //l4_m8 delaem 2 metoda
    return wd.findElements(locator).size() > 0;
  }

  public void adminLogin() {
    wd.get("http://localhost/litecart/admin/");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();
    wd.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }

  @BeforeMethod
  public void setUp() throws Exception {
    /* System.setProperty("webdriver.chrome.driver", "c:/Tools/chromedriver.exe");//s3_l3_m2 Variant 1 s setPropery
    wd = new ChromeDriver(new ChromeDriverService.Builder().usingDriverExecutable(new File("c:/Tools/chromedriver.exe")).build());//Variant 2*/
    /*   DesiredCapabilities caps = new DesiredCapabilities(); //s3_l3_m4 Capabilities
    caps.setCapability("unexpectedAlertBehaviour", "dismiss"); // eta capabilities est' v IE, netu v Chrome
    wd = new ChromeDriver(caps);  // wd = new InternetExplorerDriver(caps);
    System.out.println(((HasCapabilities) wd).getCapabilities()); //vivod vseh vozmozhnosti(capabilities)  */
    /*ChromeOptions options = new ChromeOptions(); //s3_l3_m5 Opcii zapuska browsera. Sm read.me
    options.addArguments("start-maximized");// http://peter.sh/experiments/chromium-command-line-switches/#start-fullscreen
    wd = new ChromeDriver(options);*/

    // if (wd != null) {      return;    }
    if (tlDriver.get() != null) {
      wd = tlDriver.get();
      wait = new WebDriverWait(wd, 10);
      return;
    }
    wd = new ChromeDriver(); //FirefoxDriver(); //ChromeDriver() //InternetExplorerDriver()
    tlDriver.set(wd);
    wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //l4_m9 Ожидание появления элемента. 5 sekund na ozhidanie elementa. Esli ne naidet, yo isklu4enie NoSuchElementException. Mehanizm neqvnih ozhidanii
    wait = new WebDriverWait(wd, 10); //sel3_l2_m2

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      wd.quit();
      wd = null;
    }));//l3_m12 Esli ne perezapuskat' tot zhe brauzer posle kazhdogo @Test */
  }

  @AfterMethod(alwaysRun = true)
  public void stop() {
    wd.manage().deleteAllCookies();
    //wd.quit();//l3_m12 dlq kazhdogo @Test brauzer budet perezapuskat'sq, esli ne hotim, to shutHook vmesto wd.quit
    //wd = null;
  }
}
