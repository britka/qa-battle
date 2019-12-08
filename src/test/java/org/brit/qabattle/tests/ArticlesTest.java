package org.brit.qabattle.tests;

import com.codeborne.selenide.WebDriverRunner;
import org.brit.qabattle.pages.DataCard;
import org.brit.qabattle.pages.MainPage;
import org.brit.qabattle.pages.menus.ArticlesType;
import org.brit.qabattle.tests.data_providers.DataProviders;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.refresh;

/**
 * @author sbrit
 */
public class ArticlesTest extends BaseTest {
    private MainPage mainPage = new MainPage();

    @BeforeClass
    public void beforeTopLevelClientsTestClass() {
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("secret", "IAmSuperSeleniumMaster"));
        refresh();
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderForTopLevelClients")
    public void downloadInfoAboutTopLevelClientsTest(String clientName) {
        refresh();
        DataCard dataCard = mainPage
                .articlesToReadMenu()
                .selectSubElement(ArticlesType.TOP_LEVEL_CLIENTS, clientName);
        String description = dataCard.getDescription();
        String downloadedDescription = dataCard.downLoadDescriptionAndReturnAsText();
        Assert.assertEquals(description, downloadedDescription);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderForTopLevelClients", enabled = false)
    public void downloadInfoAboutTopLevelClientsIgnoreWhiteSpacesTest(String clientName) {
        refresh();
        DataCard dataCard = mainPage
                .articlesToReadMenu()
                .selectSubElement(ArticlesType.TOP_LEVEL_CLIENTS, clientName);
        String description = dataCard.getDescription().replaceAll("\\s+\n", "\n");
        String downloadedDescription = dataCard.downLoadDescriptionAndReturnAsText().replaceAll("\\s+\n", "\n");
        Assert.assertEquals(description, downloadedDescription);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderAdvertisers")
    public void downloadInfoAboutAdvertisersTest(String advertiserName) {
        refresh();
        DataCard dataCard = mainPage
                .articlesToReadMenu()
                .selectSubElement(ArticlesType.ADVERTISERS, advertiserName);
        String description = dataCard.getDescription();
        String downloadedDescription = dataCard.downLoadDescriptionAndReturnAsText();
        Assert.assertEquals(description, downloadedDescription);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "dataProviderPublishers")
    public void downloadInfoAboutPublisherTest(String publisherName) {
        refresh();
        DataCard dataCard = mainPage
                .articlesToReadMenu()
                .selectSubElement(ArticlesType.PUBLISHERS, publisherName);
        String description = dataCard.getDescription();
        String downloadedDescription = dataCard.downLoadDescriptionAndReturnAsText();
        Assert.assertEquals(description, downloadedDescription);
    }

    @Test(dataProvider = "articleTypes")
    public void moveArticleFromSavedToRead(ArticlesType type) {
        refresh();
        // Precondition: Move all advertisers to saved
        String[] objects = getDataByType(type);
        for (String name : objects) {
            mainPage
                    .articlesToReadMenu()
                    .selectSubElement(type, name)
                    .scrollDescriptionToTheBottom()
                    .moveToSaved();
        }

        for (String name : objects) {
            mainPage
                    .savedArticlesMenu()
                    .selectSubElement(type, name)
                    .scrollDescriptionToTheBottom()
                    .removeFromSaved();
        }

        Assert.assertFalse(mainPage.savedArticlesMenu().isSubTreeVisible(type));
        List<String> advertisersAsListRead = mainPage
                .articlesToReadMenu()
                .getSubElementsAsListOfStrings(type);
        Assert.assertTrue(advertisersAsListRead.containsAll(Arrays.asList(objects)));
    }

    @Test(dataProvider = "articleTypes")
    public void moveArticlesToSave(ArticlesType type) {
        refresh();
        String[] objects = getDataByType(type);
        for (String name : objects) {
            mainPage
                    .articlesToReadMenu()
                    .selectSubElement(type, name)
                    .scrollDescriptionToTheBottom()
                    .moveToSaved();
        }
        Assert.assertFalse(mainPage.articlesToReadMenu().isSubTreeVisible(type));
        List<String> advertisersAsListSaved = mainPage
                .savedArticlesMenu()
                .getSubElementsAsListOfStrings(type);
        Assert.assertTrue(advertisersAsListSaved.containsAll(Arrays.asList(objects)));
    }

    @Test
    public void checkSavedArticlesShouldExistAfterRefreshPage() {
        ArticlesType type = ArticlesType.ADVERTISERS;
        String[] objects = getDataByType(type);
        for (String name : objects) {
            mainPage
                    .articlesToReadMenu()
                    .selectSubElement(type, name)
                    .scrollDescriptionToTheBottom()
                    .moveToSaved();
        }
        refresh();
        Assert.assertTrue(mainPage.savedArticlesMenu().isSubTreeVisible(type));
    }

    @Test
    public void checkImageDimensionsWhileSliding() {
        SoftAssert softAssert = new SoftAssert();
        DataCard adidasCard = mainPage
                .articlesToReadMenu()
                .selectSubElement(ArticlesType.ADVERTISERS, "Adidas");
        DataCard.ImageSize imageSize = adidasCard.getImageDimension();
        for (int i = 1; i <= 100; i++) {
            DataCard.ImageSize previousSize = adidasCard.getImageDimension();
            adidasCard.slideJS(i);
            DataCard.ImageSize currentSize = adidasCard.getImageDimension();
            softAssert.assertEquals(currentSize.compareTo(previousSize), 1,
                    "Image size should be changed: \n" +
                            "Slider value: " + i + "\n" +
                            "Size before slide:" + previousSize + "\n" +
                            "Size after slide:" + currentSize);
        }
        softAssert.assertAll();
    }

    @DataProvider(name = "articleTypes")
    public Object[] dataProviderArticleType() {
        return ArticlesType.values();
    }

    private String[] getDataByType(ArticlesType type) {
        switch (type) {
            case ADVERTISERS:
                return (String[]) DataProviders.dataProviderAdvertisers();
            case PUBLISHERS:
                return (String[]) DataProviders.dataProviderPublishers();
            case TOP_LEVEL_CLIENTS:
                return (String[]) DataProviders.dataProviderForTopLevelClients();
            default:
                return null;
        }
    }


}
