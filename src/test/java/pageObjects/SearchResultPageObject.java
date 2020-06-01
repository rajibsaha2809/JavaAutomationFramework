package pageObjects;

import org.junit.Assert;
import org.mortbay.log.Log;
import org.openqa.selenium.By;
import pageContainers.*;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;
import utils.VUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class SearchResultPageObject extends SharedPageObject {

    SearchResultPageContainer searchResultPageContainer;

    public SearchResultPageObject() {
        searchResultPageContainer = PageFactory.initElements(Driver.getCurrentDriver(), SearchResultPageContainer.class);
    }

    public void verifyPage(String itemToSearch) {
        Assert.assertTrue("The search result is not found", searchResultPageContainer.verifyPage.isDisplayed());
        Assert.assertTrue("The result is not found for searched items", Driver.getCurrentDriver().findElement(
        By.xpath("//span[contains(text(), '" + itemToSearch + "')]")).isDisplayed());
    }

    public void verifySearchResult() {
        if (searchResultPageContainer.list_ResultItem.size()>0) {
            System.out.println("The results shows matching items");
        } else
            System.out.println("No result found");
    }

    public void sortItem(String strSortType) {
        searchResultPageContainer.lnk_sortMenu.click();
        VUtils.waitFor(2);
        if (strSortType.equalsIgnoreCase("Lowest")) {
            searchResultPageContainer.lnk_sortByLowestPrice.click();
        } else if (strSortType.equalsIgnoreCase( "Highest")) {
            searchResultPageContainer.lnk_sortByHighestPrice.click();
        }
    }

    public void validateSortedPrice(String sortingOrder) {
        int itemPriceCount = searchResultPageContainer.price_ResultItem.size();
        List<Integer> list = new ArrayList<>();
        for (int item=0; item<itemPriceCount; item++) {
            list.add(Integer.parseInt(searchResultPageContainer.price_ResultItem.get(item).getText().trim().
                    replace("£","").replace(",","").
                    replace(".","")));
        }
        List<Integer> sortedList = new ArrayList<Integer>(list);
        boolean sorted = false;
        if (sortingOrder.equalsIgnoreCase("Lowest")) {
            Collections.sort(sortedList);
            sorted = sortedList.equals(list);
        } else if (sortingOrder.equalsIgnoreCase("Highest")) {
            Collections.sort(sortedList);
            Collections.reverse(sortedList);
            sorted = sortedList.equals(list);
        }
        Log.info("Expected list is : " + sortedList);
        Log.info("Actual list is   : " + list);
        assertTrue("The price is not in " + sortingOrder + " order", sorted);
        Log.info("The price is in " + sortingOrder + " order");
    }

    public void selectDropDownValue(String valueToSelect) {
        if (searchResultPageContainer.btn_ItemsPerPage.isDisplayed()) {
            searchResultPageContainer.btn_ItemsPerPage.click();
            Driver.getCurrentDriver().findElement(By.xpath("//span[text()='" + valueToSelect + "']/ancestor::a")).
                    click();
        }
    }

    public void verifyPagination() {
        Assert.assertTrue("The result is not paginated", searchResultPageContainer.item_Pagination.isDisplayed());
    }

}
