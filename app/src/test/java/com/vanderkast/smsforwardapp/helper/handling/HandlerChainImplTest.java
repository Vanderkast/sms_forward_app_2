package com.vanderkast.smsforwardapp.helper.handling;

import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerChainImplTest {

    @Test
    public void into_to_ls() {
        String result = HandlerChain.start(new StringIntHandler(), "3")
                .next(new IntToLsHandler())
                .get();
        assertNotNull(result);
        assertEquals("LLL", result);
    }

    static class StringIntHandler implements Handler<String, Integer> {
        @Override
        public Integer handle(String input) {
            return Integer.parseInt(input);
        }
    }

    static class IntToLsHandler implements Handler<Integer, String> {
        @Override
        public String handle(Integer input) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < input; i++) {
                builder.append('L');
            }
            return builder.toString();
        }
    }
}