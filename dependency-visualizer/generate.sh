#!/bin/bash
#
# Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

# calls DotGenerator with the provided arguments to generate the DOT file

#  sample usage: 
# To generate a DOT file for bundles matching foo: ./generate.sh -i wires.xml -o foo.dot -m foo
foo=`grep -m 1 "<version>" pom.xml | sed "s%<version>%%" | sed "s%</version>%%"`
VERSION=`echo $foo`
java -cp target/hk2-dependency-visualizer-$VERSION-jar-with-dependencies.jar com.sun.enterprise.tools.visualizer.hk2.DotGenerator $*
