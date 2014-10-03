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
<b>Released version 0.0.1</b>
*******************************************

Seletest has been uploaded in sonatype nexus.<br>

Add this to your pom.xml:<br>

Under \<project\> tag <br>

\<repositories\><br>
    \<repository\><br>
      \<id\>oss.sonatype.org\</id\><br>
      \<url\>https://oss.sonatype.org/content/groups/public/\</url\><br>
   \</repository\><br>
 \</repositories\><br>
 
Under \<dependencies\> tag:<br>

\<dependency\><br>
			\<groupId\>com.github.giannispapadakis\</groupId\><br>
			\<artifactId\>Seletest\</artifactId\><br>
			\<version\>0.0.1\</version\><br>
\</dependency\><br>

See wiki for setting up Spring Maven Project and running first test<br>

<b>Upcoming:</b>
* Set up Appium Server on Windows 7 and run test against android emulator
* Set up Selenium Grid server and register a node


