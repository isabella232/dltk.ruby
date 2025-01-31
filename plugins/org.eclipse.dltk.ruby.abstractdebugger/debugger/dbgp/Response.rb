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

require 'base64'
require 'dbgp/Command.rb'
require 'dbgp/XmlElement.rb'
require 'dbgp/ErrorElement.rb'

module XoredDebugger

    class Response < XmlElement
        def initialize(command)
            super('response')
            add_attribute('command', command.name)
            if (!command.transaction_id.nil?)
                add_attribute('transaction_id', command.transaction_id)
            end                      
            nil
        end
        
        def set_error(code)
           set_data(ErrorElement.new(code)) 
        end     
        
        def get_error
           data = get_data
           if (!data.nil? && (data.is_a? ErrorElement))
               return data.code
           else
               return 0
           end 
        end    
    end    
end # module
