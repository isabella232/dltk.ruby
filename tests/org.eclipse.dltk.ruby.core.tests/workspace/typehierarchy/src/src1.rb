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

class AA
	def functionA
		1
	end
end

class BB < AA
	def functionB
		2
	end
end

class CC < BB
	def functionC
		3
	end
end

class DD < AA
	def functionD
		4
	end
end