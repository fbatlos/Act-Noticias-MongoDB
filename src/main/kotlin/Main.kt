import model.Juego
import service.JuegosApp
import java.util.*

fun main() {

    val juegos = JuegosApp()

    println( juegos.listarJuegos().toString())

    println( juegos.registrarJuego(Juego(titulo = "" , genero = "Musical", precio = 29.99, Date())))

    println( juegos.listarJuegos().toString())
}