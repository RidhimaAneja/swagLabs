package pageobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {

  protected final WebDriverWait wait;
  private WebDriver driver;
  private String validUserName = "standard_user";
  private String validPassword = "secret_sauce";
  private String invalidUserName = "abc";
  private String invalidPassword = "abc";

  @FindBy(className = "login_logo")
  private WebElement loginLogo;

  @FindBy(id = "login-button")
  private WebElement loginButton;

  @FindBy(xpath = "//h3[@data-test='error']")
  private WebElement errorMessage;

  @FindBy(id = "user-name")
  private WebElement userNameField;

  @FindBy(id = "password")
  private WebElement passwordField;

  public LoginPage(final WebDriver driver) {
    wait = new WebDriverWait(driver, 30);
    PageFactory.initElements(driver, this);
    wait.until(ExpectedConditions.visibilityOf(loginLogo));
    this.driver = driver;
  }

  public void validateExpectedError(String expectedError) {
    wait.until(ExpectedConditions.visibilityOf(errorMessage));
    String errorMessageText = errorMessage.getText();
    Assert.assertEquals(expectedError, errorMessageText, "Error message does not match");
  }

  public void clickLoginButton() {
    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    loginButton.click();
  }

  public void enterUserName(String userNameType) {
    wait.until(ExpectedConditions.visibilityOf(userNameField));
    if(userNameType.equalsIgnoreCase("valid")) {
      userNameField.sendKeys(validUserName);
    } else if(userNameType.equalsIgnoreCase("invalid")) {
      userNameField.sendKeys(invalidUserName);
    }
  }

  public void enterPassword(String passwordType) {
    wait.until(ExpectedConditions.visibilityOf(passwordField));
    if(passwordType.equalsIgnoreCase("valid")) {
      passwordField.sendKeys(validPassword);
    } else if(passwordType.equalsIgnoreCase("invalid")) {
      passwordField.sendKeys(invalidPassword);
    }
  }

  public void validateFocusChangesOnKeyPress() {
    userNameField.sendKeys(Keys.TAB);// will move the focus of the element
    passwordField.sendKeys(validPassword);
    passwordField.sendKeys(Keys.ENTER);
    /* Will take us to inventory page without clicking on
     Login button */
  }

  public void loginToTheApplication() {
    enterUserName("valid");
    enterPassword("valid");
    clickLoginButton();
  }

}
