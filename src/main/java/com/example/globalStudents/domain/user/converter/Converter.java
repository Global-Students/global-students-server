package com.example.globalStudents.domain.user.converter;

public interface Converter <T,M,K>{
    public T toEntity(M dto);

    public K toResponse(T entity);
}

