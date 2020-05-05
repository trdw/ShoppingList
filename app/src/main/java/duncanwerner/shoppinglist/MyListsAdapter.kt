package duncanwerner.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var myLists = emptyList<MyList>() // Cached copy of lists

    inner class MyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listItemView: TextView = itemView.findViewById(R.id.listTitleTextView)
        val listCheckbox: CheckBox = itemView.findViewById(R.id.listCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_layout, parent, false)
        return MyListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        val current = myLists[position]
        holder.listItemView.text = current.title
        holder.listCheckbox.isChecked = current.isChecked
        holder.listCheckbox.setOnClickListener {
            current.isChecked = holder.listCheckbox.isChecked
        }
    }

    internal fun setMyLists(myLists: List<MyList>) {
        this.myLists = myLists
        notifyDataSetChanged()
    }

    override fun getItemCount() = myLists.size
}