package duncanwerner.shoppinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MyList::class, MyItem::class], version = 1, exportSchema = false)
public abstract class MyDatabase: RoomDatabase() {
    abstract fun myListDAO(): MyListDAO
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase ?= null

        fun getDatabase(context: Context, scope: CoroutineScope): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).addCallback(MyDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class MyDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.myListDAO())
                }
            }
        }

        suspend fun populateDatabase(myListDAO: MyListDAO) {
//            myListDAO.deleteAll()
        }
    }
}
