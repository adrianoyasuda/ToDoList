package yasuda.ifpr.edu.com.br.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import yasuda.ifpr.edu.com.br.todolist.db.AppDatabase
import yasuda.ifpr.edu.com.br.todolist.db.TodoDao
import yasuda.ifpr.edu.com.br.todolist.entities.todo

class MainActivity : AppCompatActivity() {

    lateinit var todoDao: TodoDao
    lateinit var adapter: ArrayAdapter<todo>
    var todoEditing: todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db =
            Room.databaseBuilder(applicationContext,
                AppDatabase::class.java,
                "To_do.db")
                .allowMainThreadQueries()
                .build()
        todoDao = db.todoDao()
        todoDao.getAll()

        bt_save.setOnClickListener { saveToDo() }

        lv_toDo.setOnItemClickListener{ _, _, position, _ ->
            editToDo(getTodoFromList(position))
        }

        bt_remove.setOnClickListener {

            if (todoEditing != null){
                todoDao.remove(todoEditing!!)
            }
            loadData()
        }

        lv_toDo.setOnItemLongClickListener { _, _, position, _ ->
            doneOrNot(getTodoFromList(position))

            true
        }
        loadData()
    }

    private fun getTodoFromList(position : Int) = adapter.getItem(position) as todo

    private fun editToDo(todo: todo) {
        tf_title.setText(todo.title)
        tf_description.setText(todo.description)
        cb_done.isChecked = todo.done
        tf_title.requestFocus()

        todoEditing = todo
    }

    private fun doneOrNot(todo: todo){
        todo.done = !todo.done
        todoEditing = todo
    }

    private fun saveToDo(){
        val title = tf_title.text.toString()
        val description = tf_description.text.toString()
        val done = cb_done.isChecked


        if (todoEditing != null){
            todoEditing?.let { td ->
                td.title = title
                td.description = description
                td.done = done
                todoDao.update(td)
            }
        }
        else{
            val td = todo(title, description, done)
            todoDao.insert(td)
        }
        loadData()
    }

    private fun loadData(){
        val td = todoDao.getAll()



        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, td)
        lv_toDo.adapter = adapter

        cleanFields()
    }

    private fun cleanFields(){
        tf_title.text.clear()
        tf_description.text.clear()
        cb_done.isChecked = false
        tf_title.requestFocus()

        todoEditing = null

    }
}