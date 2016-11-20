package freethinker.Web_Obj_Repo_Manager;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import jxl.Sheet;
import jxl.Workbook;

// JXL library is used so that only XLS files can be used here.
//getCell(Column, Row)
public class App {

	static Workbook repoWB;
	static Sheet repoSheet;
	static WebDriver driver;

	public static void main(String[] args) throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://docs.seleniumhq.org/download/maven.jsp");
		// getWebElement("hello").click();
		System.out.println(getWebElements("hello").size());
		driver.close();
	}

	public static WebElement getWebElement(String ObjectName) throws Exception {
		checkXLS("repository");
		checkSheet("repository", "repo");
		loadSheet("repository", "repo");
		int count = 0;
		String identifierType;
		String identifiervalue;
		int rows;
		rows = repoSheet.getRows();
		for (int i = 0; i < rows; i++) {
			if (repoSheet.getCell(1, i).getContents().toString().trim().equalsIgnoreCase(ObjectName.trim())) {
				count++;
			}
		}
		if (count == 0) {
			throw new Exception("\nNo element is present in repository.xls with the name :- " + ObjectName);
		}
		if (count > 1) {
			throw new Exception("\nMultiple elements found in repository.xls with the same name:- " + ObjectName);
		}
		// below code will run if only one element is there in the file.

		identifierType = repoSheet.getCell(3, count).getContents().trim().toLowerCase();
		identifiervalue = repoSheet.getCell(2, count).getContents().trim();
		switch (identifierType) {
		case "id":
			if (driver.findElements(By.id(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.id(identifiervalue));
		case "name":
			if (driver.findElements(By.name(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.name(identifiervalue));

		case "xpath":
			if (driver.findElements(By.xpath(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.xpath(identifiervalue));
		case "tagname":
			if (driver.findElements(By.tagName(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.tagName(identifiervalue));

		case "classname":
			if (driver.findElements(By.className(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.className(identifiervalue));
		case "cssselector":
			if (driver.findElements(By.cssSelector(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.cssSelector(identifiervalue));

		case "linktext":
			if (driver.findElements(By.linkText(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.linkText(identifiervalue));

		case "partiallinktext":
			if (driver.findElements(By.partialLinkText(identifiervalue)).size() > 1) {
				throw new Exception("\nMore than one element found with reference to :- " + ObjectName);
			}
			return driver.findElement(By.partialLinkText(identifiervalue));
		default:
			throw new Exception("\nInvalid identifier Type of " + ObjectName);
		}
	}

	public static List<WebElement> getWebElements(String ObjectName) throws Exception {
		checkXLS("repository");
		checkSheet("repository", "repo");
		loadSheet("repository", "repo");
		int count = 0;
		String identifierType;
		String identifiervalue;
		int rows;
		rows = repoSheet.getRows();
		for (int i = 0; i < rows; i++) {
			if (repoSheet.getCell(1, i).getContents().toString().trim().equalsIgnoreCase(ObjectName.trim())) {
				count++;
			}
		}
		if (count == 0) {
			throw new Exception("\nNo element is present in repository.xls with the name :- " + ObjectName);
		}
		if (count > 1) {
			throw new Exception("\nMultiple elements found in repository.xls with the same name:- " + ObjectName);
		}
		// below code will run if only one element is there in the file.

		identifierType = repoSheet.getCell(3, count).getContents().trim().toLowerCase();
		identifiervalue = repoSheet.getCell(2, count).getContents().trim();
		switch (identifierType) {
		case "id":
			return driver.findElements(By.id(identifiervalue));
		case "name":

			return driver.findElements(By.name(identifiervalue));

		case "xpath":

			return driver.findElements(By.xpath(identifiervalue));
		case "tagname":

			return driver.findElements(By.tagName(identifiervalue));

		case "classname":

			return driver.findElements(By.className(identifiervalue));
		case "cssselector":

			return driver.findElements(By.cssSelector(identifiervalue));

		case "linktext":

			return driver.findElements(By.linkText(identifiervalue));

		case "partiallinktext":

			return driver.findElements(By.partialLinkText(identifiervalue));
		default:
			throw new Exception("\nInvalid identifier Type of " + ObjectName);
		}
	}

	public static void loadSheet(String xlsFileName, String sheetName) throws Exception {
		checkXLS(xlsFileName);
		repoWB = Workbook.getWorkbook(new File(System.getProperty("user.dir") + "\\files\\" + xlsFileName + ".xls"));
		repoSheet = repoWB.getSheet(sheetName);

		if (!repoSheet.getCell(0, 0).getContents().equalsIgnoreCase("Page name")
				|| !repoSheet.getCell(1, 0).getContents().equalsIgnoreCase("Object name")
				|| !repoSheet.getCell(2, 0).getContents().equalsIgnoreCase("Identifier value")
				|| !repoSheet.getCell(3, 0).getContents().equalsIgnoreCase("Identifier type")) {
			throw new Exception("\n**NON-COMPATIBLE HEADER**.Correct it with \n Page name,Object name,Identifier value,Identifier type");

		}
	}

	public static void checkXLS(String xlsFileName) throws Exception {
		try {
			Workbook temp = Workbook
					.getWorkbook(new File(System.getProperty("user.dir") + "\\files\\" + xlsFileName + ".xls"));
			temp.close();
		} catch (java.io.FileNotFoundException e) {
			throw new Exception("\nNo file found with the name:- " + "'" + xlsFileName + "'" + "\n");

		}
	}

	public static void checkSheet(String xlsFileName, String sheetName) throws Exception {
		Workbook temp;
		checkXLS(xlsFileName);
		temp = Workbook.getWorkbook(new File(System.getProperty("user.dir") + "\\files\\" + xlsFileName + ".xls"));
		if (temp.getSheet(sheetName) == null) {
			throw new Exception("\nNo sheet found with the Name:- " + "'" + sheetName + "'" + "\n");
		}
		temp.close();
	}
}
