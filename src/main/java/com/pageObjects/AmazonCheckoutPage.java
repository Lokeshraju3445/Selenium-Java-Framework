package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonCheckoutPage {

	public WebDriver driver;
	public WebDriverWait wait;

	public AmazonCheckoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
	}

	// All objects should be defined here
	private By signInBtn = By.id("a-autoid-1-announce");
	private By emailField = By.id("ap_email");
	private By passwordField = By.id("ap_password");
	private By continueBtn = By.id("continue");
	private By signInSubmitBtn = By.id("signInSubmit");
	private By deliveryAddressSection = By.cssSelector("div[data-testid='Address_DesktopItem']");
	private By useThisAddressBtn = By.cssSelector("input[aria-label*='Use this address']");
	private By paymentMethodSection = By.cssSelector("div[data-testid='Payment_DesktopItem']");
	private By placeOrderBtn = By.cssSelector("input[aria-label*='Place your order']");
	private By orderReviewContainer = By.cssSelector("div[data-testid='ORDER_SUMMARY_CONTAINER']");
	private By orderTotal = By.cssSelector("span.a-price-whole");
	private By continueShoppingBtn = By.cssSelector("a[href*='/gp/cart']");

	// All methods should be defined here
	public WebElement getSignInBtn() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
			return driver.findElement(signInBtn);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getEmailField() {
		wait.until(ExpectedConditions.presenceOfElementLocated(emailField));
		return driver.findElement(emailField);
	}

	public WebElement getPasswordField() {
		return driver.findElement(passwordField);
	}

	public WebElement getContinueBtn() {
		return driver.findElement(continueBtn);
	}

	public WebElement getSignInSubmitBtn() {
		return driver.findElement(signInSubmitBtn);
	}

	public WebElement getDeliveryAddressSection() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(deliveryAddressSection));
			return driver.findElement(deliveryAddressSection);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getUseThisAddressBtn() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(useThisAddressBtn));
			return driver.findElement(useThisAddressBtn);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getPaymentMethodSection() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(paymentMethodSection));
			return driver.findElement(paymentMethodSection);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getPlaceOrderBtn() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
			return driver.findElement(placeOrderBtn);
		} catch (Exception e) {
			return null;
		}
	}

	public String getOrderTotal() {
		try {
			return driver.findElement(orderTotal).getText();
		} catch (Exception e) {
			return "N/A";
		}
	}

	public boolean isOrderReviewVisible() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(orderReviewContainer));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void signIn(String email, String password) throws InterruptedException {
		getEmailField().clear();
		getEmailField().sendKeys(email);
		Thread.sleep(500);
		getContinueBtn().click();
		Thread.sleep(2000);

		getPasswordField().clear();
		getPasswordField().sendKeys(password);
		Thread.sleep(500);
		getSignInSubmitBtn().click();
		Thread.sleep(3000);
	}

	public void selectDeliveryAddress() throws InterruptedException {
		Thread.sleep(1500);
		WebElement addressSection = getDeliveryAddressSection();
		if (addressSection != null) {
			addressSection.click();
			Thread.sleep(1500);
		}
	}

	public void selectPaymentMethod() throws InterruptedException {
		Thread.sleep(1500);
		WebElement paymentSection = getPaymentMethodSection();
		if (paymentSection != null) {
			paymentSection.click();
			Thread.sleep(1500);
		}
	}

}
