package test;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EpamAddressScrapingRobot extends BaseRobot {

	/**
	 * Opens EPAM website and collects the address of EPAM USA and phone number then
	 * saves it to a text file.
	 */
	public void doWork() throws Exception {
		ScrapingModel model = scrape();
		Files.write(Paths.get("scraping_results.txt"), model.toString().getBytes());
	}

	protected ScrapingModel scrape() {
		getDriver().get("https://www.epam.com");

		// find first element containing US office locations and make sure it's visible
		WebElement usLink = findElementByXpathContainingText("//*[attribute::class='locations-viewer__country-btn']",
				"united states");
		scrollTo(usLink);
		usLink.click();

		// get the first available US address
		WebElement firstAvailableUsAddress = findElementByXpathContainingText(
				"//li[attribute::class='locations-viewer__office']", "usa");

		String address = getInnerText(
				firstAvailableUsAddress.findElement(By.xpath(".//div[@class='locations-viewer__office-address']/p")));
		String phone = getInnerText(firstAvailableUsAddress
				.findElement(By.xpath(".//div[@class='locations-viewer__office-contact']/span")));

		// generate the scraping model results
		ScrapingModel result = new ScrapingModel();
		result.setAddress(address);
		result.setPhone(phone);
		return result;
	}

}
