@Regression @Smoke
Feature: Home Page Functionality for Pluralsight Clone Application

  Background: 
    Given User launches the Pluralsight Clone Application using link "https://nirajt03.github.io/sample-website/"

  Scenario Outline: Verify home page features
    Given User login as "<Authority>"
    And User needs to navigate to home page
    When Home Page must be visible to user
    Then Verify Home page header text as "<Expected Home Page Header>"
    Then Verify Home page description text as "<Expected Home Page Description>"
    And Logout from Pluralsight clone application

    Examples: 
      | Authority | Expected Home Page Header        | Expected Home Page Description                                                                                                                                                                                                                            |
      | Admin     | The technology learning platform | Keep up with technology with expert-led courses, assessments and tools that help you build the skills you need, when you need them. For organizations, get unprecedented insight into skills strengths and weaknesses and align learning to what matters. |
      | User      | The technology learning platform | Keep up with technology with expert-led courses, assessments and tools that help you build the skills you need, when you need them. For organizations, get unprecedented insight into skills strengths and weaknesses and align learning to what matters. |
