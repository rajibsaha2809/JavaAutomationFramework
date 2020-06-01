package pageContainers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultPageContainer {

    @FindBy(xpath = "//h1[contains(text(),'results for ')]")
    public WebElement verifyPage;

    @FindBy(xpath = "//ul[@class='srp-results srp-list clearfix']/li")
    public List<WebElement> list_ResultItem;

    @FindBy(xpath = "//label[text()='Sort']/..//button")
    public WebElement lnk_sortMenu;

    @FindBy(xpath = "//span[text()='Lowest price']/ancestor::a")
    public WebElement lnk_sortByLowestPrice;

    @FindBy(xpath = "//span[text()='Highest price']/ancestor::a")
    public WebElement lnk_sortByHighestPrice;

    @FindBy(xpath = "//ul[@class='srp-results srp-list clearfix']/li//div[@class='s-item__detail s-item__detail--primary'][1]/span")
    public List<WebElement> price_ResultItem;

    @FindBy(xpath = "//button[contains(@aria-controls,'SEARCH_PAGINATION_MODEL')]")
    public WebElement btn_ItemsPerPage;

    @FindBy(xpath = "//nav[@class='pagination']")
    public WebElement item_Pagination;

}
