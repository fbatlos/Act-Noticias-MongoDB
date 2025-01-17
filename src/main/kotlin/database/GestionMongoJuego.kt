package database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv
import model.Juegos

class GestionMongoJuego() {

    fun findAll(): List<Juegos> {

        try {
            val db = ConexionBD.getDatabase("fbatlos")
        }catch (e: Exception){
            println(e)
        }

    }

}

