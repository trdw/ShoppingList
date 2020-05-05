//package duncanwerner.shoppinglist
//
//import android.content.Context
//import android.os.Bundle
//import android.text.Layout
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.fragment_list.*
//import kotlinx.android.synthetic.main.list_item_layout.*
//
//class ListFragment : Fragment() {
//    private lateinit var listModel: ListModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onAttach(context: Context) {
//        listModel = ViewModelProvider(this.activity!!.viewModelStore, ViewModelProvider.AndroidViewModelFactory(this.activity!!.application))
//            .get(ListModel::class.java)
//        Log.e("TAG", listModel.lists.value?.size.toString())
//        super.onAttach(context)
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        listsView.layoutManager = LinearLayoutManager(activity)
//        listsView.adapter = MyListAdapter {myList: MyList -> myListClicked(myList)}
//    }
//
//    private fun myListClicked(myList: MyList) {
//
//    }
//
//    inner class MyListAdapter (val onClickListener: (MyList) -> Unit): RecyclerView.Adapter<MyListHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
//            val inflater = LayoutInflater.from(activity)
//            val itemView = inflater.inflate(R.layout.list_item_layout, parent, false)
//            return MyListHolder(itemView)
//        }
//
//        override fun onBindViewHolder(holder: MyListHolder, position: Int) {
//            listModel.lists.value?.get(position).let {
//                if (it != null) {
//                    holder.bindMyList(it, onClickListener)
//                }
//            }
//
//        }
//
//        override fun getItemCount(): Int = /*listModel.lists.value!!.size*/ 1
//    }
//
//    inner class MyListHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
//        fun bindMyList(myList: MyList, onClickListener: (MyList) -> Unit) {
//            listItemName.text = myList.name
//            itemView.setOnClickListener { onClickListener(myList) }
//        }
//    }
//
//}
