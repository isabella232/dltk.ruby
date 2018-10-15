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

class Foo
def initialize
p 'Foo'
end

def foo_f
end
end

class Bar
def initialize
p 'Bar'
end

def bar_f
end
end

t = false

x = (Bar).new
x.


foo = Foo.new
bar = Bar.new


