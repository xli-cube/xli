package com.xli.cache.monitor;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class CacheMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -591933856009449396L;

    public static final int TYPE_PUT = 1;
    public static final int TYPE_PUT_ALL = 2;
    public static final int TYPE_REMOVE = 3;
    public static final int TYPE_REMOVE_ALL = 4;

    private String sourceId;

    private String area;

    private String cacheName;

    private int type;

    private Object[] keys;

    /**
     * this field is reserved.
     */
    private Object[] values;

}
