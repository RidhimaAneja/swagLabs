package testcases;


import library.OpenApplication;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobject.InventoryPage;
import pageobject.LoginPage;

public class LoginTests {

  WebDriver driver;
  LoginPage loginPage;
  InventoryPage inventoryPage;
  OpenApplication openApplication;
  String errorMessageWhenNoUsernameAndPassword = "Epic sadface: Username is required";
  String errorMessageWhenNoPassword = "Epic sadface: Password is required";
  String errorMessageWhenIncorrectUserNameAndPassword =
      "Epic sadface: Username and password do " + "not match any user in this service";

  @BeforeMethod
  @Parameters("browserName")
  public void setup(String browserName) {
    openApplication =new OpenApplication(browserName);
    driver = openApplication.reachSwagLabsSite();
    loginPage = new LoginPage(driver);
    inventoryPage = new InventoryPage(driver);
  }

  @Test(groups = "AC1")
  public void loginWithoutUserNameAndPassword() {
    try {
      loginPage.clickLoginButton();
      loginPage.validateExpectedError(errorMessageWhenNoUsernameAndPassword);
    } catch(Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test(groups = "AC1")
  public void loginWithUserNameAndWithoutPassword() {
    try {
      loginPage.enterUserName("valid");
      loginPage.clickLoginButton();
      loginPage.validateExpectedError(errorMessageWhenNoPassword);
    } catch(Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test(groups = "AC1")
  public void loginWithIncorrectUserNameAndPassword() {
    try {
      loginPage.enterUserName("invalid");
      loginPage.enterPassword("invalid");
      loginPage.clickLoginButton();
      loginPage.validateExpectedError(errorMessageWhenIncorrectUserNameAndPassword);
    } catch(Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test(groups = "AC1")
  public void loginWithValidUserNameAndPassword() {
    try {
      loginPage.enterUserName("valid");
      loginPage.enterPassword("valid");
      loginPage.clickLoginButton();
      inventoryPage.userIsRedirectedToExpectedPage();
    } catch(Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test(groups = "AC2")
  public void validateExpectedFocusOnKeyPressUsingKeyboard() {
    try {
      loginPage.enterUserName("valid");
      loginPage.validateFocusChangesOnKeyPress();
      inventoryPage.userIsRedirectedToExpectedPage();
    } catch(Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @AfterMethod
  public void closeBrowser(ITestResult result) {
    if(result.getStatus() == ITestResult.FAILURE)
    System.out.println("** " + result.getMethod()
                             .getMethodName() + " failed**");
    if(result.getStatus() == ITestResult.SUCCESS)
      System.out.println("** " + result.getMethod()
                                       .getMethodName() + " passed**");
    if(result.getStatus() == ITestResult.SKIP)
      System.out.println("** " + result.getMethod()
                                       .getMethodName() + " skipped**");
    openApplication.closeBrowser(driver);
  }


}
