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

# runs the dot program against a DOT file to generate directed graphs

# $1 = dot file
# $2 = output JPG file

# sample usage: to create a jpeg image of foo.dot: ./rundot foo.dot foo.jpg

dot -Goverlap=false -Tjpg $1 -o $2 

#open the generated image
#eog $2 &
