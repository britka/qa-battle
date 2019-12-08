package org.brit.qabattle.pages;

import org.brit.qabattle.pages.menus.ArticlesToReadMenu;
import org.brit.qabattle.pages.menus.SavedArticlesMenu;
import org.brit.qabattle.pages.userprofile.UserProfileSettingsPage;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author sbrit
 */
public class MainPage {
    protected static Logger logger = Logger.getLogger(MainPage.class.getName());
    public ArticlesToReadMenu articlesToReadMenu() {
        logger.info("Get article to read menu");
        return new ArticlesToReadMenu();
    }

    public SavedArticlesMenu savedArticlesMenu() {
        logger.info("Get saved articles menu");
        return new SavedArticlesMenu();
    }

    public UserProfileSettingsPage userProfile() {
        logger.info("Go to user info section");
        $("#avatarContainer img").click();
        return new UserProfileSettingsPage();
    }
}
