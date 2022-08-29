package br.com.desafio.totalshake.service;

import br.com.desafio.totalshake.dto.ItemPedidoDTO;
import br.com.desafio.totalshake.entities.ItemPedido;
import br.com.desafio.totalshake.entities.Pedido;
import br.com.desafio.totalshake.exceptions.ItemPedidoNotFoundException;
import br.com.desafio.totalshake.exceptions.PedidoNotFoundException;
import br.com.desafio.totalshake.repository.ItemPedidoRepository;
import br.com.desafio.totalshake.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;



    public ItemPedidoDTO adicionarItem(ItemPedidoDTO dto) {
        Pedido pedido = findPedidoById(dto.getIdPedido());
        var itemPedido = itemPedidoRepository.save(ItemPedido.of(dto, pedido));

        return ItemPedidoDTO.of(itemPedido);
    }


    public List<ItemPedidoDTO> findAll() {
        var items = itemPedidoRepository.findAll();

        return items.stream().map(ItemPedidoDTO::of).toList();
    }


    public ItemPedidoDTO atualizarItem(Long idItemPedido, ItemPedidoDTO dto) {
        var itemPedido = itemPedidoRepository.findById(idItemPedido).orElseThrow(ItemPedidoNotFoundException::new);
        var updatedItemPedido = ItemPedido.of(dto, itemPedido.getPedido());

        updatedItemPedido.setId(itemPedido.getId());
        return ItemPedidoDTO.of(itemPedidoRepository.save(updatedItemPedido));
    }

    public void deleteItem(Long id) {
        itemPedidoRepository.delete( itemPedidoRepository.findById(id).orElseThrow(ItemPedidoNotFoundException::new) );
    }

    private Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(PedidoNotFoundException::new);
    }

    public ItemPedidoDTO findItemPedidoById(Long idItemPedido) {
        return ItemPedidoDTO.of(itemPedidoRepository.findById(idItemPedido).orElseThrow(ItemPedidoNotFoundException::new));
    }


}