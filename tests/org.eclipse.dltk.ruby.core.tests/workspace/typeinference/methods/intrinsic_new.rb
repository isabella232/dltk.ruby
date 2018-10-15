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


class MethodsIntrinsicNew
	class Bar
		def foo
			Foo.new ## expr Foo.new => MethodsIntrinsicNew{Foo%
		end
	end
	
	class Foo
	end
end
