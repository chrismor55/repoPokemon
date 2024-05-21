package main;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
public class main {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://chrmor55:ch241105@clusterchristian.txpceyk.mongodb.net/?retryWrites=true&w=majority&appName=ClusterChristian";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
            	MongoDatabase database = mongoClient.getDatabase("Christian");
            	MongoCollection<Document> collection = database.getCollection("Pokemon");
            	 //crearPokemon
                Document newPokemon = new Document ("nombre","Piplup").append("tipo", "Agua");
                collection.insertOne(newPokemon);
                System.out.println("Pokemon insertado");
                //Actualizar pokemon
                collection.updateOne(Filters.eq("nombre", "Piplup"),Updates.set("level", 14));
                System.out.println("Pokemon Actualizado");
                
                //encontrar pokemon
                Document foundPokemon = collection.find(Filters.eq("nombre","tipo")).first();
                if(foundPokemon != null) {
                	System.out.println("Pokemon encontrado "+foundPokemon.toJson());
                }else {
                	System.out.println("No se ha encontrado el pokemon");
                }
                //borrar pokemon
                collection.deleteOne(Filters.eq("nombre","Piplup"));
                System.out.println("Pokemon borrado");
                
                Document deletePokemon = collection.find(Filters.eq("nombre","Piplup")).first();
                if(deletePokemon != null) {
                	System.out.println("El pokemon todavia existe "+ deletePokemon.toJson());
                }else {
                	System.out.println("Se elimino correctamente");
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
