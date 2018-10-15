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

def identity(x) 
        x
end

class Test
        def lenta(x)
                @temp = identity(x)
                @temp2 = identity(@temp)
                @temp3 = identity(@temp2)
                identity(@temp3)                                
        end

        def lenta2(x)
                @var = lenta(x)
                @x = lenta(@var)
                lenta(@x)                               
        end

        def lenta3
                puts 'xxxxxx'
        end

        def Test.xxx
                t = Test.new
                t = t.lenta(t).lenta2(t)                
        end     
end


t = identity(identity(identity(identity(Test.xxx))))
t.


