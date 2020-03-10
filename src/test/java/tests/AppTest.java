package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helpers.Helpers;
import helpers.Screenshooter;
import helpers.WebDriverManager;
import pages.PageLogin;
import pages.PageLogon;
import pages.PageReservation;

public class AppTest {

	private WebDriver driver;
	ArrayList<String> tabs;

	@BeforeMethod
	public void setUp() {
		// DesiredCapabilities caps = new DesiredCapabilities();

		System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
		driver = new FirefoxDriver();

		// System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		// driver = new ChromeDriver();
		// driver.manage().window().maximize();
		// driver.manage().window().fullscreen();

		//driver.manage().window().setSize(new Dimension(800, 800));
		//driver.manage().window().setPosition(new Point(600, 0));
		driver.navigate().to("http://newtours.demoaut.com/");

		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		String googleWindow = "window.open('http://www.google.com')";
		javaScriptExecutor.executeScript(googleWindow);
		tabs = new ArrayList<String>(driver.getWindowHandles());

		// try { Thread.sleep(5); } catch (InterruptedException e) {
		// e.printStackTrace(); }
	}

	@Test
	public void loginIncorrecto() {		
		WebDriverManager.setWindowSize(driver, 800, 800);
		WebDriverManager.setWindowPosition(driver, 600, 0);
		driver.switchTo().window(tabs.get(1)).navigate().to("http://www.youtube.com/user/Draculinio");
		driver.switchTo().window(tabs.get(0));
		PageLogin pageLogin = new PageLogin(driver);
		PageLogon pageLogon = new PageLogon(driver);
		pageLogin.login("user", "user");
		pageLogon.assertLogonPage();
	}
	
	@Test
	public void loginIncorrecto2() {
		//WebDriverManager.setWindowSize(driver, "maximized");
		WebDriverManager.setWindowSize(driver, 800, 800);
		WebDriverManager.setWindowPosition(driver, 600, 0);
		driver.switchTo().window(tabs.get(0));
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "imercury");
		pageReservation.assertPage();
	}
	
	@Test
	public void login_CorrectoReserva() {
		WebDriverManager.setWindowSize(driver, 800, 800);
		WebDriverManager.setWindowPosition(driver, 600, 0);
		driver.switchTo().window(tabs.get(0));
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercury");
		pageReservation.selectPassengers(2);
		pageReservation.selectFromPort(3);
		pageReservation.selectToPort("London");
	}

	@Test
	public void pruebaCantidadCampos() {
		WebDriverManager.setWindowSize(driver, 800, 800);
		WebDriverManager.setWindowPosition(driver, 600, 0);
		driver.switchTo().window(tabs.get(0));
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.verifyFields();
	}
	
	@AfterMethod
	public void finalizarPrueba(ITestResult result) {
		if (!result.isSuccess()) {
			Screenshooter.takeScreenshot("screenshots/ERROR ", driver);
		}
		driver.switchTo().window(tabs.get(0)).close();
		driver.switchTo().window(tabs.get(1)).close();
		

	}
}