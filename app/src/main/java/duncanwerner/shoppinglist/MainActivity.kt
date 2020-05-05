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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MyListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        listModel = ViewModelProvider(this).get(MyListModel::class.java)
        listModel.allLists.observe(this, Observer { myLists ->
            myLists?.let {adapter.setMyLists(it)}
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, EditListActivity::class.java)
            startActivityForResult(intent, newMyListActivityRequestCode)
        }

        val fabDelete = findViewById<FloatingActionButton>(R.id.fabDelete)
        fabDelete.setOnClickListener {
            listModel.deleteChecked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newMyListActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(EditListActivity.EXTRA_REPLY)?.let {
                val myList = MyList(it)
                listModel.insert(myList)
            }
        } else {
            Toast.makeText(applicationContext, R.string.list_save_error, Toast.LENGTH_LONG).show()
        }
    }
}
