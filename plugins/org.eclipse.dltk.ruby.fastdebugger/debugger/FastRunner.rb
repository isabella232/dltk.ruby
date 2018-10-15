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

require 'AbstractRunner'
                       
module XoredDebugger
    class FastRunner < AbstractRunner
        def create_debugger
            require 'fast/FastDebugger'
            FastDebugger.new() 
        end
        
        def load_libraries()
            super
            require 'rubygems'
            require 'ruby-debug-base.rb'
        end        
    end # class FastRunner
    
    FastRunner.new.run
end # XoredDebugger
