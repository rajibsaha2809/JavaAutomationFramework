package pageObjects;

import org.junit.Assert;
import pageContainers.*;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

public class HomePageObject extends SharedPageObject {

    private static HomePageContainer homePageContainer;

    public HomePageObject() {
        homePageContainer = PageFactory.initElements(Driver.getCurrentDriver(), HomePageContainer.class);
    }

    public void verifyPage() {
        Assert.assertTrue("The home page is not opened", homePageContainer.verifyPage.isDisplayed());
    }

    public void searchItem(String itemToSearch) {
        homePageContainer.txt_searchItem.sendKeys(itemToSearch);
    }

    public void clickSearch() {
        homePageContainer.btn_search.click();
    }
}
