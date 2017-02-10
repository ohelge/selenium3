package ru.stqa.ol.sel3.litecart;

import com.google.common.collect.Ordering;
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
  @Test
  public void zadanie10() { // Задание 10. Проверить, что открывается правильная страница товара http://software-testing.ru/lms/mod/assign/view.php?id=38592
/*    Более точно, нужно открыть главную страницу, выбрать первый товар в категории Campaigns и проверить следующее:
    а) на главной странице и на странице товара совпадает текст названия товара
    б) на главной странице и на странице товара совпадают цены (обычная и акционная)
    в) обычная цена серая и зачёркнутая, а акционная цена красная и жирная (это надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
    г) акционная цена крупнее, чем обычная (это надо проверить на каждой странице независимо)
    Необходимо убедиться, что тесты работают в разных браузерах, желательно проверить во всех трёх ключевых браузерах (Chrome, Firefox, IE).*/
    wd.get("http://localhost/litecart/");
    WebElement duck= wd.findElement(By.cssSelector("div#box-campaigns div.name"));
    WebElement duckRegular = wd.findElement(By.cssSelector("div#box-campaigns s.regular-price"));
    WebElement duckCampaign = wd.findElement(By.cssSelector("div#box-campaigns strong.campaign-price"));
    String duckName = duck.getAttribute("textContent"); //dlq parametra getAttribute smotri tab Properties v Chrome!
    String duckRegularPrice = duckRegular.getAttribute("textContent");
    String duckCampaignPrice = duckCampaign.getAttribute("textContent");

    assertEquals( duckRegular.getCssValue("color"),"rgba(119, 119, 119, 1)");
    assertEquals( duckRegular.getCssValue("text-decoration"),"line-through");

    assertEquals( duckCampaign.getCssValue("color"),"rgba(204, 0, 0, 1)");
    assertEquals( duckCampaign.getCssValue("font-weight"),"bold");

    System.out.println(duckRegular.getCssValue("text-decoration"));

    wd.findElement(By.cssSelector("div#box-campaigns a.link")).click();
    WebElement duckRegular1 = wd.findElement(By.cssSelector("s.regular-price"));
    WebElement duckCampaign1 = wd.findElement(By.cssSelector("strong.campaign-price"));
    assertEquals(wd.findElement(By.cssSelector("h1.title")).getAttribute("textContent"), duckName);
    assertEquals(duckRegular1.getAttribute("textContent"), duckRegularPrice);
    assertEquals(duckCampaign1.getAttribute("textContent"), duckCampaignPrice);

    assertEquals( wd.findElement(By.cssSelector("body")).getCssValue("color"),"rgba(102, 102, 102, 1)");
    assertEquals( duckRegular1.getCssValue("text-decoration"),"line-through");
    assertEquals( duckCampaign1.getCssValue("color"),"rgba(204, 0, 0, 1)");
    assertEquals( duckCampaign1.getCssValue("font-weight"),"bold");




    System.out.println(duckName + "\n" + duckRegularPrice + " " + duckCampaignPrice);
    //try {       Thread.sleep(1000);        } catch (Exception e) {          throw new RuntimeException(e);        }

  }

  @Test
  //zadanie 9 Проверить сортировку стран и геозон в админке http://software-testing.ru/lms/mod/assign/view.php?id=38591
  public void zadanie9() {
    adminLogin();
    wd.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[class='row']"));
    List<String> countries = null;
    for (WebElement row : rows) {
      String country = row.findElement(By.cssSelector("tr[class='row']")).getText();
      countries.add(country);
    }
    boolean sorted = Ordering.natural().isOrdered(countries);
  }

  @Test(enabled = true)
  public void zadanie8() { //Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров http://software-testing.ru/lms/mod/assign/view.php?id=38589
    wd.get("http://localhost/litecart");
    /*List<WebElement> ducks = wd.findElements(By.xpath("//ul[@class='listing-wrapper products']/li[@class='product column shadow hover-light']"));
    проверки типа li[@class='product column shadow hover-light'] в xpath весьма опасны, потому что если вдруг этому элементу будет добавлен новый класс
    (верстальщики могут запросто это сделать) или даже просто изменится порядок классов -- локатор перестанет работать. */
    List<WebElement> ducks = wd.findElements(By.xpath("//div[@class='image-wrapper']"));
    for (WebElement duck : ducks) {
      List<WebElement> duck1 = duck.findElements(By.cssSelector("div.sticker")); //class="sticker new" означает, что у элемента два класса, а не один составной.
      // Ну и как я уже писал -- порядок классов может меняться по прихоти верстальщика или генератора кода страниц, поэтому локатор [class^='...'] весьма рискованный
      System.out.println(duck.getTagName() + duck.getText() + "\n"); //esli iwem vse "sale" to: $$("div .sale") ili "div.sticker.sale"
      assertEquals(duck1.size(), 1);
    }
  }

  @Test(enabled = true) //Задание 7.
  public void zadanie7() {
    adminLogin();
    //Задание 7. Сделайте сценарий, проходящий по всем разделам админки http://software-testing.ru/lms/mod/assign/view.php?id=38588
    List<WebElement> rows = wd.findElements(By.cssSelector("li#app-"));
    int i = 0;
    while (rows.size() != i) {
      rows.get(i).findElement(By.cssSelector("a")).click();
      assertTrue(isElementPresent(By.cssSelector("h1")));
      i++;
      if (isElementPresent(By.cssSelector("li#app-[class='selected'] ul"))) {
        List<WebElement> rowsSelected = wd.findElements(By.cssSelector("li#app-[class='selected'] ul li"));
        int j = 0;
        while (rowsSelected.size() != j) {
          rowsSelected.get(j).findElement(By.cssSelector("a")).click();
          assertTrue(isElementPresent(By.cssSelector("h1")));
          rowsSelected = wd.findElements(By.cssSelector("li#app-[class='selected'] ul li"));
          j++;
        }
      }
      rows = wd.findElements(By.cssSelector("li#app-"));
    }
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


}
