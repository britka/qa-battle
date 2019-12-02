package org.brit.qabattle.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

/**
 * @author sbrit
 */
public class MainMenu {
    private SelenideElement mainMenuRootElement = $$("#mainContainer .card").first();

    private MainMenu clickOnTopLevelItem(String itemName) {
        getMainMenuItem(itemName).click();
        return new MainMenu();
    }

    private SelenideElement getMainMenuItem(String menuItemName) {
        return mainMenuRootElement.$$(".tree-main-node button").find(Condition.exactText(menuItemName));
    }

    public MainMenu openAdvertisersMenu() {
        if (!isSubmenuVisible("Advertisers")) {
            clickOnTopLevelItem("Advertisers");
        }
        return new MainMenu();
    }

    public MainMenu closeAdvertisersMenu() {
        if (isSubmenuVisible("Advertisers")) {
            clickOnTopLevelItem("Advertisers");
        }
        return new MainMenu();
    }

    public MainMenu openPublishersMenu() {
        if (!isSubmenuVisible("Publishers")) {
            return clickOnTopLevelItem("Publishers");
        }
        return new MainMenu();
    }

    public MainMenu closesPublishersMenu() {
        if (isSubmenuVisible("Publishers")) {
            return clickOnTopLevelItem("Publishers");
        }
        return new MainMenu();
    }

    public MainMenu openTopLevelClients() {
        if (!isSubmenuVisible("Top level clients")) {
            return clickOnTopLevelItem("Top level clients");
        }
        return new MainMenu();
    }

    public MainMenu closeTopLevelClients() {
        if (isSubmenuVisible("Top level clients")) {
            return clickOnTopLevelItem("Top level clients");
        }
        return new MainMenu();
    }

    private boolean isSubmenuVisible(String mainMenuItemName) {
        return getMainMenuItem(mainMenuItemName).parent().$(".sub-tree").is(Condition.visible);
    }


    public DataCard openTopLevelClient(String clientName) {
        openTopLevelClients().getMainMenuItem(clientName).click();
        return new DataCard();
    }


}
