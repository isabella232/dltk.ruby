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
		if 2 > 3
			boz(24)
		else
			boz(42)
		end
	end
	
	def boz(foo)
		foo
	end
end

Bar.new.func ## expr Bar.new.func => Fixnum%

