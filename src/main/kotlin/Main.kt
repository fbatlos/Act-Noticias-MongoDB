import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import database.ConexionMongo
import model.Cliente
import model.Comentario
import model.Direccion
import model.Noticia
import org.bson.types.ObjectId
import service.ClienteService
import service.NoticiaService
import java.util.*

fun main() {

    // Abrir la conexión con la BD
    val database = ConexionMongo.getDatabase("adaprueba")

    // Obtener la colección
    val collectionUsuario: MongoCollection<Cliente> = database.getCollection("collUsuarios", Cliente::class.java)

    val collectionNoticias: MongoCollection<Noticia> = database.getCollection("collNoticias", Noticia::class.java)

    val collectionComentario: MongoCollection<Comentario> = database.getCollection("collComentarios", Comentario::class.java)


    val clienteService = ClienteService(collectionUsuario)

   //Insert Realizado   clienteService.insertarCliente(Cliente("paco7777@gmail.com","paco", "paco107",true, listOf(),Direccion("Lopo","123","12344","Poblinas")))

    //Delete Realizado           clienteService.deleteCliente("")


    val noticiaService: NoticiaService = NoticiaService(collectionNoticias, collectionUsuario)

    /*Insert
    noticiaService.insertarNoticia(Noticia(ObjectId(),"6","6",
        Date(),
        listOf("mol"),
        null
    ), "Runcho")

     */





    val noticiasUser = noticiaService.findByFecha()


    var index = 1
    noticiasUser?.forEach { noticia -> println("${index++} - $noticia") }


    ConexionMongo.close()
}


