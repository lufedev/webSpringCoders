package com.coders.bookshopp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookRestController {
    @Autowired

    private BookRepository bookRepository;
    @GetMapping("/livros")
    public List<Book> listarLivros() {
        return bookRepository.findAll();
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Optional<Book>> pesquisarLivroPorId(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book != null) {
            return ResponseEntity.ok().body(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/livros")
    public Book adicionarLivro(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<?> removerLivro(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());

    }
}