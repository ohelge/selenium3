import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

/**
 * Created by OL A546902 on 2017-01-27.
 */
public class TestWeb {
  FirefoxDriver wd;

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
  }

  @Test
  public void web() {
    wd.get("http://software-testing.ru/trainings/schedule?task=3&cid=242");
  }

  @AfterMethod
  public void stop() {
    wd.quit();
  }


}
