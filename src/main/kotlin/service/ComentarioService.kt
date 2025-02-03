package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import model.Cliente
import model.Comentario
import model.Noticia
import utils.Log

class ComentarioService(val collComentarios: MongoCollection<Comentario>, val collCliente: MongoCollection<Cliente>, val collNoticia:MongoCollection<Noticia>) {

    private val ruta = "ComentarioService"

//Al insertar un comentario comprobamos si el usuario existe y este activo, tras eso buscamos si la noticia existe, traseso insertaremos el comentario deseado
    fun insertarComentario(comentario: Comentario, usuario: String, noticia: String) {

        val filtroUser = Filters.eq("nick",usuario)

        val usercoment = collCliente.find(filtroUser).firstOrNull()

        if (usercoment != null && usercoment?.estado == true) {
            val noticiaComent = collNoticia.find(Filters.eq("titulo", noticia)).firstOrNull()

            if (noticiaComent != null) {

                if (comentario.comentario.isNotEmpty()) {
                    comentario.user = usercoment.nick

                    comentario.noticia = noticiaComent.titulo

                    collComentarios.insertOne(comentario)

                    println("$comentario se ha registrado correctamente.")
                    Log.escribir(listOf("[GOOD]",ruta,"$comentario se ha registrado correctamente."))
                }else{
                    println("No se puede subir un comentario vacio.")
                    Log.escribir(listOf("[ERROR]",ruta,"No se puede subir un comentario vacio."))
                }
            }else{
                println("No existe la noticia.")
                Log.escribir(listOf("[ERROR]",ruta,"No existe la noticia."))
            }

        }else if(usercoment?.estado == false){
            println("El usuario esta inactivo o baneado.")
            Log.escribir(listOf("[ERROR]",ruta,"El usuario esta inactivo o baneado."))
        }else{
            println("No existe el usuario.")
            Log.escribir(listOf("[ERROR]",ruta,"No existe el usuario."))
        }
    }
//Filtramos por el nombre de la noticia y comprobamos los comentarios a ver si hay o no
    fun getComentariosByNoticia(noticiaId: String): List<Comentario>? {
        val filtroComentario = Filters.eq("noticia",noticiaId)

        val comentarios = collComentarios.find(filtroComentario).toList()

        if (comentarios.isNotEmpty()) {
            Log.escribir(listOf("[GOOD]",ruta,"Se han otorgado ${comentarios.size} comentarios."))
            return comentarios
        }else{
            println("No existe la noticia.")
            Log.escribir(listOf("[ERROR]",ruta,"No existe la noticia."))
            return null
        }
    }

}