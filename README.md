Seletest 
========
![seletest](https://cloud.githubusercontent.com/assets/3785668/4871463/ff777690-61b7-11e4-9cb7-916e8d43f616.png)

<a href="https://buildhive.cloudbees.com/job/GiannisPapadakis/job/Seletest/">
<img src="https://camo.githubusercontent.com/f35d0c52028b388ea9593c5fd2bf78a3b955c7af/68747470733a2f2f6275696c64686976652e636c6f7564626565732e636f6d2f6a6f622f6d696368616c2d6c6970736b692f6a6f622f706167652d6f626a656374732d7765626472697665722f62616467652f69636f6e" alt="Build Status" data-canonical-source="https://buildhive.cloudbees.com/job/michal-lipski/job/page-objects-webdriver/badge/icon" style="max-width:100%;">

*****************************************************************************************
Web and Mobile Automation testing framework based on Spring - Webdriver - Appium in Java.
*****************************************************************************************

This is a Java Framework based on WebDriver API to interact with web or mobile applications for performing automated functional tests.

******<b>Javadoc: http://giannispapadakis.github.io/Seletest/</b> ******


<html>

<body>
<table style="width:100%; border: 1px solid black;border-collapse: collapse;">
  <tr>
    <td>Web End to End functional</td>
    <td><b>Supported</b></td>		
  </tr>
  <tr>
    <td>DB transactions tests</td>
    <td><b>Not supported yet...</b></td>		
  </tr>
  <tr>
    <td>Client performance tests</td>
    <td><b>Supported</b></td>		
  </tr>
   <tr>
    <td>Mobile End to End functional</td>
    <td><b>Partially supported (Android)</b></td>		
  </tr>
  <tr>
    <td>Web Security tests</td>
    <td><b>Upcoming</b></td>		
  </tr>
</table>

</body>
</html>

<b>Frameworks - tools:</b><br>
* Selenium 2 in Java<br>
* TestNG JUnit framework<br>
* Spring Java Framework<br>
* ReportNG<br>
* Appium Java client<br>
* Apache Maven<br>
* AspectJ<br>
* Browsermob-proxy<br>


<b>Current Drivers supported:</b><br>
* ChromeDriver<br>
* InternetExplorerDriver<br>
* FirefoxDriver<br>
* SafariDriver<br>
* OperaDriver<br>
* PhantomJSDriver<br>
* AppiumDriver (IOSDriver-AndroidDriver)<br>

<b>Features:</b>
 * Fluent logging mechanism and error handling using AspectJ support with advices
 * Interaction with Page Objects and Page Facades using hard or soft assertions
 * Asynchronous execution of verifications with Spring Task Async Executors covering dynamic pages (AngularJS)
 * Appium support with custom TouchAction API for interaction with Android devices-emulators
 * JS errors collection during execution of tests
 * JVM memory usage with JMX client
 * HAR file with network traffic logs using browser-mob proxy that can be analyzed in online tools like https://code.google.com/p/harviewer/ 
 * Custom JQuery selector replaces CSS pseudo-classes support in WebDriver (<b>:contains('') / nth-child</b>)
 * Custom Angular selectors (<b>Upcoming</b>)


<b>Tips for Internet Explorer execution</b><br>
On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value. The value can be on or off, as long as it is the same for every zone. To set the Protected Mode settings, choose "Internet Options..." from the Tools menu, and click on the Security tab. For each zone, there will be a check box at the bottom of the tab labeled "Enable Protected Mode".<br>
Additionally, "Enhanced Protected Mode" must be disabled for IE 10 and higher. This option is found in the Advanced tab of the Internet Options dialog.


*******************************************
<b>Released versions</b>
*******************************************

Seletest has been uploaded in sonatype nexus.<br>

Add this to your pom.xml:<br>

<b>Under \<project\> tag</b> <br>

![repo](https://cloud.githubusercontent.com/assets/3785668/4512733/cb9308ba-4b43-11e4-8101-905376c28c6e.png)
 
<b>Under \<dependencies\> tag:</b><br>

![seletest](https://cloud.githubusercontent.com/assets/3785668/4512750/02aa9048-4b44-11e4-9444-98ba48f35769.png)

See wiki for setting up Spring Maven Project and running first tests<br>

<b> You are very welcome to <a href="https://guides.github.com/activities/contributing-to-open-source/">contribute</a> to the project</b>

<b>Upcoming:</b>
* Set up Appium Server on Windows 7 and run test against android emulator
* Set up Selenium Grid server and register a node
* Use SeletestUtils project to automate remote appium-selenium node configuration


