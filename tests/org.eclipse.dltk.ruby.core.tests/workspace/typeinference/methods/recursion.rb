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

class FooRecursion
	def foo(x)
		if x > 0
			foo(x-1)
		else
			42
		end
	end
	
	def bar
		foo ## expr foo => Fixnum%
	end
end

