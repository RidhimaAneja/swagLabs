package pageobject;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InventoryPage {

  protected final WebDriverWait wait;
  WebDriver driver;
  int numberOfItemsInCart = 0;
  JavascriptExecutor js;
  private String expectedWindowUrl = "/inventory.html";

  @FindBy(className = "app_logo")
  private WebElement appLogo;

  @FindBy(xpath = "//div[@class='pricebar']/button")
  private List<WebElement> cartManagementButtons;

  @FindBy(xpath = "//a[contains(@class,'shopping_cart')]/span")
  private WebElement shoppingCart;

  @FindBy(xpath = "//a[contains(@class,'shopping_cart')]")
  private WebElement shoppingCartButton;

  public InventoryPage(final WebDriver driver) {
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
    this.driver = driver;
    js = (JavascriptExecutor) driver;
  }

  public void userIsRedirectedToExpectedPage() {
    wait.until(ExpectedConditions.visibilityOf(appLogo));
    String windowUrl = driver.getCurrentUrl();
    Assert.assertTrue(windowUrl.contains(expectedWindowUrl), "url of window does not match");
  }

  public void addAProductInCartAndValidate() {
    boolean itemAdded = false;
    int idOfElementAdded = 0;
    wait.until(ExpectedConditions.visibilityOfAllElements(cartManagementButtons));
    Assert.assertTrue(cartManagementButtons.size() >= 1, "There are no items on Inventory page");

    //using for loop to fetch index of element clicked. Else stream could be used
    for(int i = 0; i <= cartManagementButtons.size(); i++) {
      if(cartManagementButtons.get(i)
                              .getText()
                              .equalsIgnoreCase("ADD TO CART")) {
        cartManagementButtons.get(i)
                             .click();
        itemAdded = true;
        idOfElementAdded = i;
        numberOfItemsInCart++;
        break;
      }
    }

    //Makes sure add to cart button is clicked
    Assert.assertTrue(itemAdded, "Add to cart button was not clicked");

    Assert.assertTrue(
        cartManagementButtons.get(idOfElementAdded)
                             .getText()
                             .equalsIgnoreCase("REMOVE"),
        "Specified item is not added to cart, or the button is not updated"); // Validates that the Add to cart button is now changed to Remove button

  }

  public void validateCounterOnCartIconIsUpdated() {
    js.executeScript("arguments[0].scrollIntoView(true);", shoppingCart);
    Assert.assertTrue(shoppingCart.getText()
                                  .equals(String.valueOf(numberOfItemsInCart)));
  }

  public void removeAnItemAndValidateThatTheButtonIsUpdated() {

    boolean itemRemoved = false;
    int idOfElementRemoved = 0;
    wait.until(ExpectedConditions.visibilityOfAllElements(cartManagementButtons));
    Assert.assertTrue(cartManagementButtons.size() >= 1, "There are no items on "
        + "Inventory "
        + "page");

    //using for loop to fetch index of element clicked. Else stream could be used
    for(int i = 0; i <= cartManagementButtons.size(); i++) {
      if(cartManagementButtons.get(i)
                              .getText()
                              .equalsIgnoreCase("REMOVE")) {
        cartManagementButtons.get(i)
                             .click();
        itemRemoved = true;
        idOfElementRemoved = i;
        --numberOfItemsInCart;
        break;
      }
    }

    //Makes sure add to cart button is clicked
    Assert.assertEquals(itemRemoved, true, "Add to cart button was not clicked");

    // Validates that the Remove button is now changed to Add to cart button
    Assert.assertTrue(
        cartManagementButtons.get(idOfElementRemoved)
                             .getText()
                             .equalsIgnoreCase("ADD TO CART"),
        "Specified item is not removed from cart, or the button is not updated");

  }

  public void navigateToBasketPage() {
    js.executeScript("arguments[0].scrollIntoView(true);", shoppingCartButton);
    shoppingCartButton.click();
  }
}
