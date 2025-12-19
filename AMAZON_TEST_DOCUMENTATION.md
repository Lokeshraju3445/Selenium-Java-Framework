# Selenium Java Framework - Amazon Cart and Checkout Test Documentation

## Overview
This document describes the Selenium Java automation code for validating the cart and checkout functionality of amazon.in for 1kg sugar.

## Project Structure

### Page Objects Created

#### 1. **AmazonHomePage.java**
Located in: `src/main/java/com/pageObjects/AmazonHomePage.java`

**Purpose**: Represents the Amazon home page

**Key Methods**:
- `getSearchBox()` - Returns the search input field element
- `getSearchBtn()` - Returns the search button element
- `getCartIcon()` - Returns the cart icon element
- `searchProduct(String productName)` - Performs a product search

**Key Locators**:
- Search box: `id="twotabsearchtextbox"`
- Search button: `id="nav-search-submit-button"`
- Cart icon: `id="nav-cart-count-container"`

---

#### 2. **AmazonProductPage.java**
Located in: `src/main/java/com/pageObjects/AmazonProductPage.java`

**Purpose**: Represents the product search results and product detail pages

**Key Methods**:
- `getProductResults()` - Returns list of search result elements
- `getAddToCartBtn()` - Returns the "Add to Cart" button with fallback selectors
- `selectProductWithWeight(String weight)` - Selects a product matching the specified weight
- `clickAddToCart()` - Clicks the Add to Cart button

**Key Locators**:
- Product results: `div[data-component-type='s-search-result']`
- Add to Cart button: `id="add-to-cart-button"` (with fallback selector)
- Product title: `h2 a span`

---

#### 3. **AmazonCartPage.java**
Located in: `src/main/java/com/pageObjects/AmazonCartPage.java`

**Purpose**: Represents the shopping cart page

**Key Methods**:
- `getCartItems()` - Returns list of items in the cart
- `getItemTitle(WebElement item)` - Gets the title of a cart item
- `getItemQuantity(WebElement item)` - Gets the quantity dropdown for an item
- `verifyProductInCart(String productName)` - Verifies a product exists in cart
- `updateQuantity(int itemIndex, int quantity)` - Updates item quantity
- `getCartSubtotal()` - Returns the cart subtotal
- `getCartItemCount()` - Returns the number of items in cart
- `proceedToCheckoutClick()` - Clicks the Proceed to Checkout button

**Key Locators**:
- Cart items: `div[data-item-index]`
- Item title: `span.a-truncate-cut`
- Item quantity: `select[data-a-id='quantity']`
- Proceed to checkout: `input[data-feature-id='proceed-to-checkout-action']`
- Cart subtotal: `span#sc-subtotal-amount-buybox .a-price-whole`

---

#### 4. **AmazonCheckoutPage.java**
Located in: `src/main/java/com/pageObjects/AmazonCheckoutPage.java`

**Purpose**: Represents the checkout page (sign-in, delivery, payment)

**Key Methods**:
- `getSignInBtn()` - Returns the sign-in button
- `getEmailField()` - Returns the email input field
- `getPasswordField()` - Returns the password input field
- `getDeliveryAddressSection()` - Returns the delivery address section
- `getPaymentMethodSection()` - Returns the payment method section
- `getPlaceOrderBtn()` - Returns the Place Order button
- `signIn(String email, String password)` - Performs sign-in
- `selectDeliveryAddress()` - Selects delivery address
- `selectPaymentMethod()` - Selects payment method
- `isOrderReviewVisible()` - Verifies order review is visible
- `getOrderTotal()` - Returns the order total amount

**Key Locators**:
- Email field: `id="ap_email"`
- Password field: `id="ap_password"`
- Continue button: `id="continue"`
- Sign-in submit: `id="signInSubmit"`
- Delivery address: `div[data-testid='Address_DesktopItem']`
- Payment method: `div[data-testid='Payment_DesktopItem']`
- Place order button: `input[aria-label*='Place your order']`

---

### Test Cases Created

#### **AmazonCartAndCheckout.java**
Located in: `src/test/java/com/cases/AmazonCartAndCheckout.java`

**Description**: Comprehensive test suite for Amazon cart and checkout validation

**Test Methods**:

