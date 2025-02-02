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
                val filtroNoticia = Filters.eq("titulo", noticia.titulo)

                val noticiabd = collNoticia.find(filtroNoticia).firstOrNull()

                if (noticia.tag.count() > 0 && noticia.titulo.isNotEmpty() && noticia.cuerpo.isNotEmpty() && noticiabd == null) {

                    noticia.user = usuario.nick
                    collNoticia.insertOne(noticia)
                    println("$noticia se ha publicado perfectamente.")

                }else {
                    if (noticiabd == null) {
                        println("La noticia requiere de todos los campos.")
                    }else{
                        println("El titulo está repetido.")
                    }
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