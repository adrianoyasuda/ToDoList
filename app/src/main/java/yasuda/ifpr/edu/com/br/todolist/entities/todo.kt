package yasuda.ifpr.edu.com.br.todolist.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do")
data class todo(
    var title: String,
    var description: String,
    var done: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    val titleDescription get() = "${title}: ${description}"

    override fun toString() = titleDescription
}