1. **testSearchFor1kgSugar()** (Priority: 1)
   - Navigates to amazon.in
   - Searches for "1kg sugar"
   - Verifies search results are displayed
   - Validates that at least one product is returned

2. **testSelectProductAndAddToCart()** (Priority: 2, Depends on: testSearchFor1kgSugar)
   - Selects a product with 1kg weight specification
   - Clicks the "Add to Cart" button
   - Waits for the action to complete

3. **testValidateCart()** (Priority: 3, Depends on: testSelectProductAndAddToCart)
   - Navigates to the cart page
   - Verifies the sugar product exists in cart
   - Validates cart item count
   - Retrieves and logs the cart subtotal

4. **testProceedToCheckout()** (Priority: 4, Depends on: testValidateCart)
   - Clicks the "Proceed to Checkout" button
   - Verifies checkout page loads successfully
   - Handles potential sign-in requirements

5. **testValidateCheckoutProcess()** (Priority: 5, Depends on: testProceedToCheckout)
   - Verifies order review is visible
   - Retrieves the order total
   - Validates delivery address section exists
   - Logs all checkout page elements

6. **testCompleteCartAndCheckoutFlow()** (Priority: 6)
   - End-to-end integration test
   - Performs complete flow from search to checkout
   - Validates each step of the process

---

## Setup Instructions

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Chrome or Firefox browser installed
- WebDriver Manager (already in pom.xml)

### Configuration

1. **Update config.properties** (optional):
```ini
browser=chrome
url=https://www.amazon.in
```

2. **Run the Tests**:

Using Maven:
```bash
mvn clean test -Dtest=AmazonCartAndCheckout
```

Using TestNG directly:
```bash
mvn test -Dsuite=testng.xml
```

---

## Key Features

✅ **Page Object Model**: Clean separation of locators and methods
✅ **Wait Strategies**: Implicit and explicit waits for reliability
✅ **Error Handling**: Try-catch blocks with meaningful error messages
✅ **Logging**: Comprehensive logging using Log4j2
✅ **Assertions**: TestNG assertions for test validation
✅ **Dependency Management**: Tests run in logical order using TestNG priorities

---

## Locator Strategy

The framework uses multiple selector strategies:
- **ID selectors** for unique elements
- **CSS selectors** for complex elements
- **XPath** (when necessary)
- **Fallback selectors** for flexibility across different Amazon pages

---

## Handling Dynamic Elements

- **WebDriverWait**: Used for elements that load dynamically
- **ExpectedConditions**: Waits for element visibility and clickability
- **Thread.sleep()**: Used for handling UI animations and transitions

---

## Test Execution Flow

```
testSearchFor1kgSugar
    ↓
testSelectProductAndAddToCart
    ↓
testValidateCart
    ↓
testProceedToCheckout
    ↓
testValidateCheckoutProcess
    ↓
testCompleteCartAndCheckoutFlow (parallel execution possible)
```

---

## Logging

All test activities are logged to:
- Console output
- Log file (configured in log4j2.xml)

Log levels used:
- `INFO`: Test progress and actions
- `ERROR`: Failures and exceptions
- `WARN`: Non-critical issues

---

## Notes

⚠️ **Important**:
1. Amazon's UI may change - selectors might need updates
2. For checkout completion testing, real credentials are required
3. Some tests may trigger CAPTCHA - handle manually if needed
4. Consider using headless mode for CI/CD environments

---

## Extending the Framework

To add more test scenarios:

1. Create new test method in `AmazonCartAndCheckout` class
2. Use existing page objects or create new ones as needed
3. Follow the naming convention: `test<Feature>()`
4. Use `@Test` annotation with appropriate priority and dependencies

---

## Troubleshooting

**Issue**: "NoSuchElementException"
- **Solution**: Check if element locators are current; update selectors if needed

**Issue**: "TimeoutException"
- **Solution**: Increase wait time or check network connectivity

**Issue**: "Product not found in cart"
- **Solution**: Verify product name in assertion; Amazon may have different product names

---

## Author
GitHub Copilot

## Framework Version
1.0

## Last Updated
December 18, 2025
# Amazon Cart and Checkout Test Configuration
browser=chrome
amazonUrl=https://www.amazon.in
searchProduct=1kg sugar
testDataPath=src/test/resources/input_data.xlsx

# Optional: Login credentials (if needed for checkout validation)
# email=your_email@gmail.com
# password=your_password

