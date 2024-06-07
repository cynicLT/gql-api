package org.cynic.gql_api.mapper;

import java.util.Optional;
import org.cynic.gql_api.domain.entity.Item;
import org.cynic.gql_api.domain.http.ItemHttp;
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
public abstract class ItemMapper {


    @Mapping(target = "orders", ignore = true)
    protected abstract ItemHttp internal(Item item);

    public Optional<ItemHttp> toHttp(Item item) {
        return Optional.ofNullable(internal(item));
    }
}
