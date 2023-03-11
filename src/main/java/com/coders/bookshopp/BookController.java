package com.coders.bookshopp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.coders.bookshopp.Book;
import com.coders.bookshopp.BookRepository;


@RestController
@RequestMapping("/livros")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String listarLivros(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "livros";
    }
    @GetMapping("/livros/adicionar")
    public String exibirFormularioAdicionarLivro(Model model) {
        model.addAttribute("livro", new Book());
        return "formulario-adicionar-livro";
    }
    @PostMapping("/livros/adicionar")
    public String adicionarLivro(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }
    @GetMapping("/livros/remover/{id}")
    public String exibirFormularioRemoverLivro(@PathVariable Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("livro",book);
            return "formulario-remover-livro";
        } else {
            return "redirect:/";
        }
    }
    @PostMapping("/livros/remover/{id}")
    public String removerLivro(@RequestParam Long id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}