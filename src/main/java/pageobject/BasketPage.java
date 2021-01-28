package pageobject;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BasketPage {

  static int numberOfItemsInCart;
  protected final WebDriverWait wait;
  WebDriver driver;

  @FindBy(className = "subheader")
  private WebElement yourCartBlock;

  @FindBy(xpath = "//a[contains(@class,'shopping_cart')]/span")
  private WebElement shoppingCartCounter;

  @FindBy(xpath = "//div[@class='item_pricebar']/button")
  private List<WebElement> removeItemButtons;

  public BasketPage(final WebDriver driver) {
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }

  public void validateThereExistsRemoveButtonWithEachItem() {
    wait.until(ExpectedConditions.visibilityOf(yourCartBlock));
    numberOfItemsInCart = Integer.parseInt(shoppingCartCounter.getText());
    // validates there is a remove button for each item in the cart
    Assert.assertTrue(
        String.valueOf(numberOfItemsInCart)
              .equals(String.valueOf(removeItemButtons.size())),
        "Number of remove buttons are not equal to number of items in the cart");
  }

  public void removeAnItemFromTheBasketAndValidate() {
    Assert.assertTrue(removeItemButtons.size() >= 1, "There are no items to remove in the basket");
    removeItemButtons.get(0)
                     .click();
    numberOfItemsInCart--;
    //comparing with the decremented values
    Assert.assertTrue(shoppingCartCounter.getText()
                                         .equals(String.valueOf(numberOfItemsInCart)));

  }

  public void emptyTheCartANdValidateThatCartCounterIsGone() {
    boolean exceptionCaught = false;
    Assert.assertTrue(removeItemButtons.size() >= 1, "There are no items to remove in the basket");
    for(WebElement removeButton: removeItemButtons) {
      removeButton.click();
    }
    try {
      shoppingCartCounter.click();
    } catch(NoSuchElementException e) {
      exceptionCaught = true;
    }
    Assert.assertTrue(exceptionCaught == true,
                      "Null pointer exception not caught, ie, element is " + "present");
    // Exception validates that element is no more presenr in the DOM
  }
}
