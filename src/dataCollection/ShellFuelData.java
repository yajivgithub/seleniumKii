package dataCollection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import objectRepository.FuelPrice;
import utilities.ExcelUtil;

public class ShellFuelData extends InitializeDriver {

	@BeforeTest
	public void launch() {
		driver = getFirefoxdriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void getData() throws Exception {

		List<FuelPrice> fuelData = new LinkedList<>();

		driver.get("https://www.shell.in/");

		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.linkText("Motorists"))).build().perform();
		Thread.sleep(2000);
		action.moveToElement(driver.findElement(By.linkText("Shell fuels"))).click().build().perform();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,1000);");
		driver.findElement(By.xpath("//div/div/div/div/article/div/div[1]/a/span")).click();
		js.executeScript("scroll(0,500);");
		List<WebElement> fuelStations = driver.findElements(By.xpath("//div/div/article/div/table/tbody/tr"));

		int stationCount = fuelStations.size();
		int i = 0;

		if (stationCount != 0) {
			for (WebElement fuelStation : fuelStations) {

				i++;
				if (i != 1) {
					FuelPrice fuelPrice = new FuelPrice();

					List<WebElement> fuelDetails = fuelStation.findElements(By.xpath("td"));

					String stationName = fuelDetails.get(0).getText();
					fuelPrice.setStation(stationName);

					String petrolPrice = fuelDetails.get(3).getText();
					fuelPrice.setPetrolPrice("Petrol Price : "+petrolPrice);

					String dieselPrice = fuelDetails.get(1).getText();
					fuelPrice.setDieselPrice("Diesel Price : "+dieselPrice);

					fuelPrice.setLpgPrice("LPG Price : N/A");

					System.out.println(stationName);
					System.out.println(petrolPrice);
					System.out.println(dieselPrice);

					fuelData.add(fuelPrice);

				}
			}

			ExcelUtil.writeDataToSheet(fuelData);
		}

	}
}
