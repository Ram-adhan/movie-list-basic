package com.inbedroom.myapplication.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.inbedroom.myapplication.databinding.ActivityMainBinding
import com.willowtreeapps.hyperion.core.Hyperion
import com.willowtreeapps.hyperion.timber.TimberPlugin
import com.willowtreeapps.hyperion.timber.timber.TimberLogTree
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvFirstSection.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvSecondSection.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.uiState.observe(this, this::uiObserver)
        viewModel.getMovies()
        binding.fab.setOnClickListener {
            Hyperion.open(this)
        }
    }

    private var isSnap = false

    private fun onItemClick(pos: Int) {
        Timber.d("On click item, pos: $pos")
        if (pos == 0) {
            isSnap = !isSnap
            binding.fab.setSnapToEdge(isSnap)
        } else
            throw IllegalArgumentException("just a test")
    }

    private fun uiObserver(uiState: UiState) {
        when (uiState) {
            is UiState.Success -> {
                binding.rvFirstSection.adapter = ItemAdapter(uiState.data).apply {
                    listener = this@MainActivity::onItemClick
                }
                binding.rvSecondSection.adapter = ItemAdapter(uiState.data).apply {
                    listener = this@MainActivity::onItemClick
                }
            }
            is UiState.Failed -> {
                Toast.makeText(this, "Failed fetch data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}