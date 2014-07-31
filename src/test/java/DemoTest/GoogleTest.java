package DemoTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.services.annotations.WebTest;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GooglePage;

@WebTest
public class GoogleTest extends SeletestWebTestBase{

    @Autowired
    GooglePage googlePage;

    @Test
    public void googleSearch(){
        GooglePage googleHomePage=googlePage.
                openPage(GooglePage.class).
                typeSearch("https://github.com/GiannisPapadakis").
                buttonSearch();

        assert googleHomePage.isTextDisplayed("GiannisPapadakis") : true;
    }
    @Test
    public void googleSearch2(){
        GooglePage googleHomePage=googlePage.
                openPage(GooglePage.class).
                typeSearch("Nothing").
                buttonSearch();

        assert googleHomePage.isTextDisplayed("Giannis") : true;
    }

}
