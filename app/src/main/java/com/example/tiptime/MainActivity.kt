package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { Konversi() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun Konversi() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val nilaiUang = stringInTextField.toDoubleOrNull()

        if (nilaiUang == null || nilaiUang == 0.0) {
            displayUang(0.0)
            return
        }

        val mataUang = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.opsi_euro -> 14370
            R.id.opsi_usd -> 14371
            R.id.opsi_yen -> 115
            else -> 3831
        }

        var konversiUang = nilaiUang * mataUang

        displayUang(konversiUang)
    }


    private fun displayUang(tip: Double) {
        val indonesianLocale = Locale("id", "ID")
        val formattedTip = NumberFormat.getCurrencyInstance(indonesianLocale).format(tip)
        binding.tipResult.text = "Nilai Rupiah: $formattedTip"
    }


    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
