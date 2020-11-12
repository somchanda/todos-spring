package com.todo.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.exception.ResourceNotFoundException;
import com.todo.model.Todo;
import com.todo.repository.TodoRepository;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class TodoController {
	@Autowired
	private TodoRepository todoRepository;
	
	//get all TODO
	@GetMapping("/todos")
	public List<Todo> getAllEmployee(){
		return todoRepository.findAll();
	}
	
	@PostMapping("/todos")
	public Todo AddTodo(@RequestBody Todo todo) {
		return todoRepository.save(todo);
	}
	
	@DeleteMapping("todos/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTodo(@PathVariable Long id){
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
		todoRepository.delete(todo);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody Todo todoDetail){
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
		todo.setCompleted(todoDetail.isCompleted());
		todo.setTitle(todoDetail.getTitle());
		
		Todo updatedTodo = todoRepository.save(todo);
		return ResponseEntity.ok(updatedTodo);
	}
	
	@PutMapping("todos/mark-complete/{id}")
	public ResponseEntity<Todo> markComplete(@PathVariable long id, @RequestBody Todo todoDetail){
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
		todo.setCompleted(todoDetail.isCompleted());
		Todo updatedTodo = todoRepository.save(todo);
		return ResponseEntity.ok(updatedTodo);
	}
}
