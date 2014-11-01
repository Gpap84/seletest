Seletest 
========
![seletest](https://cloud.githubusercontent.com/assets/3785668/4871449/dcb9ef44-61b6-11e4-91dc-507370d15d5d.png)

<a href="https://buildhive.cloudbees.com/job/GiannisPapadakis/job/Seletest/">
<img src="https://camo.githubusercontent.com/f35d0c52028b388ea9593c5fd2bf78a3b955c7af/68747470733a2f2f6275696c64686976652e636c6f7564626565732e636f6d2f6a6f622f6d696368616c2d6c6970736b692f6a6f622f706167652d6f626a656374732d7765626472697665722f62616467652f69636f6e" alt="Build Status" data-canonical-source="https://buildhive.cloudbees.com/job/michal-lipski/job/page-objects-webdriver/badge/icon" style="max-width:100%;">

*****************************************************************************************
Web and Mobile Automation testing framework based on Spring - Webdriver - Appium in Java.
*****************************************************************************************

This is a Java Framework based on WebDriver API to interact with web or mobile applications for performing automated functional tests.

******<b>Javadoc: http://giannispapadakis.github.io/Seletest/</b> ******


Tests supported:<br>
a] Web End to End functional <b>Supported</b><br>
b] DB transactions tests <b>Not supported yet...</b><br>
c] Client performance tests <b>Supported</b><br>
d] Mobile End to End functional <b>Partially supported (Android)</b><br>
e] Web Security tests <b>Upcoming</b>


Tools used:<br>
<b>* Selenium 2 in Java</b><br>
<b>* TestNG JUnit framework</b><br>
<b>* Spring Java Framework</b><br>
<b>* ReportNG</b><br>
<b>* Appium Java client</b><br>
<b>* Apache Maven</b><br>
<b>* AspectJ</b><br>
<b>* Browsermob-proxy</b><br>


Current Drivers supported:<br>
a] ChromeDriver<br>
b] InternetExplorerDriver<br>
c] FirefoxDriver<br>
d] PhantomJSDriver<br>
e] AppiumDriver<br>

Features:
 * Fluent logging mechanism using AspectJ support with advices
 * Interaction with Page Objects and Page Facades using hard or soft assertions (with hard assertions, exception is thrown, thus preventing any further execution and with soft assertions, exceptions are stored to Map and verification of @Test is done only when the test finished)
 * All the verification methods are asynchronously executed using Spring Task Executor covering dynamic pages
 * Appium support with custom TouchAction API for interaction with Android devices-emulators
 * JS errors collection during execution of tests
 * JVM memory usage
 * HAR file with network traffic logs using browser-mob proxy that can be analyzed in online tools like https://code.google.com/p/harviewer/ 
 * Custom JQuery selector replaces CSS Pseudo-classes support in WebDriver (:contains('')/nth-child)
 * Custom Angular selectors (<b>Upcoming</b>)


<b>Tips for IEDriver</b><br>
On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value. The value can be on or off, as long as it is the same for every zone. To set the Protected Mode settings, choose "Internet Options..." from the Tools menu, and click on the Security tab. For each zone, there will be a check box at the bottom of the tab labeled "Enable Protected Mode".<br>
Additionally, "Enhanced Protected Mode" must be disabled for IE 10 and higher. This option is found in the Advanced tab of the Internet Options dialog.


*******************************************
<b>Released version 0.0.1 - 0.0.2</b>
*******************************************

Seletest has been uploaded in sonatype nexus.<br>

Add this to your pom.xml:<br>

<b>Under \<project\> tag</b> <br>

![repo](https://cloud.githubusercontent.com/assets/3785668/4512733/cb9308ba-4b43-11e4-8101-905376c28c6e.png)
 
<b>Under \<dependencies\> tag:</b><br>

![seletest](https://cloud.githubusercontent.com/assets/3785668/4512750/02aa9048-4b44-11e4-9444-98ba48f35769.png)

See wiki for setting up Spring Maven Project and running first tests<br>

<b>Upcoming:</b>
* Set up Appium Server on Windows 7 and run test against android emulator
* Set up Selenium Grid server and register a node
* Use SeletestUtils project to automate remote appium-selenium node configuration


