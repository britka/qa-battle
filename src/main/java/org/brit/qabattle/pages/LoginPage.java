package org.brit.qabattle.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author sbrit
 */
public class LoginPage {
    private SelenideElement userLoginInput = $("#loginInput");
    private SelenideElement userPasswordInput = $("#passwordInput");
    private SelenideElement button = $$(".btn.btn-primary").last();
    private SelenideElement signInButton = $(".card-footer img");

    public MainPage login(String userLogin, String userPass) {
        button.shouldBe(Condition.disabled, Condition.visible, Condition.exactText("Hover me faster!"));
        $$(".form-group").first().click();
        userLoginInput.setValue(userLogin);
        $$(".form-group").last().click();
        userPasswordInput.click();
        userPasswordInput.setValue(userPass);
        button.shouldBe(Condition.enabled, Condition.exactText("Hover me faster!"))
                .hover()
                .shouldHave(Condition.exactText("Wait for some time..."))
                .shouldBe(Condition.disappear);
        signInButton.shouldBe(Condition.appear).click();
        confirm("Are you sure you want to login?");
        confirm("Really sure?");
        $("#mainContainer").shouldBe(Condition.visible);
        return new MainPage();
    }


}
