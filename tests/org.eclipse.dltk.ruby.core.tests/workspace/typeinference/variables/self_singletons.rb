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


class BozSelfSingletons
	class Bar
		xxx
		self ## expr self => BozSelfSingletons{Bar
		
		def foo
			xxx
			self ## expr self => BozSelfSingletons{Bar%
		end
		
		def self.foo
			xxxx
			self ## expr self => BozSelfSingletons{Bar
		end
		
		def Bar.foo
			xxx
			self ## expr self => BozSelfSingletons{Bar
		end
	end
end
