package kr.ac.jbnu.lsh.wsd.controller;

import kr.ac.jbnu.lsh.wsd.model.Todo;
import kr.ac.jbnu.lsh.wsd.response.ApiResponse;
import kr.ac.jbnu.lsh.wsd.service.TodoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import kr.ac.jbnu.lsh.wsd.exception.ServiceUnavailableException;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET 1 - 전체 Todo 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Todo>>> getAllTodos() {

        List<Todo> todos = todoService.getAllTodos();

        return ResponseEntity.ok(
                ApiResponse.success(todos)
        );
    }

    // GET 2 - 특정 Todo 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTodo(@PathVariable Long id) {

        Todo todo = todoService.getTodo(id);

        if (todo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Todo를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(
                ApiResponse.success(todo)
        );
    }

    // POST 1 - 새로운 Todo 생성
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTodo(@RequestBody Todo todo) {

        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("title은 필수입니다."));
        }

        Todo createdTodo = todoService.createTodo(todo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdTodo));
    }

    // POST 2 - Todo 완료 처리
    @PostMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<?>> completeTodo(@PathVariable Long id) {

        Todo todo = todoService.getTodo(id);

        if (todo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Todo를 찾을 수 없습니다."));
        }

        todo.setCompleted(true);

        return ResponseEntity.ok(
                ApiResponse.success(todo)
        );
    }

    // PUT 1 - Todo 내용 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateTodo(
            @PathVariable Long id,
            @RequestBody Todo newTodo) {

        if (newTodo.getTitle() == null || newTodo.getTitle().isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("title은 필수입니다."));
        }

        Todo updatedTodo = todoService.updateTodo(id, newTodo);

        if (updatedTodo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Todo를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(
                ApiResponse.success(updatedTodo)
        );
    }

    // PUT 2 - Todo 우선순위 수정
    @PutMapping("/{id}/priority")
    public ResponseEntity<ApiResponse<?>> updatePriority(
            @PathVariable Long id,
            @RequestParam int priority) {

        if (priority < 1 || priority > 5) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("priority는 1~5 사이여야 합니다."));
        }

        Todo updatedTodo = todoService.updatePriority(id, priority);

        if (updatedTodo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Todo를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(
                ApiResponse.success(updatedTodo)
        );
    }

    // DELETE 1 - 특정 Todo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTodo(@PathVariable Long id) {

        boolean deleted = todoService.deleteTodo(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Todo를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Todo가 삭제되었습니다.")
        );
    }


    // DELETE 2 - 전체 Todo 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteAllTodos(
            @RequestParam(required = false) String mode) {

        // 500 테스트
        if ("error".equals(mode)) {
            throw new RuntimeException("강제 서버 오류");
        }

        // 503 테스트
        if ("maintenance".equals(mode)) {
            throw new ServiceUnavailableException(
                    "현재 서버 점검 중입니다."
            );
        }

        todoService.deleteAllTodos();

        return ResponseEntity.ok(
                ApiResponse.success("모든 Todo가 삭제되었습니다.")
        );
    }
}