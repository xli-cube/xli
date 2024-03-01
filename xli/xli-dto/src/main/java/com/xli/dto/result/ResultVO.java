package com.xli.dto.result;

import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.result.status.Status;
import lombok.Data;

@Data
public class ResultVO<T> {

    private String code;

    private String msg;

    private Object data;

    public ResultVO(Status status, String msg) {
        this.code = status.getCode();
        this.msg = msg;
    }

    public ResultVO(Status status, String msg, T data) {
        this.code = status.getCode();
        this.msg = msg;
        if (data instanceof TableModel<?> tableModel) {
            this.data = tableModel;
        } else if (data instanceof TreeModel) {
            this.data = ((TreeModel<?>) data).getData();
        } else {
            this.data = data;
        }
    }
}
