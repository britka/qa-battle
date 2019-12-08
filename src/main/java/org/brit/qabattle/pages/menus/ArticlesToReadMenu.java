package org.brit.qabattle.pages.menus;

import static com.codeborne.selenide.Selenide.$$;

/**
 * @author sbrit
 */
public class ArticlesToReadMenu extends MenuBaseClass {

    public ArticlesToReadMenu() {
        mainMenuRootElement = $$("#mainContainer .card").first();
    }
}
