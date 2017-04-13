import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class CalculatorTest {

  private WebDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName(BrowserType.CHROME);
    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    driver.get("https://demo.tds.airast.org/TDSCalculator/TDSCalculator.html?mode=ScientificInv");
  }

  @After
  public void tearDown() throws Exception {
    if (driver != null)
      driver.quit();
  }

  @Test
  public void should_get_result_2_when_1_plus_1() throws MalformedURLException {
    WebElement num1 = driver.findElement(By.cssSelector("#num1"));
    assertThat(num1.getText(), is("1"));

    WebElement opPlus = driver.findElement(By.cssSelector("#plus"));
    assertThat(opPlus.getText(), is("+"));

    WebElement eq = driver.findElement(By.cssSelector("#equals"));
    assertThat(eq.getText(), is("="));

    num1.click();
    opPlus.click();
    num1.click();
    eq.click();

    WebElement result = driver.findElement(By.cssSelector("#textinput"));
    assertThat(result.getAttribute("value"), is("2"));
  }

  @Test
  public void should_get_result_0_when_1_minus_1() throws MalformedURLException {
    WebElement num1 = driver.findElement(By.cssSelector("#num1"));
    assertThat(num1.getText(), is("1"));

    WebElement opPlus = driver.findElement(By.cssSelector("#minus"));
    assertThat(opPlus.getText(), is("−"));

    WebElement eq = driver.findElement(By.cssSelector("#equals"));
    assertThat(eq.getText(), is("="));

    num1.click();
    opPlus.click();
    num1.click();
    eq.click();

    WebElement result = driver.findElement(By.cssSelector("#textinput"));
    assertThat(result.getAttribute("value"), is("0"));
  }

  @Test
  public void should_get_result_1_when_1_multiply_1() throws MalformedURLException {
    WebElement num1 = driver.findElement(By.cssSelector("#num1"));
    assertThat(num1.getText(), is("1"));

    WebElement opPlus = driver.findElement(By.cssSelector("#multiply"));
    assertThat(opPlus.getText(), is("x"));

    WebElement eq = driver.findElement(By.cssSelector("#equals"));
    assertThat(eq.getText(), is("="));

    num1.click();
    opPlus.click();
    num1.click();
    eq.click();

    WebElement result = driver.findElement(By.cssSelector("#textinput"));
    assertThat(result.getAttribute("value"), is("1"));
  }

  @Test
  public void should_get_result_1_when_1_divide_1() throws MalformedURLException {
    WebElement num1 = driver.findElement(By.cssSelector("#num1"));
    assertThat(num1.getText(), is("1"));

    WebElement opPlus = driver.findElement(By.cssSelector("#divide"));
    assertThat(opPlus.getText(), is("÷"));

    WebElement eq = driver.findElement(By.cssSelector("#equals"));
    assertThat(eq.getText(), is("="));

    num1.click();
    opPlus.click();
    num1.click();
    eq.click();

    WebElement result = driver.findElement(By.cssSelector("#textinput"));
    assertThat(result.getAttribute("value"), is("1"));
  }

//  @Test
//  public void should_get_result_2_when_1_plus_1_and_using_selenide() throws MalformedURLException {
//    Configuration.browser = BrowserType.FIREFOX;
//    Configuration.remote = "http://localhost:5000/wd/hub";
//
//    open("https://demo.tds.airast.org/TDSCalculator/TDSCalculator.html?mode=ScientificInv");
//    SelenideElement num1 = $("#num1").shouldHave(Condition.text("1"));
//    num1.click();
//
//    $("#plus").shouldHave(Condition.text("+")).click();
//    num1.click();
//    $("#equals").shouldHave(Condition.text("=")).click();
//    $("#textinput").shouldHave(Condition.value("2"));
//  }

}
