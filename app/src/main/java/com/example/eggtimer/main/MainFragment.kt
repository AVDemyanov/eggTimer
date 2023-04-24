package com.example.eggtimer.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.eggtimer.R
import com.example.eggtimer.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels{MainViewModel.Factory(requireContext().applicationContext)}
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener { viewModel.onButtonClicked() }
        binding.bottomNavBar.setOnItemSelectedListener { item ->
            viewModel.onItemSelected(item.itemId)
            return@setOnItemSelectedListener true
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                when (state) {
                    is Initialization -> setupInitialization(state)
                    is Running -> setupRunning(state)
                    Done -> setupDone()
                }

            }
        }
    }

    private fun setupDone() {
        with(binding) {
            message.text = getString(R.string.done)
            startBtn.text = getString(R.string.ok_btn_title)
            bottomNavBar.menu.setGroupEnabled(R.id.main_group, true)
        }
    }

    private fun setupRunning(state: Running) {
        val timeString = getString(R.string.time_template, state.time)
        with(binding) {
            message.text = timeString
            startBtn.text = getString(R.string.stop_btn_title)
            bottomNavBar.menu.setGroupEnabled(R.id.main_group, false)
        }
    }

    private fun setupInitialization(state: Initialization) {
        val timeString = getString(R.string.time_template, state.time)
        with(binding) {
            message.text = timeString
            startBtn.text = getString(R.string.start_btn_title)
            bottomNavBar.menu.setGroupEnabled(R.id.main_group, true)
        }

    }
}


