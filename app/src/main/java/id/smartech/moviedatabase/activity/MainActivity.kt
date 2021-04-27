package id.smartech.moviedatabase.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.home.HomeFragment
import id.smartech.moviedatabase.activity.movie.MovieFragment
import id.smartech.moviedatabase.activity.person.PersonFragment
import id.smartech.moviedatabase.activity.tvseries.TvSeriesFragment
import id.smartech.moviedatabase.base.BaseActivity
import id.smartech.moviedatabase.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)

        setNavigationDrawer()

    }

    private fun setNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, bind.drawerLayout, R.string.open, R.string.close)
        bind.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "The Movie DB"

        val homeFragment = HomeFragment()
        val movieFragment =
            MovieFragment()
        val tvFragment = TvSeriesFragment()
        val personFragment =
            PersonFragment()

        currentFragment(homeFragment)

        bind.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    currentFragment(homeFragment)
                    supportActionBar?.title = "Home"
                }
                R.id.nav_movie -> {
                    currentFragment(movieFragment)
                    supportActionBar?.title = "Movie"
                }
                R.id.nav_tv -> {
                    currentFragment(tvFragment)
                    supportActionBar?.title = "TV Series"
                }
                R.id.nav_person -> {
                    currentFragment(personFragment)
                    supportActionBar?.title = "Person / Cast"
                }
            }
            bind.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun currentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment).commit()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}