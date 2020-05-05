package duncanwerner.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyListDAO {
    @Query("SELECT * FROM mylist")
    fun getAll(): LiveData<List<MyList>>

    @Query("SELECT * FROM mylist WHERE id IS (:id)")
    fun getListByID(id: Int): LiveData<MyList>

    @Query("DELETE FROM mylist WHERE id IS (:id)")
    suspend fun deleteListByID(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(myData: MyList)

    @Query("DELETE FROM mylist")
    suspend fun deleteAll()
}