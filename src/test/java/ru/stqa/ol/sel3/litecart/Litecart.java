package ru.stqa.ol.sel3.litecart;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart extends TestBase {

  @Test (enabled = false)
  public void builderAdminLogin() {
    wd.get("http://localhost/litecart/admin/");

    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();
    wd.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
    wd.manage().deleteAllCookies();
  }

  @Test
  public void google() {
    wd.get("http://google.com"); //ili wd.navigate().to("http://google.com");
    wd.findElement(By.id("gs_ok0")).click(); //otkrivaem klaviaturu
    for (int i = 1; i < 5; i++) {
      wd.findElement(By.id("K32")).click(); // probel
    }
    WebElement q = wd.findElement(By.name("q")); //ili  wd.findElement(By.name("q")).sendKeys("webdriver");
    // wd.navigate().refresh(); // esli obnovim stranicu to polu4im "stale exception" (stale element reference: element is not attached to the page document) takogo elementa net!
    q.sendKeys("webdriver");
    //Assert.assertFalse(isElementPresent(By.name("XXX"))); //l4_m8 http://software-testing.ru/lms/mod/lesson/view.php?id=38587&pageid=613
    //Assert.assertFalse(areElementsPresent(By.name("XXX")));
    //Assert.assertFalse(areElementsPresent(By.xpath("//div[")));//l4_m8 net parnoi skobki. Togda vibrasit'sq isklu4enie
    Assert.assertFalse(isElementPresent(By.xpath("//div[")));//l4_m8 net parnoi skobki. Togda ne vibrasit'sq isklu4enie t.k. est' ego perehvat v metode. 4tobi test padal nado dobavit' catch InvalidSelectorException
     // wd.findElement(By.name("btnG")).click();
    // wait.until(titleIs("webdriver - Sök på Google"));
  }

  @Test (enabled = false)
  public void builderAdminLogin2() {
    wd.get("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();
    wd.findElement(By.name("username")).clear();
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("login")).click();
    wd.manage().deleteAllCookies();

  }
}
