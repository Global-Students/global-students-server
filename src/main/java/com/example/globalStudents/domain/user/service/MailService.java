package com.example.globalStudents.domain.user.service;

public interface MailService{
    public void sendFindIdMail(String toEmail,String id);
    public void sendFindPasswordMail(String toEmail);

}
