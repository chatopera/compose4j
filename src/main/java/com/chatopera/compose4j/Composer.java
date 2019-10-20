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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Composer<T extends AbstractContext> {

    /**
     * 中间件
     */
    private final List<Middleware> middlewares = new ArrayList<>();

    /**
     * 注册中间件
     *
     * @param mw
     */
    public void use(final Middleware mw) {
        middlewares.add(mw);
    }

    public void handle(final T context) throws Compose4jRuntimeException {
        if (middlewares.size() > 0) {
            Iterator<Middleware> it = middlewares.iterator();

            final Functional fn = new Functional() {
                @Override
                public void apply() {
                    if (it.hasNext()) {
                        it.next().apply(context, this);
                    }

                }
            };
            it.next().apply(context, fn);
        } else {
            // 没有中间件
            throw new Compose4jRuntimeException("Middleware(s) not found");
        }
    }

}
