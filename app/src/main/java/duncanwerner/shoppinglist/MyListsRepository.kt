package duncanwerner.shoppinglist

import androidx.lifecycle.LiveData

class MyListsRepository (private val myListDAO: MyListDAO) {
    val allLists: LiveData<List<MyList>> = myListDAO.getAllLists()
    var itemsList: LiveData<List<MyItem>> = myListDAO.getItemsByListID(0)

    suspend fun insert(myList: MyList) {
        myListDAO.insert(myList)
    }

    suspend fun insert(myItem: MyItem) {
        myListDAO.insertItem(myItem)
    }

    suspend fun updateItem(myItem: MyItem) {
        myListDAO.updateItem(myItem)
    }

    suspend fun deleteChecked() {
        allLists.value?.forEach {
            if (it.isChecked) {
                myListDAO.deleteListByID(it.id)
            }
        }
    }

    suspend fun deleteItem(myItem: MyItem) {
        myListDAO.deleteItemByID(myItem.id)
    }

    fun loadItemsByList(myList: MyList) {
        itemsList = myListDAO.getItemsByListID(myList.id)
    }

    fun getListByTitle(title: String) = myListDAO.getListByTitle(title)

    fun getListByID(id: Int) = myListDAO.getListByID(id)

}