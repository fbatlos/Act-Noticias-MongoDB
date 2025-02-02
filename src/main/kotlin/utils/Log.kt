package utils

import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Log {

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")

    private val logFile: File = File("src/main/resources/log.txt")

    init {
        try {
            if (!logFile.exists()) {
                logFile.createNewFile()
            }
        } catch (e: Exception) {
            println("Error al crear el archivo de log: ${e.message}")
        }
    }


    fun escribir(accion: List<String>) {
        val tiempo = LocalDateTime.now().format(dateTimeFormatter)
        val logEntry = "${accion[0]} ${accion[1]} - $tiempo - ${accion[2]}\n"

        try {
            FileWriter(logFile, true).use { writer ->
                writer.append(logEntry)
            }
        } catch (e: Exception) {
            println("Error al escribir en el log: ${e.message}")
        }
    }
}
