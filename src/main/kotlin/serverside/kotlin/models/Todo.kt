package serverside.kotlin.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.dao.*

object Todos : IntIdTable() {
  val text = text("text")
  val done = bool("done").default(false)
}

class Todo(id: EntityID<Int>) : IntEntity(id) {
 companion object : IntEntityClass<Todo>(Todos)
 var text by Todos.text
 var done by Todos.done
}

// ---------- Return Json Template ----------

data class TodoJSON(
  val id: String,
  val text: String,
  val done: Boolean
)

data class TodoJSONArray(val result: MutableList<TodoJSON?>)

data class TodoParams(val text: String, val done: String)