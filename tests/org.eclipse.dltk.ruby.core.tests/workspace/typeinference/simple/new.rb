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


class FooNew

	def ultimate_answer
		42
	end
	
	def dining_philosopher
		ultimate_answer
	end

end

class BarNew
	def boz
		FooNew.new
	end
	
	def func
		boz.dining_philosopher
	end
end

FooNew.new.ultimate_answer ## expr FooNew.new.ultimate_answer => Fixnum%
FooNew.new.dining_philosopher ## expr FooNew.new.dining_philosopher => Fixnum%
BarNew.new.boz ## expr BarNew.new.boz => FooNew%
BarNew.new.func ## expr BarNew.new.func => Fixnum%

