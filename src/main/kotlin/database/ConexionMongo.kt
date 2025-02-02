package database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv

object ConexionMongo {
//Aquí realizamos la conexion con el cluster
    private val mongoClient: MongoClient by lazy {
        val dotenv = dotenv()
        val connectString = dotenv["URL_MONGODB_2"]

        MongoClients.create(connectString)
    }
//obtenemos la conexion de la base de datos
    fun getDatabase(bd: String) : MongoDatabase {
        return mongoClient.getDatabase(bd)
    }
//cerramos la conexion una vez todo acabe
    fun close() {
        mongoClient.close()
    }

}