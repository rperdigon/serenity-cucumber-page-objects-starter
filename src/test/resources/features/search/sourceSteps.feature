Feature: buy a bag

  Scenario: buy a bug
    Given go to web "https://www.saucedemo.com/"
    Then check to "login" has loaded
    When send text "standard_user" to element "user"
    And send text "secret_sauce" to element "pass"
    And click in "login"
    Then check to "Products" has loaded
    When click "Add to cart" on item in the list "Products" whose title is "Sauce Labs Backpack"
    Then we check that the cart has "1" product
    And click in "cart icon"
    Then check to "Your Cart" has loaded
    And click in "checkout"
    Then check to "Your Information" has loaded
    When send text to:
      | First Name      | John  |
      | Last Name       | Dou   |
      | Zip/Postal Code | 58580 |
    And click in "Continue"
    Then check to "Checkout" has loaded
    And verify that the product "Sauce Labs Backpack" exists
    When click in "Finish"

#  We check that the product data corresponds to the solution
#
#  we press “finish”
#
#  We check that we are on the full page
#
#  click on "back home"
#
#  We check that we are on the “home” page
