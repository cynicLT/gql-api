package org.cynic.gql_api.domain.http;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record ItemHttp(
    Long id,
    String name,
    String description,
    List<OrderHttp> orders
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
