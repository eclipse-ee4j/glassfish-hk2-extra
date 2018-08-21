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

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author Kohsuke Kawaguchi
 */
public class OSGiManifest {
    public final Set<ExportedPackage> exportPackages;

    public final Set<RequiredBundle> requiredBundles;

    public final Set<ImportedPackage> importedPackages;

    public final Set<String> privatePackages;

    public final String version;

    public final String symbolName;

    /**
     * Human-readable name of the bundle.
     */
    public final String name;

    public OSGiManifest(Manifest m) {
        Attributes atts = m.getMainAttributes();

        name = atts.getValue("Bundle-Name");
        version = atts.getValue("Bundle-Version");
        symbolName = atts.getValue("Bundle-SymbolicName");

        {
            Set<ImportedPackage> ipkgs = new TreeSet<ImportedPackage>();
            String ip = atts.getValue("Import-Package");
            if(ip!=null) {
                Lexer sc = new Lexer(ip);
                while(!sc.isEmpty())
                    ipkgs.add(new ImportedPackage(sc));
            }
            importedPackages = Collections.unmodifiableSet(ipkgs);
        }

        {
            String pp = atts.getValue("Private-Package");
            if(pp!=null)
            privatePackages = Collections.unmodifiableSet(new TreeSet<String>(Arrays.asList(pp.split(","))));
            else
                privatePackages = Collections.emptySet();
        }

        {
            Set<ExportedPackage> pkgs = new TreeSet<ExportedPackage>();
            String ep = atts.getValue("Export-Package");
            if(ep!=null) {
                Lexer sc = new Lexer(ep);
                while(!sc.isEmpty())
                    pkgs.add(new ExportedPackage(sc));
            }
            exportPackages = Collections.unmodifiableSet(pkgs);
        }

        {
            Set<RequiredBundle> bundles = new TreeSet<RequiredBundle>();
            String rb = atts.getValue("Require-Bundle");
            if(rb!=null) {
                Lexer sc = new Lexer(rb);
                while(!sc.isEmpty())
                    bundles.add(new RequiredBundle(sc));
            }
            requiredBundles = Collections.unmodifiableSet(bundles);
        }
    }
}
