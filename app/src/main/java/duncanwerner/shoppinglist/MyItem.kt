package duncanwerner.shoppinglist

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = MyList::class,
    parentColumns = ["id"],
    childColumns = ["listID"],
    onDelete = ForeignKey.CASCADE)])
class MyItem(val listID: Int,
             val title: String,
             val quantity: Int,
             val price: Float) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    fun setID(id: Int) {
        this.id = id
    }

    constructor(id: Int, listID: Int, title: String, quantity: Int, price: Float) : this(listID, title, quantity, price) {
        this.id = id
    }
    var isChecked: Boolean = false
}