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

class Args3

	def wow
		Usable.new.work (42, Args2Class.new)
	end

end


class Usable
	def work(x,y)
		xxx
		x ## expr x => Fixnum%
		xxx
		y ## expr y => Arg2Class% 
	end
end

class Arg2Class

	def use1
	
		z = Usable.new
		z.work (34, self)
	
	end

end

