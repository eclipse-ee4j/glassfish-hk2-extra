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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kohsuke Kawaguchi
 */
public class Lexer {
    private String str;

    public Lexer(String str) {
        this.str = str;
    }

    /**
     * Makes sure that we are looking at 's', and consumes it.
     */
    public String consume(String s) {
        if(!str.startsWith(s))
            throw new IllegalStateException("Expecting "+s+" bout found "+str);
        str = str.substring(s.length());
        return s;
    }

    private String consume(int len) {
        String r = str.substring(0,len);
        str = str.substring(len);
        return r;
    }

    public boolean at(Pattern p) {
        return p.matcher(str).lookingAt();
    }

    public String read(Pattern p) {
        Matcher m = p.matcher(str);
        if(m.lookingAt())
            return consume(m.group());
        else
            throw new IllegalStateException("Expecting "+p+" but found "+str);
    }

    /**
     * Read all the way until we find the given character.
     *
     * @return
     *      String without 'ch'.
     */
    public String readUntil(char ch) {
        if (str.indexOf(ch)==-1)
            return str;
        return consume(str.indexOf(ch));
    }

    public char nextChar() {
        return consume(1).charAt(0);
    }

    /**
     * Are we looking at this character?
     */
    public boolean at(char ch) {
        return !isEmpty() && str.charAt(0)==ch;
    }

    public boolean isEmpty() {
        return str.length()==0;
    }
}
