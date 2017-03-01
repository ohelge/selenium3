package ru.stqa.ol.sel3.litecart;

import com.google.common.collect.Ordering;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by A546902 on 2017-01-31.
 */
public class Litecart extends TestBase {
  @Test
  public void zadanie17() { /*Задание 17. Проверьте отсутствие сообщений в логе браузера
    Сделайте сценарий, который проверяет, не появляются ли в логе браузера сообщения при открытии страниц в учебном приложении, а именно -- страниц товаров в каталоге в административной панели.
    Сценарий должен состоять из следующих частей:
    1) зайти в админку  2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
    3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)*/
    List<String> names = new ArrayList<>();
    adminLogin("admin", "admin");
    wd.get(url + "/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    List<WebElement> rows = wd.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[not(@class='footer') and not(@class='header') and (@class='row')] "));
    for (WebElement row : rows) {
      String name = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
      names.add(name);
      System.out.println(name);
    }
    for (int i= 3; i< names.size(); i++) {
      wd.findElement(By.xpath(String.format("//a[.='%s']", names.get(i) ))).click();
      wd.manage().logs().get("browser").forEach(l -> System.out.println(l));
      wd.get(url + "/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    }
  }
  @Test
  public void getBrowserLogs() { //L10_m6 Доступ к логам браузера
    wd.navigate().to("http://selenium2.ru");
    System.out.println(wd.manage().logs().getAvailableLogTypes());//vivedet "[browser, driver, client]" driver i client bespolezni a vot browser polezen
    wd.manage().logs().get("browser").forEach(l -> System.out.println(l));//L10_m6 .getAll(): mojno v cikle proitis' i vipolnit' proverki tipa net owibok, prosto Info owibki itd
    //.filter(Level level) mojno otfil'trovat' ili cikl .forEach(lambda funk)
    wd.manage().logs().get("performance").forEach(l -> System.out.println(l)); //Posle dobavleniq 4h strok iz https://sites.google.com/a/chromium.org/chromedriver/logging/performance-log(sm L10_m6) vivodit ewe i [performance, browser, driver, client]
  }
  @Test
  public void zadanie14() {/* Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне
  Сценарий должен состоять из следующих частей:  1) зайти в админку
  2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
  3) открыть на редактирование какую-нибудь страну или начать создание новой
  4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.
  Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank". Но в этом упражнении требуется именно кликнуть по ссылке,
   чтобы она открылась в новом окне, потом переключиться в новое окно, закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.
  Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.*/
    adminLogin("admin", "admin");
    wd.get(url + "/litecart/admin/?app=countries&doc=countries");
    wd.findElement(By.cssSelector("tr.row td a")).click();
    String originalWindow = wd.getWindowHandle();
    Set<String> existingWindows = wd.getWindowHandles();
    wd.findElement(By.cssSelector("form[metod='post'] a[target='_blank']")).click();
// ожидание появления нового окна, идентификатор которого отсутствует в списке oldWindows, остаётся в качестве самостоятельного упражнения
    //String newWindow = wait.until(anyWindowOtherThan(existingWindows));
    //wd.switchTo().window(newWindow);
    wd.close();
    wd.switchTo().window(originalWindow);
    try {       Thread.sleep(2000);        } catch (Exception e) {          throw new RuntimeException(e);        }
  }

  @Test
  public void zadanie13() throws InterruptedException {/*Задание 13. Сделайте сценарий работы с корзиной
    Сделайте сценарий для добавления товаров в корзину и удаления товаров из корзины.
    1) открыть главную страницу    2) открыть первый товар из списка
    2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
    3) подождать, пока счётчик товаров в корзине обновится
    4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
    5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
    6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица*/
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // Неявные ожидания надо отключить, потому что в этом сценарии они приводят не нужному торможению.
    for (int i =1 ; i<=3; i++) {
      wd.get(url + "/litecart/");
      wd.findElement(By.cssSelector("ul.products a")).click();
      wait.until(presenceOfElementLocated(By.xpath("//button[@name='add_cart_product']")));
      if (isElementPresent(By.cssSelector("select"))) {
        Select size = new Select(wd.findElement(By.cssSelector("select[name='options[Size]']")));
        size.selectByValue("Small");
      }
      wd.findElement(By.cssSelector("[name='add_cart_product']")).click();
      wait.until (presenceOfElementLocated(By.xpath(String.format("//span[.='%s']", i)))) ;
      assertTrue ( Integer.parseInt (wd.findElement(By.cssSelector("span.quantity")).getAttribute("textContent")) == i) ;
    }
    wd.findElement(By.cssSelector("div#cart a")).click();
    wait.until(presenceOfElementLocated(By.cssSelector("span.phone")));
    do {
      for (int count = 0;; count++) { //vklu4aem qvnie ozhidaniq
        if (count > 10)
          throw new TimeoutException();
        try {
          wd.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
          try {       Thread.sleep(2000);        } catch (Exception e) {          throw new RuntimeException(e);        }
          break;
        }catch (NoSuchElementException e) {}
        Thread.sleep(1000);
      }
    }while (! isElementPresent(By.xpath("//*[.='There are no items in your cart.']")));
  }

  @Test
  public void zadanie12() { /*Задание 12. Сделайте сценарий добавления товара
Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).
Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product", заполнить поля с информацией о товаре и сохранить.
Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке Prices можно не добавлять.
Переключение между вкладками происходит не мгновенно, поэтому после переключения можно сделать небольшую паузу (о том, как делать более правильные ожидания, будет рассказано в следующих занятиях).
Картинку с изображением товара нужно уложить в репозиторий вместе с кодом. При этом указывать в коде полный абсолютный путь к файлу плохо, на другой машине работать не будет. Надо средствами языка программирования преобразовать относительный путь в абсолютный.
После сохранения товара нужно убедиться, что он появился в каталоге (в админке). Клиентскую часть магазина можно не проверять.*/
    adminLogin("admin", "admin");
    wd.findElement(By.xpath("//ul/*[2]/a")).click();
    assertEquals(wd.findElement(By.cssSelector("h1")).getText(), "Catalog");
    wd.findElement(By.cssSelector("div a.button:nth-child(2)")).click();
    assertEquals(wd.findElement(By.cssSelector("h1")).getText(), "Add New Product");
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
    Select price = new Select (wd.findElement(By.cssSelector("select[name='purchase_price_currency_code']")));
    price.selectByValue("USD");
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
    wd.get(url + "/litecart/");
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
    wd.get(url + "/litecart/");
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
  public void zadanie9() {  /*1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
    а) проверить, что страны расположены в алфавитном порядке
    б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
    2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке*/
    adminLogin("admin", "admin");
    wd.get(url + "/litecart/admin/?app=countries&doc=countries");
    List<WebElement> rows = wd.findElements(By.cssSelector("tr.row"));
    List<String> countries = new ArrayList<>();
    List<String> counriesExt = new ArrayList<>();//dlq stran s rajonami
    List<String> zones = new ArrayList<>();//dlq zon
    int i = 0;
    for (WebElement row : rows) {
      String country = row.findElement(By.cssSelector("td:nth-child(5)")).getText();
      countries.add(country);
   }
    assertTrue(Ordering.natural().isOrdered(countries));//iz. compile 'com.google.guava:guava:21.0'  import com.google.common.collect.Ordering;*/
    for (WebElement row : rows) {
      if (Integer.parseInt( row.findElement(By.cssSelector("td:nth-child(6)")).getText() ) != 0) {
        String countryExt = row.findElement(By.cssSelector("td:nth-child(5)")).getText();
        counriesExt.add(countryExt);
        System.out.println(row.findElement(By.cssSelector("td:nth-child(6)")).getText() + " zones in "+ countryExt  );
     }
    }
    for (i=0; i< counriesExt.size(); i++ ){
      wd.findElement(By.xpath(String.format("//a[.='%s']", counriesExt.get(i) ))).click();
      System.out.println(counriesExt.get(i)+ "\n");
      List<WebElement> rowsExt = wd.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[not(@class='header')]"));
      for (WebElement rowExt : rowsExt) {
        String zone = rowExt.findElement(By.cssSelector("td:nth-child(3)")).getText();
        zones.add(zone);
        System.out.println(zone + ":");
      }
      zones.remove(zones.size()-1);
      assertTrue(Ordering.natural().isOrdered(zones));
      wd.get(url + "/litecart/admin/?app=countries&doc=countries");
      zones.clear();
    }
  }
  @Test(enabled = true)
  public void zadanie8() { //Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров http://software-testing.ru/lms/mod/assign/view.php?id=38589
    wd.get(url + "/litecart");
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
    adminLogin("admin", "admin");
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

  @Test(enabled = true)
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
    //wd.findElement(By.name("btnG")).click();
    wd.findElement(By.name("_btnG")).click();//L10_m5 lomaem lokator,4tobi shvatit' isklu4enie i skrinwot sm. public void onException(Throwable throwable, WebDriver driver)
    wait.until(titleIs("webdriver - Sök på Google"));
    Assert.assertTrue(isElementPresent(By.cssSelector(".rc"))); //l5_m9 Nawelsq element klassa rc. Na stranice mnogo takih blokov, no mi iwem odin
  }
}
