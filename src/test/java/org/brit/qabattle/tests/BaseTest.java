package org.brit.qabattle.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.brit.qabattle.utils.FileDownloaderResponseFilter;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;

/**
 * @author sbrit
 */
public class BaseTest {
    @BeforeSuite
    public void beforeSuite() {
        if (Configuration.browser.equals("firefox"))
            initFirefox();
        Configuration.timeout = 12000;
        Configuration.startMaximized = true;
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
        open("http://localhost:8080/");
        //      WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("secret", "IAmSuperSeleniumMaster"));
        refresh();
        FileDownloaderResponseFilter fileDownloadFilter = FileDownloaderResponseFilter.withContent("text/plain");
        if (getSelenideProxy().responseFilter("Download.filter") == null) {
            getSelenideProxy().addResponseFilter("Download.filter", fileDownloadFilter);
        }

    }

    private void initFirefox() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.openFile",
                "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.closeWhenDone", false);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        Configuration.browserCapabilities = capabilities;
    }


}
