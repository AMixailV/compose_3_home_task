package ru.mixail_akulov.compose_3_home_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val itemsFlow = MutableStateFlow(generateListItems())

    val stateFlow: Flow<State> = itemsFlow.map { itemsList ->
        State(items = itemsList)
    }

    fun delete(item: Item) {
        viewModelScope.launch {
            val updateItem = item.copy(isDeleting = true)
            updateItem(updateItem)
            delay(2000)
            deleteItem(item)
        }
    }

    private fun updateItem(newItem: Item) {
        val oldItemsList = itemsFlow.value
        val newItemsList = ArrayList(oldItemsList)
        newItemsList[findIndex(newItem)] = newItem
        itemsFlow.value = newItemsList
    }

    private fun deleteItem(item: Item) {
        val oldItemsList = itemsFlow.value
        val newItemsList = ArrayList(oldItemsList)
        newItemsList.removeAt(findIndex(item))
        itemsFlow.value = newItemsList
    }

    private fun findIndex(item: Item): Int {
        return itemsFlow.value.indexOfFirst {it.id == item.id}
    }

    private fun generateListItems(): List<Item> = List(30) {
        Item(
            id = it.toLong(),
            title = "List Item ${it + 1}",
            isDeleting = false
        )
    }

    class State(
        val items: List<Item>
    ) {
        val totalCount: Int get() = items.size
    }
}