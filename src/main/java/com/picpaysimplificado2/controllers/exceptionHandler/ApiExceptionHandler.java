package com.picpaysimplificado2.controllers.exceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.picpaysimplificado2.infra.exceptions.EntityNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String THE_PROPERTY_S_DOESN_T_NOT_EXISTS = "The property '%s' doesn't not exists. Fix or remove this property and try again";
    public static final String THE_PROPERTY_RECEIVED_INVALID_VALUE = "The property '%s' received the value '%s' which is of an invalid type. Correct it and inform a value compatible with the type '%s'.";
    public static final String REQUEST_BODY_INVALID = "The request body is invalid. Check syntax error.";
    public static final String AN_UNEXPECTED_INTERNAL_ERROR = "An unexpected internal system error has occurred. Try again and if the problem persists, contact your system administrator.";
    public static final String INVALID_FIEDS = "One or more fields are invalid. Fill in correctly and try again.";
    public static final String RESOURCE_NOT_FOUND = "The resource '%s' you tried to access does not exist!";
    public static final String INVALID_PARAMETER = "URL parameter '%s' has value '%s' which is of an invalid type. Correct and enter a value compatible with type '%s'.";
    @Autowired
    private MessageSource messageSource;
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable cause = ex.getCause();
        if(cause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException)cause,headers,(HttpStatus) status,request);
        }
        if(cause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException)cause,headers,(HttpStatus) status,request);
        }
        ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
        String detail = REQUEST_BODY_INVALID;
        Problem problem = createProblemBuilder((HttpStatus) status,problemType,detail).build();
        return handleExceptionInternal(ex,problem, headers, status, request);
    }
    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
        String path = ex.getPath().stream().map(x -> x.getFieldName()).collect(Collectors.joining("."));
        String details = String.format(THE_PROPERTY_RECEIVED_INVALID_VALUE,path,ex.getValue(),ex.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder(status,problemType,details).build();
        return this.handleExceptionInternal(ex,problem,headers,status,request);
    }
    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
        String path = ex.getPath().stream().map(x -> x.getFieldName()).collect(Collectors.joining("."));
        String details = String.format(THE_PROPERTY_S_DOESN_T_NOT_EXISTS,path);
        Problem problem = createProblemBuilder(status,problemType,details).build();
        return this.handleExceptionInternal(ex,problem,headers,status,request);

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String requestURL = ex.getRequestURL();
        String detail = String.format(RESOURCE_NOT_FOUND,requestURL);
        Problem problem = createProblemBuilder((HttpStatus) status,ProblemType.RESOURCE_NOT_FOUND,detail).build();
        return this.handleExceptionInternal(ex,problem,headers,status,request);
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers,
                    (HttpStatus) status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String path = ex.getParameter().getParameterName();
        String value = ex.getValue().toString();
        String type = ex.getRequiredType().getSimpleName();
        String details = String.format(INVALID_PARAMETER,path, value, type);
        Problem problem = createProblemBuilder(status, problemType, details).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null){
            HttpStatus convertStatus = (HttpStatus) status;
            body = Problem.builder().title(convertStatus.getReasonPhrase())
                    .status(status.value())
                    .build();
        }else if (body instanceof String) {
            body = Problem.builder().title((String) body).status(status.value()).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request){
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status,problemType,ex.getMessage()).build();
        return handleExceptionInternal(ex,problem,new HttpHeaders(),status,request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleOperationUnauthorizedException(DataIntegrityViolationException ex, WebRequest request){
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status,problemType,ex.getMessage()).build();
        return handleExceptionInternal(ex,problem,new HttpHeaders(),status,request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughy(Exception ex, WebRequest request){
        ProblemType problemType = ProblemType.BUSINESS_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = AN_UNEXPECTED_INTERNAL_ERROR;

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String details = INVALID_FIEDS;
        ProblemType problemType = ProblemType.INVALID_DATA;
        BindingResult bindingResult = ex.getBindingResult();
        List<Problem.Field> fields = bindingResult.getFieldErrors().stream()
                .map(err -> {

                    String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
                    return Problem.Field.builder()
                            .name(err.getField())
                            .userMessage(message).build();
                }).collect(Collectors.toList());
        Problem problem = createProblemBuilder((HttpStatus) status,problemType,details).fields(fields).build();
       return handleExceptionInternal(ex,problem,headers,status,request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .timeStamp(LocalDateTime.now())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .status(status.value())
                .detail(detail);
    }
}
