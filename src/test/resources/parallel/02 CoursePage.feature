@Regression @Smoke
Feature: Course Page Functionality for Pluralsight Clone Application

  Background: 
    Given User launches the Pluralsight Clone Application using link "https://nirajt03.github.io/sample-website/"

  Scenario Outline: Verify Course Page Features (TCs-006,TCs-007,TCs-008,TCs-008)
    Given User login as "<Authority>"
    And Click on Course tab in Nav Bar List
    When Select required course from filtered course list and Move to Course page as "<Course Name>"
    Then Verify selected Course header text as "<Course Header text>"
    Then Verify selected Course description test as "<Course Description text>"
    Then Validate other course details as "<Author Name>", "<Free Trail text>" and "<Course Overview text>"
    And Logout from Pluralsight clone application

  Examples: 
  | Authority | Course Name                          | Course Header text                   | Course Description text                                                                                                                                                                                                                 | Author Name | Free Trail text           | Course Overview text |
  | Admin     | Java Fundamentals: The Java Language | Java Fundamentals: The Java Language | This course provides thorough coverage of the core Java platform, giving you the skills needed to begin developing in the Java Runtime Environment (JRE) and serving as a solid foundation for all Java-based development environments. | Jim Wilson  | Start a FREE 10-day trial | Play course overview |
  | User      | Java Fundamentals: The Java Language | Java Fundamentals: The Java Language | This course provides thorough coverage of the core Java platform, giving you the skills needed to begin developing in the Java Runtime Environment (JRE) and serving as a solid foundation for all Java-based development environments. | Jim Wilson  | Start a FREE 10-day trial | Play course overview |
