# Use Cases Delfinen

## Create Member

1. The system prompts the user a unique ID

   a. If nothing is entered, the system assigns a unique id
2. system prompts for name, everything is accepted

3. The system prompts for gender

4. The system prompts for a birthdate

   a. Gives junior or senior membership
5. The system prompts for an email and phone

6. The system prompts for competitiveness level (Casual or competitor)

7. The system assigns a creation date

8. The system updates the member's file/DB

## View Member info

1. The user must choose which member he wants to view

   a. The User searches on ID or name

    - If the user searches on ID, and it exists, the user is directed to the "member view"
    - If there is more than one match, the matches are displayed, and the user must choose by picking a corresponding
      number

2. The system displays member information (Name, Age, etc.)

3. The system displays different options

   a. Update Member info

   b. Anonymize the member

## Update Member info

1. The system displays "Update member" options

   a. Everything except ID is displayed

2. The user chooses what he/she wants to change

3. The user gets directed to the "Change" menu

4. The user is prompted if he/she is sure that they want to commit the change

   a. If yes, the information is updated and saved to the file

   b. If not, the user is redirected back to the "members options" menu

## Update Payments

1. The user receives a list with paid memberships, and the list contains a Member ID

2. The user verifies that everything looks OK

   a. If everything looks OK, the user can accept, and the memberships are updated

   b. If something is not OK, the file should be moved to a backup file with a prefix like "error" + timestamp, and this
   must be handled manually by the finance department

## Income Predictions

1. The system displays the income pr. Month the next 12 months; it shows a total as well.

## Expiring memberships

1. The system displays a list of passing memberships for the next 30 days

2. The user can choose to add or remove members from the list

3. The user gets prompted if he wishes to send a request for renewal of membership

   a. If yes, the system generates a file containing the member ID for the finance department

   b. If no, the user is directed to the main menu

## Create Competition

1. The user gets prompted for competition Start date, name, and time

## View Competition

1. Same logic as view member but for competitions

## Register competition results

1. The user gets prompted for user ID

2. The user chooses discipline

   a. The user decides distance based on discipline

3. User enters time

4. The user gets prompted if he wishes to add another time and field for the same user

   a. If yes, repeat 2-4 from above

   b. If no, the user gets redirected to the competition’s menu

## Display top 5

1. User chooses discipline

   a. User chooses distance
2. The system displays the top 5 within the different splits
