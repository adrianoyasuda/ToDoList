package yasuda.ifpr.edu.com.br.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import yasuda.ifpr.edu.com.br.todolist.entities.todo

@Database(entities = arrayOf(todo::class),version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}