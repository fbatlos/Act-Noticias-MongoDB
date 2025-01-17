package model

import java.util.Date

data class Juegos(
    val titulo:String,
    val genero:String,
    val precio:Double,
    val fecha_lanzamiento:Date
)
