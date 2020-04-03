package com.example.homoma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragmentManager = supportFragmentManager
    private val homeFragment = HomeFragment()
    private val transactionsFragment = TransactionsFragment()
    private val categoriesFragment = CategoriesFragment()
    private val settingsFragment = SettingsFragment()
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Display First Fragment initially */
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, homeFragment)
        fragmentTransaction.commit()

        /* Display the toolbar and drawer */
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.nav_home ->{
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, homeFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_transactions ->{
                Toast.makeText(this, "Transactions", Toast.LENGTH_SHORT).show()

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, transactionsFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_categories ->{
                Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show()

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, categoriesFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_settings ->{
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, settingsFragment)
                fragmentTransaction.commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
