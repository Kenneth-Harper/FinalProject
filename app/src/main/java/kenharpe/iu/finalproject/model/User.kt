package kenharpe.iu.finalproject.model

import java.util.Date

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var dateAccountCreated: Date = Date(),
    var profilePictureURL: String = "",
    var recentOrders: List<String> = listOf()
    )