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

class Foo
	
	@abx = 45
	
	@@clvar = 42

	def cool
		@abx = "ab"
		@@clvar = "be" 
	end
	
	@abx ## expr @abx => Fixnum%
	
	@@clvar ## expr @@clvar => Fixnum%
		

end

class Foo

	def wow(a,x)
		@abx ## expr @abx => String%
		@@clvar ## expr @@clvar => String%
	end

end