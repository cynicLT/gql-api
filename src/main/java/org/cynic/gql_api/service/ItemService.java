package org.cynic.gql_api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections4.IterableUtils;
import org.cynic.gql_api.domain.ApplicationException;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.cynic.gql_api.mapper.ItemMapper;
import org.cynic.gql_api.repository.ItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }


    public List<ItemHttp> items() {
        return IterableUtils.toList(itemRepository.findAll(null)).stream().map(itemMapper::toHttp).flatMap(Optional::stream).toList();
    }

    public List<ItemHttp> itemsBy(Long orderId) {
        return itemRepository.findAll(ItemRepository.byOrderId(orderId)).stream().map(itemMapper::toHttp).flatMap(Optional::stream).toList();
    }

    public List<ItemHttp> itemsBy(OrderHttp http) {
        return itemsBy(http.id());
    }

    public ItemHttp itemBy(Long id) {
        return itemRepository.findOne(ItemRepository.byId(id)).flatMap(itemMapper::toHttp)
            .orElseThrow(() -> new ApplicationException("item.notfound", Map.entry("id", id)));

    }
}
