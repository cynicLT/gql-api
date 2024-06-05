package org.cynic.gql_api.domain.http;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderHttp(
    String id,
    String name,
    String description,
    OffsetDateTime dateTime,
    List<ItemHttp> items
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
