package page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ListAccountPage extends BasePage {

	WebDriver driver;

	@FindBy(how = How.XPATH, using = "//h5")
	WebElement listAccountPageHeader;

	@FindBy(how = How.XPATH, using = "//table[@class='table table-striped table-bordered']")
	WebElement accountDeleteButton;


//  FindBy does not take variable
//	@FindBy(how = How.XPATH, using = "//td[contains(text(),'"+rndAccountNameExpected+"')]//following-sibling::*[2]//child::a[2]")
//	WebElement AccountDeleteButton;

	@FindBy(how = How.XPATH, using = "//button[@data-bb-handler='confirm']")
	WebElement accountDeleteConfirmationOkButton;

	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-success fade in']//child::button")
	WebElement accountDeleteSuccessMessage;

	public ListAccountPage(WebDriver driver) {
		this.driver = driver;
	}

	public void waitForListAccountPage() {
		waitForElement(driver, 10, listAccountPageHeader);
	}

	public List<String> getColumnDatFor(String columnHeader) {

		List<String> columnData1 = new ArrayList<String>();
		int index = getColumnHeaderIndexFor(columnHeader);
		String columnDataXpath = "//table[@class='table table-striped table-bordered']/tbody/tr/td[" + index + "]";
		List<WebElement> columnDataElements = driver.findElements(By.xpath(columnDataXpath));

		for (int i = 0; i < columnDataElements.size(); i++) {
			columnData1.add(i, columnDataElements.get(i).getText());
		}

		return columnData1;
	}

	private int getColumnHeaderIndexFor(String columnHeader) {

		String columnHeaderXpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/th";
		List<WebElement> columnHeaderElements = driver.findElements(By.xpath(columnHeaderXpath));
		for (int index = 0; index < columnHeaderElements.size(); index++) {
			if (columnHeaderElements.get(index).getText().equalsIgnoreCase(columnHeader)) {
				return index + 1;
			}

		}
		return 0;
	}

	public void clickOnAccountDeleteButton(String dynamicAccount) {
	/*	waitForElement(driver, 20,
				accountDeleteButton.findElement(
						By.xpath("//table[@class='table table-striped table-bordered']/descendant::td[contains(text(),'"
								+ dynamicAccount + "')]//following-sibling::*[2]//child::a[2]"))); */
		accountDeleteButton.findElement(
				By.xpath("//table[@class='table table-striped table-bordered']/descendant::td[contains(text(),'"
						+ dynamicAccount + "')]//following-sibling::*[2]//child::a[2]")).click();
	}

	public void waitForAccountDeleteConfirmatioOkButton() {
		waitForElement(driver, 10, accountDeleteConfirmationOkButton);

	}

	public void clickOnAccountDeleteConfirmationOkButton() {
		accountDeleteConfirmationOkButton.click();
	}

	public void clickOnAcccountDeleteSuccessMessage() {
		 accountDeleteSuccessMessage.click();
	}

}
