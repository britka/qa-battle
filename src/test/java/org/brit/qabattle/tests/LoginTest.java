package org.brit.qabattle.tests;

import com.codeborne.selenide.Condition;
import org.brit.qabattle.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author sbrit
 */
public class LoginTest extends BaseTest {
    @BeforeMethod
    public void beforeMethod() {
        clearBrowserCookies();
        refresh();
    }

    @Test
    public void loginSuccessTest() {
        new LoginPage().login("test", "test");
        $("#mainContainer").shouldBe(Condition.visible);
    }

    @Test
    public void loginUnsuccessfulTest() {
        new LoginPage().login("test1", "test1");
        $("#mainContainer .error").shouldBe(Condition.visible);
    }


}
