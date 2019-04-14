/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package serverside.kotlin

import io.ktor.application.*
// import io.ktor.gson.*

import io.ktor.jackson.jackson
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.CORS

import serverside.kotlin.datastore.DatabaseManager
import serverside.kotlin.models.TodoParams
import serverside.kotlin.controller.TodoController

data class Info(val message: String)

fun main(args: Array<String>) {

  val databaseManager = DatabaseManager()
  // Open the connection to database
  databaseManager.Connect()

  // Drop Tables
  // databaseManager.DropTable()

  // Create Tables
  // databaseManager.CreateTable()


  embeddedServer(
    Netty,
    port = Integer.parseInt(System.getenv("PORT") ?: "8080"),
    module = Application::mainModule
  ).start(wait = true)
}

fun Application.mainModule() {
  install(ContentNegotiation) {
    // gson {
    //   setDateFormat(DateFormat.LONG)
    //   setPrettyPrinting()
    // }
    jackson {}
  }
  install(CORS)
  {
    method(HttpMethod.Options)
    anyHost()
  }
  routing {
    root()
  }
}

fun Routing.root() {

  val todoController = TodoController()

  get("/") {
    val response = Info("this api is working! ver 1.0.0")
    call.respond(response)
  }
  get("/todo") {
    call.respond(todoController.todoIndex())
  }
  post("/todo/{id}") {
    // call.receive<TodoParams>()
    // println("purchase order: ${po.toString()}")
    call.respond(todoController.todoCreate())
  }
  put("/todo/{id}") {
    val id = call.parameters["id"].toString().toInt()
    call.respond(todoController.todoUpdate(id))
  }
  delete("/todo/{id}") {
    val id = call.parameters["id"].toString().toInt()
    call.respond(todoController.todoDelete(id))
  }
}