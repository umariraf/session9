package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NewAccountPage extends BasePage {

	WebDriver driver;

	public NewAccountPage(WebDriver driver) {

		this.driver = driver;

	}

	@FindBy(how = How.XPATH, using = "//title[contains(text(),'Accounts')]")
	WebElement newAccountPageTitle;
	@FindBy(how = How.ID, using = "account")
	WebElement accountTitle;
	@FindBy(how = How.ID, using = "description")
	WebElement description;
	@FindBy(how = How.ID, using = "balance")
	WebElement initialBalance;
	@FindBy(how = How.XPATH, using = "//button[@type='submit' and @class='btn btn-primary']")
	WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-success fade in']//child::i")
	WebElement acccountCreateSuccessMessage;

	public String getNewAccountPageTitle() {
		return driver.getTitle();
	}

	public void fillAccountInformationForm(String accountName, String descriptions, String initialBalances) {
		accountTitle.sendKeys(accountName);
		description.sendKeys(descriptions);
		initialBalance.sendKeys(initialBalances);
		submitButton.click();
	}

	public WebElement displayAcccountCreateSuccessMessageActual() {
		return acccountCreateSuccessMessage;
	}

}
