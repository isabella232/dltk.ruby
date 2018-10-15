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
	def doo
		creator.cool_method
	end
	
	def creator
		Bar.new
	end
	
end

class Bar
	
	def cool_method
		42
	end
	
end

class Baz
	def bobobo
		Bar.new.cool_method
		f = Foo.new
		f.doo
	end
end

Foo.new.creator ## expr Foo.new.creator => Bar%
Baz.new.bobobo ## expr Baz.new.bobobo => Fixnum%
