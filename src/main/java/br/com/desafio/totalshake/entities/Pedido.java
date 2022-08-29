package br.com.desafio.totalshake.entities;

import br.com.desafio.totalshake.dto.PedidoDTO;
import br.com.desafio.totalshake.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Pedido {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itensPedido;

    public static Pedido of(PedidoDTO request) {
        var pedido = new Pedido();
        pedido.setId(request.getId());
        pedido.setStatus(request.getStatus());
        pedido.setDataHora(request.getDataHora());
        return pedido;
    }

    @PrePersist
    void prePersist() {
        this.dataHora = LocalDateTime.now();
    }
}