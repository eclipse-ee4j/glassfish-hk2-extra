<?xml version="1.0" encoding="ISO-8859-1"?>
<!--

    Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

-->

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
    <h2>Package wiring details</h2>
    <table border="1">
    <tr bgcolor="#9acd32">
      <th align="left">Package</th>
      <th align="left">Version</th>
      <th align="left">Exporter(s)</th>
      <th align="left">Importer(s)</th>
    </tr>
    <xsl:for-each select="Wires/Package">
    <tr>
      <td><xsl:value-of select="@name"/></td>
      <td><xsl:value-of select="@version"/></td>
      <td><xsl:value-of select="Exporters"/></td>
      <td><xsl:value-of select="Importers"/></td>
    </tr>
    </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>
