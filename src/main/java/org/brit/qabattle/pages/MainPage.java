package org.brit.qabattle.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import sun.applet.Main;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * @author sbrit
 */
public class MainPage {
    private SelenideElement mainContainer = $("#mainContainer");
    private SelenideElement topLevelClientsButton = $$("button").find(Condition.exactText("Top level clients"));

    public MainMenu menu(){
        return new MainMenu();
    }
}
