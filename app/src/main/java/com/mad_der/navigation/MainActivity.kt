package com.mad_der.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mad_der.navigation.databinding.ActivityMainBinding
import com.mad_der.navigation.view.CameraFragment
import com.mad_der.navigation.view.GpsFragment
import com.mad_der.navigation.view.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        navigationInit()
    }

    private fun navigationInit() {
        findViewById<BottomNavigationView>(R.id.navigation).setOnItemSelectedListener {
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = findFragmentByItemId(it.itemId)

            detachAllFragment(fragmentTransaction)
            if (fragment == null) {
                createFragment(fragmentTransaction, it.itemId)
            } else {
                fragmentTransaction.attach(fragment)
            }
            fragmentTransaction.commit()
            true
        }
    }

    private fun findFragmentByItemId(itemId: Int): Fragment? =
        fragmentManager.findFragmentByTag(
            when (itemId) {
                R.id.standard -> FRAGMENT_HOME_TAG
                R.id.location -> FRAGMENT_LOCATION_TAG
                R.id.camera -> FRAGMENT_CAMERA_TAG
                else -> ""
            }
        )

    private fun createFragment(transaction: FragmentTransaction, itemId: Int) {
        when (itemId) {
            R.id.standard -> transaction.add(
                R.id.fragmentSpace,
                HomeFragment(),
                FRAGMENT_HOME_TAG
            )
            R.id.location -> transaction.add(
                R.id.fragmentSpace,
                GpsFragment(),
                FRAGMENT_LOCATION_TAG
            )
            R.id.camera -> transaction.add(
                R.id.fragmentSpace,
                CameraFragment(),
                FRAGMENT_CAMERA_TAG
            )
        }
    }

    private fun detachAllFragment(transaction: FragmentTransaction) {
        fragmentManager.findFragmentByTag(FRAGMENT_HOME_TAG)?.let {
            transaction.detach(it)
        }
        fragmentManager.findFragmentByTag(FRAGMENT_LOCATION_TAG)?.let {
            transaction.detach(it)
        }
        fragmentManager.findFragmentByTag(FRAGMENT_CAMERA_TAG)?.let {
            transaction.detach(it)
        }
    }

    companion object {
        private const val FRAGMENT_HOME_TAG = "home"
        private const val FRAGMENT_LOCATION_TAG = "location"
        private const val FRAGMENT_CAMERA_TAG = "camera"
    }
}