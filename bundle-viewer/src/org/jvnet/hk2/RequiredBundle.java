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

import java.util.regex.Pattern;

/**
 * @author Kohsuke Kawaguchi
 */
public final class RequiredBundle extends Named implements Comparable<RequiredBundle> {
    /**
     * Version constraint.
     */
    public final String version;

    /**
     * Resolution.
     *
     * TODO: what are possible values?
     */
    public final String resolution;

    public final String visibility;

    RequiredBundle(Lexer sc) {
        super(sc);

        String version=null,resolution=null,visibility=null;

        while(true) {
            if(sc.at(BUNDLE_VERSION)) {
                sc.read(BUNDLE_VERSION);
                version = sc.readUntil('\"');
                sc.consume("\"");
                continue;
            }
            if(sc.at(RESOLUTION)) {
                sc.read(RESOLUTION);
                resolution = sc.read(TOKEN);
                continue;
            }
            if(sc.at(VISIBILITY)) {
                sc.read(VISIBILITY);
                visibility = sc.read(TOKEN);
                continue;
            }
            break;
        }

        this.version = version;
        this.resolution = resolution;
        this.visibility = visibility;

        if(sc.at(','))
            sc.consume(",");
    }

    public int compareTo(RequiredBundle that) {
        return this.name.compareTo(that.name);
    }

    static final Pattern PACKAGE = Pattern.compile("[^,;]+");
    static final Pattern BUNDLE_VERSION = Pattern.compile(";bundle-version=\"");
    static final Pattern RESOLUTION = Pattern.compile(";resolution:=");
    static final Pattern VISIBILITY = Pattern.compile(";visibility:=");
    static final Pattern TOKEN = Pattern.compile("[A-Za-z]+");
}
