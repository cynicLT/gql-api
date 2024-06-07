package org.cynic.gql_api.mapper;

import java.util.Optional;
import org.cynic.gql_api.domain.entity.Order;
import org.cynic.gql_api.domain.http.OrderHttp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class OrderMapper {


    @Mapping(target = "items", ignore = true)
    protected abstract OrderHttp internal(Order order);

    public Optional<OrderHttp> toHttp(Order order) {
        return Optional.ofNullable(internal(order));
    }
}
