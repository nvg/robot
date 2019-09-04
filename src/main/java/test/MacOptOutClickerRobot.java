package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MacOptOutClickerRobot extends BaseRobot {

	public static final String BASE = "https://epuat.mcmaster.ca/psp/drepuat";

	public static void main(String[] args) throws Exception {
		String user = args[0];
		String pass = args[1];
		Integer reps = Integer.parseInt(args[2]);

		MacOptOutClickerRobot test = new MacOptOutClickerRobot();
		try {
			while (reps > 0) {
				test.setUser(user);
				test.setPass(pass);
				test.doWork();
				reps--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			test.close();
		}
	}

	private String user;
	private String pass;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void doWork() throws Exception {
		getDriver().get(BASE + "/?cmd=login&languageCd=ENG&");
		sleep(1);

		WebElement userId = findElementById("userid");
		userId.sendKeys(getUser());
		WebElement pass = findElementById("pwd");
		pass.sendKeys(getPass());

		WebElement singIn = findElementByXpath("//*[attribute::value=\"Sign In\"]");
		singIn.click();

		sleep(2);

		getDriver().get(BASE + "/EMPLOYEE/SA/c/MCM_CUSTOM_MENU.MCM_OPT_STDNT.GBL");

		sleep(2);

		WebElement firstAvailableFee = findElementById("win0divMCM_OPT_DLT_WRK_MCM_OPT_OUTctrl$0");
		scrollTo(firstAvailableFee);
		firstAvailableFee.click();

		WebElement nextButton = findElementById("MCM_OPT_STD_WRK_SAVEBTN$0");
		nextButton.click();

		WebDriver driver = getDriver().switchTo().frame("ptModFrame_0");

		WebElement doneButton = driver.findElement(By.id("#ICSave"));
		doneButton.click();

		sleep(10);
	}

}
