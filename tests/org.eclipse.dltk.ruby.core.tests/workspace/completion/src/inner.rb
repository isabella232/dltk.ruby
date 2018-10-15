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


class Bar
	def func
		boz.dining_philosopher
	end
	
	def boz
		Foo42.new.dining_philosopher
		Foo42::
	end
end

class Foo42
	class Inner
	end
	
	def dining_philosopher
		ultimate_answer
	end

	def ultimate_answer
		42
	end

end