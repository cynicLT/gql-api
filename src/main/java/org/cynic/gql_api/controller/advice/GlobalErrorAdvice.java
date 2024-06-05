package org.cynic.gql_api.controller.advice;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.cynic.gql_api.domain.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalErrorAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorAdvice.class);

    @GraphQlExceptionHandler(ApplicationException.class)
    public final GraphQLError handleApplicationException(ApplicationException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                exception.getMessage(),
                exception.getValues()
            )
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }

    @GraphQlExceptionHandler(MissingServletRequestParameterException.class)
    public final GraphQLError handleMissingParameterException(MissingServletRequestParameterException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                "error.parameter.missing",
                Map.ofEntries(
                    Map.entry("name", exception.getParameterName()),
                    Map.entry("type", exception.getParameterType())
                )
            )
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();

    }

    @GraphQlExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final GraphQLError handleTypeException(MethodArgumentTypeMismatchException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                "error.parameter.invalid-type",
                Map.ofEntries(
                    Map.entry("name", exception.getName()),
                    Map.entry("value", Objects.toString(exception.getValue(), StringUtils.EMPTY)),
                    Map.entry("type", ClassUtils.getSimpleName(exception.getRequiredType()))
                )
            )
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }

    @GraphQlExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    public final GraphQLError handleArgumentConversionException(MethodArgumentConversionNotSupportedException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                "error.parameter.non-convertable",
                Map.ofEntries(
                    Map.entry("name", exception.getName()),
                    Map.entry("value", Objects.toString(exception.getValue(), StringUtils.EMPTY)),
                    Map.entry("type", ClassUtils.getSimpleName(exception.getRequiredType()))
                )
            )
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }

    @GraphQlExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final GraphQLError handleHttpMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(
                "error.http-method.not-supported",
                Map.ofEntries(
                    Map.entry("method", exception.getMethod())
                )
            )
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }

    @GraphQlExceptionHandler(DataAccessException.class)
    public GraphQLError handleDataAccessException(DataAccessException exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.INTERNAL_ERROR)
            .message("error.database")
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }

    @GraphQlExceptionHandler(Throwable.class)
    public final GraphQLError handleUnknownException(Throwable exception, DataFetchingEnvironment environment) {
        LOGGER.error("", exception);

        return GraphQLError.newError()
            .errorType(ErrorType.INTERNAL_ERROR)
            .message("error.unknown")
            .extensions(
                Map.ofEntries(
                    Map.entry("message", ExceptionUtils.getMessage(exception)),
                    Map.entry("arguments", environment.getArguments())
                )
            )
            .build();
    }
}
