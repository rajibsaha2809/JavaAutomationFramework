@ebaySearch @InitializePage
Feature: Customer verify ebay search functionality
  As non-registered customer
  I want to search an item in ebay
  So that I can sort, filter and see details of resulting products

  @001
  Scenario: Search and verify results

    Given As a non-registered customer I navigate to ebay
    When I search for an item Mobile Phone
    And I click on search button
    Then I get a list of matching results for Mobile Phone
    When I sort the results by Lowest Price
    Then the results are listed in the page sorted by Lowest Price
    When I sort the results by Highest Price
    Then the results are listed in the page sorted by Highest Price

  @002
  Scenario: Search and navigate through results pages

    Given As a non-registered customer I navigate to ebay
    When I search for an item Mobile Phone
    And I click on search button
    Then I get a list of matching results for Mobile Phone
    When I set value for Items Per Page is 25
    Then I can see pagination

