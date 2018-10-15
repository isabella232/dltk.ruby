###############################################################################
# Copyright (c) 2012 Tristan Hume
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0 which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# SPDX-License-Identifier: EPL-2.0
#

###############################################################################

# This script checks if the required debugger gem is installed
require "rubygems"
begin
  require 'ruby-debug-base'
  puts 'true'
rescue LoadError
  puts 'false'
end