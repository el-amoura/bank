
# Simple Data Warehouse Application

This is a simple Java with Spring Boot application that generates reports on existing data.
It exposes a simple API where you can run a query on the existing test values and expect a result.
The test data is stored on a remote MySQL database.

The test values were extracted from [here](http://adverity-challenge.s3-website-eu-west-1.amazonaws.com/PIxSyyrIKFORrCXfMYqZBI.csv) and inserted into MySQL.

You can test the API [here](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com:8090/api/swagger-ui.html#/data-warehouse-controller#/).
In this link you will find a Swagger interface for easy testing, but if you want to call the API you can do so with this [link](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com/).

# Accessing Swagger interface

- Go to the swagger link [here](http://ec2-18-156-33-147.eu-central-1.compute.amazonaws.com:8090/api/swagger-ui.html#/).
- Click on `data-warehouse-controller` button
- Now you will see the available API methods
-- `/addOne` : used to add one entry in the database
-- `/get`: used to retrieve a list of entries for a given data source and campaign
-- `/queryData`: used for running different types of queries on the available test data.

# Calling the available methods with Swagger
- In the Swagger interface click on the method you want to test
- Click on the `Try it out` button on the right
- Enter the required parameters and hit the `execute` button on the bottom
- The result is displayed bellow

# Calling the `queryData` method

To make a test call with Swagger, post the request JSON in the `body` field.
The request should look like this:

`{`
  `"campaign": "Adventmarkt Touristik",`
  `"dataSource": "Google Ads",`
  `"endDate": "12/24/19",`
  `"metricType": "CLICKS",`
  `"queryPerDay": false,`
  `"startDate": "11/12/19"`
`}`

If the request is successful you will get a JSON response like this:

`{`
  `"metricType": "CLICKS",`
  `"queryPerDay": false,`
  `"dataSource": "Google Ads",`
  `"campaign": "Adventmarkt Touristik",`
  `"startDate": [`
  `  2019,`
  `  11,`
  `  12`
  `],`
  `"endDate": [`
  `  2019,`
  `  12,`
  `  24`
  `],`
  `"totalResult": "4793",`
  `"perDayResult": null`
`}`

The response contains details about the request and a `"totalResult": "4793",` that represents the data we searched for.
Note that we also have a `"perDayResult": null`. These two types of responses are populated depending on the `"queryPerDay"` field in the request. Because we requested `"queryPerDay": false` we got a result that represented the total amount for the given period.

Test example where `"queryPerDay"` is true:
Request:
`{`
  `"campaign": "Adventmarkt Touristik",`
  `"dataSource": "Google Ads",`
  `"endDate": "11/13/19",`
  `"metricType": "CLICKS",`
  `"queryPerDay": true,`
  `"startDate": "11/12/19"`
`}`

Response:

`{`
`  "metricType": "CLICKS",`
`  "queryPerDay": true,`
`  "dataSource": "Google Ads",`
`  "campaign": "Adventmarkt Touristik",`
`  "startDate": [`
`    2019,`
`    11,`
`    12`
`  ],`
`  "endDate": [`
`    2019,`
`    11,`
`    13`
`  ],`
`  "totalResult": null,`
`  "perDayResult": {`
`    "2019-11-12": "7",`
`    "2019-11-13": "16"`
`  }`
`}`

Now the response contains `"perDayResult": {
    "2019-11-12": "7",
    "2019-11-13": "16"
  }` because we requested the total amount of CLICKS for the given data source and campaign, in the specified time range.
  
The only mandatory field is `metricType` so the request can look like this:
`{`
  `"metricType": "CLICKS"`
`}`
and you would receive the total amount of CLICKS in the database for all data sources and campaigns.

# Available metric types
There a three possible metric types:
    -- `CLICKS`
    -- `IMPRESSIONS`
    -- `CTR`
    

   

