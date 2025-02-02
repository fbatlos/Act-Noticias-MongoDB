package model

data class Cliente(
    val _id: String,
    val nombre: String,
    val nick: String,
    val estado:Boolean,
    val tlfn: List<String>,
    val direccion: Direccion?
)