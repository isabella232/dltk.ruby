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

class Foo66
	
	class <<self
		def cool(x,y,z)
			p "1"
		end
	end
	
	def self.cool2
		p "2"
		Foo66.new
	end
	
	def inst_meth
	end
	
end

Foo66.cool
Foo66.cool2
Foo66.cool2.