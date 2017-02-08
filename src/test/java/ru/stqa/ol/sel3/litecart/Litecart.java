package ru.stqa.ol.sel3.litecart;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.javaScriptThrowsNoExceptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart extends TestBase {

  @Test(enabled = true)
  public void zadanie8() { //Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров http://software-testing.ru/lms/mod/assign/view.php?id=38589
    wd.get("http://localhost/litecart");
    /*List<WebElement> ducks = wd.findElements(By.xpath("//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']"));
    проверки типа li[@class='product column shadow hover-light'] в xpath весьма опасны, потому что если вдруг этому элементу будет добавлен новый класс
    (верстальщики могут запросто это сделать) или даже просто изменится порядок классов -- локатор перестанет работать. */
    List<WebElement> ducks = wd.findElements(By.xpath("//div[@class='image-wrapper']"));
    for (WebElement duck : ducks) {
      List<WebElement> duck1 = duck.findElements(By.cssSelector("[class^='sticker']"));
      System.out.println(duck.getTagName() + duck.getText() + "\n");
      assertEquals(duck1.size(), 1);
    }
  }

  @Test(enabled = true) //Задание 7.
  public void zadanie7() {
    wd.get("http://localhost/litecart/admin/");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();
    wd.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
    //Задание 7. Сделайте сценарий, проходящий по всем разделам админки http://software-testing.ru/lms/mod/assign/view.php?id=38588
    //List<WebElement> rows = wd.findElements(By.xpath("//li[@id='app-']"));
    List<WebElement> rows = wd.findElements(By.cssSelector("li#app-"));
    for (WebElement row : rows) {
      row.findElement(By.cssSelector("a")).click();
      try {Thread.sleep(1000);    } catch (Exception e) {     throw new RuntimeException(e);    }
      List <WebElement> rowsSelected = wd.findElements(By.cssSelector("li#app-[class='selected'] ul li"));

      for (WebElement rowSelected : rowsSelected) {
        rowSelected.findElement(By.cssSelector("a")).click(); // Zdes' ne nazhimaet vtoroi element Logotype, po4emu?
        try {Thread.sleep(1000);    } catch (Exception e) {     throw new RuntimeException(e);    }
      }
    }
    /*wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=appearance&doc=template']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Template')]"));
    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=appearance&doc=logotype']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Logotype')]")); */


    wd.manage().deleteAllCookies();
  }

  @Test(enabled = false)
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
    //Assert.assertFalse(isElementPresent(By.xpath("//div[")));//l4_m8 net parnoi skobki. Togda ne vibrasit'sq isklu4enie t.k. est' ego perehvat v metode. 4tobi test padal nado dobavit' catch InvalidSelectorException
    wd.findElement(By.name("btnG")).click();
    wait.until(titleIs("webdriver - Sök på Google"));
    Assert.assertTrue(isElementPresent(By.cssSelector(".rc"))); //l5_m9 Nawelsq element klassa rc. Na stranice mnogo takih blokov, no mi iwem odin
  }

  @Test(enabled = false)
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
