package yasuda.ifpr.edu.com.br.todolist.db

import androidx.room.*
import yasuda.ifpr.edu.com.br.todolist.entities.todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM to_do")
    fun getAll(): List<todo>

    @Query("SELECT * FROM to_do WHERE id = :id LIMIT 1")
    fun findById(id: Int): todo?

    @Query("SELECT * FROM to_do WHERE title LIKE :title AND description LIKE :description")
    fun findByName(title: String, description: String): List<todo>

    @Insert
    fun  insert(todo: todo)

    @Update
    fun  update(todo: todo)

    @Delete
    fun remove(todo: todo)


}