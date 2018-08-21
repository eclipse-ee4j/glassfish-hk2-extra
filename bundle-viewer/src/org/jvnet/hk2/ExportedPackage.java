/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import static org.jvnet.hk2.OSGiConstants.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Kohsuke Kawaguchi
 */
public class ExportedPackage extends Named implements Comparable<ExportedPackage> {
    /**
     * List of packages that are transitively exposed through this package.
     */
    public final Set<String> uses;

    public final String include;

    public final String version;

    ExportedPackage(Lexer sc) {
        super(sc);
        if (sc.at(INCLUDE)) {
            sc.read(INCLUDE);
            include = sc.readUntil(';');
        } else {
            include=null;
        }
        if(sc.at(USES)) {
            sc.read(USES);
            uses = Collections.unmodifiableSet(
                new TreeSet<String>(Arrays.asList(sc.readUntil('\"').split(","))));
        } else
            uses = Collections.emptySet();
        if(sc.at(VERSION)) {
            sc.read(VERSION);
            version = unquote(sc.read(POSSIBLY_QUOTED_TOKEN));
        } else
            version = null;
        if(sc.at(','))
            sc.consume(",");
    }

    private String unquote(String s) {
        if(s.startsWith("\"")) {
            assert s.endsWith("\"");
            return s.substring(1,s.length()-1);
        }
        return s;
    }

    public int compareTo(ExportedPackage that) {
        return this.name.compareTo(that.name);
    }
}
