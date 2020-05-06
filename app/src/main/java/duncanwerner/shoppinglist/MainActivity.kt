// Duncan Werner
// CMP SCI 5020 Project 3
// This is a shopping list app

package duncanwerner.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var listModel: MyListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listModel = ViewModelProvider(this).get(MyListModel::class.java)
        val listFragment = ListFragment()

        if (supportFragmentManager.backStackEntryCount < 1) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, listFragment, "TAG")
            transaction.commit()
        }
    }
}
