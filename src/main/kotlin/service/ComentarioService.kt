package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import model.Cliente
import model.Comentario
import model.Noticia

class ComentarioService(val collComentarios: MongoCollection<Comentario>, val collCliente: MongoCollection<Cliente>, val collNoticia:MongoCollection<Noticia>) {

//Al insertar un comentario comprobamos si el usuario existe y este activo, tras eso buscamos si la noticia existe, traseso insertaremos el comentario deseado
    fun insertarComentario(comentario: Comentario, usuario: String, noticia: String) {

        val filtroUser = Filters.eq("nick",usuario)

        val usercoment = collCliente.find(filtroUser).firstOrNull()

        if (usercoment != null && usercoment?.estado == true) {
            val noticiaComent = collNoticia.find(Filters.eq("titulo", noticia)).firstOrNull()

            if (noticiaComent != null) {
                comentario.user = usercoment.nick

                comentario.noticia = noticiaComent.titulo

                collComentarios.insertOne(comentario)

                println(comentario)
            }else{
                println("No existe la noticia.")
            }

        }else{
            println("No existe el usuario.")
        }
    }
//Filtramos por el nombre de la noticia y comprobamos los comentarios a ver si hay o no
    fun getComentariosByNoticia(noticiaId: String): List<Comentario>? {
        val filtroComentario = Filters.eq("noticia",noticiaId)

        val comentarios = collComentarios.find(filtroComentario).toList()

        if (comentarios.isNotEmpty()) {
            return comentarios
        }else{
            println("No existe la noticia.")
            return null
        }
    }

}