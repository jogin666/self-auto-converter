package com.zy.auto.converter.process;

import com.zy.auto.converter.constant.ClassSuffixEnum;
import com.zy.auto.converter.utils.ConverterCodeGenerator;

/**
 * @Author Jong
 * @Date 2021/1/21 9:42
 */
public class Processor {

    public void generatorCode(Class<?> clazz, ClassSuffixEnum sourceClassSuffix,ClassSuffixEnum targetClassSuffix){
        ConverterCodeGenerator generator = new ConverterCodeGenerator();
        generator.beginGenerate(clazz,sourceClassSuffix.getSuffix(),targetClassSuffix.getSuffix());
    }

}
