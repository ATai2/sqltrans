package com.ppx.example;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Setter
@Getter
public class ServiceParam {
    private String id;
    private Integer xh;
    private String parametername;
    private Integer parameterposition;
    private String defaultvalue;
    private String parametertype;
    private String description;
}
