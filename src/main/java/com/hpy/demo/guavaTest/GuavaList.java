package com.hpy.demo.guavaTest;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hpy.demo.dbTest.entity.DTO.PersonDTO;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A51398
 * 尝试guava
 */
public class GuavaList {
    public static void main(String[] args) {
        ArrayList<PersonDTO> people = Lists.newArrayList(new PersonDTO().setName("小明").setSex(1), new PersonDTO().setSex(1).setName("小李"));
        List<String> transform = Lists.transform(people, new Function<PersonDTO, String>() {
            @Override
            public @Nullable String apply(@Nullable PersonDTO personDTO) {
                return personDTO != null ? personDTO.getName() : "";
            }
        });
        System.out.println("updateBefore："+transform);
        people.remove(0);
        System.out.println("updateAfter:"+transform);
    }
}
