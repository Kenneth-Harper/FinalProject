package kenharpe.iu.finalproject.model

import java.time.Instant
import java.util.Date

data class Order(
    var userID : String = "",
    var total : Float = 0f,
    var items : MutableList<String> = mutableListOf(),
    var address: String = "",
    var timePlaced: Date = Date.from(Instant.now()),
)