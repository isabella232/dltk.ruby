###############################################################################
# Copyright (c) 2005, 2007 IBM Corporation and others.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0 which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# SPDX-License-Identifier: EPL-2.0
#

###############################################################################

class SomeExt
	def SomeExt.foo
	end
end

## get SomeExt{foo

class MAbc
end

def MAbc.meta1
end

def MAbc::meta2
end

## get MAbc{meta1
## get MAbc{meta2
