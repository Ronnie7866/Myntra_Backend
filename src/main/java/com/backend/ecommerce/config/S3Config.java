package com.backend.ecommerce.config;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access.key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 client() {
        if (accessKey == null || secretKey == null || region == null) {
            throw new IllegalArgumentException("AWS credentials and region must be set");
        }

        ClientConfiguration clientConfig = new ClientConfiguration()
                .withConnectionTimeout(5000) // 5 seconds
                .withSocketTimeout(5000) // 5 seconds
                .withMaxConnections(100) // Set max connections
                .withConnectionTTL(30000) // 30 seconds
                .withRetryPolicy(PredefinedRetryPolicies.getDefaultRetryPolicy());

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .withClientConfiguration(clientConfig)
                .build();
    }
}
