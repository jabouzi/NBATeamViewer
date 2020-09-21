package com.skanderjabouzi.nbateamviewer.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.presentation.action
import com.skanderjabouzi.nbateamviewer.presentation.snack


abstract class BaseActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showMessage(getString(R.string.players_list))
  }

  private fun showMessage(message: String) {
    getWindow().getDecorView().snack(message, Snackbar.LENGTH_INDEFINITE) {
      action(getString(R.string.ok)) {}
    }
  }
}