package model

import java.util.Date

data class Juego(
    var titulo:String,
    val genero:String,
    val precio:Double,
    val fecha_lanzamiento:Date
){
    override fun toString(): String {
        return "titulo: $titulo , genero: $genero, precio: $precio, fecha_lanzamiento: $fecha_lanzamiento"
    }
}
