package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExcelUtilsTest {
	WebDriver driver;

	// @BeforeTest
	public void setUpTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "test1data")
	public void test1(String id, String first_name, String last_name, String email, String gender, String ip_address,
			String birthday) throws Exception {
		int id_ = Integer.parseInt(id);
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(birthday);
		String formattedDate = formatter.format(date);
		System.out.println(id_ + " | " + first_name + " | " + last_name + " | " + email + " | " + gender + " | "
				+ ip_address + " | " + formattedDate);

	}

	@DataProvider(name = "test1data")
	public Object[][] getData() {
		String projectPath = System.getProperty("user.dir");
		String excelPath = "src/main/resources/data/data.xlsx";
		Object data[][] = testData(excelPath, "sheet2");
		return data;

	}

	public Object[][] testData(String excelPath, String sheetName) {

		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);

		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();

		Object data[][] = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {

				String cellData = excel.getCellDataString(i, j);
				// System.out.print(cellData+" | ");
				data[i - 1][j] = cellData;

			}
			// System.out.println();
		}
		return data;

	}
}
