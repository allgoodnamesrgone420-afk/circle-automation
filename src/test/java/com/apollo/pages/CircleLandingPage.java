package com.apollo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CircleLandingPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // header
    @FindBy(css = "img[alt*='Apollo'], a[href='/'] img")
    private WebElement apolloLogo;

    @FindBy(css = "div[class*='HeaderNew_loginCta']")
    private WebElement loginCta;

    // sticky bar — mix of linkText and partialLinkText
    @FindBy(linkText = "Circle Benefits")
    private WebElement benefitsLink;

    @FindBy(partialLinkText = "Plans For You")
    private WebElement plansLink;

    @FindBy(partialLinkText = "Member Reviews")
    private WebElement reviewsLink;

    @FindBy(linkText = "FAQs")
    private WebElement faqsLink;

    // plan block
    @FindBy(xpath = "//*[contains(text(),'Get Circle Membership')]")
    private WebElement getCircleCta;

    @FindBy(xpath = "//*[contains(text(),'12 Months')]")
    private WebElement planName;

    @FindBy(xpath = "//*[contains(text(),'199')]")
    private WebElement offerPrice;

    @FindBy(xpath = "//*[contains(text(),'599')]")
    private WebElement listPrice;

    @FindBy(xpath = "//*[self::button or self::a][contains(.,'Join Circle')]")
    private WebElement joinCircleBtn;

    @FindBy(css = "input[type='radio']")
    private List<WebElement> planRadios;

    @FindBy(css = "button")
    private List<WebElement> allButtons;

    public CircleLandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    private void scrollAndClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", element);
        sleep(800);
        js.executeScript("arguments[0].click();", element);
        sleep(800);
    }

    private void scrollTo(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", element);
        sleep(800);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    public boolean isCirclePageLoaded() {
        String url = driver.getCurrentUrl().toLowerCase();
        return url.contains("circle") || driver.getPageSource().contains("Circle");
    }

    public void clickBenefits() {
        scrollAndClick(benefitsLink);
    }

    public void clickPlans() {
        scrollAndClick(plansLink);
    }

    public void clickReviews() {
        scrollAndClick(reviewsLink);
    }

    public void clickFaqs() {
        scrollAndClick(faqsLink);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.visibilityOf(loginCta));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", loginCta);
        sleep(500);
        js.executeScript("arguments[0].click();", loginCta);
        sleep(2000);
    }

    public void clickJoinCircle() {
        scrollAndClick(joinCircleBtn);
    }

    public void clickGetCircle() {
        scrollAndClick(getCircleCta);
    }

    public boolean isLoginVisible() {
        scrollTo(loginCta);
        return loginCta.isDisplayed();
    }

    public boolean isPlanVisible() {
        scrollTo(planName);
        return planName.isDisplayed();
    }

    public boolean is199Visible() {
        scrollTo(offerPrice);
        return offerPrice.getText().contains("199");
    }

    public boolean is599Visible() {
        scrollTo(listPrice);
        return listPrice.getText().contains("599");
    }

    public boolean isJoinVisible() {
        scrollTo(joinCircleBtn);
        return joinCircleBtn.isDisplayed();
    }

    public boolean pageHas(String text) {
        return driver.getPageSource().contains(text);
    }

    public int buttonCount() {
        return allButtons.size();
    }

    /**
     * Clicks every visible plan radio. If the page has only one plan,
     * this still clicks that one.
     */
    public int clickAllPlanOptions() {
        int clicked = 0;

        if (planRadios.isEmpty()) {
            WebElement firstPlan = driver.findElement(
                    By.xpath("//*[contains(text(),'12 Months') or contains(.,'Join Circle')]"));
            scrollAndClick(firstPlan);
            return 1;
        }

        for (WebElement radio : planRadios) {
            try {
                if (!radio.isDisplayed()) {
                    continue;
                }
                scrollAndClick(radio);
                clicked++;
            } catch (Exception ignored) {
            }
        }
        return clicked;
    }
}