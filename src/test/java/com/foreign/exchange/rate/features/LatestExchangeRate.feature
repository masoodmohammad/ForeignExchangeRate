Feature: Latest Foreign Exchange Rates

@TestCase4 @SanityTesting
Scenario Outline: Get the Latest Foreign Exchange reference rates

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"

Examples: 
      | URL               | ContentType      | RequestMethod | StatusCode |
      | /api/latest 	  | application/json | GET           |        200 |


@TestCase5 @RegressionTesting
Scenario Outline: Request specific exchange rates by setting the symbols parameter

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"
And Verify the response value of "<Base>" is "<Currency>"

Examples: 
      | URL               		| ContentType      | RequestMethod | StatusCode | Base | Currency	|
      | /api/latest?symbols=USD | application/json | GET           |        200 | base | EUR		|
      
      
@TestCase6 @RegressionTesting
Scenario Outline: Hit the incorrect or incomplete URL

Given Set EndPointURL as "<URL>"
When Set the header content type as "<ContentType>"
When Hit the API with request method is "<RequestMethod>"
Then Verify the status code is "<StatusCode>"
And Verify the response value of "<Error>" is "<Message>"

Examples: 
      | URL               | ContentType      | RequestMethod | StatusCode | Error | Message |
      | /api		 	  | application/json | GET           |        400 | error | time data 'api' does not match format '%Y-%m-%d'|
      