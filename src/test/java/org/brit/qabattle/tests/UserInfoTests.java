package org.brit.qabattle.tests;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.brit.qabattle.pages.MainPage;
import org.brit.qabattle.pages.userprofile.PaymentInfoPage;
import org.brit.qabattle.pages.userprofile.PaymentSystem;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.refresh;

/**
 * @author sbrit
 */
public class UserInfoTests extends BaseTest {
    String userName = "UserName_" + RandomStringUtils.randomAlphanumeric(6);
    String userLastName = "UserName_" + RandomStringUtils.randomAlphanumeric(6);
    String cardNum = RandomStringUtils.randomNumeric(16);
    int paymentDay = 16;
    PaymentSystem paymentSystem = PaymentSystem.MASTER_CARD;
    private MainPage mainPage = new MainPage();

    @BeforeClass
    public void beforeTopLevelClientsTestClass() {
        clearBrowserCookies();
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("secret", "IAmSuperSeleniumMaster"));
        refresh();
    }

    @Test
    public void firstNameLastNameTests() {
        mainPage
                .userProfile()
                .chooseUserProfile()
                .saveUserProfile()
                .checkIfFirstNameErrorVisible()
                .setUserLastName(userLastName)
                .saveUserProfile()
                .checkIfFirstNameErrorVisible()
                .setUserLastName("")
                .setUserFirstName(userName)
                .saveUserProfile()
                .checkIfLastNameErrorVisible()
                .setUserFirstName(userName)
                .setUserLastName(userLastName)
                .saveUserProfile()
                .checkIfSuccessMessageVisible();
    }

    @Test
    public void savePaymentInfoPage() {
        PaymentInfoPage paymentInfoPage = mainPage
                .userProfile()
                .choosePaymentInfo()
                .savePaymentInfo()
                .checkIfCardNumberErrorVisible()
                .setCardNumber(cardNum)
                .savePaymentInfo()
                .checkIfPaymentSystemErrorVisible()
                .setPaymentSystem(PaymentSystem.VISA)
                .savePaymentInfo()
                .checkIfSuccessMessageVisible();
        Assert.assertEquals(paymentInfoPage.selectDayOfPayment(0).getDayOfPayment(), 1);
        Assert.assertEquals(paymentInfoPage.selectDayOfPayment(1).getDayOfPayment(), 1);
        Assert.assertEquals(paymentInfoPage.selectDayOfPayment(15).getDayOfPayment(), 15);
        Assert.assertEquals(paymentInfoPage.selectDayOfPayment(31).getDayOfPayment(), 31);
        Assert.assertEquals(paymentInfoPage.selectDayOfPayment(32).getDayOfPayment(), 31);
    }

    @Test
    public void checkCookiesAfterUpdateUserInfo() {
        mainPage
                .userProfile()
                .choosePaymentInfo()
                .setCardNumber(cardNum)
                .setPaymentSystem(paymentSystem)
                .selectDayOfPayment(paymentDay)
                .savePaymentInfo()
                .chooseUserProfile()
                .setUserFirstName(userName)
                .setUserLastName(userLastName)
                .saveUserProfile();
        Map<String, String> map = getCookiesAsMap();
        checkCookies(map);
        refresh();
        map = getCookiesAsMap();
        checkCookies(map);
    }

    private Map<String, String> getCookiesAsMap() {
        return WebDriverRunner
                .getWebDriver().manage().getCookies()
                .stream().collect(Collectors.toMap(Cookie::getName, Cookie::getValue, (a, b) -> b));
    }

    private void checkCookies(Map<String, String> cookies) {
        Assert.assertEquals(cookies.get("cardNumber"), cardNum);
        Assert.assertEquals(cookies.get("firstName"), userName);
        Assert.assertEquals(cookies.get("lastName"), userLastName);
        Assert.assertEquals(cookies.get("paymentDay"), Integer.toString(paymentDay));
        Assert.assertEquals(cookies.get("paymentSystem"), Integer.toString(paymentSystem.ordinal()));
    }


}
