package testcases;


import library.OpenApplication;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobject.BasketPage;
import pageobject.InventoryPage;
import pageobject.LoginPage;

public class BasketTests {

  WebDriver driver;
  LoginPage loginPage;
  InventoryPage inventoryPage;
  BasketPage basketPage;
  OpenApplication openApplication;

  @BeforeMethod
  @Parameters("browserName")
  public void setup(String browserName) {
    openApplication = new OpenApplication(browserName);
    driver = openApplication.reachSwagLabsSite();
    loginPage = new LoginPage(driver);
    inventoryPage = new InventoryPage(driver);
    basketPage = new BasketPage(driver);
  }


  @Test(groups = "AC4")
  public void validateBasketIsUpdatedOnRemovingItems() {
    try {
      loginPage.loginToTheApplication(); // Log In to the application
      inventoryPage.userIsRedirectedToExpectedPage(); // validate user is at inventory page
      inventoryPage.addAProductInCartAndValidate();
      inventoryPage.addAProductInCartAndValidate(); //adds 1 item in a time
      inventoryPage.navigateToBasketPage();
      basketPage.validateThereExistsRemoveButtonWithEachItem();
      basketPage.removeAnItemFromTheBasketAndValidate();
      basketPage.emptyTheCartANdValidateThatCartCounterIsGone();

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
