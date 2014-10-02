Seletest
========

*****************************************************************************************
Web and Mobile Automation testing framework based on Spring - Webdriver - Appium in Java.
*****************************************************************************************

This is a Java Framework based on WebDriver API to interact with web or mobile applications for performing automated functional tests.

******<b>Javadoc: http://giannispapadakis.github.io/Seletest/</b> ******


Tests supported:<br>
a] Web End to End functional <b>Supported</b><br>
b] DB transactions tests <b>Not supported yet...</b><br>
c] Client performance Tests <b>Supported...</b><br>
d] Mobile End to End functional <b>Partially supported (Android)</b><br>


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
b] InternetExplorerDriver for IE9<br>
c] FirefoxDriver<br>
d] PhantomJSDriver<br>
e] AppiumDriver<br>


<b>Tips for IEDriver</b><br>
On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value. The value can be on or off, as long as it is the same for every zone. To set the Protected Mode settings, choose "Internet Options..." from the Tools menu, and click on the Security tab. For each zone, there will be a check box at the bottom of the tab labeled "Enable Protected Mode".<br>
Additionally, "Enhanced Protected Mode" must be disabled for IE 10 and higher. This option is found in the Advanced tab of the Internet Options dialog.


*******************************************
<b>Steps for running Demo</b>
*******************************************

1] Download project into your local environment<br>
2] IDE: Using your favorite IDE you need to register it to lombok project first. <br>Locate artifact <b><LocalMavenRepo>\org\projectlombok\lombok\</b> and double click to jar. <br>In the window you must specify the location where STS.exe or eclipse.exe is located and press Install.<br> After this step you can import the project to IDE and compile it.<br>
3] You can run a test suite by running the corresponding xml file from eclipse or using maven commands specifying the suite (see the wiki for further details)<br>

<b>Upcoming:</b>
* Set up appium server on Windows 7 and run test against android emulator
* Set up Selenium Grid server and register a node 
* Use released jar of Seletest and create your own test project in IDE


