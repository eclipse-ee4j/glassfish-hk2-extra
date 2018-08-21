#!/bin/sh -x
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

echo Change the maven local repo path as per your environment
MVN_LOCAL_REPO=$HOME/.m2/repository

# Pick the current version. something like 1.0.13-SNAPSHOT
foo=`grep -m 1 "<version>" pom.xml | sed "s%<version>%%" | sed "s%</version>%%"`
VERSION=`echo $foo`
CLASSPATH=$MVN_LOCAL_REPO/org/glassfish/hk2/hk2-dependency-verifier/$VERSION/hk2-dependency-verifier-$VERSION.jar:$MVN_LOCAL_REPO/org/glassfish/hk2/hk2-api/$VERSION/hk2-api-$VERSION.jar:$MVN_LOCAL_REPO/org/glassfish/hk2/hk2-core/$VERSION/hk2-core-$VERSION.jar:$MVN_LOCAL_REPO/org/glassfish/hk2/auto-depends/$VERSION/auto-depends-$VERSION.jar:$MVN_LOCAL_REPO/org/glassfish/hk2/osgi-adapter/$VERSION/osgi-adapter-$VERSION.jar:$MVN_LOCAL_REPO/org/apache/bcel/bcel/5.2/bcel-5.2.jar:$MVN_LOCAL_REPO/org/osgi/org.osgi.core/4.2.0/org.osgi.core-4.2.0.jar

java $JDEBUG -ea -DExcludedPatterns="javax." -cp $CLASSPATH -DdebugOutput=/tmp/closure.txt com.sun.enterprise.tools.verifier.hk2.PackageAnalyser $*
