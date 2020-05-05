package duncanwerner.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyItemDAO {
    @Query("SELECT * FROM myitem")
    fun getAll(): LiveData<List<MyItem>>

    @Query("SELECT * FROM myitem WHERE id IS (:id)")
    fun getListByID(id: Int): LiveData<MyItem>

    @Query("DELETE FROM myitem WHERE id IS (:id)")
    suspend fun deleteListByID(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(myData: MyItem)

    @Query("DELETE FROM myitem")
    suspend fun deleteAll()
}