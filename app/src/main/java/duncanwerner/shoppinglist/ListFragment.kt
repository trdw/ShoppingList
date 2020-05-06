package duncanwerner.shoppinglist

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.list_title_dialog.*

class ListFragment : Fragment(), MyListAdapter.OnItemClickListener {
    private lateinit var listModel: MyListModel

    override fun onItemClicked(myList: MyList) {
        listModel.setCurrentListID(myList.id)
        listModel.loadItems(myList)
        val itemFragment = ItemFragment()
        val transaction = this.activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, itemFragment, "TAG")
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listModel = ViewModelProvider(this.activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(this.activity!!.application))
            .get(MyListModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MyListAdapter(this.activity!!, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.activity!!)
        listModel = ViewModelProvider(this.activity!!).get(MyListModel::class.java)
        listModel.allLists.observe(this.activity!!, Observer { myLists ->
            myLists?.let {adapter.setMyLists(it)}
        })

        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                val dialogView = inflater.inflate(R.layout.list_title_dialog, null)
                builder.setView(dialogView)
                    .setTitle(R.string.dialog_title)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                        val newListTitle =
                            dialogView.findViewById<EditText>(R.id.editTitleDialog).text.toString()
                        if (newListTitle.isNotEmpty()) {
                            val newList = MyList(newListTitle)
                            listModel.insert(newList)
                            val toast = Toast.makeText(context, "List added!", Toast.LENGTH_LONG)
                            toast.show()
                        }  else {
                            val toast = Toast.makeText(context, "List not added!", Toast.LENGTH_LONG)
                            toast.show()
                        }
                    })
                    .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener{ dialog, which ->
                        val toast = Toast.makeText(context, "List not added!", Toast.LENGTH_LONG)
                        toast.show()
                    })
                builder.create()
                builder.show()
                }
            }
//            val itemFragment = ItemFragment()
//            val transaction = this.activity!!.supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, itemFragment, "TAG")
//            transaction.addToBackStack(null)
//            transaction.commit()

        val fabDelete = view.findViewById<FloatingActionButton>(R.id.fabDelete)
        fabDelete.setOnClickListener {
            listModel.deleteChecked()
        }
    }

}
