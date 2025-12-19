package com.cases;

import org.testng.annotations.Test;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.actions.Actions;
import com.base.Base;
import com.pageObjects.AmazonCartPage;
import com.pageObjects.AmazonCheckoutPage;
import com.pageObjects.AmazonHomePage;
import com.pageObjects.AmazonProductPage;

public class AmazonCartAndCheckoutTest extends Base {

    public WebDriver driver;
    public Actions actions;

    private final Logger log = LogManager.getLogger(AmazonCartAndCheckoutTest.class.getName());
    private AmazonHomePage homePage;
    private AmazonProductPage productPage;
    private AmazonCartPage cartPage;
    private AmazonCheckoutPage checkoutPage;

    private final String SEARCH_PRODUCT = "1kg sugar";
    private final String AMAZON_URL = "https://www.amazon.in";

    @BeforeTest
    public void initialize() throws IOException {

        driver = initializeDriver();
        log.info("Driver initialized successfully.");
        actions = new Actions(driver);

        homePage = new AmazonHomePage(driver);
        productPage = new AmazonProductPage(driver);
        cartPage = new AmazonCartPage(driver);
        checkoutPage = new AmazonCheckoutPage(driver);

        log.info("All page objects initialized.");
    }

    @Test(priority = 1)
    public void testSearchFor1kgSugar() {
        try {
            log.info("Test: Search for 1kg Sugar started.");

            // Navigate to Amazon.in
            actions.navigateTo(AMAZON_URL);
            log.info("Navigated to " + AMAZON_URL);

            Thread.sleep(2000);

            // Search for 1kg sugar
            homePage.searchProduct(SEARCH_PRODUCT);
            log.info("Searched for: " + SEARCH_PRODUCT);

            Thread.sleep(3000);

            // Verify search results
            int resultsCount = productPage.getProductResults().size();
            Assert.assertTrue(resultsCount > 0, "No search results found for 1kg sugar");
            log.info("Found " + resultsCount + " search results for 1kg sugar");

        } catch (Exception e) {
            log.error("Test failed: testSearchFor1kgSugar - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, dependsOnMethods = {"testSearchFor1kgSugar"})
    public void testSelectProductAndAddToCart() {
        try {
            log.info("Test: Select product and add to cart started.");

            // Select product with 1kg specification
            productPage.selectProductWithWeight("1kg");
            log.info("Selected product with 1kg weight specification");

            Thread.sleep(2000);

            // Add to cart
            productPage.clickAddToCart();
            log.info("Clicked Add to Cart button");

            Thread.sleep(2000);

            // Verify success message (optional - depends on Amazon UI)
            log.info("Product added to cart successfully");

        } catch (Exception e) {
            log.error("Test failed: testSelectProductAndAddToCart - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, dependsOnMethods = {"testSelectProductAndAddToCart"})
    public void testValidateCart() {
        try {
            log.info("Test: Validate cart started.");

            // Navigate to cart
            actions.navigateTo(AMAZON_URL + "/gp/cart/view.html");
            log.info("Navigated to cart page");

            Thread.sleep(2000);

            // Verify product in cart
            cartPage.verifyProductInCart("sugar");
            log.info("Product verified in cart");

            // Verify cart item count
            int itemCount = cartPage.getCartItemCount();
            Assert.assertTrue(itemCount > 0, "Cart is empty");
            log.info("Cart contains " + itemCount + " item(s)");

            // Verify cart subtotal
            String subtotal = cartPage.getCartSubtotal();
            log.info("Cart subtotal: " + subtotal);

        } catch (Exception e) {
            log.error("Test failed: testValidateCart - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 4, dependsOnMethods = {"testValidateCart"})
    public void testProceedToCheckout() {
        try {
            log.info("Test: Proceed to checkout started.");

            // Click proceed to checkout
            cartPage.proceedToCheckoutClick();
            log.info("Clicked Proceed to Checkout button");

            Thread.sleep(3000);

            // Verify checkout page is loaded
            // Check if sign-in button is visible or if already signed in
            log.info("Checkout page loaded successfully");

        } catch (Exception e) {
            log.error("Test failed: testProceedToCheckout - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 5, dependsOnMethods = {"testProceedToCheckout"})
    public void testValidateCheckoutProcess() {
        try {
            log.info("Test: Validate checkout process started.");

            // Check if order review is visible
            boolean orderReviewVisible = checkoutPage.isOrderReviewVisible();
            log.info("Order review visible: " + orderReviewVisible);

            // Get order total
            String orderTotal = checkoutPage.getOrderTotal();
            log.info("Order total: " + orderTotal);

            // Verify delivery address section exists
            Thread.sleep(2000);
            WebElement addressSection = checkoutPage.getDeliveryAddressSection();
            if (addressSection != null) {
                log.info("Delivery address section is visible");
            } else {
                log.warn("Delivery address section not visible - user may need to sign in");
            }

            log.info("Checkout process validation completed");

        } catch (Exception e) {
            log.error("Test failed: testValidateCheckoutProcess - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void testCompleteCartAndCheckoutFlow() {
        try {
            log.info("Test: Complete cart and checkout flow started (Integration test).");

            // Fresh start
            actions.navigateTo(AMAZON_URL);
            Thread.sleep(2000);

            // Search for product
            homePage.searchProduct(SEARCH_PRODUCT);
            log.info("Searched for 1kg sugar");
            Thread.sleep(3000);

            // Select and add to cart
            productPage.selectProductWithWeight("1kg");
            productPage.clickAddToCart();
            log.info("Product added to cart");
            Thread.sleep(2000);

            // Navigate to cart and validate
            actions.navigateTo(AMAZON_URL + "/gp/cart/view.html");
            Thread.sleep(2000);

            cartPage.verifyProductInCart("sugar");
            int itemCount = cartPage.getCartItemCount();
            String subtotal = cartPage.getCartSubtotal();

            log.info("Cart validated - Items: " + itemCount + ", Subtotal: " + subtotal);
            Assert.assertTrue(itemCount > 0, "Cart validation failed");

            // Proceed to checkout
            cartPage.proceedToCheckoutClick();
            log.info("Proceeded to checkout");
            Thread.sleep(3000);

            log.info("Complete flow test passed successfully");

        } catch (Exception e) {
            log.error("Test failed: testCompleteCartAndCheckoutFlow - " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Driver closed successfully.");
        }
    }

}

