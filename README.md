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
d] Mobile End to End functional <b>Not fully supported yet</b><br>


Tools used:<br>
<b>* Selenium 2 in Java</b><br>
<b>* TestNG JUnit framework</b><br>
<b>* Spring Java Framework</b><br>
<b>* ReportNG</b><br>
<b>* Appium Java client</b><br>
<b>* Apache Maven</b><br>
<b>* AspectJ</b><br>
<b>* Browsermob-proxy</b><br>


Current Drivers supported<br>
a] ChromeDriver<br>
b] InternetExplorerDriver for IE9<br>
c] FirefoxDriver<br>
d] PhantomJSDrive<br>
e] AppiumDriver<br>

<b>Tips for IEDriver</b><br>
On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value. The value can be on or off, as long as it is the same for every zone. To set the Protected Mode settings, choose "Internet Options..." from the Tools menu, and click on the Security tab. For each zone, there will be a check box at the bottom of the tab labeled "Enable Protected Mode".<br>
Additionally, "Enhanced Protected Mode" must be disabled for IE 10 and higher. This option is found in the Advanced tab of the Internet Options dialog.

********************************************************
<b>Inject beans to non Spring classes (TestNG listeners)</b>
********************************************************

To actually get the injection working to domain objects (non Spring classes) you need to weave some Spring aspects in your domain class. While you could do this by compile time weaving, i chose load time weaving as it is much simpler to set up in development environment. The cruicial part is to activate a Java agent for your runtime that now weaves the aspects into the domain class during class loading.

<b>Eclipse:</b> 
Run with JVM arguments:
-javaagent:${localMavenRepository}\org\springframework\spring-agent\2.5.6\spring-agent-2.5.6.jar
-noverify

<b>Maven:</b>
Surefire argument: 
<argLine>
       -javaagent:${settings.localRepository}/org/springframework/spring-agent/2.5.6/spring-agent-2.5.6.jar
</argLine>
