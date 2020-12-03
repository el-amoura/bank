
# Savings Account Demo Application

This is a simple Java + Spring Boot application that simulates a savings account.
It exposes a simple API where you can add users and create savings accounts for them.
The data is stored on a remote MySQL database.


You can test the API [here](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com:8090/api/swagger-ui.html#/data-warehouse-controller#/).
In this link you will find a Swagger interface for easy testing, but if you want to call the API you can do so with this [link](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com/).

# Accessing Swagger interface

- Go to the swagger link [here](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com:8090/api/swagger-ui.html#/).
You will find two available controllers:
    - user-controller
    - savings-account-controller


- Click on `user-controller` button
- Now you will see the available API methods
-- `/create` : create a new user in the database
-- `/id`: retrieve a user from the database with the user id
-- `/identification`: retrieve a user from the database with the user identification (personal identification number)

# Calling the available methods with Swagger
- In the Swagger interface click on the method you want to test
- Click on the `Try it out` button on the right
- Enter the required parameters and hit the `execute` button on the bottom
- The result is displayed bellow

# Calling the `user/create` method

To make a test call with Swagger, post the request JSON in the `body` field.
The request should look like this:

`{`
  `"email": "test@test.ro",`
  `"firstName": "testF",`
  `"identificationNumber": "1234567890",`
  `"lastName": "testL",`
  `"middleName": "testM"`
`}`

If the request is successful you will get a JSON response like this:

`{`
  `"id": 23342,`
  `"identificationNumber": "1234567890",`
  `"firstName": "testF",`
  `"lastName": "testL",`
  `"middleName": "testM",`
  `"email": "test@test.ro"`
`}`

If there are any validation errors you should see a message in the response body.

The successful response contains the user details entered in the request and the generated user id. We will use this id to call the rest of the api methods.


# Calling the `savings-account-controller`:
- # savings/create
This method will create a savings account for a given user if all validations pass.
Example request: creating a a savings account for the given userId with 0 balance and interest rate.
`{`
  `"balance": 0,`
  `"interestRate": 0,`
  `"userId": 23342`
`}`

Response:
`{`
  `"id": 23343,`
  `"userId": 23342,`
  `"balance": 0,`
  `"interestRate": 0`
`}`

We can now make a call to the deposit method `savings/deposit`
Example request:
`{`
  `"amount": 50,`
  `"userId": 23342`
`}`
Response:
`{`
  `"id": 23343,`
  `"userId": 23342,`
  `"balance": 50,`
  `"interestRate": 0`
`}`

We can retrieve data about the saving account using the method `savings/id/` with the savings id as input or call the method `savings/userId` that takes the userId as input.

- # calling the `savings/withdraw` method:
Example request:
`{`
  `"amount": 20,`
  `"userId": 23342`
`}`
Response:
`{`
  `"id": 23343,`
  `"userId": 23342,`
  `"balance": 30,`
  `"interestRate": 0`
`}`
We have made a successful withdrawal from the user savings account and the response gives us the remaining balance the account has.

There are a few other methods in the controllers that you can test with.



# API limitations:
This is a demo application so there are some things that can be improved:
    -- the user data should contain more identification fields like address or phone
    -- there should be a history with all transactions made by a user
    -- validations are minimal and could result in some errors with out of scope data
    -- there are no unit tests implemented
    -- an authentication system should be implemented for more security
    -- method calls should use a token instead of a user id
