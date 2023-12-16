package kenharpe.iu.finalproject.model

data class Restaurant (
    val id : String = "",
    val name: String = "",
    val pictureList: MutableList<String> = mutableListOf(),
    val menu: MutableList<Food> = mutableListOf(),
    val address: String = ""
)