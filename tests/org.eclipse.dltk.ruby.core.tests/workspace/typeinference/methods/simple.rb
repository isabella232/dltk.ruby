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


class MethodsSimpleBoz
	class Bar
		def foo
			bar ## expr bar => String%
		end
		
		def bar
			"Foo"
		end
	end
end
