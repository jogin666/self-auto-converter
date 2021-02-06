package com.zy.auto.converter.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author Jong
 * @Date 2021/2/6 13:51
 * @Version 1.0
 */

@Getter
public enum ClassSuffixEnum {
    VO(1,"VO"),
    BO(2,"BO"),
    DTO(3,"DTO")
    ;

    private int code;
    private String suffix;

    ClassSuffixEnum(int code,String suffix){
        this.code = code;
        this.suffix = suffix;
    }

}
