package serverside.kotlin.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

import serverside.kotlin.models.Todo
import serverside.kotlin.models.Todos

import serverside.kotlin.models.TodoJSON
import serverside.kotlin.models.TodoJSONArray

data class TodoInfo(val message: String)

class TodoController {
  fun todoIndex(): TodoJSONArray {
    val todos = transaction {
      var todoList = Todo.all().toList()
      var todos: MutableList<TodoJSON?> = mutableListOf()

      todoList.forEach {
        todos.add(TodoJSON( 
          id = it.id.toString(),
          text = it.text,
          done = it.done
        ))
      }
      todos
    }

    return TodoJSONArray(todos)
  }

  fun todoCreate(): TodoJSON {
    val todo = transaction {
      var newTodo = Todo.new { text = "test text" }
      TodoJSON(
        id = newTodo.id.toString(),
        text = newTodo.text,
        done = newTodo.done
      )
    }
    return todo
  }

  fun todoUpdate(id: Int): TodoInfo {
    val message = transaction {
      var todo = Todo.findById(id)
      if (todo != null) {
        todo.done = true
        todo.text = "updated test text"
        "Status 20X"
      } else {
        "Status 40X"
      } 
    }
    return TodoInfo(message)
  }

  fun todoDelete(id: Int): TodoInfo {
    val message = transaction {
      var todo = Todo.findById(id)
      if (todo != null) {
        todo.delete()
        "Status 20X"
      } else {
        "Status 40X"
      }
    }

    return TodoInfo(message)
  }
}

