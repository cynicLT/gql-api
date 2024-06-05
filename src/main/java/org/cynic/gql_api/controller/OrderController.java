package org.cynic.gql_api.controller;

import java.util.List;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @QueryMapping
    public List<OrderHttp> orders() {
        return orderService.orders();
    }

    @SchemaMapping(typeName = "Item", field = "orders")
    public List<ItemHttp> itemsByOrderId(Long id) {
        return itemService.itemsByOrderId(id);
    }

    @QueryMapping
    public OrderHttp orderBy(@Argument Long id) {
        return orderService.orderBy(id);
    }
}
