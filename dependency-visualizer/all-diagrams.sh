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

#Generates visualizations for all the major containers in glassfish v3

# First all major containers
./subset.sh admin
./subset.sh admingui
./subset.sh appclient
#./subset.sh common
./subset.sh connector
./subset.sh core
./subset.sh deployment
./subset.sh ejb
#./subset.sh embedded
./subset.sh flashlight
./subset.sh install
#./subset.sh jbi
./subset.sh jdbc
./subset.sh jms
./subset.sh orb
./subset.sh persistence
#./subset.sh packager
./subset.sh security
./subset.sh transaction
#./subset.sh verifier
./subset.sh web
./subset.sh webservices
#./subset.sh extras

