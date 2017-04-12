# Selenium Grid Workshop

https://gitpitch.com/qinyu/selenium-grid-workshop/master

---

### Agenda
1. Selenium WebDriver Intro
2. Selenium Grid Intro
3. Scalable Tests
4. Scale Selenium Grid with Docker
5. Demo & Summary

---

### Selenium WebDriver

![selenium webdriver](http://www.seleniumframework.com/wp-content/uploads/2015/01/selenium-rc_architecturepng.png)

---

### Practice 1

Selenium Environment Preparation  

+++ 

### Checklist

- Firefox & Chrome Installed
- geckodriver & chromedriver downloaded and unzipped
- geckodriver & chromedriver added to `PATH`(optional)

---

### Practice 2

Write a selenium test  

* **Scenario**: 1+1 = 2  
* **AUT**: https://demo.tds.airast.org/TDSCalculator/TDSCalculator.html?mode=ScientificInv  

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
driver.get("https://demo.tds.airast.org/TDSCalculator/TDSCalculator.html?mode=ScientificInv");

WebElement num1 = driver.findElement(By.cssSelector("#num1"));assertThat(num1.getText(), is("1"));
num1.click();
...
WebElement result = driver.findElement(By.cssSelector("#textinput"));
assertThat(result.getAttribute("value"), is("2"));
```
---

### One More Thing

Concise Java DSL for Selenium -- **Selenide**  

http://selenide.org/quick-start.html  


```java
Configuration.browser=BrowserType.FIREFOX;
open("https://demo.tds.airast.org/TDSCalculator/TDSCalculator.html?mode=ScientificInv");
$("#num1").shouldHave(text("1")).click();
...
$("textinput").shouldHave(value("2"));
```

--- 

### Discussion

Assumptions:
1. Already have PCs with enough performance(eg, 4 CPU cores with 32GB memory)
2. Both functionality and compatibility must be covered as much as possible
3. Get tests' feedback as soon as possible

What's the Bottleneck of Automation Infrastructure?

+++

### Selenium WebDriver Summary

- :thumbsdown: Type, version and number of browsers are limited(can't cover compatibility fully)
- :thumbsdown: Can not scale(especially for CI)
- :thumbsup: Convinient to debug tests as browser is on the same machine  <!-- .element: class="fragment" -->

+++

### Selenium Grid

![selenium grid](http://www.seleniumframework.com/wp-content/uploads/2015/01/Overall_GRID_architecture.png)

+++

### Terminology - Hub

+++

### Terminology - Node

+++

---

### Practice 3

Setup "Remote" and "Distributed" Selenium Grid/Node  

Tips:
https://github.com/SeleniumHQ/selenium/wiki/Grid2

+++

### Setup Hub

Command to Start Hub:  
```sh
java -jar selenium-server-standalone-3.3.1.jar -role hub
```

Check other arguments:  
```sh
java -jar selenium-server-standalone-3.3.1.jar -role hub -help
```

+++

### Key Hub Arguments

+++

### Register a Node

Command to Register a Node:  
```sh
java -jar selenium-server-standalone-3.0.1.jar -role node -hub http://localhost:4444/grid/register -browser browserName=firefox,maxInstances=3 -browser browserName=chrome,maxInstances=5
```

Check other arguments:  
```sh
java -jar selenium-server-standalone-3.0.1.jar -role node -help
```

+++

### Node Config File

+++

### Check Grid Console

http://localhost:4444/grid/console
http://localhost:4444/wd/hub/status

+++

### Use Remote Driver

Selenium:  
```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setBrowserName(BrowserType.FIREFOX);
WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
```

Selenide:  
```java
Configuration.browser = BrowserType.FIREFOX;
Configuration.remote = "http://localhost:5000/wd/hub";
```

--- 

### One More Thing

**Custom Selenium Grid**

- Proxy
- Matcher
- Prioritizer
- Servlets

---

### Discussion

What would prevent us achieving quick feedback in previous demo 
though Selenium Grid Infrastructure is setup already?

+++ 

### Scalable Tests

FIRST



+++

### Practice 4

Make tests scalable

+++

### Using Setup/Teardown

```java
```

+++

### Implement More Tests 

**Scenario**:  
1+1 = 2  
1-1 = 0  
1*1 = 1  
1/1 = 1  
...

+++

### Run Tests In Parallel

Configuration of `maven-surefire-plugin`

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.12.4</version>
    <executions>
        <execution>
            <id>cucumber tests</id>
            <phase>integration-test</phase>
            <goals>
                <goal>test</goal>
            </goals>
            <configuration>
                <threadCount>5</threadCount>
                <includes>
                    <include>**/*IT.java</include>
                </includes>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Discussion

Is there any other way for paralleling tests?

---

### Docker 101

- **docker images -a** command that lists all the images
- **docker run** Run a command in a new container
- **docker -d** command that used along with docker run that runs container in background and prints container ID
- **docker -p** command to explicitly map a single port or range of ports.
- **docker --link <name or id>**:alias name is the name of the container we’re linking to and alias is an alias for the link name.
- **docker ps** :Shows running containers by default. 
- **docker kill**: Kill one or more running containers
- **docker rm**: Remove containers

---

### Selenium Grid Docker Images

- **selenium/hub**: Image for running a Selenium Grid Hub
- **selenium/node-chrome-debug**: Selenium node with Chrome installed and runs a VNC server, needs to be connected to a Selenium Grid Hub
- **selenium/node-firefox-debug**: Selenium node with Firefox installed and runs a VNC server, needs to be connected to a Selenium Grid Hub

---

### Practice 5

Setup Selenium Infrastructure by Docker

+++

### Setup Hub & Nodes

```sh
docker run -d -p 5000:4444 --name selenium-hub  -P selenium/hub
docker run -d --link selenium-hub:hub -P -p 12346:5900 --name chrome selenium/node-chrome-debug
```

+++?gist=a513d69045a3f303301a3c6fe1a7a5b5 

### Or Docker Compose

```sh
```

+++ 

---

### Cloud Testing

---

### Summary
- Selenium Grid provides scalable automation
- Keep in mind that test must be independent and isolated when implementing
- Leverage Docker to automatically scale Selenium Grid


---

### Appendix
List of tooling required(need downloaded)  
**JDK(1.7+)**  
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html  
**Maven(3.3+)**  
https://maven.apache.org/download.cgi  
**Selenium Standalone Server(3.3.1)**  
https://goo.gl/uTXEJ1  
http://www.seleniumhq.org/download/  

+++

### Requirements(Cont.)

**Browsers**
   - Firefox: https://www.google.com/chrome/
   - Chrome: https://www.mozilla.org/en-US/firefox/new/

**Browser Drivers**
   - geckodriver(0.15.0): https://github.com/mozilla/geckodriver/releases
   - chromedriver(2.29): https://sites.google.com/a/chromium.org/chromedriver/

