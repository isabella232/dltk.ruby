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

class Class1
	class Class2
		
		#doc for foo
		def foo
			(bar)::Class2
			CGI
		end
		
		#doc for bar
		def bar
			c = Class2.new
			c.private_methods
			Class1
		end
	end	
end
n = Class1::Class2.new
n ## expr n => Class1{Class2%