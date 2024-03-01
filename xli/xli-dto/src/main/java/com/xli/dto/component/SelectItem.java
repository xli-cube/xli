package com.xli.dto.component;

import lombok.Data;

@Data
public class SelectItem {

    private String value;

    private String label;

    private String status;

    public SelectItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public SelectItem(String value, String label, String status) {
        this.value = value;
        this.label = label;
        this.status = status;
    }
}
