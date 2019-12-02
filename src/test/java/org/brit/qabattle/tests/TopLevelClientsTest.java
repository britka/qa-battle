package org.brit.qabattle.tests;

import org.brit.qabattle.pages.DataCard;
import org.brit.qabattle.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.sleep;

/**
 * @author sbrit
 */
public class TopLevelClientsTest extends BaseTest {

    @Test
    public void downloadDescriptionTest() {
        DataCard dataCard = new LoginPage()
                .login("test", "test")
                .menu()
                .openTopLevelClient("Bugs Bunny");
//        String file = dataCard
//                .downLoadDescriptionAndReturnAsText();
//        String dataCardDiscr = dataCard.getDescription();
//        Assert.assertEquals(dataCardDiscr, file);

        dataCard.slide(85);
        sleep(3000);
        dataCard.slide(-50);



    }
}
