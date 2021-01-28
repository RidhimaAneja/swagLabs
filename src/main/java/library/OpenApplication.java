package library;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class OpenApplication {

  private WebDriver driver;

  public OpenApplication(String browserName) {
    if(browserName.equalsIgnoreCase("firefox")) {
      System.setProperty(
          "webdriver.gecko.driver",
          System.getProperty("user.dir") + "/src/main" + "/resources/geckodriver.exe");
      driver = new FirefoxDriver();
    }
    if(browserName.equalsIgnoreCase("chrome")) {
      System.setProperty(
          "webdriver.chrome.driver",
          System.getProperty("user.dir") + "/src/main" + "/resources/chromedriver.exe");
      driver = new ChromeDriver();
    }

    if(browserName.equalsIgnoreCase("ie")) {
      System.setProperty(
          "webdriver.ie.driver",
          System.getProperty("user.dir") + "/src/main" + "/resources/IEdriverServer.exe");
      driver = new InternetExplorerDriver();
    }

  }

  public WebDriver reachSwagLabsSite() {
    driver.get("https://www.saucedemo.com/");
    return driver;
  }

  public void closeBrowser(WebDriver driver) {
    driver.quit(); // if multiple windows are open
  }

}
