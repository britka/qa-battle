package org.brit.qabattle.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;

/**
 * @author sbrit
 */
public class BaseTest {
    @BeforeSuite
    public void beforeSuite() {
        Configuration.timeout = 12000;
        Configuration.startMaximized = true;
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
        open("http://localhost:8080/");
    }
}
