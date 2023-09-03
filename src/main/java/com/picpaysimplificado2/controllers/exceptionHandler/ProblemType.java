package com.picpaysimplificado2.controllers.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    RESOURCE_NOT_FOUND("/resource-not-found","Resource not found!"),
    ENTITY_IN_USE("/entity-in-use","Entity in use"),
    BUSINESS_ERROR("/business-error","Business error!"),
    INCOMPREENSIBLE_MESSAGE("/incompreensible-message","Incompreensible message"),
    INVALID_PARAMETER("/invalid-parameter","Invalid parameter"),
    INVALID_DATA("/invalid-data","Invalid data!"),
    SYSTEM_ERROR("/internal-error","internal error");


    private String uri;
    private String title;

    private ProblemType(String uri, String title){
        this.uri = uri;
        this.title = title;
    }


}
