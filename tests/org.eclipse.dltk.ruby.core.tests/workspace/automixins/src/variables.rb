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

class Var
	
	@a = 1
	@@b = 2
	
	def foo
		@a = 4
		p @a
		p @@b		
	end
	
	def self.foo
		@b = 45
		p @a
		p @b
		p @@b		
	end
	
end

## get Var%{@a

## get Var{@a
## get Var{@b
## get Var{@@b

class Var2

	class Foo
		abc = 4
	end
	
	class << self
		def foo2
			abc2 = 5
		end
	end
	
	local = 34

end

## get Var2{local
## get Var2{Foo{abc
## get Var2{foo2{abc2

def global_method
	var = 34
end

## get Object%{global_method{var

def (::Var2).meta1
	@m1 = 33
	@@m2 = 455
end

## get Var2{@m1
## get Var2{@@m2
## get Var2%{@@m2
