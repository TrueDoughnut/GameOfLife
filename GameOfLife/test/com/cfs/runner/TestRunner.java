package com.cfs.runner;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRunner {

    Runner runner;

    @BeforeEach
    public void before(){
        runner = new Runner();
    }


}
