## Selenium Grid Workshop/Training

https://gitpitch.com/qinyu/selenium-grid-workshop/master

---

### Agenda
1. Selenium WebDriver
2. Scalable Tests
3. Selenium Grid
4. Scale Selenium Grid
5. Summary

---

### Selenium WebDriver

![selenium webdriver](http://www.seleniumframework.com/wp-content/uploads/2015/01/selenium-rc_architecturepng.png)

---

### Practice 1

Selenium Environment Setup  

+++ 

### Checklist

- Firefox & Chrome Installed
- geckodriver & chromedriver downloaded and unzipped
- geckodriver & chromedriver added to `PATH`(optional)

---

### Practice 2

Write a selenium test for online calculator  

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

### Scalable Tests

**F.I.R.S.T** Principles of Unit Testing

**FAST**<!-- .element: class="fragment" --> **ISOLATED/INDEPENDENT**<!-- .element: class="fragment" --> **REPEATABLE**<!-- .element: class="fragment" --> **SELF-VALIDATING**<!-- .element: class="fragment" --> **THOROUGH/TIMELY**<!-- .element: class="fragment" -->

https://pragprog.com/magazines/2012-01/unit-tests-are-first <!-- .element: class="fragment" -->
+++ 

## Isolated & Repeatable

- A test should NOT depend on any data or resource in the environment/instance in which it is running.
- Deterministic results - should yield the same results every time and at every location where they run.
- Each test should setup or arrange it's own data/resources.
- No order-of-run dependency. They should pass or fail the same way in suite or when run individually.

>In order to parallel tests to get quick feedback<!-- .element: class="fragment" -->

---

### Practice 3

Parallel scalable tests

+++

### Take Care of Resource

Using JUnit "Setup/Teardown"

```java
@Before
public void setUp() throws Exception {
  DesiredCapabilities capabilities = new DesiredCapabilities();
  capabilities.setBrowserName(BrowserType.CHROME);
  driver = new RemoteWebDriver(new URL("http://localhost:5000/wd/hub"), capabilities);
}
@After
public void tearDown() throws Exception {
  driver.quit();
}
```

+++

### Implement More Tests 

**Scenarios**:  
1+1 = 2  
1-1 = 0  
1*1 = 1  
1/1 = 1  
...

+++

### Parallel Tests

Using `maven-surefire-plugin` parallel feature

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.12.4</version>
            <configuration>
                <parallel>methods</parallel>
                <threadCount>5</threadCount>
            </configuration>
        </plugin>
    </plugins>
</build>
```

> **Attention**: Take concurrency problem carefully!

---

### Question

Is there any other way for paralleling tests?

- Group tests and run separately in different process  <!-- .element: class="fragment" -->
  - Take Cucumber for example, you can group test with tags and run with maven plugin `cucumber-jvm-parallel-plugin`  <!-- .element: class="fragment" -->
- Need merge separate tests reports  <!-- .element: class="fragment" -->
  - Take Cucumber for example, you may use `cucumber-reporting` library
<!-- .element: class="fragment" -->

https://github.com/temyers/cucumber-jvm-parallel-plugin  <!-- .element: class="fragment" -->
https://github.com/damianszczepanik/cucumber-reporting  <!-- .element: class="fragment" -->

But..<!-- .element: class="fragment" -->

---

### Discussion

![selenium webdriver](http://www.seleniumframework.com/wp-content/uploads/2015/01/selenium-rc_architecturepng.png)

+++

Assumptions:
1. Already have PC with enough performance(eg, 4 CPU cores with 32GB memory)
2. Both functionality and compatibility must be covered as much as possible
3. Get tests' feedback as soon as possible

What's the Bottleneck of Automation Infrastructure?

+++

- Type, version and number of browsers are limited(can't cover compatibility fully)
- Can not scale(especially for CI)
- Convinient to debug tests as browser is on the same machine  <!-- .element: class="fragment" -->

---

### Selenium Grid

+++?image=http://www.seleniumframework.com/wp-content/uploads/2015/01/Overall_GRID_architecture.png

+++

### Terminology - Hub

- The intermediary and manager
- Accepts requests to run tests
- Allocates Test Slots to nodes
- Takes instructions directly from client, and executes them remotely on nodes
- The Hub only manages threads—it performs zero browser work

+++

### Terminology - Node

- Registers itself to Hub listed in config
- Communicates its config to the Hub at registration time
- Receives json wire protocol instructions from the Hub
- Executes instructions, keeping threads separated
- The Node does not evaluate, make judgments, or control anything: it only receives and executes instructions (and throws exceptions)

---

### Practice 4

Setup "Remote" and "Distributed" Selenium Grid/Node  

https://github.com/SeleniumHQ/selenium/wiki/Grid2

+++

### Setup Hub

Command to Start Hub:  
```sh
java -jar selenium-server-standalone-3.3.1.jar -role hub
```

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

```json
{
    "capabilities": [{
        "browserName": "chrome",
        "maxInstances": 5
    },],
    "configuration": {
        "cleanUpCycle": 2000,
        "proxy": "org.openqa.grid.selenium.proxy.WebDriverRemoteProxy",
        "port": 5555,
        "host": "localhost",
        "register": true,
        "hubPort": 4444,
        "maxSessions": 5
    }
}
```

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

- Proxy: Custom logic at each of the nodes such as “auto restart” (or) “start/stop video recording” etc.
- CapabilityMatcher: Decides on which test should be routed to which node
- Prioritizer: Used to sort queued new Session requests
- Servlets: Grid plugins

---

### Question

Is there any tool to ease maintaining work?

>[Selenium Grid Extras](https://github.com/groupon/Selenium-Grid-Extras)  
>[Jenkins Selenium Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Selenium+Plugin)

And... <!-- .element: class="fragment" -->

---

### Docker

+++?image=https://docs.docker.com/engine/article-img/architecture.svg

+++

### Docker 101

- **docker images -a** command that lists all the images
- **docker run** Run a command in a new container
- **docker -d** command that used along with docker run that runs container in background and prints container ID
- **docker -p** command to explicitly map a single port or range of ports.

+++ 

- **docker --link \<`name or id`\>:`alias`** `name or id` is the name of the container we’re linking to and `alias` is an alias for the link name.
- **docker ps** :Shows running containers by default. 
- **docker kill**: Kill one or more running containers
- **docker rm**: Remove containers

---

### Selenium Grid Docker Images

- **selenium/hub**: Image for running a Selenium Grid Hub
- **selenium/node-chrome-debug**: Selenium node with Chrome installed and runs a VNC server, needs to be connected to a Selenium Grid Hub
- **selenium/node-firefox-debug**: Selenium node with Firefox installed and runs a VNC server, needs to be connected to a Selenium Grid Hub
- ...

https://github.com/SeleniumHQ/docker-selenium<!-- .element: class="fragment" -->

---

### Practice 5

Setup Selenium Infrastructure by Docker

+++

### Setup Hub & Nodes

```sh
docker run -d -p 5000:4444 --name selenium-hub  -P selenium/hub
docker run -d --link selenium-hub:hub -P -p 12346:5900 --name chrome selenium/node-chrome-debug
```

+++

### Or Docker Compose

+++?gist=a513d69045a3f303301a3c6fe1a7a5b5 

--- 

### One More Thing

Scale Grid on demand  

>[zalenium](https://github.com/zalando/zalenium)  
>[Selenium Grid Scaler](https://github.com/mhardin/SeleniumGridScaler)  

---

### Question

How much does it coast to maintain Selenium Grid on cloud?

+++

[Distributed Automation Using Selenium Grid/AWS/Autoscaling](
https://www.youtube.com/watch?v=cbIfU1fvGeo)

##### Comparing AWS cost to Data centre <!-- .element: class="fragment" -->
> 1 Medium box (~$8000/per month)  
> 1 Large box (~$10000/per month)  
> 1 VM (~$2000/per month)  
> Total AWS cost for Batch Processing Topology (~$800/per month)  
<!-- .element: class="fragment" -->

And...<!-- .element: class="fragment" -->

---

### Testing Cloud

**BrowserStack** vs. **Sauce Labs** vs. **TestingBot**
https://stackshare.io/stackups/browserstack-vs-sauce-labs-vs-testingbot

**Selenium Compatibale!**<!-- .element: class="fragment" -->`

---

### Summary
- Selenium Grid provides scalable automation
- Keep in mind that test must be repeatable and isolated when implementing
- Leverage Docker and Cloud to automatically scale Selenium Grid

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

**Browsers**
   - Firefox: https://www.google.com/chrome/
   - Chrome: https://www.mozilla.org/en-US/firefox/new/

**Browser Drivers**
   - geckodriver(0.15.0): https://github.com/mozilla/geckodriver/releases
   - chromedriver(2.29): https://sites.google.com/a/chromium.org/chromedriver/

