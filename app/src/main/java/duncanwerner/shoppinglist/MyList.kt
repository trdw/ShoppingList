package duncanwerner.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MyList(val title: String) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    fun setID(id: Int) {
        this.id = id
    }

    var isChecked: Boolean = false

    var total: Float = 0f
}