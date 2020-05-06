package duncanwerner.shoppinglist

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemFragment : Fragment(), MyItemAdapter.OnItemClickListener, MyItemAdapter.OnItemCheckboxClickedListener {
    private lateinit var listModel: MyListModel

    override fun onItemClicked(myItem: MyItem) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.add_item_dialog, null)
            dialogView.findViewById<EditText>(R.id.itemNameEditText).setText(myItem.title)
            dialogView.findViewById<EditText>(R.id.itemQuantityEditText).setText(myItem.quantity.toString())
            dialogView.findViewById<EditText>(R.id.itemPriceEditText)
                .setText(myItem.price.toString())
            builder.setView(dialogView)
                .setTitle(R.string.dialog_item_details)
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                    val newItemName =
                        dialogView.findViewById<EditText>(R.id.itemNameEditText).text.toString()
                    val newItemQuantity =
                        dialogView.findViewById<EditText>(R.id.itemQuantityEditText).text.toString()
                            .toInt()
                    val newItemPrice =
                        dialogView.findViewById<EditText>(R.id.itemPriceEditText).text.toString()
                            .toFloat()
                    if (newItemName.isNotEmpty()) {
                        val newItem = MyItem(myItem.id,
                            listModel.getCurrentListID(),
                            newItemName,
                            newItemQuantity,
                            newItemPrice
                        )
                        listModel.updateItem(myItem, newItem)
                        val toast = Toast.makeText(context, "Item updated!", Toast.LENGTH_LONG)
                        toast.show()
                    } else {
                        val toast = Toast.makeText(context, "Item not updated!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                })
                .setNegativeButton(
                    R.string.delete,
                    DialogInterface.OnClickListener { dialog, which ->
                        listModel.deleteItem(myItem)
                        val toast = Toast.makeText(context, "Item deleted!", Toast.LENGTH_LONG)
                        toast.show()
                    })
                .setNeutralButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, which ->
                        val toast = Toast.makeText(context, "Item not added!", Toast.LENGTH_LONG)
                        toast.show()
                    })
            builder.create()
            builder.show()
        }
    }

    override fun onItemCheckboxClicked(myItem: MyItem) {
        listModel.updateItem(myItem, myItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listModel = ViewModelProvider(this.activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(this.activity!!.application))
            .get(MyListModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewItemList)
        val adapter = MyItemAdapter(this.activity!!, this, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.activity!!)
        listModel.getCurrentList().observe(this.activity!!, Observer { myList ->
            myList?.let {view.findViewById<TextView>(R.id.editTitle).setText(myList.title)}
        })

        listModel.total.observe(this.activity!!, Observer {
            it?.let {view.findViewById<TextView>(R.id.listTotal).text = "Total: $it"}
        })
        listModel.getItemsList().observe(this.activity!!, Observer {itemList ->
            itemList?.let {adapter.setMyItems(it)}
        })

        val fabAddItem = view.findViewById<FloatingActionButton>(R.id.fabAddItem)
        fabAddItem.setOnClickListener {
            activity?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = requireActivity().layoutInflater
                val dialogView = inflater.inflate(R.layout.add_item_dialog, null)
                builder.setView(dialogView)
                    .setTitle(R.string.dialog_item_details)
                    .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                        val newItemName =
                            dialogView.findViewById<EditText>(R.id.itemNameEditText).text.toString()
                        val newItemQuantity =
                            dialogView.findViewById<EditText>(R.id.itemQuantityEditText).text.toString().toInt()
                        val newItemPrice =
                            dialogView.findViewById<EditText>(R.id.itemPriceEditText).text.toString().toFloat()
                        if (newItemName.isNotEmpty()) {
                            val newItem = MyItem(listModel.getCurrentListID(), newItemName, newItemQuantity, newItemPrice)
                            listModel.insert(newItem)
                            val toast = Toast.makeText(context, "Item added!", Toast.LENGTH_LONG)
                            toast.show()
                        }  else {
                            val toast = Toast.makeText(context, "Item not added!", Toast.LENGTH_LONG)
                            toast.show()
                        }
                    })
                    .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener{ dialog, which ->
                        val toast = Toast.makeText(context, "Item not added!", Toast.LENGTH_LONG)
                        toast.show()
                    })
                builder.create()
                builder.show()
            }
        }
    }
}
