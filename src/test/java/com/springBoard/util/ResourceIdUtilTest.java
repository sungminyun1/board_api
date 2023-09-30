package com.springBoard.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceIdUtilTest {

    @Test
    public void uuidTest(){
        String uuid = ResourceIdUtil.generate();
        System.out.print(uuid);
    }
}