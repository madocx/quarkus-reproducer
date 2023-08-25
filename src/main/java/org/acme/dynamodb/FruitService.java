package org.acme.dynamodb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@ApplicationScoped
public class FruitService {
	
	public final static String FRUIT_NAME_COL = "fruitName";
    public final static String FRUIT_DESC_COL = "fruitDescription";
    public final static String FRUIT_TABLE_NAME = "QuarkusFruits";

    private DynamoDbAsyncTable<Fruit> fruitTable;
    

    @Inject
    FruitService(DynamoDbEnhancedAsyncClient dynamoEnhancedAsyncClient) {
        fruitTable = dynamoEnhancedAsyncClient.table(FRUIT_TABLE_NAME, TableSchema.fromClass(Fruit.class));
    }

    public Uni<Fruit> get(String name) {
        Key partitionKey = Key.builder().partitionValue(name).build();
        return Uni.createFrom().completionStage(() -> fruitTable.getItem(partitionKey))
        		.call(result -> Uni.createFrom().item(logResults(result)));
//        		.invoke(result -> Uni.createFrom().item(logResults(result)));
    }
	
	
	private Object logResults(Object response) {
		String formattedResponse = "";
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			formattedResponse = gson.toJson(response);
		}catch(Exception e) {
			Log.error(e.getMessage(), e);
		}		
		
				
		Log.info(formattedResponse);
		return response;
	}
	
}