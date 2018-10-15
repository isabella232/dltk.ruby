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


class MethodsSimpleBoz2
	class Bar
		def foo
			bar ## expr bar => Fixnum%
		end
		
		def bar
			42
		end
	end
end
