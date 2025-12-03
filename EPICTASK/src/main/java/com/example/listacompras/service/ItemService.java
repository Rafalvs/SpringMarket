package com.example.listacompras.service;

import com.example.listacompras.model.Item;
import java.util.List;

public interface ItemService {
    List<Item> listar();
    Item buscar(Long id);
    Item salvar(Item item);
    Item atualizar(Long id, Item item);
    void remover(Long id);
}
