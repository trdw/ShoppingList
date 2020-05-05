package duncanwerner.shoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyListModel(application: Application) : AndroidViewModel(application) {
    private val repository: MyListsRepository
    val allLists: LiveData<List<MyList>>

    init {
        val myListDAO = MyDatabase.getDatabase(application, viewModelScope).myListDAO()
        repository = MyListsRepository(myListDAO)
        allLists = repository.allLists
    }

    fun insert(myList: MyList) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(myList)
    }

    fun deleteChecked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteChecked()
        }
    }

    fun loadItems(listID: Int) {

    }
}