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

module XoredDebugger    
    class Params
        attr_reader :host, :port, :key, :script, :log
        
        @@instance = nil
        def Params.instance
            if (@@instance.nil?)
                @@instance = Params.new
            end
            @@instance
        end
                 
        def initialize
            @host   = ENV['DBGP_RUBY_HOST']
            @port   = ENV['DBGP_RUBY_PORT'].to_i
            @key    = ENV['DBGP_RUBY_KEY']
            @script = $0
            @log    = ENV['DBGP_RUBY_LOG']
        end
        
        def valid?
            not (@host.nil? or @port == 0 or @key.nil? or @script.nil?)
        end
        
        def to_s
            res = ''
            res += "Time:   #{Time.new.to_s}\n"
            res += "Host:   #{@host.to_s}\n"
            res += "Port:   #{@port.to_s}\n"
            res += "Key:    #{@key.to_s}\n"
            res += "Script: #{@script.to_s}\n"
            res += "Test:   #{@test.to_s}\n"
			
			res += "Environment:\n"
			ENV.keys.sort.each do |key|
				res += "\t#{key}=#{ENV[key]}\n"
			end

			res += "Input args:\n"
			ARGV.each { |arg|
				res += "\t#{arg}\n"
			}

			res += "Include paths:\n"
			$:.each { |path|
				res += "\t#{path}\n"
			}
            res
        end
    end # class Params 
end # module XoredDebugger
