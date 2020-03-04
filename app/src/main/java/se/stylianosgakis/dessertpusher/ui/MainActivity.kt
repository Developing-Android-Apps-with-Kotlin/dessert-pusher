package se.stylianosgakis.dessertpusher.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import se.stylianosgakis.dessertpusher.DessertTimer
import se.stylianosgakis.dessertpusher.R
import se.stylianosgakis.dessertpusher.databinding.ActivityMainBinding
import se.stylianosgakis.dessertpusher.model.Dessert

class MainActivity : AppCompatActivity(), LifecycleObserver {
    private var revenue = 0
    private var dessertsSold = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: DessertTimer
    private val dessertList = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = dessertList.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer = DessertTimer()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.dessertButton.setOnClickListener {
            dessertClicked()
        }
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    override fun onStart() {
        super.onStart()
        timer.startTimer()
    }

    override fun onStop() {
        super.onStop()
        timer.stopTimer()
    }

    private fun dessertClicked() {
        updateScore()
        showCurrentDessert()
    }

    private fun updateScore() {
        revenue += currentDessert.price
        binding.revenue = revenue
        binding.amountSold = ++dessertsSold
    }

    private fun showCurrentDessert() {
        val newDessert = dessertList.last { it.startProductionAmount <= dessertsSold }
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> shareProgress()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareProgress() {
        try {
            startActivity(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT, getString(R.string.share_text, dessertsSold, revenue)
                    )
                }
            )
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.sharing_not_available), Toast.LENGTH_LONG
            ).show()
        }
    }
}
