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

class A
	def functionA
		1
	end
end

class B < A
	def functionB
		2
	end
end

class C < B
	def functionC
		3
	end
end

class D < A
	def functionD
		4
	end
end