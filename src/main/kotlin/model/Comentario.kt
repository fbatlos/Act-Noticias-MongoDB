package model

import org.bson.types.ObjectId
import java.util.*

data class Comentario(
    val _id: ObjectId,
    val user: String,
    val noticia: String,
    val comentario: String,
    val fech_pub: Date,

    )
