package org.brit.qabattle.pages.menus;

import static com.codeborne.selenide.Selenide.$$;

/**
 * @author sbrit
 */
public class SavedArticlesMenu extends MenuBaseClass {
    public SavedArticlesMenu() {
        mainMenuRootElement = $$("#mainContainer .card").last();
    }
}
