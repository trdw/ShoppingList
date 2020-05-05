package duncanwerner.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditListActivity : AppCompatActivity() {
    private lateinit var editTitleView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)
        editTitleView = findViewById(R.id.edit_title)

        val fabSave = findViewById<FloatingActionButton>(R.id.fab_save)
        fabSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTitleView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val myList = MyList(editTitleView.text.toString())
                replyIntent.putExtra(EXTRA_REPLY, myList.title)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

    companion object {
        const val EXTRA_REPLY = "extra_reply"
    }
}
