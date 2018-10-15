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

class Abc
end

class XYZ
	
	include Abc

	class Wow
	end
end

## get Abc
## get XYZ
## get XYZ{Wow

class ::Global1
	class ::Global2
		class ::XYZ::Wow::Subwow
		end
	end
end

## get Global1
## get Global2
## get XYZ{Wow{Subwow


module MegaModule
	def megamethod
	end
end

## get MegaModule%{megamethod