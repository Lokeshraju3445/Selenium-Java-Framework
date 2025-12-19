package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class AmazonProductPage {

	public WebDriver driver;
	public WebDriverWait wait;

	public AmazonProductPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
	}

	// All objects should be defined here
	private By productResults = By.cssSelector("div[data-component-type='s-search-result']");
	private By productTitle = By.cssSelector("h2 a span");
	private By addToCartBtn = By.id("add-to-cart-button");
	private By productWeight = By.cssSelector("span[data-a-color='secondary']");

	// All methods should be defined here
	public List<WebElement> getProductResults() {
		return driver.findElements(productResults);
	}

	public WebElement getAddToCartBtn() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
			return driver.findElement(addToCartBtn);
		} catch (Exception e) {
			// Alternative selector
			return driver.findElement(By.cssSelector("input[aria-label*='Add to Shopping Cart']"));
		}
	}

	public List<WebElement> getProductTitles() {
		return driver.findElements(productTitle);
	}

	public void selectProductWithWeight(String weight) throws InterruptedException {
		List<WebElement> results = getProductResults();

		for (WebElement result : results) {
			try {
				WebElement titleElement = result.findElement(productTitle);
				String title = titleElement.getText();

				// Check if product title contains the weight specification
				if (title.contains(weight)) {
					titleElement.click();
					Thread.sleep(2000);
					return;
				}
			} catch (Exception e) {
				continue;
			}
		}

		// If exact weight not found, click the first product
		if (!results.isEmpty()) {
			results.get(0).findElement(productTitle).click();
			Thread.sleep(2000);
		}
	}

	public void clickAddToCart() throws InterruptedException {
		Thread.sleep(1000);
		WebElement addBtn = getAddToCartBtn();
		addBtn.click();
		Thread.sleep(2000);
	}

}

