package com.example.listacompras.controller;

import com.example.listacompras.model.Item;
import com.example.listacompras.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Item buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody Item novo) {
        Item salvo = service.salvar(novo);
        return ResponseEntity.created(URI.create("/api/itens/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public Item atualizar(@PathVariable Long id, @Valid @RequestBody Item atualizacao) {
        return service.atualizar(id, atualizacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
