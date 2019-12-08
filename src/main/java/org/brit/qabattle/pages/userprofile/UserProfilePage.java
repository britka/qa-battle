package org.brit.qabattle.pages.userprofile;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * @author sbrit
 */
public class UserProfilePage extends UserProfileSettingsPage {
    public UserProfilePage setUserFirstName(String firstName) {
        logger.info("Set user name: " + firstName);
        $("#firstNameInput").setValue(firstName);
        return this;
    }

    public UserProfilePage setUserLastName(String lastName) {
        logger.info("Set user last name: " + lastName);
        $("#lastNameInput").setValue(lastName);
        return this;
    }

    public UserProfilePage saveUserProfile() {
        logger.info("Save user info...");
        $$("button").find(Condition.exactText("Save user info")).click();
        return this;
    }

    public UserProfilePage checkIfSuccessMessageVisible() {
        logger.info("Checking if success message is visible");
        $("#successUserInfoSaveInfo").shouldBe(Condition.visible);
        return this;
    }

    public UserProfilePage checkIfFirstNameErrorVisible() {
        logger.info("Checking if error message for first name field is visible");
        $("#firstNameInput").parent().$(".invalid-feedback").shouldBe(Condition.visible);
        return this;
    }

    public UserProfilePage checkIfLastNameErrorVisible() {
        logger.info("Checking if error message for last name field is visible");
        $("#lastNameInput").parent().$(".invalid-feedback").shouldBe(Condition.visible);
        return this;
    }

    public UserProfilePage checkIfLastNameErrorContainsText(String text) {
        logger.info("Checking if error message for last name field contains text: " + text);
        $("#lastNameInput").parent().$(".invalid-feedback").shouldHave(Condition.text(text));
        return this;
    }

    public UserProfilePage checkIfFirstNameErrorContainsText(String text) {
        logger.info("Checking if error message for first name field contains text: " + text);
        $("#firstNameInput").parent().$(".invalid-feedback").shouldHave(Condition.text(text));
        return this;
    }


}
