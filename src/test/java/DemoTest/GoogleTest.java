package DemoTest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.DataDriven;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.AssertionType;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GooglePage;

@DataDriven(filePath="./target/test-classes/DataSources/demoTest.properties")
@SeleniumTest
@SuppressWarnings({"rawtypes"})
public class GoogleTest extends SeletestWebTestBase{

    @Autowired
    GooglePage googlePage;

    @SeleniumTest(assertion=AssertionType.HARD, driver=DriverType.SELENIUM)
    @Test
    public void googleSearch(Map<String, String> map){
        googlePage.
        typeSearch(map.get("GoogleSearch")).
        buttonSearch();

        SessionControl.verifyController().elementPresent(map.get("ExpectedResult"));
    }

    @SeleniumTest(assertion=AssertionType.SOFT)
    @Test
    public void googleSearch2(Map<String, String> map){
        googlePage.
        typeSearch(map.get("GoogleSearch2")).
        buttonSearch();

        SessionControl.verifyController().elementPresent(map.get("ExpectedResult"));
    }

}
