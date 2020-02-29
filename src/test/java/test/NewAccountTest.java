package test;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import page.DashBoardPage;
import page.ListAccountPage;
import page.LoginPage;
import page.NewAccountPage;
import util.BrowserFactory;
import util.ExcelReader;

public class NewAccountTest {

	WebDriver driver;

	ExcelReader reader = new ExcelReader("./data/data.xlsx");
	String browser = reader.getCellData("Sheet1", "BrowserName", 2);
	String username = reader.getCellData("Sheet1", "UserName", 2);
	String password = reader.getCellData("Sheet1", "Password", 2);
	String AccountNameExpected = reader.getCellData("Sheet2", "AccountTitle", 2) + new Random().nextInt(999);
	String DescriptionExpected = reader.getCellData("Sheet2", "Description", 2);
	String InitialBalance = reader.getCellData("Sheet2", "InitialBalance", 2);

	DashBoardPage dashboardPage;
	NewAccountPage newAccountPage;
	ListAccountPage listAccountPage;

	@BeforeTest
	public void initialization() {
		BrowserFactory browserFactory = new BrowserFactory();
		driver = browserFactory.startBrowser(browser);
		dashboardPage = PageFactory.initElements(driver, DashBoardPage.class);
		newAccountPage = PageFactory.initElements(driver, NewAccountPage.class);
		listAccountPage = PageFactory.initElements(driver, ListAccountPage.class);
	}

	@Test(priority = 1)
	public void addNewAccount() throws InterruptedException {

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		String expectedTitle = "Login - TechFios Test Application - Billing";
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle, "Login Page M!Smatched ..");
		loginPage.login(username, password);

//		DashBoardPage dashboardPage = PageFactory.initElements(driver, DashBoardPage.class);
		dashboardPage.waitForDashboardPage();
		dashboardPage.goToNewAccountPage();

		Thread.sleep(1000);

//		NewAccountPage newAccountPage = PageFactory.initElements(driver, NewAccountPage.class);
		String expectedTitleNewAccountPage = "Accounts- TechFios Test Application - Billing";
		String actualTitleNewAccountPage = newAccountPage.getNewAccountPageTitle();
		Assert.assertEquals(actualTitleNewAccountPage, expectedTitleNewAccountPage, "New Account Page M!Smatched ..");

		newAccountPage.fillAccountInformationForm(AccountNameExpected, DescriptionExpected, InitialBalance);
		Thread.sleep(1000);

//      Simple Assert		
		Assert.assertTrue(newAccountPage.displayAcccountCreateSuccessMessageActual().isDisplayed(),
				"Account Created Successfully Not Displayed !!");

//      Go to List Account Page
		dashboardPage.goToListAccountPage();
//		ListAccountPage listAccountPage = PageFactory.initElements(driver, ListAccountPage.class);
		listAccountPage.waitForListAccountPage();

//      Assert with Dynamic Web Table from List Account Page
		List<String> columnData = listAccountPage.getColumnDatFor("Account");
		Assert.assertTrue(isDataPresent(AccountNameExpected, columnData),
				"" + AccountNameExpected + " Account NOT posted !");

//      Scroll down and Delete Account
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		listAccountPage.clickOnAccountDeleteButton(AccountNameExpected);
		listAccountPage.waitForAccountDeleteConfirmatioOkButton();
		listAccountPage.clickOnAccountDeleteConfirmationOkButton();
		listAccountPage.clickOnAcccountDeleteSuccessMessage();
		columnData = listAccountPage.getColumnDatFor("Account");
		Assert.assertFalse(isDataPresent(AccountNameExpected, columnData),
				"" + AccountNameExpected + " Account NOT deleted!!");

		
		driver.quit();

	}

	private boolean isDataPresent(String AccountNameExpected, List<String> columnData) {
		for (String cellData : columnData) {
			if (cellData.equalsIgnoreCase(AccountNameExpected)) {
				System.out.println("Displayed Data: " + cellData);
				return true;
			}
			
		}
        System.out.println("Data delection successful!");
		return false;
	}

}
