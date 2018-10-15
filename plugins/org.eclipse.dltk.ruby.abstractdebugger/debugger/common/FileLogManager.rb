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
	class FileLogManager
        def initialize(filename)
            @f = File.open(filename, 'w')
        end

        def puts(str)
            @f.puts(str)
            @f.flush
        end

        def close
            @f.close
        end
    end # class FileLogManager
end # module XoredDebugger
