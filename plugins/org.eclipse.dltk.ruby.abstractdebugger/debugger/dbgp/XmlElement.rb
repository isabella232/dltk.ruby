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
require 'cgi'
require 'dbgp/ErrorMessages.rb'

module XoredDebugger

    class XmlElement
        def initialize(name)
            @name = name
            @attributes = {}
            @data = nil
            @encode_data = true
            nil
        end
        
        def add_attribute(name, value)
            @attributes[name.to_s] = value.to_s
            nil
        end
        
        def get_attribute(name)
            @attributes[name.to_s]
        end

        def remove_attribute(name)
            @attributes.delete(name.to_s) 
        end
        
        def set_data(data, encode = false)
            @data = data
            @encode_data = encode
            nil
        end
        
        def get_data()
            @data
        end                                      
        
        def to_xml
            if (@data.nil?)
                sprintf(PACKET_WITHOUT_DATA_TEMPLATE, @name, attributes_xml) 
            else                    
                sprintf(PACKET_WITH_DATA_TEMPLATE, @name, attributes_xml, 
                    prepare_data(@data), @name)                                
            end               
        end

        alias to_s :to_xml   
                    
    protected    
	    PACKET_WITH_DATA_TEMPLATE = '<%s%s>%s</%s>'
	    PACKET_WITHOUT_DATA_TEMPLATE = '<%s%s/>'
        
        def attributes_xml
            result = ''
            @attributes.each { |name, value| result += sprintf(' %s="%s"', name, CGI::escapeHTML(value)) }
            result
        end
        
        def prepare_data(data)
            if (@encode_data)
            	encoded = Base64.encode64(data.to_s)
            	encoded.chomp!
                return '<![CDATA[' + encoded + ']]>'
            else 
                data
            end
        end        
    end    
end # module
