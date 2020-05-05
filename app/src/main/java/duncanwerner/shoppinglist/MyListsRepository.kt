package duncanwerner.shoppinglist

import androidx.lifecycle.LiveData

class MyListsRepository (private val myListDAO: MyListDAO) {
    val allLists: LiveData<List<MyList>> = myListDAO.getAll()

    //val items: LiveData<List<>>

    suspend fun insert(myList: MyList) {
        myListDAO.insert(myList)
    }

    suspend fun deleteChecked() {
        allLists.value?.forEach {
            if (it.isChecked) {
                myListDAO.deleteListByID(it.id)
            }
        }

    }
}