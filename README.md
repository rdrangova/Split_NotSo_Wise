Split(NotSo)Wise is a client-server application with functionality similar to that of Splitwise.

Splitwise aims at facilitating the sharing of accounts between friends and roommates and reducing "just buy beer in this hostel" disputes.

Client console application that receives user commands, sends them for processing to the server, accepts its response and provides it to the user in a readable format.

Functional requirements
-> User registration with username and password; Registered users are stored in a file on the server - it serves as a database. Upon stopping and restarting, the server can load already registered users into its memory.

-> Login;

A registered user can:

adds already registered users to the Friend List based on their username. For example:

$ add-friend <username>
creates a group consisting of several already registered users:

$ create-group <group_name> <username> <username> ... <username>
Groups are created by one user, with each group comprising three or more users. You can imagine that "friendly" relationships are a group of two people.

adds the amount paid by him to the obligations of:

another friend from his friend list:
$ split <amount> <username> <reason_for_payment>
a group in which:
$ split-group <amount> <group_name> <reason_for_payment>
gets his status - the amounts he owes his friends and in his groups and the amounts they owe him. For example:

$ get-status
Friends:
* Pavel Petrov (pavel97): Owes you 10 LV

Groups
* 8thDecember
- Pavel Petrov (pavel97): Owes you 25 LV
- Hristo Hristov (ico_h): Owes you 25 LV
- Harry Gerogiev (harryharry): You owe 5 LV
You can preview all groups and friends or only those with "outstanding bills".

The newly entered amount is evenly split between all participants in the group or half if divided by a Friend from a Friend List.

When one user A owes money to another user B, the obligation can only be "repaid" (with the appropriate command) by user B.

$ payed <amount> <username>
For example:

$ get-status
Friends:
* Pavel Petrov (pavel97): Owes you 10 LV
* Hristo Hristov (ico_h): You owe 5 LV

$ payed 5 pavel97
Pavel Petrov (pavel97) payed you 5 LV.
Current status: Owes you 5 LV

$ get-status
Friends:
* Pavel Petrov (pavel97): Owes you 5 LV
* Hristo Hristov (ico_h): You owe 5 LV
When one user A owes user B an amount (for example $ 5), but before returning them to B adds another amount that he paid (eg $ 5), then the amounts due to both are recalculated (the amount due to A will it gets $ 2.50, B still owes nothing but has to charge $ 2.50).

$ get-status
Friends:
* Pavel Petrov (pavel97): Owes you 10 LV
* Hristo Hristov (ico_h): You owe 5 LV

$ split 5 ico_h limes and oranges
Splitted 5 LV between you and Hristo Hristov.
Current status: You owe 2.50 LV

$ get-status
Friends:
* Pavel Petrov (pavel97): Owes you 5 LV
* Hristo Hristov (ico_h): You owe 2.50 LV
Each time a user logs on to the system, he is notified if his friends have added amounts or "repaid" debts. For example:

$ login alex alexslongpassword
Successful login!
No notifications to show.
or

$ login alex alexslongpassword
Successful login!
*** Notifications ***
Friends:
Misho approved your payment 10 LV [Mixtape beers].

Groups:
* Roomates:
You owe Gery 20 LV [Tanya Bday Present].

* Family:
You owe Alex 150 LV [Surprise trip for mom and dad]
The user can see a history of payments made by him. This history is stored in a file on the server.

Non-functional requirements
The server can serve multiple users in parallel.
Error messages
If used incorrectly, appropriate error messages should be displayed to the user.
