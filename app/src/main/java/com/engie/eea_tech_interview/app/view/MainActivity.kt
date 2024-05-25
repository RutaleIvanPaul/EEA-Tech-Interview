package com.engie.eea_tech_interview.app.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit by inject()
    private val viewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right, // Enter animation
                        R.anim.slide_out_left, // Exit animation
                        R.anim.slide_in_left, // Pop enter animation
                        R.anim.slide_out_right// Pop exit animation
                    )
                .replace(R.id.fragment_container, HomeFragment())
                .commit()

        }


    }
}

