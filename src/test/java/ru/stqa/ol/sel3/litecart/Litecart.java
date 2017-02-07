package ru.stqa.ol.sel3.litecart;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.javaScriptThrowsNoExceptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart extends TestBase {

  @Test (enabled = true)
  public void zadanie8() { //Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров http://software-testing.ru/lms/mod/assign/view.php?id=38589
    wd.get("http://localhost/litecart");
    List<WebElement> ducks = wd.findElements(By.xpath("//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']"));
    for (WebElement duck : ducks) {
      duck.findElement(By.cssSelector("div[class^='sticker']"));
      System.out.println(duck.getText() + "\n");
    }

  }

  @Test(enabled = false) //Задание 7.
  public void zadanie7() {
    wd.get("http://localhost/litecart/admin/");
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("username")).click();
    wd.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
    //Задание 7. Сделайте сценарий, проходящий по всем разделам админки http://software-testing.ru/lms/mod/assign/view.php?id=38588
    // List<WebElement> table = wd.findElements(By.xpath("//ul[@id='box-apps-menu']"));
    List<WebElement> rows = wd.findElements(By.xpath("//ul[@id='box-apps-menu']/li[@id='app-']"));
    for (WebElement row : rows) {
      row.findElement(By.xpath("/a[1]")).click();
    }
    /*wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=appearance&doc=template']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Template')]"));
    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=appearance&doc=logotype']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Logotype')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=catalog&doc=catalog']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Catalog')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=countries&doc=countries']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Countries')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=currencies&doc=currencies']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Currencies')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=customers&doc=customers']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Customers')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Geo Zones')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=languages&doc=languages']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Languages')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=modules&doc=jobs']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Job Modules')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=orders&doc=orders']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Orders')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=reports&doc=monthly_sales']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Monthly Sales')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=settings&doc=store_info']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Settings')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=slides&doc=slides']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Slides')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=tax&doc=tax_classes']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Tax Classes')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=translations&doc=search']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Search Translations')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=users&doc=users']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'Users')]"));

    wd.findElement(By.xpath("//a[@href='http://localhost/litecart/admin/?app=vqmods&doc=vqmods']")).click();
    wd.findElement(By.xpath("//h1[contains(text(),'vQmods')]"));*/

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
