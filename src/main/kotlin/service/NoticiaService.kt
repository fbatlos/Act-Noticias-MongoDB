package service

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import model.Cliente
import model.Noticia
import utils.Log
import java.util.Date

class NoticiaService(val collNoticia:MongoCollection<Noticia>, val collCliente: MongoCollection<Cliente>) {

    private val ruta = "NoticiaService"

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
                    Log.escribir(listOf("[GOOD]",ruta,"$noticia se ha publicado perfectamente."))

                }else {
                    if (noticiabd == null) {
                        println("La noticia requiere de todos los campos.")
                        Log.escribir(listOf("[ERROR]",ruta,"La noticia requiere de todos los campos."))
                    }else{
                        println("El titulo está repetido.")
                        Log.escribir(listOf("[ERROR]",ruta,"El titulo está repetido."))
                    }
                }
            }else{
                println("El usuario está baneado.")
                Log.escribir(listOf("[ERROR]",ruta,"El usuario está baneado."))
            }
        }else{
            println("El usuario no es correcto.")
            Log.escribir(listOf("[ERROR]",ruta,"El usuario no es correcto."))

        }
    }

    fun allNoticias(): List<Noticia>? {
        Log.escribir(listOf("[GOOD]",ruta,"Se dieron todas las noticias."))
        return  collNoticia.find().toList()
    }
//Buscamos la noticia por el nick del usuario
    fun findByNick(nick:String): List<Noticia>? {
        val noticiasNick = collNoticia.find(Filters.eq("user", nick)).toList()

        if (noticiasNick.isNotEmpty()) {
            Log.escribir(listOf("[GOOD]",ruta,"Se encontraron ${noticiasNick.count()} noticias del usuario."))
            return noticiasNick
        }else{
            Log.escribir(listOf("[ERROR]",ruta,"No hay noticias de este usuario."))
            return null
        }
    }
//buscamos las noticas por los tags añadidos
    fun findByTag(tags: List<String>): List<Noticia>? {
        val filtro = Filters.all("tag", tags)

        val noticias  = collNoticia.find(filtro).toList()

        Log.escribir(listOf("[GOOD]",ruta,"Se encontraron ${noticias.count()} noticias con el tag."))

        return noticias
    }
//obtenemos las noticias, las ordeno por el atributo fecha_pub y la limito a 10 para cumplir el requisito
    fun findByFecha(): List<Noticia> {
        val noticias = collNoticia.find().sort(Sorts.descending("fecha_pub")).limit(10).toList()

        Log.escribir(listOf("[GOOD]",ruta,"Se encontraron las ${noticias.count()} ultimas noticias."))

        return noticias
    }

}