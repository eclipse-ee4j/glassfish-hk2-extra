/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.hk2.osgiresourcelocator;

import java.net.URL;
import java.util.List;

/**
 * This class provides helper methods to look up resources that are part of OSGi bundles,
 * but can't be exported. e.g., META-INF/mailcap file used by JavaMail.
 *
 * This class has been carefully coded to be loadable in non-OSGi environment.
 * When it is used in such an environment, various getENtry methods return null.
 *
 * @author Sanjeeb.Sahoo@Sun.COM
 */
public abstract class ResourceFinder {
    private static ResourceFinder _me;

    public static void initialize(ResourceFinder singleton) {
        if (singleton == null) throw new NullPointerException("Did you intend to call reset()?");
        if (_me != null) throw new IllegalStateException("Already initialzed with [" + _me + "]");
        _me = singleton;
    }

    public static synchronized void reset() {
        if (_me == null) {
            throw new IllegalStateException("Not yet initialized");
        }
        _me = null;
    }


    public static URL findEntry(String path) {
        if (_me == null) return null;
        return _me.findEntry1(path);
    }

    public static List<URL> findEntries(String path) {
        if (_me == null) return null;
        return _me.findEntries1(path);
    }

    /*package*/
    abstract URL findEntry1(String path);

    /*package*/
    abstract List<URL> findEntries1(String path);
}
