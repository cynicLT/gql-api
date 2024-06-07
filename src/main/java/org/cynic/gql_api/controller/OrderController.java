package org.cynic.gql_api.controller;

import java.util.List;
import java.util.Map;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.cynic.gql_api.service.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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


    @BatchMapping(typeName = "Item", field = "orders")
    public Map<ItemHttp, List<OrderHttp>> ordersByItems(List<ItemHttp> http) {
        return orderService.ordersBy(http);
    }


    @QueryMapping
    public OrderHttp orderById(@Argument Long id) {
        return orderService.orderBy(id);
    }
}
