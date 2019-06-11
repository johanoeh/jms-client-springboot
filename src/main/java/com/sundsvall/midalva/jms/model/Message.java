package com.sundsvall.midalva.jms.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    private Long id;
    private String message;
    private String instanceName;
}
