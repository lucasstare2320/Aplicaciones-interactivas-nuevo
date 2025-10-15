package com.uade.keepstar.entity.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.uade.keepstar.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private List<Item> items;
    private String status;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private Long productId;
        private Integer quantity;
    }

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.items = order.getItems().stream().map(oi -> new Item(oi.getProduct().getId(),oi.getQuantity())).collect(Collectors.toList());
        this.status = order.getStatus().name();
    }
}
