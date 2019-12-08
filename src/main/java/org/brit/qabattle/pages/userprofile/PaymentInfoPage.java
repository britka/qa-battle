package org.brit.qabattle.pages.userprofile;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author sbrit
 */
public class PaymentInfoPage extends UserProfileSettingsPage {
    public PaymentInfoPage setCardNumber(String cardNumber) {
        logger.info("Set card number field with: " + cardNumber);
        $("#cardNumberInput").setValue(cardNumber);
        return this;
    }

    public PaymentInfoPage checkIfCardNumberErrorVisible() {
        logger.info("Check if error for credit card field is visible");
        $("#cardNumberInput").parent().$(".invalid-feedback").shouldBe(Condition.visible);
        return this;
    }

    public PaymentInfoPage checkIfCardNumberErrorContainsText(String text) {
        logger.info("Check if error for credit card field contains text: " + text);
        $("#cardNumberInput").parent().$(".invalid-feedback").shouldHave(Condition.text(text));
        return this;
    }

    public PaymentInfoPage setPaymentSystem(PaymentSystem paymentSystem) {
        logger.info("Set payment system field with: " + paymentSystem);
        SelenideElement selectPayment = $("#paymentSystemSelect");
        selectPayment.selectOption(paymentSystem.ordinal());
        return this;
    }

    public PaymentInfoPage checkIfPaymentSystemErrorVisible() {
        logger.info("Check if error for payment systems field is visible");
        $("#paymentSystemSelect").parent().$(".invalid-feedback").shouldBe(Condition.visible);
        return this;
    }

    public PaymentInfoPage checkIfPaymentSystemErrorContainsText(String text) {
        logger.info("Check if error for payment systems field contains text: " + text);
        $("#paymentSystemSelect").parent().$(".invalid-feedback").shouldHave(Condition.text(text));
        return this;
    }

    public PaymentInfoPage selectDayOfPayment(int dayOfPayment) {
        logger.info("Select day of payment with: " + dayOfPayment);
        SelenideElement selectDayOfPayment = $("#paymentRange");
        executeJavaScript("arguments[0].value = " + dayOfPayment, selectDayOfPayment);
        executeJavaScript("arguments[0].dispatchEvent(new Event('input'))", selectDayOfPayment);
        return this;
    }

    public int getDayOfPayment() {
        logger.info("Getting day of payment");
        return Integer.parseInt($("h6").getText().split("\\s")[2]);
    }

    public PaymentInfoPage savePaymentInfo() {
        logger.info("Save payment info");
        $$("button").find(Condition.text("Save payment info")).click();
        return this;
    }

    public PaymentInfoPage checkIfSuccessMessageVisible() {
        logger.info("Check if success message for payment info is visible");
        $("#successPaymentInfoSaveInfo").shouldBe(Condition.visible);
        return this;
    }


}
