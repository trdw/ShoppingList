package duncanwerner.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyListModel(application: Application) : AndroidViewModel(application) {
    private val repository: MyListsRepository
    val allLists: LiveData<List<MyList>>

    private var itemsList: LiveData<List<MyItem>>

    val total : MutableLiveData<Float>

    private var currentListID: Int = 0
    private var currentListIndex: Int = 0

    init {
        val myListDAO = MyDatabase.getDatabase(application, viewModelScope).myListDAO()
        repository = MyListsRepository(myListDAO)
        allLists = repository.allLists
        itemsList = repository.itemsList
        total = MutableLiveData(0f)
    }

    fun insert(myList: MyList) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(myList)
    }

    fun insert(myItem: MyItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(myItem)
        }
    }

    fun deleteChecked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteChecked()
        }
    }

    fun deleteItem(myItem: MyItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(myItem)
        }
    }

    fun setCurrentListID(listID: Int) {
        currentListID = listID
    }

    fun getCurrentListID() = currentListID
    fun getCurrentList() = repository.getListByID(currentListID)
    fun loadItems(myList: MyList) {
        repository.loadItemsByList(myList)
        itemsList = repository.itemsList
        updateTotal()
    }

    fun getItemsList() = itemsList

    fun updateItem(newItem: MyItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(newItem)
        }
    }

    fun updateTotal() {
        var newTotal = 0f
        itemsList.value?.forEach {
            newTotal += + it.price * it.quantity
        }
        total.postValue(newTotal)
    }
}