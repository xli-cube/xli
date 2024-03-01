package com.xli.dto.params;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class TreeNodeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3308822386644101751L;

    private String id;

    private String text;

    private String pid;
}
