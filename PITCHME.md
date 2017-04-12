# Selenium Grid Workshop

[]

---

### Agenda
1. 

---

### Requirements
List of tooling required(need downloaded)  
**JDK(1.7+)**  
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html  
**Maven(3.3+)**  
https://maven.apache.org/download.cgi  
**Selenium Standalone Server(3.3.1)**  
https://goo.gl/uTXEJ1  
http://www.seleniumhq.org/download/  
**Cucumber Plugin for IDEs(optional)**  <!-- .element: class="fragment" -->
   - Eclipse: https://cucumber.io/cucumber-eclipse/  <!-- .element: class="fragment" -->
   - IntelliJ: https://plugins.jetbrains.com/plugin/7212-cucumber-for-java  <!-- .element: class="fragment" -->

+++

### Requirements(Cont.)
**Browsers**
   - Firefox: https://www.google.com/chrome/
   - Chrome: https://www.mozilla.org/en-US/firefox/new/

**Browser Drivers**
   - geckodriver(0.15.0): https://github.com/mozilla/geckodriver/releases
   - chromedriver(2.29): https://sites.google.com/a/chromium.org/chromedriver/

---

### Selenium WebDriver

![selenium webdriver](http://www.seleniumframework.com/wp-content/uploads/2015/01/selenium-rc_architecturepng.png)

---

### Practice 1

Selenium Environment Preparation  

- Firefox & Chrome Installed
- geckodriver & chromedriver downloaded and unzipped
- geckodriver & chromedriver added to `PATH`(optional)

---

### Practice 2

Write a selenium test  

**Scenario**: 1+1 = 2
**AUT**: http://www.calculator.net/

+++

### Setup Maven Project

Dependencies:  
* com.selenium.seleniumhq:selenium:3.3.1
* junit:junit:4.12

+++

### Implement Test
Selenium API Tips:

```java
WebDriver driver = new FirefoxDriver(new DesiredCapabilities());
driver.get("http://www.calculator.net");

WebElement num1 = driver.findElements(By.cssSelector(".scinm")).get(6);assertThat(num1.getText(), is("1"));
num1.click();
```
---

### One More Thing

Concise Java DSL for Selenium -- **Selenide**

http://selenide.org/quick-start.html  
http://selenide.org/documentation.html  

```java
Configuration.browser="firfox"
open("http://www.calculator.net")
$$(".scinm").get(6)
            .shouldHave(text("1"))
            .click();
```

---

### Selenium WebDriver Summary

- Convinient to debug tests as browser is on the same machine
- Type, version and number of browsers are limited
- Can not scale(especially for CI)

+++

### Selenium Grid

![selenium grid](http://www.seleniumframework.com/wp-content/uploads/2015/01/Overall_GRID_architecture.png)

---

### Practice 2

Setup & Use 'Distributed' Selenium Grid

+++

### Setup Hub

+++

### Register Node

+++

### Grid Console

+++

### Use Remote Driver

---

### Problems


+++ 

### TestCases Requirement

FIRST

+++

### Practice 3

Using `@After` clean up sessions

+++


---

### Selenium Grid Benefits

---

### Practice 4

Write more cases and parallel them  

**Scenario**:  
1+1 = 2  
1-1 = 0  
1*1 = 1  
1/1 = 1  
...

### Implement More Test Cases


### Run In Parallel

Configuration of `maven-surefire-plugin`

```xml
```

### Question

- Is there any other way for paralleling tests?
- 






### Summary









