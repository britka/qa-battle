package org.brit.qabattle.pages.menus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.brit.qabattle.pages.DataCard;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author sbrit
 */
public class MenuBaseClass {
    protected static Logger logger = Logger.getLogger(MenuBaseClass.class.getName());
    protected SelenideElement mainMenuRootElement;

    public MenuBaseClass openArticleByType(ArticlesType type) {
        logger.info("Open article type: " + type.getValue());
        if (!isSubTreeVisible(type)) {
            getArticleTypeElement(type).click();
        }
        return this;
    }

    private SelenideElement getArticleTypeElement(ArticlesType type) {
        logger.info("Get article type element by type: " + type.getValue());
        return mainMenuRootElement
                .$$(".tree-main-node > button")
                .find(Condition.exactText(type.getValue()));
    }

    public boolean isSubTreeVisible(ArticlesType type) {
        logger.info("Check if subtree visible of article by type: " + type.getValue());
        return getArticleTypeElement(type)
                .parent()
                .$("div[class$=sub-tree]").is(Condition.visible);
    }

    public DataCard selectSubElement(ArticlesType type, String subElementName) {
        logger.info("Select subelement of " + type.getValue() + " with name: " + subElementName);
        openArticleByType(type);
        getArticleTypeElement(type).parent().$("div[class$=sub-tree]").$$("button").find(Condition.exactText(subElementName)).click();
        return new DataCard();
    }

    public List<String> getSubElementsAsListOfStrings(ArticlesType type) {
        logger.info("Get all elements of article type " + type.getValue() + " as list of strings");
        openArticleByType(type);
        return getArticleTypeElement(type).parent().$("div[class$=sub-tree]").$$("button").texts();
    }


}
