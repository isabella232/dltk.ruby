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

#JRubySourceParser.HASH_FIXER1
a :b =>do
end
C.a :b =>do
end

a :b => do
end
C.a :b => do
end

a(:b =>)do
end
C.a(:b =>)do
end

a( :b => ) do
end
C.a( :b => ) do
end


#JRubySourceParser.HASH_FIXER2
j :k =>
C.j :k =>

j :k => 
C.j :k => 

j{k :l =>}
C.j{k :l =>}

j{k :l => }
C.j{k :l => }

j(k :l =>)
C.j(k :l =>)

j(k :l => )
C.j(k :l => )
