package pt.goncalo.poc.dynamodb;

import java.util.Optional;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pt.goncalo.poc.dynamodb.entities.TestDocument;
import pt.goncalo.poc.dynamodb.repository.TestRepository;

@SpringBootApplication
public class DynamodbApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(DynamodbApplication.class, args);
	}

	@Autowired
	private TestRepository repository;
	@Autowired
    private AmazonDynamoDB dynamoDB;

	private DynamoDBMapper dynamoDBMapper;
	private static final Logger logger = LoggerFactory.getLogger(DynamodbApplication.class);

	/**
	 * Creates the required tables and executes one query to test
	 * 
	 * @param strings
	 * @throws Exception
	 */


	@Override
	public void run(String... strings) throws Exception {

		dynamoDBMapper = new DynamoDBMapper(dynamoDB);

		// Create the table - this should be done in IaC ( terraform for instance )
		CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(TestDocument.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

		TableUtils.createTableIfNotExists(dynamoDB, tableRequest);

		TestDocument testDocument = new TestDocument();

		testDocument.setOneValue("value");

		testDocument = repository.save(testDocument);

		logger.info("Saved AwsService object: " + new Gson().toJson(testDocument));

		String testDocumentId = testDocument.getId();

		logger.info("AWS Service ID: " + testDocumentId);

		Optional<TestDocument> awsServiceQueried = repository.findById(testDocumentId);

		if (awsServiceQueried.get() != null) {
			logger.info("Queried object: " + new Gson().toJson(awsServiceQueried.get()));
		}

		Iterable<TestDocument> tDocuments = repository.findAll();

		for (TestDocument tDocument : tDocuments) {
			logger.info("List object: " + new Gson().toJson(tDocument));
		}
	}
}