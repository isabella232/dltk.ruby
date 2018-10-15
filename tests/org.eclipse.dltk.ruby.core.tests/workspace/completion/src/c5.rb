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

class Test
def print
puts 'print!!!'
end
end

class Person
def Person.test
end

attr_accessor :first_name, :email
attr_accessor :last_name, :address

def initialize
@first_name = @last_name = @email = ""
end

def test
return Test.new
end
end

sandy = Person.new

sandy.first_name = "Sandy"
sandy.last_name = "Koh"


x = sandy.test()
x. 

