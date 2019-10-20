/*
 * Copyright (C) 2019 Chatopera Inc, All rights reserved.
 * <https://www.chatopera.com>
 * This software and related documentation are provided under a license agreement containing
 * restrictions on use and disclosure and are protected by intellectual property laws.
 * Except as expressly permitted in your license agreement or allowed by law, you may not use,
 * copy, reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform,
 * publish, or display any part, in any form, or by any means. Reverse engineering, disassembly,
 * or decompilation of this software, unless required by law for interoperability, is prohibited.
 */

package com.chatopera.compose4j;

import com.chatopera.compose4j.exception.Compose4jRuntimeException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class Compose4jTest
        extends TestCase {

    private final static Logger logger = LoggerFactory.getLogger(Compose4jTest.class);

    private final String VAL1 = "what's up";

    protected void print(final HashMap<String, String> map) {
        if (map != null) {
            logger.info("   [print] object items size {}", map.size());

            for (final String key : map.keySet()) {
                logger.info("   [print] key {}, value {}", key, map.get(key));
            }
        } else {
            logger.info("   [print] object is null.");
        }
    }


    class Mw1 implements Middleware<SampleContext> {
        @Override
        public void apply(final SampleContext ctx, final Functional next) {
            logger.info("[Mw1] apply 1");

            ctx.getData().put("foo", "bar");
            ctx.getData().put("foo2", "bar2");
            print(ctx.getData());
            next.apply();
            logger.info("[Mw1] apply 2");
            print(ctx.getData());
        }
    }


    class Mw2 implements Middleware<SampleContext> {

        @Override
        public void apply(final SampleContext ctx, final Functional next) {
            logger.info("[Mw2] apply 1");
            ctx.getData().put("foo", VAL1);
            ctx.getData().put("foo3", "another item");
            print(ctx.getData());
            next.apply();
            logger.info("[Mw2] apply 2");
            print(ctx.getData());
        }
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Compose4jTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(Compose4jTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testCompose() throws Compose4jRuntimeException {
        Composer composer = new Composer<SampleContext>();
        composer.use(new Mw1());
        composer.use(new Mw2());

        SampleContext context = new SampleContext();

        composer.handle(context);

        logger.info("[testCompose] result");

        print(context.getData());

        assertTrue(context.getData().containsKey("foo"));
        assertEquals(context.getData().get("foo"), VAL1);
        assertEquals(context.getData().size(), 3);

        assertTrue(context.getData().containsKey("foo2"));
        assertTrue(context.getData().containsKey("foo3"));
    }
}
