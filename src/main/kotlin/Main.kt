import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import database.ConexionMongo
import model.Cliente
import model.Comentario
import model.Direccion
import model.Noticia
import org.bson.types.ObjectId
import service.ClienteService
import service.ComentarioService
import service.NoticiaService
import utils.Utils
import java.util.*

fun main() {

    // Abrir la conexión con la BD
    val database = ConexionMongo.getDatabase("adaprueba")

    // Obtener la colección
    val collectionUsuario: MongoCollection<Cliente> = database.getCollection("collUsuarios", Cliente::class.java)

    val collectionNoticias: MongoCollection<Noticia> = database.getCollection("collNoticias", Noticia::class.java)

    val collectionComentario: MongoCollection<Comentario> = database.getCollection("collComentarios", Comentario::class.java)


    val clienteService = ClienteService(collectionUsuario)

    val noticiaService: NoticiaService = NoticiaService(collectionNoticias, collectionUsuario)

    val comentarioService = ComentarioService(collectionComentario,collectionUsuario,collectionNoticias)

    while (true) {
        println("\nMenú:")
        println("1. Registrar usuario")
        println("2. Publicar noticia")
        println("3. Escribir comentario")
        println("4. Listar noticias de un usuario")
        println("5. Listar comentarios de una noticia")
        println("6. Buscar noticias por etiqueta")
        println("7. Listar las 10 últimas noticias")
        println("8. Salir")
        print("Seleccione una opción: ")

        try {

            when (readln().toInt()) {
                1 -> {
                    print("Ingrese email del usuario: ")
                    val email = readln()
                    print("Ingrese su nombre completo: ")
                    val nombre = readln()
                    print("Ingrese nick del usuario: ")
                    val nick = readln()
                    val telefonos = Utils.getTelefonos()
                    val direccion = Utils.getDireccion()
                    clienteService.insertarCliente(Cliente(_id = email,nombre,nick,true,telefonos,direccion))
                    println("Fue regsitrado correctamente.")
                }

                2 -> {
                    print("Ingrese el autor: ")
                    val autor = readln()
                    print("Ingrese título de la noticia: ")
                    val titulo = readln()
                    print("Ingrese contenido de la noticia: ")
                    val contenido = readln()

                    val tags = Utils.getTags()

                    val noticia = Noticia(ObjectId(),titulo,contenido,Date(),tags,null)
                    noticiaService.insertarNoticia(noticia,autor)

                }

                3 -> {
                    print("Ingrese el nombre de la noticia: ")
                    val noticia = readln()
                    print("Ingrese nick del usuario: ")
                    val usuario = readln()
                    print("Ingrese el comentario: ")
                    val contenido = readln()

                    val comentario = Comentario(ObjectId(),null,null,contenido, Date())

                    comentarioService.insertarComentario(comentario,usuario,noticia)

                }

                4 -> {
                    print("Ingrese nick del usuario: ")
                    val usuario = readln()
                    val noticias = noticiaService.findByNick(usuario)

                    if (noticias != null) {
                        noticias?.forEach { println(it) }
                    }else{
                        println("No se ha encontrado el usuario.")
                    }
                }

                5 -> {
                    print("Ingrese el nombre de la noticia: ")
                    val noticia = readln()

                    comentarioService.getComentariosByNoticia(noticia)?.forEach { println(it) }
                }

                6 -> {
                    print("Ingrese etiqueta a buscar: ")
                    val tags = Utils.getTags()

                    noticiaService.findByTag(tags)?.forEach { println(it) }
                }

                7 -> noticiaService.findByFecha().forEach { println(it) }
                8 -> {
                    println("Saliendo...")
                    break
                }

                else -> println("Opción inválida")
            }
        }catch (ex:Exception){
            println("[ERROR]:${ex.message}")
        }
    }

    ConexionMongo.close()
}


