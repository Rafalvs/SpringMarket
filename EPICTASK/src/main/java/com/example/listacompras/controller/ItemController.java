package com.example.listacompras.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.listacompras.model.Item;
import com.example.listacompras.service.ItemService;

import jakarta.validation.Valid;

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

@Controller
@RequestMapping("/lista")
class ItemWebController {

    private final ItemService itemService;

    public ItemWebController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String mostrarLista(Model model) {
        model.addAttribute("itens", itemService.listar());
        model.addAttribute("item", new Item());
        return "lista";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Item item) {
        if (item.getId() == null) {
            itemService.salvar(item);
        } else {
            itemService.atualizar(item.getId(), item);
        }
        return "redirect:/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.buscar(id));
        model.addAttribute("itens", itemService.listar());
        return "lista";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        itemService.remover(id);
        return "redirect:/lista";
    }
}
