package utils

import model.Direccion

object Utils {
//Pediremos los numeros de usuario, le he añadido un pequeño filtro para que los telefonos sean algo mas realeas
    fun getTelefonos():List<String>{
        val telefonos = mutableListOf<String>()
        val regex = Regex("^[0-9]{9,15}\$")

        while (true) {
            print("Ingrese un número de teléfono (o escriba 'salir' para finalizar): ")
            val telefono = readLine()
            if (telefono == "salir") {
                break
            } else if (!telefono.isNullOrBlank() && regex.matches(telefono)) {
                telefonos.add(telefono)
                println("Telefono añadido.")
            } else {
                println("Número inválido. Debe contener entre 9 y 15 dígitos sin espacios ni caracteres especiales.")
            }
        }
        return telefonos
    }
//Pediremos la dirección del usuario
    fun getDireccion(): Direccion {
        print("Ingrese la calle: ")
        val calle = readLine().orEmpty()
        print("Ingrese el número: ")
        val num = readLine().orEmpty()
        print("Ingrese el código postal: ")
        val cp = readLine().orEmpty()
        print("Ingrese la ciudad: ")
        val ciudad = readLine().orEmpty()

        return Direccion(calle, num, cp, ciudad)
    }
//Generaremos la lista de tags y hay una opción de verlos por si has añadido muchos
    fun getTags():List<String>{
        val tags = mutableListOf<String>()

        while (true) {
            print("Ingrese un tag (o escriba 'salir' para finalizar o 'ver' o '' para ver todos los tags puestos): ")
            val tag = readLine()

            if (tag == "salir"){
                break
            }else if (tag.isNullOrBlank() || tag == "ver"){
                tags.forEach { println(it) }
            }else {
                tags.add(tag)
            }
        }

        return tags
    }

}