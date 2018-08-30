package com.jeff.algorithm;

import com.jeff.application.configuration.Converter;
import org.junit.Test;

import java.util.List;

public class DataProcessorTest {

    @Test
    public void test(){
        System.out.println("test....................ewrerw........");
        List<String> oldPosts = Converter.getOldPosts();
        for(String name : oldPosts){
            System.out.println(name);
        }
    }

}
