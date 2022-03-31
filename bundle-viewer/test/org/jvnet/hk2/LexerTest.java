/*
 * Copyright (c) 2022 Contributors to Eclipse Foundation. All rights reserved.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class LexerTest {
    private static String KNOWN_EXPORTS_ENTRY = "jakarta.ejb.spi;uses:=\"jakarta.ejb,jakarta.ejb.embeddable\";version=\"4.0.0\",jakarta.ejb;uses:=\"jakarta.transaction\";version=\"4.0.0\",jakarta.ejb.embeddable;uses:=\"jakarta.ejb.spi,jakarta.ejb,javax.naming\";version=\"4.0.0\"";

    @Test
    void shouldFind3ExportedPackages() {
        Lexer lexer = new Lexer(KNOWN_EXPORTS_ENTRY);

        List<ExportedPackage> packages = new ArrayList<ExportedPackage>();
        while (!lexer.isEmpty()) {
            packages.add(new ExportedPackage(lexer));
        }

        assertEquals(3, packages.size());
    }

    @Test
    void shouldFind1stExportedPackage() {
        testFoundExportedPackage(KNOWN_EXPORTS_ENTRY, 0, "jakarta.ejb.spi",
                Arrays.asList("jakarta.ejb", "jakarta.ejb.embeddable"), "4.0.0");

    }

    @Test
    void shouldFind2ndExportedPackage() {
        testFoundExportedPackage(KNOWN_EXPORTS_ENTRY, 1, "jakarta.ejb",
                Arrays.asList("jakarta.transaction"), "4.0.0");
    }

    @Test
    void shouldFind3rdExportedPackage() {
        testFoundExportedPackage(KNOWN_EXPORTS_ENTRY, 2, "jakarta.ejb.embeddable",
                Arrays.asList("jakarta.ejb.spi", "jakarta.ejb", "javax.naming"), "4.0.0");
    }

    private void testFoundExportedPackage(String manifestEntry, int packageIndex, String expectedName,
                                          Collection<String> expectedUses, String expectedVersion) {
        Lexer lexer = new Lexer(manifestEntry);

        List<ExportedPackage> packages = new ArrayList<ExportedPackage>();
        while (!lexer.isEmpty()) {
            packages.add(new ExportedPackage(lexer));
        }

        ExportedPackage testedPackage = packages.get(packageIndex);

        assertEquals(expectedName, testedPackage.name);
        assertTrue(testedPackage.uses.containsAll(expectedUses));
        assertEquals(expectedVersion, testedPackage.version);
    }
}

