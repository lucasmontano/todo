package com.lucasmontano.tasks.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lucasmontano.tasks.databinding.FragmentTaskListBinding
import com.lucasmontano.tasks.viewmodels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel: TaskListViewModel by viewModels()

    private lateinit var binding: FragmentTaskListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.lifecycleOwner = viewLifecycleOwner

        subscribeToUiState()

        return binding.root
    }

    private fun subscribeToUiState() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            uiState.tasks.forEach {
                Log.d(TAG, it.title)
            }
        })
    }

    companion object {

        val TAG: String = TaskListFragment::class.java.simpleName
    }
}
