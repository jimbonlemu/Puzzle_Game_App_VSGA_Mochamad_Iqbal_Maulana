package com.jimbonlemu.vsga_app_1

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.button.MaterialButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textV: TextView
    private val buttonValues = StringBuilder()
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridLayout = findViewById(R.id.gridLayout)
        textV = findViewById(R.id.view_text)
        restartGame()

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


    }

    private fun restartGame() {
        gridLayout.removeAllViews()
        buttonValues.clear()
        textV.text = ""

        val random = Random()
        val alphabet = ('a'..'z').toList() // Daftar huruf a-z dalam urutan

        val shuffledAlphabet = alphabet.shuffled(random) // Mengacak urutan huruf

        for (i in 0 until 26) {
            val buttonText = shuffledAlphabet[i].toString()

            val button = MaterialButton(this)
            val layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.button_width)
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.button_height)
            layoutParams.setMargins(8, 8, 8, 8)

            button.layoutParams = layoutParams
            button.text = buttonText
            button.textSize = 25f
            gridLayout.addView(button)

            button.setOnClickListener {
                val clickedButtonText = button.text.toString() + " "

                buttonValues.append(clickedButtonText)

                textV.text = buttonValues.toString()

                // Cek apakah nilai pada textV adalah urutan a-z
                if (isAlphabeticalOrder(textV.text.toString())) {
                    showWinDialog()
                }
            }
        }
    }

    private fun isAlphabeticalOrder(text: String): Boolean {
        val sortedText = text.replace(" ", "").toLowerCase().toCharArray()
        sortedText.sort()

        return sortedText.contentEquals(('a'..'z').toList().toCharArray())
    }

    private fun showWinDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("You Win It!")
        dialogBuilder.setMessage("Congratulations! You have arranged the letters in alphabetical order.")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            restartGame()
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showExitDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Peringatan!")
        dialogBuilder.setMessage("Apakah anda yakin ingin keluar dari aplikasi ?")
        dialogBuilder.setPositiveButton("Yes") { _, _ ->
            finish()
        }
        dialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menusbutton, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clean -> {
                restartGame()
                Toast.makeText(this, "Tombol Ulangi diklik", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_out -> {
                showExitDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}