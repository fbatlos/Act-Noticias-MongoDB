package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import model.Cliente
import model.Noticia
import java.util.Date

class NoticiaService(val collNoticia:MongoCollection<Noticia>, val collCliente: MongoCollection<Cliente>) {

    //Comprobamos si el usuario que redacta la notica existe, tras eso comprobamos si esta activo y si ha añadido algun tagy si toddo esta en orden se insertará
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
//Buscamos la noticia por el nick del usuario
    fun findByNick(nick:String): List<Noticia>? {
        val noticiasNick = collNoticia.find(Filters.eq("user", nick)).toList()

        if (noticiasNick.isNotEmpty()) {
            return noticiasNick
        }else{
            return null
        }
    }
//buscamos las noticas por los tags añadidos
    fun findByTag(tags: List<String>): List<Noticia>? {
        val filtro = Filters.all("tag", tags)

        val noticias  = collNoticia.find(filtro).toList()

        return noticias
    }
//obtenemos las noticias, las ordeno por el atributo fecha_pub y la limito a 10 para cumplir el requisito
    fun findByFecha(): List<Noticia> {
        val noticias = collNoticia.find().sort(Sorts.descending("fecha_pub")).limit(10).toList()
        return noticias
    }

}