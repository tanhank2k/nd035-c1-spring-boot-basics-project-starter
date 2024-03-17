package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String username = "user1";
	private String password = "123";


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	@Order(2)
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	@Order(3)
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}


	@Test
	@Order(4)
	public void test_case_1_logout(){
		doMockSignUp("Redirection","Test",username,password);
		doLogIn(username, password);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		Assertions.assertEquals("Login",  driver.getTitle());
	}

	@Test
	@Order(5)
	public void test_case_2_LoginFailed(){
		doMockSignUp("Redirection","Test","LS1","123");
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys("LS1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys("password");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertEquals("Login",  driver.getTitle());
	}

	@Test
	@Order(6)
	public void test_case_3_CreateNote(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
		WebElement addNoteBtn = driver.findElement(By.id("add-note-btn"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(addNoteBtn)).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("title");
		WebElement description = driver.findElement(By.id("note-description"));
		description.sendKeys("description");
		WebElement saveBtn = driver.findElement(By.id("save-note-btn"));
		saveBtn.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check for note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		Boolean created = false;
		for (int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals("title")) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Test
	@Order(7)
	public void test_case_4_UpdateNote(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		var notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		// update
		WebElement editBtn = driver.findElement(By.name("edit-btn"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("1");
		var saveBtn = driver.findElement(By.id("save-note-btn"));
		saveBtn.click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		var notesTable = driver.findElement(By.id("userTable"));
		var noteTitles = notesTable.findElements(By.tagName("th"));
		Boolean updated = false;
		for (int i=0; i < noteTitles.size(); i++) {
			WebElement element = noteTitles.get(i);
			if (element.getAttribute("innerHTML").equals("title1")) {
				updated = true;
				break;
			}
		}
		Assertions.assertTrue(updated);
	}

	@Test
	@Order(8)
	public void test_case_5_DeleteNote(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);

		var notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		var notesTable = driver.findElement(By.id("userTable"));
		var noteTd = notesTable.findElements(By.tagName("td"));
		WebElement deleteBtn = null;
		for (int i=0; i < noteTd.size(); i++) {
			deleteBtn = noteTd.get(i).findElement(By.name("delete-btn"));
			if (Objects.nonNull(deleteBtn)) {
				break;
			}
		}
		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		notesTable = driver.findElement(By.id("userTable"));
		var noteTitles = notesTable.findElements(By.tagName("th"));
		Boolean hasDeletedNote = false;
		for (int i=0; i < noteTitles.size(); i++) {
			WebElement element = noteTitles.get(i);
			if (element.getAttribute("innerHTML").equals("title1")) {
				hasDeletedNote = true;
				break;
			}
		}
		Assertions.assertFalse(hasDeletedNote);
	}

	@Test
	@Order(9)
	public void test_case_6_CreateCredential(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		WebElement notesTab = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
		WebElement addNoteBtn = driver.findElement(By.id("add-credential-btn"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(addNoteBtn)).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("credential.com");
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("credentialUsername");
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("credentialPassword");
		WebElement saveBtn = driver.findElement(By.id("save-credential-btn"));
		saveBtn.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check for note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();

		WebElement notesTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		Boolean created = false;
		for (int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals("credential.com")) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Test
	@Order(10)
	public void test_case_7_UpdateCredential(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		var credentialTabs = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTabs)).click();

		// update
		WebElement editBtn = driver.findElement(By.name("edit-credential-btn"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(".vn");
		var saveBtn = driver.findElement(By.id("save-credential-btn"));
		saveBtn.click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		credentialTabs = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTabs)).click();

		var credentialTable = driver.findElement(By.id("credentialTable"));
		var credentialTh = credentialTable.findElements(By.tagName("th"));
		Boolean updated = false;
		for (int i=0; i < credentialTh.size(); i++) {
			WebElement element = credentialTh.get(i);
			if (element.getAttribute("innerHTML").equals("credential.com.vn")) {
				updated = true;
				break;
			}
		}
		Assertions.assertTrue(updated);
	}

	@Test
	@Order(11)
	public void test_case_8_DeleteCredential(){
		doLogIn(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);

		var credentialTabs = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTabs)).click();

		var credentialTable = driver.findElement(By.id("credentialTable"));
		var credentialTd = credentialTable.findElements(By.tagName("td"));
		WebElement deleteBtn = null;
		for (int i=0; i < credentialTd.size(); i++) {
			deleteBtn = credentialTd.get(i).findElement(By.name("delete-credential-btn"));
			if (Objects.nonNull(deleteBtn)) {
				break;
			}
		}
		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		credentialTabs = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTabs)).click();

		credentialTable = driver.findElement(By.id("credentialTable"));
		var credentialTh = credentialTable.findElements(By.tagName("th"));
		Boolean hasDeletedCredential = false;
		for (int i=0; i < credentialTh.size(); i++) {
			WebElement element = credentialTh.get(i);
			if (element.getAttribute("innerHTML").equals("credential.com.vn")) {
				hasDeletedCredential = true;
				break;
			}
		}
		Assertions.assertFalse(hasDeletedCredential);
	}

}
