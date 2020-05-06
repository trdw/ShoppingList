package duncanwerner.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyItemAdapter internal constructor(
    private val context: Context, val itemClickListener: OnItemClickListener, val itemCheckboxClickedListener: OnItemCheckboxClickedListener
) : RecyclerView.Adapter<MyItemAdapter.MyItemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var myItems = emptyList<MyItem>() // Cached copy of lists

    interface OnItemClickListener {
        fun onItemClicked(myItem: MyItem)
    }

    interface OnItemCheckboxClickedListener {
        fun onItemCheckboxClicked(myItem: MyItem)
    }

    inner class MyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemDetailsView: LinearLayout = itemView.findViewById(R.id.itemDetails)
        val itemNameView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemQuantityView: TextView = itemView.findViewById(R.id.itemQuantityTextView)
        val itemPriceView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.itemCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val itemView = inflater.inflate(R.layout.item_item_layout, parent, false)
        return MyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val current = myItems[position]
        holder.itemDetailsView.setOnClickListener {
            itemClickListener.onItemClicked(current)
        }
        holder.itemNameView.text = current.title
        holder.itemQuantityView.text = "Quantity: ${current.quantity}"
        holder.itemPriceView.text = "Price: ${current.price}"
        holder.itemCheckbox.isChecked = current.isChecked
        holder.itemCheckbox.setOnClickListener {
            current.isChecked = holder.itemCheckbox.isChecked
            itemCheckboxClickedListener.onItemCheckboxClicked(current)
        }
    }

    internal fun setMyItems(myItems: List<MyItem>) {
        this.myItems = myItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = myItems.size
}