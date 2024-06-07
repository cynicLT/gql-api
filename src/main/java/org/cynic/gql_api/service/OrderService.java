package org.cynic.gql_api.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.IterableUtils;
import org.cynic.gql_api.domain.ApplicationException;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.cynic.gql_api.mapper.OrderMapper;
import org.cynic.gql_api.repository.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderHttp> orders() {
        return orderRepository.findAll(null)
            .stream()
            .map(orderMapper::toHttp)
            .flatMap(Optional::stream)
            .toList();
    }

    public Map<ItemHttp, List<OrderHttp>> ordersBy(List<ItemHttp> http) {

        return orderRepository.findAll(OrderRepository.byItems(http))
            .stream()
            .map(it -> Map.entry(it,
                it.getItems()
                    .stream()
                    .map(t -> IterableUtils.find(http, z -> z.id().equals(t.getId())))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()))
            )
            .flatMap(it -> it.getValue().stream().map(t -> Map.entry(t, it.getKey())))
            .map(it -> Map.entry(
                    it.getKey(),
                    orderMapper.toHttp(it.getValue())
                )
            )
            .collect(Collectors.groupingBy(
                    Entry::getKey,
                    Collectors.filtering(t -> t.getValue().isPresent(), Collectors.mapping(t -> t.getValue().get(), Collectors.toList()))
                )
            );

    }

    public OrderHttp orderBy(Long id) {
        return orderRepository.findOne(OrderRepository.byId(id))
            .flatMap(orderMapper::toHttp)
            .orElseThrow(() -> new ApplicationException("error.order.not.found", Map.entry("id", id)));
    }
}
