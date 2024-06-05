package org.cynic.gql_api.framework.coercing;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.NullValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import java.util.Locale;

public final class VoidCoercing implements Coercing<Object, Object> {

    public static final GraphQLScalarType SCALAR_TYPE = GraphQLScalarType.newScalar()
        .name("Void")
        .coercing(new VoidCoercing())
        .build();


    @Override
    public Object serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
        return null;
    }

    @Override
    public Object parseValue(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingParseValueException {
        return null;
    }

    @Override
    public Object parseLiteral(Value<?> input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale)
        throws CoercingParseLiteralException {

        return null;
    }

    @Override
    public Value<?> valueToLiteral(Object input, GraphQLContext graphQLContext, Locale locale) {
        return NullValue.of();
    }
}
