package kenharpe.iu.finalproject

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kenharpe.iu.finalproject.model.Food
import kenharpe.iu.finalproject.model.Restaurant
import kenharpe.iu.finalproject.model.User
import java.time.Instant
import java.util.Date

class GlobalViewModel : ViewModel() {
    private var auth: FirebaseAuth

    private val storageReference = Firebase.storage("gs://final-project-e5c60.appspot.com").reference // Where images will get stored (should I get there)

    // Log in Data
    private val _currentUser = MutableLiveData(User())
    val currentUser: LiveData<User> get() = _currentUser

    private val _authState = MutableLiveData<AuthState>(AuthState.NOTSIGNEDIN)
    val authState: LiveData<AuthState> get() = _authState

    private val _profilePicture = MutableLiveData<String>()
    val profilePicture: LiveData<String> get() = _profilePicture

    private val _selectedPicture = MutableLiveData<Uri?>()
    val selectedPicture: LiveData<Uri?> = _selectedPicture

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    // Homescreen Livedata
    private val _favoriteRestaurants = MutableLiveData(mutableListOf<Restaurant>())
    val favoriteRestaurants: LiveData<List<Restaurant>> = _favoriteRestaurants as LiveData<List<Restaurant>>

    private val _allRestaurants = MutableLiveData(mutableListOf<Restaurant>())
    val allRestaurants: LiveData<List<Restaurant>> get() = _allRestaurants as LiveData<List<Restaurant>>


    // Single Restaurant Livedata
    private val _currentlySelectedRestaurant = MutableLiveData<Restaurant>()
    val currentlySelectedRestaurant : LiveData<Restaurant> get() = _currentlySelectedRestaurant

    private val _restaurantImages = MutableLiveData(mutableListOf<String>())
    val restaurantImages : LiveData<List<String>> get() = _restaurantImages as LiveData<List<String>>

    private val _restaurantMenuOptions = MutableLiveData(mutableListOf<Food>())
    val restaurantMenuOptions : LiveData<List<Food>> get() = _restaurantMenuOptions as LiveData<List<Food>>


    // Order Live Data
    private val _foodChoices = MutableLiveData(mutableMapOf<Food, Int>())
    val foodChoices : LiveData<Map<Food, Int>> get() = _foodChoices as LiveData<Map<Food, Int>>


    // Navigation Live Data
    private val _goToHomeScreen = MutableLiveData<Boolean>()
    val goToHomeScreen: LiveData<Boolean> get() = _goToHomeScreen

    private val _goToOrderScreen = MutableLiveData<Boolean>()
    val goToOrderScreen: LiveData<Boolean> get() = _goToOrderScreen

    private val _goToCheckoutScreen = MutableLiveData<Boolean>()
    val goToCheckoutScreen: LiveData<Boolean> get() = _goToCheckoutScreen

    //Database Collection References
    private lateinit var userCollection: CollectionReference
    private lateinit var orderCollection: CollectionReference
    private lateinit var restaurantCollection: CollectionReference


    init {
        auth = Firebase.auth
        setUpDatabase()
    }

    fun subtractFoodItem(item: Food)
    {
        if (_foodChoices.value?.get(item) != null)
        {
            var newValue = _foodChoices.value!![item]!! - 1
            if (newValue <= 0)
            {
                _foodChoices.value!!.remove(item)
            }
            else
            {
                _foodChoices.value!![item] = newValue
            }
        }
    }

    fun addFoodItem(item: Food)
    {
        if (_foodChoices.value?.get(item) != null)
        {
            _foodChoices.value!![item] = _foodChoices.value!![item]!! + 1
        }
        else
        {
            _foodChoices.value!![item] = 1
        }
    }

    fun onRestaurantClicked(restaurantID: String)
    {
        val restaurantQuery = restaurantCollection.document(restaurantID).addSnapshotListener { value, error ->
            val possibleRest = value?.toObject(Restaurant::class.java)
            if (possibleRest != null)
            {
                _currentlySelectedRestaurant.value = possibleRest!!
                _restaurantImages.value = possibleRest.pictureList
                _restaurantMenuOptions.value = possibleRest.menu
            }
        }
    }

    private fun setUpDatabase() {
        val database = Firebase.firestore
        userCollection = database.collection("users")
        restaurantCollection = database.collection("restaurants")

        val restaurantQuery = restaurantCollection.get().addOnSuccessListener { snapshot ->
            if (snapshot.documents.isNotEmpty())
            {
                for (document in snapshot.documents)
                {
                    _allRestaurants.value?.add(document.toObject(Restaurant::class.java)!!)
                }
            }
        }
    }

    fun updateName(name: String) {
        _currentUser.value!!.name = name
    }

    fun updateEmail(email: String) {
        _currentUser.value!!.email = email
    }

    fun updatePassword(newPassword: String) {
        _currentUser.value!!.password = newPassword
    }

    fun trySignIn()
    {
        val thisUser = currentUser.value!!
        if (thisUser.email.isEmpty() || thisUser.password.isEmpty()) {
            _errorMessage.value = "Invalid email and/or password submission"
            return
        }
        auth.signInWithEmailAndPassword(thisUser.email, thisUser.password)
            .addOnSuccessListener {
                val userQuery = userCollection.whereEqualTo("id", auth.currentUser!!.uid)
                userQuery.get().addOnSuccessListener {
                    _currentUser.value = it.documents[0].toObject(User::class.java)!!
                    _authState.value = AuthState.SIGNEDIN
                    _goToHomeScreen.value = true
                }
            }
            .addOnFailureListener {
                auth.createUserWithEmailAndPassword(currentUser.value!!.email, currentUser.value!!.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            thisUser.id = it.result.user!!.uid
                            thisUser.dateAccountCreated = Date.from(Instant.now())
                            thisUser.recentOrders = listOf()
                            saveProfilePicture(thisUser.id)
                            userCollection.add(currentUser).addOnCompleteListener {
                                trySignIn()
                            }
                        }
                    }
            }
    }

    fun signOut()
    {
        _authState.value = AuthState.NOTSIGNEDIN
        _currentUser.value = User()
    }

    fun saveProfilePicture(userID: String) {
//        val fileReference = storageReference.child("ProfilePictures/$userID/profile_picture")
//        val uploadPicture = selectedPicture.value ?: Uri.EMPTY
//        val uploadTask = fileReference.putFile(uploadPicture)
    }

}

enum class AuthState
{
    SIGNEDIN, NOTSIGNEDIN
}