package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

// Get the hint from this github:
// https://github.com/skb1129/super-duper-drive/blob/3ae900f96133933a31852b02a4ca6b7bc4a51a36/src/test/java/com/udacity/cloudstorage/CloudStorageApplicationTests.java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private static final String FIRST_NAME = "Test";
	private static final String LAST_NAME = "User";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "password";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testUnauthorizedUser() {
		String homeUrl = "http://localhost:" + this.port + "/home";
		String loginUrl = "http://localhost:" + this.port + "/login";
		driver.get(homeUrl);

		Assertions.assertEquals(driver.getCurrentUrl(), loginUrl);
	}

	@Test
	@Order(2)
	public void testSignupUser() throws InterruptedException {
		String homeUrl = "http://localhost:" + this.port + "/home";
		String loginUrl = "http://localhost:" + this.port + "/login";
		signupUser();
		loginUser();

		Assertions.assertEquals(driver.getCurrentUrl(), homeUrl);

		WebElement logout = driver.findElement(By.id("logout"));
		logout.click();
		Thread.sleep(1000);
		Assertions.assertEquals(driver.getCurrentUrl(), loginUrl + "?logout");

		driver.get(homeUrl);
		Thread.sleep(1000);
		Assertions.assertEquals(driver.getCurrentUrl(), loginUrl);
	}

	@Test
	@Order(3)
	public void testNoteCreate() throws InterruptedException {
		loginUser();

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(1000);

		WebElement showNoteModel = driver.findElement(By.id("show-note-modal"));
		showNoteModel.click();
		Thread.sleep(1000);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("note title test");

		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys("note description");

		WebElement saveNote = driver.findElement(By.id("save-note"));
		saveNote.click();
		Thread.sleep(1000);

		WebElement success = driver.findElement(By.tagName("h1"));
		String text = success.getText();
		Assertions.assertEquals(text, "Success");

		WebElement goHome = driver.findElement(By.id("go-home"));
		goHome.click();
		Thread.sleep(1000);

		WebElement notesTabNew = driver.findElement(By.id("nav-notes-tab"));
		notesTabNew.click();
		Thread.sleep(1000);

		WebElement savedNote = driver.findElement(By.cssSelector("th.note-title-row"));
		Assertions.assertEquals(savedNote.getText(), "note title test");
	}

	@Test
	@Order(4)
	public void testNoteEdit() throws InterruptedException {
		loginUser();

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(1000);

		WebElement editNote = driver.findElement(By.cssSelector("button.edit-note"));
		editNote.click();
		Thread.sleep(1000);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys(" edit");
		WebElement saveNote = driver.findElement(By.id("save-note"));
		saveNote.click();
		Thread.sleep(1000);


		WebElement goHome = driver.findElement(By.id("go-home"));
		goHome.click();
		Thread.sleep(1000);

		WebElement notesTabNew = driver.findElement(By.id("nav-notes-tab"));
		notesTabNew.click();
		Thread.sleep(1000);

		WebElement savedNote = driver.findElement(By.cssSelector("th.note-title-row"));
		Assertions.assertEquals(savedNote.getText(), "note title test edit");
	}

	@Test
	@Order(5)
	public void testNoteDelete() throws InterruptedException {
		loginUser();

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(1000);

		WebElement deleteNote = driver.findElement(By.cssSelector("a.delete-note"));
		deleteNote.click();
		Thread.sleep(1000);

		List<WebElement> noteTitleList = driver.findElements(By.className("note-title-row"));
		Assertions.assertTrue(noteTitleList.isEmpty());
	}

	@Test
	@Order(6)
	public void testCredentialCreate() throws InterruptedException {
		loginUser();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(1000);

		WebElement showCredentialsModel = driver.findElement(By.id("show-credentials-modal"));
		showCredentialsModel.click();
		Thread.sleep(1000);

		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("www.google.com");

		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("username");

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("password");

		WebElement credentialSubmit = driver.findElement(By.id("credential-submit"));
		credentialSubmit.click();
		Thread.sleep(1000);

		WebElement goHome = driver.findElement(By.id("go-home"));
		goHome.click();
		Thread.sleep(1000);

		WebElement credentialsTabNew = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTabNew.click();
		Thread.sleep(1000);

		WebElement savedCredentialUrl = driver.findElement(By.cssSelector("th.saved-credential-url"));
		Assertions.assertEquals(savedCredentialUrl.getText(), "www.google.com" );
		Thread.sleep(500);

		WebElement savedCredentialUsername = driver.findElement(By.cssSelector("td.saved-credential-username"));
		Assertions.assertEquals(savedCredentialUsername.getText(), "username" );
		Thread.sleep(500);

		WebElement savedCredentialPassword = driver.findElement(By.cssSelector("td.saved-credential-password"));
		Assertions.assertNotEquals(savedCredentialPassword.getText(), "password");
	}

	@Test
	@Order(7)
	public void testCredentialEdit() throws InterruptedException {
		loginUser();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(1000);

		WebElement editCredential = driver.findElement(By.cssSelector("button.edit-credential"));
		editCredential.click();
		Thread.sleep(500);

		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(".cn");
		WebElement saveCredential = driver.findElement(By.id("credential-submit"));
		saveCredential.click();
		Thread.sleep(1000);


		WebElement goHome = driver.findElement(By.id("go-home"));
		goHome.click();
		Thread.sleep(1000);

		WebElement notesTab2 = driver.findElement(By.id("nav-credentials-tab"));
		notesTab2.click();
		Thread.sleep(500);

		WebElement savedCredential = driver.findElement(By.cssSelector("th.saved-credential-url"));
		Assertions.assertEquals(savedCredential.getText(), "www.google.com.cn");
	}

	@Test
	@Order(8)
	public void testCredentialDelete() throws InterruptedException {
		loginUser();

		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(1000);

		WebElement deleteCredential = driver.findElement(By.cssSelector("a.delete-credential"));
		deleteCredential.click();
		Thread.sleep(1000);

		WebElement goHome = driver.findElement(By.id("go-home"));
		goHome.click();
		Thread.sleep(1000);

		WebElement credentialTabNew = driver.findElement(By.id("nav-credentials-tab"));
		credentialTabNew.click();
		Thread.sleep(500);

		List<WebElement> credentialList = driver.findElements(By.cssSelector("th.saved-credential-url"));
		Assertions.assertTrue(credentialList.isEmpty());
	}

	private void signupUser() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(1000);

		WebElement signupLinkInLogin = driver.findElement(By.id("signup-in-login"));
		signupLinkInLogin.click();
		Thread.sleep(1000);

		WebElement firstNameInput = driver.findElement(By.id("inputFirstName"));
		firstNameInput.sendKeys(FIRST_NAME);

		WebElement lastNameInput = driver.findElement(By.id("inputLastName"));
		lastNameInput.sendKeys(LAST_NAME);

		WebElement usernameInput = driver.findElement(By.id("inputUsername"));
		usernameInput.sendKeys(USERNAME);

		WebElement passwordInput = driver.findElement(By.id("inputPassword"));
		passwordInput.sendKeys(PASSWORD);

		WebElement signupButton = driver.findElement(By.id("signup"));
		signupButton.click();
	}

	private void loginUser() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(1000);

		WebElement usernameInput = driver.findElement(By.id("inputUsername"));
		usernameInput.sendKeys(USERNAME);

		WebElement passwordInput = driver.findElement(By.id("inputPassword"));
		passwordInput.sendKeys(PASSWORD);

		WebElement login = driver.findElement(By.id("login"));
		login.click();
	}
}
