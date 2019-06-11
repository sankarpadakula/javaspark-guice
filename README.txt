This demo is about a REST API for money transfer.

Requirements
Main application:

Java 8
Maven 3+
Functional tests:

Usage
Package the executable application with:

mvn clean compile package
And execute:

mvn jerry:run
The server is now listening at 
POST: http://localhost:8080/account.
{
    "name":"john",
    "sortCode":"SORT345",
    "accountNumber":"345678",
    "amount":50,
    "message": "testing1"
}

GET http://localhost:8080/accounts?sortcode=SORT123&account=123456

POST: http://localhost:8080/transfer
{
    "from": {
    	"sortCode": "SORT123",
    	"accountNumber": "123456"
    },
    "to": {	
    	"sortCode": "SORT124",
    	"accountNumber": "123457"
    	
    },
    "amount":50,
    "message": "testing1"
}

