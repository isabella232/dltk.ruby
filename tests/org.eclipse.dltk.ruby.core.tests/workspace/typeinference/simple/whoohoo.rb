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
	def ultimate_answer
		42
	end
	
	def dining_philosopher
		ultimate_answer
	end
end

Foo.new.ultimate_answer ## expr Foo.new.ultimate_answer => Fixnum%
Foo.new.dining_philosopher ## expr Foo.new.dining_philosopher => Fixnum%
