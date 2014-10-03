package DemoTest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.DataSource;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.AssertionType;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GooglePage;

@DataSource(filePath="./target/test-classes/DataSources/demoTest.properties")
@SeleniumTest
public class GoogleTest extends SeletestWebTestBase{

    @Autowired
    GooglePage googlePage;

    @SeleniumTest(assertion=AssertionType.HARD, driver=DriverType.SELENIUM)
    @Test
    public void googleSearch(Map<String, String> map) throws InterruptedException, ExecutionException{
        googlePage.typeSearch(map.get("GoogleSearch")).buttonSearch();
        SessionControl.verifyController().elementPresent(map.get("ExpectedResult")).get();
    }

    @SeleniumTest(assertion=AssertionType.SOFT)
    @Test
    public void googleSearch2(Map<String, String> map) throws InterruptedException, ExecutionException{
        googlePage.typeSearch(map.get("GoogleSearch2")).buttonSearch();
        SessionControl.verifyController().elementPresent(map.get("ExpectedResult")).get();
    }

}
