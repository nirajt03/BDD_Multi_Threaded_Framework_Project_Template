@Regression @Smoke
Feature: Search Page functionalities for Pluralsight Clone Application

  Background: 
    Given User launches the Pluralsight Clone Application using link "https://nirajt03.github.io/sample-website/"

  Scenario Outline: Verify Search Page features (TCs-012,TCs-013,TCs-014,TCs-015)
    Given User login as "<Authority>"
    And Search required course in search box as "<Course Name>"
    Then Verify selected filter tabs options
    And Click on Course tab in Nav Bar List
    Then Verify filtered courses list details
    And Validate other tabs details in nav bar list
    Then Logout from Pluralsight clone application

    Examples: 
      | Authority | Course Name                          |
      | Admin     | Java Fundamentals: The Java Language |
      | User      | Java Fundamentals: The Java Language |

 
  Scenario Outline: Verify Search Java Course functionality (TCs-016,TCs-017,TCs-018,TCs-019,TCs-020)
    Given User login as "<Authority>"
    And Search required course in search box as "<Course Name>"
    Then Click on Course tab in Nav Bar List
    Then Verify filtered courses list details
    When Select required course from filtered course list and Move to Course page as "<Course Name>"
    Then Verify selected Course header text as "<Course Header text>"
    Then Verify selected Course description test as "<Course Description text>"
    Then Logout from Pluralsight clone application

    Examples: 
      | Authority | Course Name                          | Course Header text                   | Course Description text                                                                                                                                                                                                                 |
      | Admin     | Java Fundamentals: The Java Language | Java Fundamentals: The Java Language | This course provides thorough coverage of the core Java platform, giving you the skills needed to begin developing in the Java Runtime Environment (JRE) and serving as a solid foundation for all Java-based development environments. |
      | User      | Java Fundamentals: The Java Language | Java Fundamentals: The Java Language | This course provides thorough coverage of the core Java platform, giving you the skills needed to begin developing in the Java Runtime Environment (JRE) and serving as a solid foundation for all Java-based development environments. |
