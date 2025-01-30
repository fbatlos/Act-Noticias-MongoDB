package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import database.ConexionMongo
import model.Cliente
import org.bson.types.ObjectId

class ClienteService(val coll : MongoCollection<Cliente>) {

    fun insertarCliente(cliente: Cliente) {

        val filtros = Filters.or(
            Filters.eq("_id", cliente._id),
            Filters.eq("nick",cliente.nick)
        )

        val userExist = coll.find(filtros)

        if (userExist.count() == 0) {
            coll.insertOne(cliente)
        }else{
            println("El usuario ya existe.")
        }


    }

    fun deleteCliente(email:String) {
        val filtro = Filters.eq("_id", email)

        val elimido = coll.findOneAndDelete(filtro)

        if (elimido != null) {
            println("el usuario eliminado es ${elimido.nombre}")
        }else{
            println("El usuario no existe.")
        }
    }

}