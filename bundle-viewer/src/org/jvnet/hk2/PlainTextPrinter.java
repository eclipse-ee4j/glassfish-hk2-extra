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

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

/**
 * @author Kohsuke Kawaguchi
 */
public class PlainTextPrinter implements Printer {
    private final PrintStream out;

    public PlainTextPrinter(PrintStream out) {
        this.out = out;
    }

    public void print(OSGiManifest m) {
        out.println("Bundle-Name:         "+m.name);
        out.println("Bundle-Version:      "+m.version);
        out.println("Bundle-SymbolicName: "+m.symbolName);

        {
            out.println("Export-Packages:");
            int w = Named.getLongestName(m.exportPackages);
            for (ExportedPackage b : m.exportPackages) {
                out.print("  "+pad(b.name,w));
                if (b.include!=null) {
                    out.println();
                    out.print("    "+pad("include: " + b.include,w));
                }
                if(b.version!=null)
                    out.print(" "+b.version);
                out.println();
            }
        }

        {
            out.println("Import-Packages:");
            int w = Named.getLongestName(m.importedPackages);
            for (ImportedPackage b : m.importedPackages) {
                out.print("  "+pad(b.name,w));
                for (Map.Entry<String, Set<String>> resolution : b.resolutions.entrySet()) {
                    out.print("    resolution: " + resolution.getKey());
                    out.println();
                    for (String packageName : resolution.getValue()) {
                        out.print("      " + packageName);
                        out.println();
                    }
                }
                if(b.version!=null)
                    out.print(" "+b.version);
                out.println();
            }
        }

        out.println("Private-Packages:");
        printPackages(m.privatePackages);

        {
            out.println("Require-Bundles:");
            int w = Named.getLongestName(m.requiredBundles);
            for (RequiredBundle b : m.requiredBundles) {
                out.print("  "+pad(b.name,w));
                if(b.version!=null)
                    out.print(" "+b.version);
                out.println();
            }
        }

        out.println();
    }
    
    private void printPackages(Set<String> packages) {
        for (String pkg : packages) {
            out.println("  "+pkg);
        }
    }

    private String pad(String str, int width) {
        while(str.length()<width) {
            str += WHITESPACE.substring(0,Math.min(WHITESPACE.length(), width - str.length()));
        }
        return str;
    }

    private final String WHITESPACE = "                                                      ";
}
