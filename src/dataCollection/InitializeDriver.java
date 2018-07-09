package dataCollection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class InitializeDriver {

	WebDriver driver;

	public WebDriver getFirefoxdriver() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	public WebDriver getChromedriver() {

		String path = "C:\\Users\\deepak.a.jayaraman\\Documents\\selenium\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
}
