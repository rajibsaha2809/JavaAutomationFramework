# CodingChallenge
This is an empty Java+Selenium+Cucumber maven project

1. You should have Java JDK 11 installed
2. Extract the given maven project from the provided .zip file
3. From the shell/command-line 
    1. Navigate to the unziped folder
    2. Run “mvn test”
    3. You should get a build success result, showing undefined test steps
4. Import the maven project in your preferred IDE. Make sure the project is loaded correct and there are no import errors.
5. Write automated tests for the given scenarios using the project setup provided. (Include any Classes and packages/folders to suit your designed solution)
6. To run your tests, from the shell/command-line use command:
    1. mvn -Dtest=TestRunner -Dcucumber.options="--tags @001" test - note that ‘@001’ is the tag name of the test scenarios provided in the .feature file
    2. Your tests should begin to run
    3. Results are shown and the build is successful
7. Provide the solution as a .zip file that can be extracted and run the same way you have done locally.

Notes: 
- Please use any libraries available for driver binary management, do not include the driver executable in the project path.
- Add validations to verify the results e.g. sorting order, matching category, tags, page navigation
- Use Page Object Model
- Use any logging framework
- The tests should generate a test results report preferably other than TestNG reports
- 

```
Scenario 1:
@001
Scenario: Search and verify results
Given I am a non-registered customer 
And I navigate to www.ebay.co.uk
When I search for an item
Then I get a list of matching results 
And the resulting items cards show: postage price, No of bids, price or show BuyItNow tag
Then I can sort the results by Lowest Price
And the results are listed in the page in the correct order
Then I can sort the results by Highest Price
And the results are listed in the page in the correct order
Then I can filter the results by 'Buy it now'
And all the results shown in the page have the 'Buy it now' tag 

Scenario 2:
@002
Scenario: Search per category
Given I am a non-registered customer 
And I navigate to www.ebay.co.uk
When I enter a search term and select a specific Category
Then I get a list of matching results
And I can verify that the results shown as per the the selected category

Scenario 3:
@003
Scenario: Search and navigate through results pages
Given I am a non-registered customer 
And I navigate to www.ebay.co.uk
When I search for an item
Then I get a list of matching results 
And the results show more than one page
Then the user can navigate through the pages to continue looking at the items
```
