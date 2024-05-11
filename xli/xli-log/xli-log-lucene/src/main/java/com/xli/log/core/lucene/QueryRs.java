package com.xli.log.core.lucene;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class QueryRs {

    private Long total;

    private List<Map<String,Object>> hits;

}
