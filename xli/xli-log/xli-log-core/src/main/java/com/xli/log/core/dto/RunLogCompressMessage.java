package com.xli.log.core.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RunLogCompressMessage {

    private Integer length;

    private byte[] body;

}
