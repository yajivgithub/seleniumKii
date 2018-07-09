package dataCollection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import objectRepository.FuelPrice;
import utilities.ExcelUtil;
import utilities.UserInterfaceUtil;

public class RelianceFuelData extends InitializeDriver {

	@BeforeTest
	public void launch() {
		driver = getFirefoxdriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void getData() throws Exception {

		List<FuelPrice> fuelData = new LinkedList<>();

		String STATE = "Karnataka";
		String CITY = "Bangalore";
		String URL = "https://www.reliancepetroleum.com/locateafuelstation";

		String stationName = null;
		String petrolPrice = null;
		String dieselPrice = null;
		String lpgPrice = "N/A";

		WebElement petrolElement = null;

		driver.get(URL);
		Select drop = new Select(driver.findElement(By.id("idState")));

		drop.selectByVisibleText(STATE);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");

		driver.findElement(By.id("idName")).sendKeys(CITY);
		driver.findElement(By.id("idBtnSubmit")).click();

		js.executeScript("scroll(0,500);");

		Actions action = new Actions(driver);

		List<WebElement> fuelStations = driver
				.findElements(By.xpath("//form/section[2]/div/div[2]/div/div[1]/div[2]/div/div[1]/div/div/ul/li"));

		int stationCount = fuelStations.size();
		int i = 0;
		System.out.println(stationCount);

		if (stationCount != 0) {
			for (WebElement fuelStation : fuelStations) {

				FuelPrice fuelPrice = new FuelPrice();

				i++;

				stationName = fuelStation.findElement(By.xpath("div/h4")).getText();
				fuelPrice.setStation(stationName);

				List<WebElement> fuelDetails = fuelStation.findElements(By.xpath("div/p"));

				for (WebElement fueldetail : fuelDetails) {
					if (fueldetail.getText().contains("Petrol price")) {
						petrolElement = fueldetail;
						petrolPrice = fueldetail.getText();
						fuelPrice.setPetrolPrice(petrolPrice);
					}

					if (fueldetail.getText().contains("Diesel price")) {
						dieselPrice = fueldetail.getText();
						fuelPrice.setDieselPrice(dieselPrice);
					}

					if (fueldetail.getText().contains("LPG price")) {
						lpgPrice = fueldetail.getText();
						fuelPrice.setLpgPrice(lpgPrice);
					}
				}

				System.out.println(stationName);
				System.out.println(petrolPrice);
				System.out.println(dieselPrice);
				System.out.println(lpgPrice);

				if (i < stationCount) {
					if (i == 1)
						action.moveToElement(petrolElement).click().build().perform();
					Thread.sleep(2000);
					UserInterfaceUtil.scrollPageDown();
				}

				fuelData.add(fuelPrice);
			}

			ExcelUtil.writeDataToSheet(fuelData);
		}

	}

	@AfterTest
	public void finish() throws IOException {
		driver.close();
	}

}
