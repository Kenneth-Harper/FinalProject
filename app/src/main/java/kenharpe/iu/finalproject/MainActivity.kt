package kenharpe.iu.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import kenharpe.iu.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private  lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private val viewModel: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: NavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)

        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        viewModel.authState.observe(this, Observer {authState ->
            if (authState == AuthState.NOTSIGNEDIN)
            {
                navController.navigate(R.id.loginFragment)
                actionBarDrawerToggle.isDrawerIndicatorEnabled = false
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            else
            {
                actionBarDrawerToggle.isDrawerIndicatorEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

            }
        })

        val headerView = binding.navView.getHeaderView(0)
        viewModel.currentUser.observe(this, Observer {user ->
            val textviewName = headerView.findViewById<TextView>(R.id.textview_HeaderName)
            val textviewEmail = headerView.findViewById<TextView>(R.id.textview_HeaderEmail)

            textviewName.text = user.name
            textviewEmail.text = user.email
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item))
            true
        else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        if (viewModel.authState.value == AuthState.NOTSIGNEDIN)
        {
            navController.navigate(R.id.loginFragment)
        }
        else
        {
            when (item.itemId)
            {
                R.id.menu_item_Home -> navController.navigate(R.id.homeFragment)
                R.id.menu_item_RecentOrders -> navController.navigate(R.id.recentOrdersFragment)
                R.id.menu_item_CalendarView -> navController.navigate(R.id.calendarFragment)
                R.id.menu_item_SignOut -> { viewModel.signOut() }
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}