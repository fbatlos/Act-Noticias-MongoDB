package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import model.Cliente
import model.Noticia

class NoticiaService(val collNoticia:MongoCollection<Noticia>, val collCliente: MongoCollection<Cliente>) {

    fun insertarNoticia(noticia: Noticia, usuario:String) {
        val filtroUser = Filters.eq("nick",usuario)

        val usuario = collCliente.find(filtroUser).firstOrNull()

        if (usuario != null) {
            if (usuario.estado == true){
                if (noticia.tag.count() > 0){
                    noticia.user = usuario.nick
                    collNoticia.insertOne(noticia)
                    println("$noticia se ha publicado perfectamente.")
                }else {
                    println("No se pueden publicar noticias sin tag.")
                }
            }else{
                println("El usuario está baneado.")
            }
        }else{
            println("El usuario no es correcto.")
        }
    }

    fun allNoticias(): List<Noticia>? {
        return  collNoticia.find().toList()
    }

    fun findByNick(nick:String): List<Noticia>? {
        val noticiasNick = collNoticia.find(Filters.eq("user", nick)).toList()

        if (noticiasNick.isNotEmpty()) {
            return noticiasNick
        }else{
            return null
        }
    }


}