@Regression @Smoke
Feature: Login Functionality for Pluralsight Clone Application

  Background: 
    Given User launches the Pluralsight Clone Application using link "https://nirajt03.github.io/sample-website/"

  @Custom
  Scenario Outline: Verify login functionality (TCs-001,TCs-002,TCs-003)
    Given Login form should be visible on launch application URL
    When User login as "<Authority>"
    Then Verify Search page has displayed after login and verify search box text as "<ExpSearchText>"
    And Logout from Pluralsight clone application

    Examples: 
      | Authority | ExpSearchText              |
      | Admin     | What do you want to learn? |
      | User      | What do you want to learn? |

  Scenario Outline: Verify Negative Login functionality (TCs-003,TCs-004,TCs-005)
    When Login form should be visible on launch application URL
    Then User should enter credentials as "<Username>" and "<Password>"
    Then Verify login error message as "<Error Message>"

    Examples: 
      | Username | Password    | Error Message                 |
      | notadmin | password123 | Invalid user name or password |
      | admin    | notpassword | Invalid user name or password |
      |          |             | Invalid user name or password |
      | admin    |             | Invalid user name or password |
      |          | password123 | Invalid user name or password |
