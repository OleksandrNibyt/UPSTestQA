# java-webdriver-cucumber

Automated test cases for UPS website.

Scenario: UPS
Given I go to "ups" page
And I open Shipping menu
And I go to Create a Shipment
When I fill out origin shipment fields
And I submit the shipment form
Then I verify origin shipment fields submitted
When I fill out destination shipment fields
Then I verify total charges appear
When I submit the shipment form
And I set packaging type
Then I verify total charges changed
When I submit new shipment form
And I select cheapest delivery option
And I submit the shipment form
And I set Saturday Delivery type
Then I verify total charges changed
When I submit the shipment form
And I select Paypal payment type
And I submit the shipment form
Then I review all recorded details on the reviewpage
And I cancel the shipment form
Then I verify shipment form is reset
