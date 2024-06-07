package org.cynic.gql_api.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.SetJoin;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.cynic.gql_api.domain.entity.Item;
import org.cynic.gql_api.domain.entity.Item_;
import org.cynic.gql_api.domain.entity.Order;
import org.cynic.gql_api.domain.entity.Order_;
import org.cynic.gql_api.domain.http.ItemHttp;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface OrderRepository extends Repository<Order, Long>, JpaSpecificationExecutor<Order> {

    @SuppressWarnings("unchecked")
    static Specification<Order> byItems(List<ItemHttp> http) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<Order, Item> items = (SetJoin<Order, Item>) root.fetch(Order_.items, JoinType.INNER);

            Set<Long> itemIds = http.stream()
                .map(ItemHttp::id)
                .collect(Collectors.toSet());

            return items.get(Item_.id).in(itemIds);
        };
    }

    static Specification<Order> byId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order_.id), id);
    }
}
