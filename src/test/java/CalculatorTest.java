import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class CalculatorTest {

  @Test
  public void should_get_result_2_when_sum_1_and_1() {
    WebDriver driver = new FirefoxDriver(new DesiredCapabilities());
    driver.get("http://www.calculator.net");

    WebElement num1 = driver.findElements(By.cssSelector(".scinm")).get(6);
    assertThat(num1.getText(), is("1"));

    WebElement opSum = driver.findElements(By.cssSelector(".sciop")).get(0);
    assertThat(opSum.getText(), is("+"));

    WebElement eq = driver.findElements(By.cssSelector(".scieq")).get(1);
    assertThat(eq.getText(), is("="));

    num1.click();
    opSum.click();
    num1.click();
    eq.click();

    WebElement result = driver.findElement(By.cssSelector("#sciOutPut"));
    assertThat(result.getText(), is("2."));
  }

}
