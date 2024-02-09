package com.example.globalStudents.domain.user.converter;

public interface UserConverter<T,M,K>{
    public T toEntity(M dto);

    public K toResponse(T entity);
}

