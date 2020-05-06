// Duncan Werner
// CMP SCI 5020 Project 3
// This is a shopping list app

package duncanwerner.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listModel: MyListModel
    private val newMyListActivityRequestCode = 1
    private val editMyListActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listModel = ViewModelProvider(this).get(MyListModel::class.java)
        val listFragment = ListFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, listFragment, "TAG")
        transaction.commit()

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == newMyListActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            data?.getStringExtra(EditListActivity.EXTRA_REPLY)?.let {
//                val myList = MyList(it)
//                listModel.insert(myList)
//            }
//        } else {
//            Toast.makeText(applicationContext, R.string.list_save_error, Toast.LENGTH_LONG).show()
//        }
//    }
}
