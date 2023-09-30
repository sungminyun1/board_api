package com.springBoard.util;

import org.junit.jupiter.api.Test;

class UtilTest {

    @Test
    public void uuidTest(){
        String uuid = Util.generateRid();
        System.out.print(uuid);
    }
}