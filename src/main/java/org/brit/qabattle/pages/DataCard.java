package org.brit.qabattle.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.brit.qabattle.utils.FileDownloaderResponseFilter;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;

/**
 * @author sbrit
 */
public class DataCard {
    protected static Logger logger = Logger.getLogger(DataCard.class.getName());
    private SelenideElement dataCardRootElement = $("#dataCard");
    private SelenideElement cardTitle = dataCardRootElement.$("h5.card-title");
    private SelenideElement cardText = dataCardRootElement.$$(".card-text").first();
    private SelenideElement downloadButton = dataCardRootElement.$$("button").find(Condition.exactText("Download info"));
    private SelenideElement moveToSavedButton = dataCardRootElement.$$("button").find(Condition.exactText("Move To saved"));
    private SelenideElement removeFromSavedButton = dataCardRootElement.$$("button").find(Condition.exactText("Removed from saved"));
    private SelenideElement cardDescription = dataCardRootElement.$("textarea");
    private SelenideElement cardImage = dataCardRootElement.$("#heroImage");

    public DataCard() {
        moveToSavedButton.shouldBe(Condition.visible);
    }

    public String getDescription() {
        logger.info("Get article description");
        return cardDescription.getValue();
    }

    public DataCard scrollDescriptionToTheBottom() {
        logger.info("Scroll description textarea to the bottom");
        executeJavaScript("arguments[0].scrollTop =  arguments[0].scrollHeight", cardDescription);
        return this;
    }

    public File downLoadDescription() {
        logger.info("Download info");
        downloadButton.click();
        return ((FileDownloaderResponseFilter) getSelenideProxy().responseFilter("Download.filter")).getTempFile();
    }

    public DataCard checkCardTitle(String title) {
        logger.info("Checking card title contains text: " + title);
        cardTitle.shouldHave(Condition.exactText(title));
        return this;
    }

    public String downLoadDescriptionAndReturnAsText() {
        logger.info("Download info and return it as text");
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

    public DataCard slideJS(int percent) {
        logger.info("Move slider with JS on " + percent + " percents");
        dataCardRootElement.$("span.ui-slider-handle").shouldBe(Condition.visible);
        int max = maxSliderValue();
        int min = minSliderValue();
        int pointsToMove = ((max - min) / 100 * percent) + min;
        executeJavaScript("   hs = $('.ui-slider').slider();\n" +
                "        hs.slider('option', 'value', " + pointsToMove + ");\n" +
                "        hs.slider('option', 'slide').call(hs, null, {handle:$('.ui-slider-handle', hs), value: " + pointsToMove + " });");
        return this;
    }

    private int minSliderValue() {
        return Integer.parseInt(executeJavaScript("return $('.ui-slider').slider('option', 'min');").toString());
    }

    private int maxSliderValue() {
        return Integer.parseInt(executeJavaScript("return $('.ui-slider').slider('option', 'max');").toString());
    }

    private int currentSliderValue() {
        return Integer.parseInt(executeJavaScript("return $('.ui-slider').slider('option', 'value');").toString());
    }

    public DataCard moveToSaved() {
        logger.info("Move card to Saved");
        moveToSavedButton.click();
        return this;
    }

    public DataCard removeFromSaved() {
        logger.info("Remove card from Saved");
        removeFromSavedButton.click();
        return this;
    }

    public ImageSize getImageDimension() {
        logger.info("Get image size");
        int height = Integer.parseInt(executeJavaScript("return arguments[0].style.height", cardImage).toString().replace("px", ""));
        int width = Integer.parseInt(executeJavaScript("return arguments[0].style.width", cardImage).toString().replace("px", ""));
        return new ImageSize(width, height);
    }


    public DataCard slide(int percent) {
        logger.info("Move slider with keys");
        CharSequence[] charSequences;
        if (percent > 0) {
            charSequences = new CharSequence[]{Keys.ARROW_RIGHT, Keys.ARROW_RIGHT};
        } else {
            charSequences = new CharSequence[]{Keys.ARROW_LEFT, Keys.ARROW_LEFT};
        }
        dataCardRootElement.$("span.ui-slider-handle").click();
        IntStream.range(0, Math.abs(percent))
                .forEach(i -> actions()
                        .sendKeys(charSequences)
                        .build().perform());
        return this;
    }

    public class ImageSize implements Comparable<ImageSize> {
        int width;
        int height;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public ImageSize setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public ImageSize setHeight(int height) {
            this.height = height;
            return this;
        }

        @Override
        public int compareTo(ImageSize o) {
            if (this.height < o.height && this.width < o.height)
                return -1;
            else if (this.height > o.height && this.width > o.height)
                return 1;
            else
                return 0;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", ImageSize.class.getSimpleName() + "[", "]")
                    .add("width=" + width)
                    .add("height=" + height)
                    .toString();
        }
    }

}
