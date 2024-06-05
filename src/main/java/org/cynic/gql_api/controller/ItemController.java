package org.cynic.gql_api.controller;

import java.util.List;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @QueryMapping
    public List<ItemHttp> items() {
        return itemService.items();
    }


    @QueryMapping
    public List<ItemHttp> itemsBy(@Argument Long orderId) {
        return itemService.itemsBy(orderId);
    }

    @SchemaMapping(typeName = "Item", field = "orders")
    public List<OrderHttp> ordersByItemId(Long id) {
        return orderService.ordersByItemId(id);
    }


    @QueryMapping
    public ItemHttp itemBy(@Argument Long id) {
        return itemService.itemBy(id);
    }

}
