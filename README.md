# COVID-19 Peak Detector

[![twiliohackathon](https://img.shields.io/badge/twiliohackathon-dev.to-success)](https://retrolog.io)

![logo](src/main/resources/static/images/flatten-curve.png)

This is a project for the TTwilio x DEV.to community hackathon 2020.

COVID-19 Peak Detector is a Spring Boot application with a DynamoDB store that displays graph information with stats about COVID-19. You can select the country you are interested in and you can create an alert by entering your phone number.

A scheduled process will check the data periodically and will send the alert when a peak is detected.

For simplicity and to avoid unnecessary spam, after sending the alert the process removes it from the database.

## Running locally

Run DynamoDB locally and update application.properties` to point to your local instance.
```
docker run -p 8000:8000 amazon/dynamodb-local
```

More information here https://hub.docker.com/r/amazon/dynamodb-local.

Properties you will need to override.

```
twilio.accountSid=REPLACE_ME
twilio.authToken=REPLACE_ME
twilio.fromPhone=REPLACE_ME
amazon.dynamodb.endpoint = http://localhost:8000/
amazon.aws.accesskey = REPLACE_ME
amazon.aws.secretkey = REPLACE_ME
```

You can the JAR file or use `/gradlew bootRun` to run the project.

## Disclaimer

Please note this project is part of a hackathon, therefore it does not reflect best coding practices necessarily e.g. there are no tests. Please do not use this project as a reference for production code.