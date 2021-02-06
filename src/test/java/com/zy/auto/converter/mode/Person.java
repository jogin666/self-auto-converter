package com.zy.auto.converter.mode;

import com.zy.auto.converter.annotation.TargetConvertInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Jong
 * @Date 2021/1/22 15:15
 */

@Data
public class Person {

    @TargetConvertInfo
    private String name;
    @TargetConvertInfo
    private Integer age;

    @TargetConvertInfo
    List<Son> sons = new ArrayList<>();
    @TargetConvertInfo
    Set<Son> sonSet = new HashSet<>();
    @TargetConvertInfo
    Son[] sonArray = new Son[12];

    @Data
    static class Son{
        @TargetConvertInfo
        private String gender;
        @TargetConvertInfo
        List<Girl> sons = new ArrayList<>();
        @TargetConvertInfo
        Set<Girl> sonSet = new HashSet<>();
        @TargetConvertInfo
        Girl[] sonArray = new Girl[12];
    }


    @Data
    class Girl{
        private String age;
    }



}
