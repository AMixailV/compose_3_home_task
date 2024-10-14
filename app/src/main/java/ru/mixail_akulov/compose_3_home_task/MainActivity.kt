package ru.mixail_akulov.compose_3_home_task

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mixail_akulov.compose_3_home_task.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        viewModel.stateFlow
            .flowWithLifecycle(lifecycle)
            .onEach(::renderState)
            .launchIn(lifecycleScope)
    }

    private fun initViews() {
        adapter = MainListAdapter{
            viewModel.delete(it)
        }
        with(binding) {
            (itemsRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
            itemsRecyclerView.adapter = adapter
        }
    }

    private fun renderState(state: MainViewModel.State) {
        adapter.submitList(state.items)
        binding.totalCountTextView.text = getString(R.string.total_count, state.totalCount)
    }
}