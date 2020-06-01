package stepDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.*;

public class StepDef {

	HomePageObject homePageObject;
	SearchResultPageObject searchResultPageObject;

	@Before("@InitializePage")
	public void startup(){
		homePageObject = new HomePageObject();
		searchResultPageObject = new SearchResultPageObject();
	}

	@Given("^As a non-registered customer I navigate to ebay$")
	public void asANonRegisteredCustomerINavigateToEbay() {
		homePageObject.verifyPage();
	}

	@When("^I search for an item (.*)$")
	public void searchfor_an_item(String itemToSearch) {
		homePageObject.searchItem(itemToSearch);
	}

	@When("^I click on search button$")
	public void i_click_on_search_button() {
		homePageObject.clickSearch();
	}

	@Then("^I get a list of matching results for (.*)$")
	public void i_get_a_list_of_matching_results(String itemToSearch) {
		searchResultPageObject.verifyPage(itemToSearch);
		searchResultPageObject.verifySearchResult();
	}

	@When("^I sort the results by (.*) Price$")
	public void i_sort_the_results_by_Price(String strSortType) {
		searchResultPageObject.sortItem(strSortType);
	}

	@Then("^the results are listed in the page sorted by (.*) Price$")
	public void the_Results_Are_Listed_In_The_Page_Sorted_By_Price(String strSortType) {
		searchResultPageObject.validateSortedPrice(strSortType);
	}

	@When("^I set value for Items Per Page is (.*)$")
	public void iSetValueForItemsPerPage(String valueToSelect) {
		searchResultPageObject.selectDropDownValue(valueToSelect);
	}

	@Then("^I can see pagination$")
	public void iCanSeePaginationForResultMoreThan() {
		searchResultPageObject.verifyPagination();
	}
}
