package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonHomePage {

	public WebDriver driver;

	public AmazonHomePage(WebDriver driver) {
		this.driver = driver;
	}

	// All objects should be defined here
	private By searchBox = By.id("twotabsearchtextbox");
	private By searchBtn = By.id("nav-search-submit-button");
	private By cartIcon = By.id("nav-cart-count-container");

	// All methods should be defined here
	public WebElement getSearchBox() {
		return driver.findElement(searchBox);
	}

	public WebElement getSearchBtn() {
		return driver.findElement(searchBtn);
	}

	public WebElement getCartIcon() {
		return driver.findElement(cartIcon);
	}

	public void searchProduct(String productName) {
		getSearchBox().clear();
		getSearchBox().sendKeys(productName);
		getSearchBtn().click();
	}

}

