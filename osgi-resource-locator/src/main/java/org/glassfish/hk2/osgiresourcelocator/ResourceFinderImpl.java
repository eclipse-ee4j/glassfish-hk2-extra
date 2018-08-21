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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjeeb.Sahoo@Sun.COM
 */
public class ResourceFinderImpl extends ResourceFinder {

    private BundleContext bundleContext;

    public ResourceFinderImpl() {
        ClassLoader cl = getClass().getClassLoader();
        if (cl instanceof BundleReference) {
            bundleContext = BundleReference.class.cast(cl).getBundle().getBundleContext();
        }
        if (bundleContext == null) {
            throw new RuntimeException("There is no bundle context available yet. " +
                    "Instatiate this class in STARTING or ACTIVE state only");
        }
    }

    URL findEntry1(String path) {
        for (Bundle bundle : bundleContext.getBundles()) {
            URL url = bundle.getEntry(path);
            if (url != null) return url;
        }
        return null;
    }

    List<URL> findEntries1(String path) {
        List<URL> urls = new ArrayList<URL>();
        for (Bundle bundle : bundleContext.getBundles()) {
            URL url = bundle.getEntry(path);
            if (url != null) urls.add(url);
        }
        return urls;
    }
}
