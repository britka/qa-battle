package org.brit.qabattle.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.brit.qabattle.utils.FileDownloaderResponseFilter;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;

/**
 * @author sbrit
 */
public class DataCard {
    private SelenideElement dataCardRootElement = $("#dataCard");
    private SelenideElement cardTitle = dataCardRootElement.$("h5.card-title");
    private SelenideElement cardText = dataCardRootElement.$$(".card-text").first();
    private SelenideElement downloadButton = dataCardRootElement.$$("button").find(Condition.exactText("Download info"));
    private SelenideElement moveToSavedButton = dataCardRootElement.$$("button").find(Condition.exactText("Move To Test"));
    private SelenideElement removeFromSavedButton = dataCardRootElement.$$("button").find(Condition.exactText("Removed from saved"));
    private SelenideElement cardDescription = dataCardRootElement.$("textarea");

    public String getDescription() {
        return cardDescription.getText();
    }

    public File downLoadDescription() {
        FileDownloaderResponseFilter fileDownloadFilter = FileDownloaderResponseFilter.withContent("text/plain");
        getSelenideProxy().addResponseFilter("Download.filter", fileDownloadFilter);
        downloadButton.click();
        return fileDownloadFilter.getTempFile();
    }

    public String downLoadDescriptionAndReturnAsText() {
        File file = downLoadDescription();
        if (file != null) {
            try {
                return FileUtils.readFileToString(file, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public DataCard slide(int p) {
        CharSequence[] charSequences;
        if (p > 0) {
            charSequences = new CharSequence[]{Keys.ARROW_RIGHT, Keys.ARROW_RIGHT};
        } else {
            charSequences = new CharSequence[]{Keys.ARROW_LEFT, Keys.ARROW_LEFT};
        }
        dataCardRootElement.$("span.ui-slider-handle").click();
        IntStream.range(0, Math.abs(p))
                .forEach(i -> actions()
                        .sendKeys(charSequences)
                        .build().perform());
        return this;
    }

}
