package com.Flipkart;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Flipkart {

    public static void main(String[] args) throws InterruptedException {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Home\\Downloads\\chromedriver-win64\\chromedriver.exe");
        
        // Initialize WebDriver
        ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);

        
        // Maximize browser window
        driver.manage().window().maximize();
        
        // Navigate to Flipkart
        driver.get("https://www.flipkart.com");

        try {
            // Close login modal if it appears
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
        } catch (Exception e) {
            System.out.println("Login modal did not appear.");
        }

        // Perform search
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("iPhone 15 plus");
        searchBox.submit();

        // Switch to the product page
        String mainPage = driver.getWindowHandle();
        System.out.println("Main Page: " + mainPage);

        driver.findElement(By.xpath("//div[normalize-space()='Apple iPhone 15 Plus (Blue, 128 GB)']")).click();

        // Handle multiple windows/tabs
        for (String page : driver.getWindowHandles()) {
            if (!page.equals(mainPage)) {
                driver.switchTo().window(page);
                break;
            }
        }

        // Print current URL and product details
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Number of products: " + driver.findElements(By.className("_7eSDEz")).size());

        // Add to cart and wait
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add to cart']")));
        addToCartButton.click();
        Thread.sleep(10000); // Simulating a wait after adding to cart

               // Go to cart and remove items using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Remove first item from cart
        WebElement firstRemoveButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sBxzFz' and text()='Remove']")));
        js.executeScript("arguments[0].click();", firstRemoveButton);

        // Debugging: Verify if the element is found and visible
        if (firstRemoveButton != null && firstRemoveButton.isDisplayed()) {
            System.out.println("First remove button found and is displayed.");
        } else {
            System.out.println("First remove button not found or not visible.");
        }

        Thread.sleep(5000);

        // Remove second item from cart
        WebElement secondRemoveButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'sBxzFz') and contains(@class, 'fF30ZI') and contains(@class, 'A0MXnh') and text()='Remove']")));
        js.executeScript("arguments[0].click();", secondRemoveButton);

        // Debugging: Verify if the element is found and visible
        if (secondRemoveButton != null && secondRemoveButton.isDisplayed()) {
            System.out.println("Second remove button found and is displayed.");
        } else {
            System.out.println("Second remove button not found or not visible.");
        }

        // Close the browser
        Thread.sleep(5000);
        driver.quit();
        System.out.println("Success");
    }
}
