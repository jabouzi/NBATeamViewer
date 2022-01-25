package com.skanderjabouzi.nbateamviewer.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.databinding.ActivityMainBinding
import com.skanderjabouzi.nbateamviewer.domain.helpers.ConnectionType.*
import com.skanderjabouzi.nbateamviewer.presentation.action
import com.skanderjabouzi.nbateamviewer.presentation.snack
import com.skanderjabouzi.nbateamviewer.presentation.util.ConnectivityLiveData
import com.skanderjabouzi.nbateamviewer.presentation.util.ConnectivityLiveData.Companion.STATE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

  private lateinit var navigationController: NavController
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    navigationController = findNavController(R.id.navigationHostFragment)
    val connectivityLiveData = ConnectivityLiveData((application))
    connectivityLiveData.observe(this, Observer<Boolean>(){
      when (it) {
        true -> if (STATE == NOT_CONNECTED) {
          showMessage(getString(R.string.connection_on))
          STATE = CONNECTED
        }
        false -> {
          showMessage(getString(R.string.no_connection))
          STATE = NOT_CONNECTED
        }
      }
    })
  }

  private fun showMessage(message: String) {
    binding.mainLayout.snack(message, Snackbar.LENGTH_INDEFINITE) {
      action(getString(R.string.ok)) {}
    }
  }

  override fun onBackPressed() {}

  companion object {
    fun getIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }

}
