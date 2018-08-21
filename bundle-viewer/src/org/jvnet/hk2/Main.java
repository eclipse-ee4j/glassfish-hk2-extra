/*
 * Copyright (c) 2008, 2018 Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Kohsuke Kawaguchi
 */
public class Main {
    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            if(args.length>1) {
                System.out.println("=== "+arg);
            }
            dump(new File(arg));
        }
    }

    public static void dump(File f) throws IOException {
        JarFile jar = new JarFile(f);
        Manifest m = jar.getManifest();
        dump(m);
        jar.close();
    }

    public static void dump(Manifest m) throws IOException {
        OSGiManifest manifest = new OSGiManifest(m);
        new PlainTextPrinter(System.out).print(manifest);
    }
}
