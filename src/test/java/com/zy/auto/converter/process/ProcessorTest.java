package com.zy.auto.converter.process;

import com.zy.auto.converter.constant.ClassSuffixEnum;
import com.zy.auto.converter.mode.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * @Author Jong
 * @Date 2021/2/6 13:37
 * @Version 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProcessorTest {

    private Processor processor;

    @BeforeAll
    void setUP() {
        processor = new Processor();
    }

    @Test
    void generatorCode() {
        processor.generatorCode(Person.class, ClassSuffixEnum.BO,ClassSuffixEnum.DTO);
    }



}