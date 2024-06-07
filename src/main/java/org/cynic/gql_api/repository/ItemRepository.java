package org.cynic.gql_api.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.SetJoin;
import org.cynic.gql_api.domain.entity.Item;
import org.cynic.gql_api.domain.entity.Item_;
import org.cynic.gql_api.domain.entity.Order;
import org.cynic.gql_api.domain.entity.Order_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ItemRepository extends Repository<Item, Long>, JpaSpecificationExecutor<Item> {

    static Specification<Item> byOrderId(Long orderId) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<Item, Order> orders = root.join(Item_.orders, JoinType.INNER);

            return criteriaBuilder.equal(orders.get(Order_.id), orderId);
        };
    }

    static Specification<Item> byId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Item_.id), id);
    }
}
