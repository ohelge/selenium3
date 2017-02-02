package ru.stqa.ol.sel3.litecart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

/**
 * Created by A546902 on 2017-02-02.
 */
public class TestBase {
  //public static WebDriver wd; //s3_l3_m12 delaem static wd i wait dlq proverki esli uzhe brauzer zapuwen.
  public WebDriver wd;
  public WebDriverWait wait;
  public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
  //l3_m12 Delaem Thread dlq zapuska neskol'kih brauzerov odnovremenne. Togda ubiraem static pered wd, t.k. ona budet inicializirovat'sq pered kazhdim testovim metodom @Test

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
    if (tlDriver.get() !=null ) {
      wd = tlDriver.get();
      wait = new WebDriverWait(wd, 10);
      return;
    }
    wd = new ChromeDriver(); //FirefoxDriver(); //ChromeDriver() //InternetExplorerDriver()
    tlDriver.set(wd);
    wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait = new WebDriverWait(wd, 10); //sel3_l2_m2

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {wd.quit(); wd = null;} ));//l3_m12 Esli ne perezapuskat' tot zhe brauzer posle kazhdogo @Test
  }

  @AfterMethod(alwaysRun = true)
  public void stop() {
    //wd.quit();//l3_m12 dlq kazhdogo @Test brauzer budet perezapuskat'sq, esli ne hotim, to shutHook vmesto wd.quit
    //wd = null;
  }
}
