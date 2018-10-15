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
		test = biz(15)
		boz(test)
	end
	
	def boz(foo)
		foo
	end
	
	def biz(foo)
		foo
	end

end

Bar.new.biz(0) ## expr Bar.new.biz(0) => Fixnum%
Bar.new.boz(42) ## expr Bar.new.boz(42) => Fixnum%
Bar.new.func ## expr Bar.new.func => Fixnum%
