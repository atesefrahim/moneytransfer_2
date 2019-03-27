MoneyTransfer between Accounts API / A Spark Restful API .

Developed using Java 8 and very light framework Spark.

All classes has been analysed using SonarLint tool.
All classes' code has been covered.
All unit test cases have been run and all successfulled.

----------------------------------

Features
Account list/read/creation/update/deletion
Transfer list/read/creation/update

--------------------------------

In-memory database // using map 
Stand-alone jar (no need for a pre-installed container/server)

Requirements
Java 8
Maven

------------------------------
How to run the application

Execute jar for process


java -jar moneytransfer-1.0-SNAPSHOT.jar

The application runs on port 8282

-----------------------------

How to use the application
Accounts /
List accounts;

GET localhost:8282/api/accounts

Result:

"[
{\"accountId\":0,\"name\":\"ibrahim\",\"balance\":1460},
{\"accountId\":1,\"name\":\"halil\",\"balance\":2560},
{\"accountId\":2,\"name\":\"ates\",\"balance\":6964}
]"

Read Account;

GET localhost:8282/api/accounts/1
{"accountId":1,"name":"halil","balance":2560}

Create an account;

POST localhost:8282/api/accounts
{"name": "Einstein","balance": 1250}

 
Update an account;

PUT localhost:8282/api/accounts/0
{"name": "Lenorda", "amount": 360}

Only the values of "name", "balance" can be updated
If one or more value is invalid, the request will fail

Delete an account;

DELETE localhost:8282/api/accounts/0

Transfers

List all transfers;

GET localhost:8282/api/transfers

Response:

"[{\"id\":0,\"toAccountId\":1,\"fromAccountId\":0,\"amount\":23,\"comment\":\"make transfer\",\"status\":\"CREATED\"},
{\"id\":1,\"toAccountId\":2,\"fromAccountId\":0,\"amount\":55,\"comment\":\"make transfer 2\",\"status\":\"CREATED\"}]"



Create a transfer;

POST localhost:8282/api/transfers
{ "toAccountId": "0", "fromAccountId": "1", "amount": "36",  "comment": "Transfer"}

Make a transfer / update

PUT localhost:8282/api/transfers/0

"{\"id\":0,\"toAccountId\":1,\"fromAccountId\":0,\"amount\":23,\"comment\":\"make transfer\",\"status\":\"UPDATED\"}"


A transfer is successfully done if:

The to and from accounts exists. There is a sufficient balance on the from account. 

The transfer status is not FAILED

If one or more of these conditions are not fulfilled, 
1- if amount is equal zero then return -> "Because transfer amount is zero, process can not be proceed. Please check your information!"

2- if amount want to send greater than fromAccount balance then return -> 
"Balance is less than amount you want send. Please check your information!"

3- if receiver(to) account id is null or zero then return -> 
"id that you send money can not be null. Please check your information!"

4- if sender(from) account id is null or zero then return -> 
""sender id can not be null. Please check your information!""
