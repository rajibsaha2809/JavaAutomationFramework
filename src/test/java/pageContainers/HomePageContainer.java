package pageContainers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageContainer {

    @FindBy(xpath = "//a[@id='gh-la']")
    public WebElement verifyPage;

    @FindBy(xpath = "//input[@placeholder='Search for anything']")
    public WebElement txt_searchItem;

    @FindBy(xpath = "//input[@value='Search']")
    public WebElement btn_search;

    @FindBy(xpath = "//select[@aria-label='Select a category for search']")
    public WebElement dropdown_selectCategory;

    @FindBy(xpath = "//button[@id='gdpr-banner-accept']")
    public WebElement btn_Accept;
}