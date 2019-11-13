package pt.goncalo.poc.dynamodb.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * TestDocument
 */
@DynamoDBTable(tableName = "TestDocument")
public class TestDocument {

    private String  id;
    private String oneValue;

    /**
     * @return the id
     */
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String  getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the oneValue
     */
    @DynamoDBAttribute
    public String getOneValue() {
        return oneValue;
    }

    /**
     * @param oneValue the oneValue to set
     */
    public void setOneValue(String oneValue) {
        this.oneValue = oneValue;
    }
    
}