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


test = 3
class Boz
	test = 7
	class Bar
		test = 18
		def func
			test = "Abc"
			test = "Def"
			test ## localvar test => String%
		end
		test ## localvar test => Fixnum%
	end
end
