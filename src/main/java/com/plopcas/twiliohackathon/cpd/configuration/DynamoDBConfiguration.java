package com.plopcas.twiliohackathon.cpd.configuration;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.plopcas.twiliohackathon.cpd.model.Alert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.plopcas.twiliohackathon.cpd.repository")
public class DynamoDBConfiguration {
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        // check table and create if it does not exist
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Alert.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }
}