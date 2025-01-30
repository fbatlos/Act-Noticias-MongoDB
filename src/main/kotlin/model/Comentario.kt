package model

import org.bson.types.ObjectId
import java.util.*

data class Comentario(
    val _id: ObjectId,
    var user: String?,
    var noticia: String?,
    val comentario: String,
    val fech_pub: Date,

    )
