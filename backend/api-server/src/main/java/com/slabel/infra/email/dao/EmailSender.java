package com.slabel.infra.email.dao;

public interface EmailSender {

    void send(String to, String email);
}
