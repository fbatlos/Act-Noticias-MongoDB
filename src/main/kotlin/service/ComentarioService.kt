package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import model.Cliente
import model.Comentario
import model.Noticia

class ComentarioService(val collComentarios: MongoCollection<Comentario>, val collCliente: MongoCollection<Cliente>, val collNoticia:MongoCollection<Noticia>) {


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

}