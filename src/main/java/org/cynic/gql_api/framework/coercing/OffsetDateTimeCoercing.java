package org.cynic.gql_api.framework.coercing;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import java.time.OffsetDateTime;
import java.util.Locale;

public final class OffsetDateTimeCoercing implements Coercing<OffsetDateTime, String> {

    public static final GraphQLScalarType SCALAR_TYPE = GraphQLScalarType.newScalar()
        .name("OffsetDateTime")
        .coercing(new OffsetDateTimeCoercing())
        .build();

    @Override
    public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
        OffsetDateTime offsetDateTime = OffsetDateTime.class.cast(dataFetcherResult);

        return offsetDateTime.toString();
    }

    @Override
    public OffsetDateTime parseValue(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingParseValueException {
        String string = input.toString();

        return OffsetDateTime.parse(string);
    }

    @Override
    public OffsetDateTime parseLiteral(Value<?> input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale)
        throws CoercingParseLiteralException {

        return parseValue(input, graphQLContext, locale);
    }

    @Override
    public Value<?> valueToLiteral(Object input, GraphQLContext graphQLContext, Locale locale) {
        return new StringValue(input.toString());
    }
}
