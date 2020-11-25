package com.hpy.demo.guava;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hpy.demo.dbTest.entity.Person;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A51398
 * 尝试guava
 */
public class GuavaList {
    public static void main(String[] args) {
        ArrayList<Person> people = Lists.newArrayList(new Person().setName("小明").setSex(1), new Person().setSex(1).setName("小李"));
        List<String> transform = Lists.transform(people, new Function<Person, String>() {
            @Override
            public @Nullable String apply(@Nullable Person person) {
                return person != null ? person.getName() : "";
            }
        });
        System.out.println("updateBefore："+transform);
        people.remove(0);
        System.out.println("updateAfter:"+transform);
    }
}
