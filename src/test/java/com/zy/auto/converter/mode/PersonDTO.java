package com.zy.auto.converter.mode;

import com.zy.auto.converter.annotation.TargetConvertInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Jong
 * @Date 2021/1/27 17:18
 */

@Data
public class PersonDTO {

    @TargetConvertInfo
    private String name;
    @TargetConvertInfo
    private Integer age;

    @TargetConvertInfo
    List<SonDTO> sons = new ArrayList<>();
    @TargetConvertInfo
    Set<SonDTO> sonSet = new HashSet<>();
    @TargetConvertInfo
    SonDTO[] sonArray = new SonDTO[12];

    @Data
    static class SonDTO{
        @TargetConvertInfo
        private String gender;
        @TargetConvertInfo
        List<GirlDTO> sons = new ArrayList<>();
        @TargetConvertInfo
        Set<GirlDTO> sonSet = new HashSet<>();
        @TargetConvertInfo
        GirlDTO[] sonArray = new GirlDTO[12];
    }


    @Data
    static class GirlDTO{
        private String age;
    }


}
