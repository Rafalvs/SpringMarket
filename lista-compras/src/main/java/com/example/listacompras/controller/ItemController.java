package com.example.listacompras.web;

import com.example.listacompras.model.Item;
import com.example.listacompras.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Item> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Item buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody Item novo) {
        Item salvo = repo.save(novo);
        return ResponseEntity.created(URI.create("/api/itens/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public Item atualizar(@PathVariable Long id, @Valid @RequestBody Item atualizacao) {
        return repo.findById(id).map(existente -> {
            existente.setNome(atualizacao.getNome());
            existente.setQuantidade(atualizacao.getQuantidade());
            existente.setPreco(atualizacao.getPreco());
            existente.setComprado(atualizacao.isComprado());
            return repo.save(existente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado");
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
