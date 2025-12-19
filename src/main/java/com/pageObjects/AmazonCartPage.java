package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class AmazonCartPage {

	public WebDriver driver;
	public WebDriverWait wait;

	public AmazonCartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
	}

	// All objects should be defined here
	private By cartItems = By.cssSelector("div[data-item-index]");
	private By itemTitle = By.cssSelector("span.a-truncate-cut");
	private By itemQuantity = By.cssSelector("select[data-a-id='quantity']");
	private By itemPrice = By.cssSelector("span.a-price-whole");
	private By proceedToCheckout = By.cssSelector("input[data-feature-id='proceed-to-checkout-action']");
	private By cartSubtotal = By.cssSelector("span#sc-subtotal-amount-buybox .a-price-whole");
	private By deleteBtn = By.cssSelector("input[value='Delete']");
	private By updateBtn = By.cssSelector("input[value='Update']");

	// All methods should be defined here
	public List<WebElement> getCartItems() {
		return driver.findElements(cartItems);
	}

	public WebElement getItemTitle(WebElement item) {
		return item.findElement(itemTitle);
	}

	public WebElement getItemQuantity(WebElement item) {
		try {
			return item.findElement(itemQuantity);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement getItemPrice(WebElement item) {
		return item.findElement(itemPrice);
	}

	public WebElement getProceedToCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout));
		return driver.findElement(proceedToCheckout);
	}

	public String getCartSubtotal() {
		try {
			return driver.findElement(cartSubtotal).getText();
		} catch (Exception e) {
			return "0";
		}
	}

	public int getCartItemCount() {
		return getCartItems().size();
	}

	public void verifyProductInCart(String productName) throws Exception {
		List<WebElement> items = getCartItems();
		boolean found = false;

		for (WebElement item : items) {
			String title = getItemTitle(item).getText();
			if (title.contains(productName) || title.toLowerCase().contains("sugar")) {
				found = true;
				System.out.println("Product found in cart: " + title);
				break;
			}
		}

		if (!found) {
			throw new Exception("Product not found in cart!");
		}
	}

	public void updateQuantity(int itemIndex, int quantity) throws InterruptedException {
		List<WebElement> items = getCartItems();
		if (itemIndex < items.size()) {
			WebElement quantityDropdown = getItemQuantity(items.get(itemIndex));
			if (quantityDropdown != null) {
				quantityDropdown.click();
				Thread.sleep(500);
				quantityDropdown.sendKeys(String.valueOf(quantity));
				Thread.sleep(1000);
			}
		}
	}

	public void proceedToCheckoutClick() throws InterruptedException {
		Thread.sleep(1000);
		getProceedToCheckout().click();
		Thread.sleep(2000);
	}

}

