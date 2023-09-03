package com.picpaysimplificado2.controllers.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {
    private Integer status;
    private LocalDateTime timeStamp;
    private String type;
    private String title;
    private String detail;
    private List<Field> fields;
    @Getter
    @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }
}
