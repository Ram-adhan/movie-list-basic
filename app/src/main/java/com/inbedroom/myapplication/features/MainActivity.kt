package com.inbedroom.myapplication.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.inbedroom.myapplication.databinding.ActivityMainBinding

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
    }

    private fun uiObserver(uiState: UiState) {
        when (uiState) {
            is UiState.Success -> {
                binding.rvFirstSection.adapter = ItemAdapter(uiState.data)
                binding.rvSecondSection.adapter = ItemAdapter(uiState.data)
            }
            is UiState.Failed -> {
                Toast.makeText(this, "Failed fetch data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}