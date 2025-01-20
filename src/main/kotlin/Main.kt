import database.ConexionBD
import model.Juego
import service.JuegosApp
import java.util.*

fun main() {
    while (true) {
        println("1. Listar juegos")
        println("2. Buscar juegos por género")
        println("3. Registrar nuevo juego")
        println("4. Eliminar juegos por género")
        println("5. Modificar juego")
        println("6. Salir")
        print("Elige una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                val juegos = JuegosApp().listarJuegos()
                juegos.forEach { println(it) }
            }
            2 -> {
                print("Introduce el genero: ")
                val genero = readLine() ?: ""
                val juegos = JuegosApp().buscarPorGenero(genero)
                juegos.forEach { println(it) }
            }
            3 -> {
                print("Introduce el titulo: ")
                val titulo = readLine() ?: ""
                print("Introduce el genero: ")
                val genero = readLine() ?: ""
                print("Introduce el precio: ")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0

                //print("Introduce la fecha de lanzamiento: ")
                val fecha = Date()

                val mensaje = JuegosApp().registrarJuego(Juego(titulo, genero, precio, fecha))
                println(mensaje)
            }
            4 -> {
                print("Introduce el genero a eliminar: ")
                val genero = readLine() ?: ""
                val mensaje = JuegosApp().eliminarPorGenero(genero)
                println(mensaje)
            }
            5 -> {
                print("Introduce el título del juego a modificar: ")
                val titulo = readLine() ?: ""
                print("Introduce el nuevo género: ")
                val genero = readLine() ?: ""
                print("Introduce el nuevo precio: ")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0
                //print("Introduce la nueva fecha de lanzamiento: ")
                val fecha = Date()
                val mensaje = JuegosApp().modificarJuego(titulo, Juego(titulo, genero, precio, fecha))
                println(mensaje)
            }
            6 -> {
                ConexionBD.close()
                println("Saliendo...")
                break
            }
            else -> println("Opción no válida")
        }
    }
}
