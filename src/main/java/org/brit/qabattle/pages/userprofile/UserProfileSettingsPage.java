package org.brit.qabattle.pages.userprofile;

import org.brit.qabattle.pages.MainPage;
import org.brit.qabattle.pages.menus.MenuBaseClass;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.back;

/**
 * @author sbrit
 */
public class UserProfileSettingsPage {
    protected static Logger logger = Logger.getLogger(UserProfileSettingsPage.class.getName());
    public UserProfilePage chooseUserProfile() {
        logger.info("Choose user profile page");
        $("#v-pills-home-tab").click();
        return new UserProfilePage();
    }

    public PaymentInfoPage choosePaymentInfo() {
        logger.info("Choose payment info page");
        $("#v-pills-messages-tab").click();
        return new PaymentInfoPage();
    }

    public MainPage backToMainPage() {
        logger.info("Back to main page");
        back();
        return new MainPage();
    }
}
