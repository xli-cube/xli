package com.xli.dto.component.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class TableModel<T> {

    @Getter
    private boolean success;

    @Getter
    @Setter
    private Long total;

    @Getter
    private List<T> data;

    protected TableModel() {
        success = true;
        total = 0L;
        data = this.fetch();
    }

    public abstract List<T> fetch();

}
