package pageobjectmodel.test;

import com.mycompany.app.bringiton.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BringItOnTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PC\\Desktop\\Selenium\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test(description = "Bring it on task test")
    public void createPasteBtnTest() {
        new PastebinHomePage(driver)
                .openPage()
                .pasteNewPaste("git config --global user.name  \"New Sheriff in Town\"\n" +
                        "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                        "git push origin master --force")
                .setSyntaxHighlighting("Bash")
                .setExpirationPeriod("10 Minutes")
                .nameNewPaste("how to gain dominance among developers")
                .createNewPaste();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "how to gain dominance among developers", "Paste Name / Title doesn't match");
        Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Bash']")).getText(), "Bash", "Syntax Highlighting doesn't match ");
        Assert.assertEquals("git config --global user.name  \"New Sheriff in Town\"\n" +
                "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                "git push origin master --force", driver.findElement(By.xpath("//ol")).getText(), "Paste doesn't match");
    }

    @AfterMethod(alwaysRun = true)
    public void kickBrowser() {
        driver.quit();
        driver = null;
    }
}
