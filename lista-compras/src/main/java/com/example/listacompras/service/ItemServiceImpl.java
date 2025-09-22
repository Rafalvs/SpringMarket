package com.example.listacompras.service;

import com.example.listacompras.model.Item;
import com.example.listacompras.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repo;

    public ItemServiceImpl(ItemRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Item> listar() {
        return repo.findAll();
    }

    @Override
    public Item buscar(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    @Override
    public Item salvar(Item item) {
        return repo.save(item);
    }

    @Override
    public Item atualizar(Long id, Item item) {
        Item existente = buscar(id);
        existente.setNome(item.getNome());
        existente.setQuantidade(item.getQuantidade());
        existente.setPreco(item.getPreco());
        existente.setComprado(item.isComprado());
        return repo.save(existente);
    }

    @Override
    public void remover(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado");
        }
        repo.deleteById(id);
    }
}
