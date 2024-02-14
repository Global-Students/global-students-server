package com.example.globalStudents.domain.user.converter;

public interface UserInquiryConverter<T,M> {

    public T toEntity(M dto, Long userId);



}
