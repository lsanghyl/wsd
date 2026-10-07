package kr.ac.jbnu.lsh.wsd.service;

import kr.ac.jbnu.lsh.wsd.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final List<Todo> todos = new ArrayList<>();
    private Long nextId = 1L;

    // Todo 생성
    public Todo createTodo(Todo todo) {
        todo.setId(nextId++);
        todo.setCompleted(false);
        todos.add(todo);

        return todo;
    }

    // 전체 Todo 조회
    public List<Todo> getAllTodos() {
        return todos;
    }

    // 특정 Todo 조회
    public Todo getTodo(Long id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Todo 수정
    public Todo updateTodo(Long id, Todo newTodo) {
        Todo todo = getTodo(id);

        if (todo == null) {
            return null;
        }

        todo.setTitle(newTodo.getTitle());
        todo.setPriority(newTodo.getPriority());

        return todo;
    }

    // 우선순위 수정
    public Todo updatePriority(Long id, int priority) {
        Todo todo = getTodo(id);

        if (todo == null) {
            return null;
        }

        todo.setPriority(priority);

        return todo;
    }

    // 특정 Todo 삭제
    public boolean deleteTodo(Long id) {
        Todo todo = getTodo(id);

        if (todo == null) {
            return false;
        }

        todos.remove(todo);
        return true;
    }

    // 전체 Todo 삭제
    public void deleteAllTodos() {
        todos.clear();
    }
}