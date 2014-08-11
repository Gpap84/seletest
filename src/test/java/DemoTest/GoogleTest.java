package DemoTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.AssertionType;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GooglePage;

@SeleniumTest
public class GoogleTest extends SeletestWebTestBase{

    @Autowired
    GooglePage googlePage;

    @SeleniumTest(assertion=AssertionType.HARD)
    @Test
    public void googleSearch(){
        googlePage.
                openPage(GooglePage.class).
                typeSearch("https://github.com/GiannisPapadakis").
                buttonSearch();

        SessionControl.verifyController().elementPresent("//*[contains(text(),'Giannis Papadakis')]");
    }

    @SeleniumTest(assertion=AssertionType.SOFT)
    @Test
    public void googleSearch2(){
        googlePage.
                openPage(GooglePage.class).
                typeSearch("Nothing").
                buttonSearch();

        SessionControl.verifyController().elementPresent("//text");
    }

}
