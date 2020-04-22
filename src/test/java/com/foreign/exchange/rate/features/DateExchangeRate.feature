Feature: Date Specific Exchange Rates

@TestCase1 @RegressionTesting
Scenario Outline: Get the Foreign Exchange reference rates for Specific date

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"

Examples: 
      | URL               | ContentType      | RequestMethod | StatusCode |
      | /api/2020-04-12	  | application/json | GET           |        200 |
      | /api/2020		  | application/json | GET           |        400 |

@TestCase2 @RegressionTesting
Scenario Outline: Request specific exchange rates by setting the symbols parameter and date

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"
And Verify the response value of "<Base>" is "<Currency>"

Examples: 
      | URL               			| ContentType      | RequestMethod | StatusCode | Base | Currency	|
      | /api/2020-04-12?symbols=USD | application/json | GET           |        200 | base | EUR		|
      
@TestCase3 @RegressionTesting
Scenario Outline: Get the Foreign Exchange reference rates for Future date

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"
And Verify the response value of "<Base>" is "<Currency>"
And Verify the "<Date>" in response matches with current date

Examples: 
      | URL               | ContentType      |RequestMethod | StatusCode | Base | Currency	| Date |
      | /api/2021-04-12	  | application/json |GET           |        200 | base | EUR		| date |
      