Feature:

  Scenario: User checks their vehicle information by registration number
    Given User inputs their car reg on the website
    When User wants to check their car details
    Then User receives details of their car by reg num

#  Scenario: User checks their vehicle information by registration number (but is invalid)
#    Given .
#    When ..
#    Then ...