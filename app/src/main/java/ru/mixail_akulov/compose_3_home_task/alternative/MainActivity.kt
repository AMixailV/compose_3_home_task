package ru.mixail_akulov.compose_3_home_task.alternative

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import com.elveum.elementadapter.simpleAdapter
import ru.mixail_akulov.compose_3_home_task.Item
import ru.mixail_akulov.compose_3_home_task.MainViewModel
import ru.mixail_akulov.compose_3_home_task.databinding.ActivityMainBinding
import ru.mixail_akulov.compose_3_home_task.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = simpleAdapter<Item, ItemBinding> {
            areItemsSame = { oldItem,   newItem -> oldItem.id == newItem.id }
            areContentsSame = { oldItem, newItem -> newItem == oldItem }
            bind {
                titleTextView.text = it.title
                deleteImageView.isVisible = !it.isDeleting
                progressBar.alpha = if (it.isDeleting) 1.0f else 0.0f
            }
            listeners {
                deleteImageView.onClick {
                    viewModel.delete(it)
                }
            }
        }
        binding.itemsRecyclerView.adapter = adapter
        (binding.itemsRecyclerView.itemAnimator as? DefaultItemAnimator)
            ?.supportsChangeAnimations = false

        viewModel.stateFlow
    }
}