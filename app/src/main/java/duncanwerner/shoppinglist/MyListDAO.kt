package duncanwerner.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface MyListDAO {
    @Query("SELECT * FROM mylist")
    fun getAllLists(): LiveData<List<MyList>>

    @Query("SELECT * FROM mylist WHERE id IS (:id)")
    fun getListByID(id: Int): LiveData<MyList>

    @Query("SELECT * FROM mylist WHERE title IS (:title)")
    fun getListByTitle(title: String): LiveData<MyList>

    @Query("DELETE FROM mylist WHERE id IS (:id)")
    suspend fun deleteListByID(id: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(myData: MyList)

    @Update
    suspend fun update(myList: MyList)

    @Update
    suspend fun updateItem(myItem: MyItem)

    @Query("DELETE FROM mylist")
    suspend fun deleteAll()

    @Query("SELECT * FROM myitem WHERE listID is (:listID)")
    fun getItemsByListID(listID: Int): LiveData<List<MyItem>>

    @Query("SELECT * FROM myitem WHERE id IS (:id)")
    fun getItemByID(id: Int): LiveData<MyItem>

    @Query("DELETE FROM myitem WHERE id IS (:id)")
    suspend fun deleteItemByID(id: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(myData: MyItem)

    @Query("DELETE FROM myitem")
    suspend fun deleteAllItems()
}