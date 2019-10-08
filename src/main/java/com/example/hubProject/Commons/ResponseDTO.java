package com.example.hubProject.Commons;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseDTO<T> {

    ResponseEntity<String> responseEntity;

    T response;
    List<T> responseList;

    public ResponseDTO() {
    }

    public ResponseDTO(ResponseEntity<String> responseEntity, T response, List<T> responseList) {
        this.responseEntity = responseEntity;
        this.response = response;
        this.responseList = responseList;
    }

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
