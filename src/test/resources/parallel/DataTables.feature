@Regression @Smoke
Feature: Read the data from excel sheet

  Scenario: Convert the data table to the user define type
    Given The excel file name and location is given as
      | Excel         | Location                                                                                                        | Sheet | Index |
      | TestData.xlsx | C:\\Users\\saura\\eclipse-workspace\\BDDFrameworkProjectTemplate\\src\\test\\resources\\testdata\\TestData.xlsx | data  |     1 |
