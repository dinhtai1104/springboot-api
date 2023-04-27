package com.springboot.api.controller;

import com.springboot.api.model.ResponseObject;
import com.springboot.api.model.Student;
import com.springboot.api.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Students")
public class StudentController {
    // DI = Dependency Injection
    @Autowired
    private StudentRepository repository;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllStudent() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("","",repository.findAll())
        );
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getStudent(@PathVariable Long id) {
        var foundStudent = repository.findById(id);
        if (foundStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Query not found id="+id, "oh!!")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("ok", "Query product successfully", foundStudent)
            );
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "insert Student", repository.save(student))
        );
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        var foundStudent = repository.findById(id);
        if (foundStudent.isPresent()) {
            foundStudent.map(t->{
                t.setStudentId(student.getStudentId());
                t.setAddress(student.getAddress());
                t.setName(student.getName());
                return repository.save(t);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","update student id:" +id, foundStudent)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("error","Not found student id:" +id, "")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteStudent(@PathVariable Long id) {
        Optional<Student> foundStudent = repository.findById(id);
        if (foundStudent.isPresent()) {
            repository.delete(foundStudent.get());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete successfully:" +id, foundStudent)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("error","Not found student id:" +id, "")
            );
        }
    }
}
