package org.cynic.gql_api.controller;

import java.util.List;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.service.ItemService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
    public List<ItemHttp> itemsByOrderId(@Argument Long orderId) {
        return itemService.itemsBy(orderId);
    }

//    @SchemaMapping(typeName = "Order", field = "items")
//    public List<ItemHttp> itemsByOrder(OrderHttp http) {
//        return itemService.itemsBy(http);
//    }

    @QueryMapping
    public ItemHttp itemById(@Argument Long id) {
        return itemService.itemBy(id);
    }

}
