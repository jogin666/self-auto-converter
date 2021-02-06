package com.zy.auto.converter.mode;

import com.zy.auto.converter.mode.PersonDTO.GirlDTO;
import com.zy.auto.converter.mode.PersonDTO.SonDTO;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Jong
 * @Date 2021/1/27 17:19
 */
public class PersonDTOConverter {

    public static PersonDTO convert(Person source) {
        if (Objects.isNull(source)) {
            return null;
        }
        PersonDTO target = new PersonDTO();
        target.setName(source.getName());
        target.setAge(source.getAge());
        if (!Objects.isNull(source.getSons()) && !source.getSons().isEmpty()) {
            List<SonDTO> listSonDTO;
            listSonDTO = source.getSons().stream().map(n1 -> {
                SonDTO sonDTO = new SonDTO();
                sonDTO.setGender(n1.getGender());
                if (!Objects.isNull(n1.getSons()) && !n1.getSons().isEmpty()) {
                    List<GirlDTO> listGirlDTO;
                    listGirlDTO = n1.getSons().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    sonDTO.setSons(listGirlDTO);
                }
                if (!Objects.isNull(n1.getSonSet()) && !n1.getSonSet().isEmpty()) {
                    Set<GirlDTO> setGirlDTO;
                    setGirlDTO = n1.getSonSet().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toSet());
                    sonDTO.setSonSet(setGirlDTO);
                }
                if (!Objects.isNull(n1.getSonArray()) && n1.getSonArray().length != 0) {
                    List<GirlDTO> listGirlDTO = Stream.of(n1.getSonArray()).map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    GirlDTO[] array = listGirlDTO.toArray(new GirlDTO[]{});
                    sonDTO.setSonArray(array);
                }
                return sonDTO;
            }).collect(Collectors.toList());
            target.setSons(listSonDTO);
        }
        if (!Objects.isNull(source.getSonSet()) && !source.getSonSet().isEmpty()) {
            Set<SonDTO> setSonDTO;
            setSonDTO = source.getSonSet().stream().map(n1 -> {
                SonDTO sonDTO = new SonDTO();
                sonDTO.setGender(n1.getGender());
                if (!Objects.isNull(n1.getSons()) && !n1.getSons().isEmpty()) {
                    List<GirlDTO> listGirlDTO;
                    listGirlDTO = n1.getSons().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    sonDTO.setSons(listGirlDTO);
                }
                if (!Objects.isNull(n1.getSonSet()) && !n1.getSonSet().isEmpty()) {
                    Set<GirlDTO> setGirlDTO;
                    setGirlDTO = n1.getSonSet().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toSet());
                    sonDTO.setSonSet(setGirlDTO);
                }
                if (!Objects.isNull(n1.getSonArray()) && n1.getSonArray().length != 0) {
                    List<GirlDTO> listGirlDTO = Stream.of(n1.getSonArray()).map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    GirlDTO[] array = listGirlDTO.toArray(new GirlDTO[]{});
                    sonDTO.setSonArray(array);
                }
                return sonDTO;
            }).collect(Collectors.toSet());
            target.setSonSet(setSonDTO);
        }
        if (!Objects.isNull(source.getSonArray()) && source.getSonArray().length != 0) {
            List<SonDTO> listSonDTO = Stream.of(source.getSonArray()).map(n1 -> {
                SonDTO sonDTO = new SonDTO();
                sonDTO.setGender(n1.getGender());
                if (!Objects.isNull(n1.getSons()) && !n1.getSons().isEmpty()) {
                    List<GirlDTO> listGirlDTO;
                    listGirlDTO = n1.getSons().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    sonDTO.setSons(listGirlDTO);
                }
                if (!Objects.isNull(n1.getSonSet()) && !n1.getSonSet().isEmpty()) {
                    Set<GirlDTO> setGirlDTO;
                    setGirlDTO = n1.getSonSet().stream().map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toSet());
                    sonDTO.setSonSet(setGirlDTO);
                }
                if (!Objects.isNull(n1.getSonArray()) && n1.getSonArray().length != 0) {
                    List<GirlDTO> listGirlDTO = Stream.of(n1.getSonArray()).map(n3 -> {
                        GirlDTO girlDTO = new GirlDTO();
                        return girlDTO;
                    }).collect(Collectors.toList());
                    GirlDTO[] array = listGirlDTO.toArray(new GirlDTO[]{});
                    sonDTO.setSonArray(array);
                }
                return sonDTO;
            }).collect(Collectors.toList());
            SonDTO[] array = listSonDTO.toArray(new SonDTO[]{});
            target.setSonArray(array);
        }
        return target;
    }

}
