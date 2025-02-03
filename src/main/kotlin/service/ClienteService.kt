package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import database.ConexionMongo
import model.Cliente
import org.bson.types.ObjectId
import utils.Log

class ClienteService(val coll : MongoCollection<Cliente>) {

    private val ruta = "ClienteService"

    //Insertaremos a los clientes nuevos cuyo email o nick no existan ya en la bd

    fun insertarCliente(cliente: Cliente) {

        val filtros = Filters.or(
            Filters.eq("_id", cliente._id),
            Filters.eq("nick",cliente.nick)
        )

        val userExist = coll.find(filtros)

        if (userExist.count() == 0) {
            if (cliente._id.isNotEmpty()  && cliente.nick.isNotEmpty()) {
                coll.insertOne(cliente)
                println("Fue regsitrado correctamente.")
                Log.escribir(listOf("[GOOD]",ruta,"$cliente fue registrado."))
            }else{
                println("El email y el nickname son campos obligatorios.")
                Log.escribir(listOf("[ERROR]",ruta,"El email y el nickname son campos obligatorios."))
            }
        }else{
            println("El usuario ya existe.")
            Log.escribir(listOf("[ERROR]",ruta,"El usuario ya existe."))
        }
    }




//Funcion añadida para eliminar usuarios
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