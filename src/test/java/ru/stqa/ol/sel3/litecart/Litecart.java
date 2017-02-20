package ru.stqa.ol.sel3.litecart;

import com.google.common.collect.Ordering;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Random;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart extends TestBase {
  @Test
  public void zadanie12() { /*Задание 12. Сделайте сценарий добавления товара
Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).
Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product", заполнить поля с информацией о товаре и сохранить.
Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке Prices можно не добавлять.
Переключение между вкладками происходит не мгновенно, поэтому после переключения можно сделать небольшую паузу (о том, как делать более правильные ожидания, будет рассказано в следующих занятиях).
Картинку с изображением товара нужно уложить в репозиторий вместе с кодом. При этом указывать в коде полный абсолютный путь к файлу плохо, на другой машине работать не будет. Надо средствами языка программирования преобразовать относительный путь в абсолютный.
После сохранения товара нужно убедиться, что он появился в каталоге (в админке). Клиентскую часть магазина можно не проверять.*/
    adminLogin();
    wd.findElement(By.xpath("//ul/*[2]/a")).click();
    assertEquals(wd.findElement(By.cssSelector("h1")).getAttribute("innerText"), " Catalog");
    wd.findElement(By.cssSelector("div a.button:nth-child(2)")).click();
    assertEquals(wd.findElement(By.cssSelector("h1")).getAttribute("innerText"), " Add New Product");
    wd.findElement(By.cssSelector("label [value='1']")).click();
    wd.findElement(By.xpath("//*[@id='tab-general']/table/tbody/tr[2]/td/span/input")).sendKeys("dDuck");
    String path = new File("src/test/resources/Sepultura.jpg").getAbsolutePath();
    wd.findElement(By.xpath("//*[@id='tab-general']/table/tbody/tr[9]/td/table/tbody/tr[1]/td/input")).sendKeys(path);

    wd.findElement(By.cssSelector("a[href='#tab-information']")).click();
    WebElement manufacturer = wd.findElement(By.cssSelector("select[name='manufacturer_id']"));
    ((JavascriptExecutor)wd).executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change')) " , manufacturer);
    wd.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys( wd.findElement(By.cssSelector("div.trumbowyg-editor")).getAttribute("baseURI") );

    wd.findElement(By.cssSelector("a[href='#tab-prices']")).click();
    wd.findElement(By.cssSelector("input[name='purchase_price']")).clear();
    wd.findElement(By.cssSelector("input[name='purchase_price']")).sendKeys("666");
    Select price = new Select (wd.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
    price.selectByValue("EUR");
    wd.findElement(By.cssSelector("input[name='prices[EUR]']")).sendKeys("555");
    wd.findElement(By.cssSelector("button[name='save']")).click();
    wd.findElement(By.xpath("//a[.='dDuck']"));
    //try {       Thread.sleep(2000);        } catch (Exception e) {          throw new RuntimeException(e);        }
  }

  @Test
  public void zadanie11() { /*Задание 11. Сделайте сценарий регистрации пользователя
1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
3) повторный вход в только что созданную учётную запись,   4) и ещё раз выход.*/
    Random rnd = new Random();
    wd.get("http://localhost/litecart/");
    wd.findElement(By.cssSelector("div#box-account-login a")).click();
    wd.findElement(By.cssSelector("input[name='firstname']")).sendKeys(generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 5 ));
    wd.findElement(By.cssSelector("input[name='lastname']")).sendKeys(generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 10 ));
    wd.findElement(By.cssSelector("input[name='address1']")).sendKeys(generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789", 30 ));
    wd.findElement(By.cssSelector("input[name='postcode'")).sendKeys(generateString(rnd, "123456789", 5 ));
    wd.findElement(By.cssSelector("input[name='city']")).sendKeys(generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 11 ));
    wd.findElement(By.name("country_code")).findElement(By.cssSelector("[value='US']")).click();
    String email= generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 6) + "@" + generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 4) + "com";
    wd.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
    String placeholder= wd.findElement(By.cssSelector("input[name='phone']")).getAttribute("placeholder");
    wd.findElement(By.cssSelector("input[name='phone']")).sendKeys(placeholder + generateString(rnd, "0123456789", 11 ));
    String pwd= generateString(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcd0123456789!#%&/?=()", 10);
    wd.findElement(By.cssSelector("input[name='password']")).sendKeys(pwd);
    wd.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys(pwd);
    wd.findElement(By.cssSelector("button[name='create_account']")).click();
    if (isElementPresent(By.xpath("//a[.='Logout']"))  ) {
     wd.findElement(By.xpath("//a[.='Logout']")).click();
     wd.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
     wd.findElement(By.cssSelector("input[name='password']")).sendKeys(pwd);
     wd.findElement(By.cssSelector("button[name='login']")).click();
     //try {       Thread.sleep(2000);        } catch (Exception e) {          throw new RuntimeException(e);        }
     assertFalse(isElementPresent(By.xpath("//div[.=' Wrong password or the account is disabled, or does not exist']")));
     wd.findElement(By.xpath("//a[.='Logout']")).click();
    }
  }
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
    WebElement duckCampaign = wd.findElement(By.cssSelector("div#box-campaigns strong.campaign-price"));// акционная жирная
    String duckName = duck.getAttribute("textContent"); //dlq parametra getAttribute smotri tab Properties v Chrome!
    String duckRegularPrice = duckRegular.getAttribute("textContent");
    String duckCampaignPrice = duckCampaign.getAttribute("textContent");
    assertTrue(duckRegular.getCssValue("color").contains("119, 119, 119"));//обычная цена серая
    assertEquals( duckRegular.getCssValue("text-decoration"),"line-through");//обычная цена зачёркнутая,
    assertTrue( duckCampaign.getCssValue("color").contains("204, 0, 0"));// акционная цена красная
    assertTrue(fontSize(duckRegular.getCssValue("font-size")) < fontSize(duckCampaign.getCssValue("font-size")));// г) акционная цена крупнее, чем обычная
    System.out.println(fontSize(duckRegular.getCssValue("font-size"))  +"  " + fontSize(duckCampaign.getCssValue("font-size")));

    wd.findElement(By.cssSelector("div#box-campaigns a.link")).click();
    try {       Thread.sleep(1000);        } catch (Exception e) {          throw new RuntimeException(e);        }
    WebElement duckRegularDuckPage = wd.findElement(By.cssSelector("s.regular-price"));
    WebElement duckCampaignDuckPage = wd.findElement(By.cssSelector("strong.campaign-price"));// акционная цена жирная
    assertEquals(wd.findElement(By.cssSelector("h1.title")).getAttribute("textContent"), duckName); // а)  на главной странице и на странице товара совпадает текст названия товара
    assertEquals(duckRegularDuckPage.getAttribute("textContent"), duckRegularPrice);//б) на главной странице и на странице товара совпадают цены (обычная)
    assertEquals(duckCampaignDuckPage.getAttribute("textContent"), duckCampaignPrice);//б) на главной странице и на странице товара совпадают цены (акционная)
    assertTrue( wd.findElement(By.cssSelector("body")).getCssValue("color").contains("102, 102, 102"));///обычная цена серая
    assertEquals( duckRegularDuckPage.getCssValue("text-decoration"),"line-through");//обычная цена зачёркнутая,
    assertTrue( duckCampaignDuckPage.getCssValue("color").contains("204, 0, 0")) ;// акционная цена красная
    assertTrue(fontSize(duckRegularDuckPage.getCssValue("font-size")) < fontSize(duckCampaignDuckPage.getCssValue("font-size")));// г) акционная цена крупнее, чем обычная
    System.out.println(fontSize(duckRegularDuckPage.getCssValue("font-size"))  +"  " + fontSize(duckCampaignDuckPage.getCssValue("font-size")));

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
