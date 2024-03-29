/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022 Contributors to the Eclipse Foundation. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.jvnet.hk2;

import static org.jvnet.hk2.OSGiConstants.PACKAGE;

import java.util.Collection;

/**
 * @author Kohsuke Kawaguchi
 */
public abstract class Named {
    /**
     * Either package name or a bundle name.
     */
    public final String name;

    Named(Lexer sc) {
        name = sc.read(PACKAGE);
    }

    public static int getLongestName(Collection<? extends Named> col) {
        int len = 0;
        for (Named n : col)
            len = Math.max(len,n.name.length());
        return len;
    }

    static String unquote(String s) {
        if(s.startsWith("\"")) {
            assert s.endsWith("\"");
            return s.substring(1,s.length()-1);
        }
        return s;
    }
}
