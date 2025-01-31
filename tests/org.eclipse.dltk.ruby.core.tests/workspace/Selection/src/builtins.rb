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

class Class  < Module
	def new (*args)
	end
	
	def superclass ()
	end
	
	def allocate ()
	end
	
	class << ::Class
		def constants ()
		end
	
		def nesting ()
		end
	
	end
end


class Module  < Object
	def <=> (arg1)
	end
	
	def public_instance_methods (*args)
	end
	
	def private_method_defined? (arg1)
	end
	
	def == (arg1)
	end
	
	def class_eval (*args)
	end
	
	def === (arg1)
	end
	
	def constants ()
	end
	
	def instance_method (arg1)
	end
	
	def freeze ()
	end
	
	def instance_methods (*args)
	end
	
	def public_method_defined? (arg1)
	end
	
	def >= (arg1)
	end
	
	def module_eval (*args)
	end
	
	def autoload (arg1, arg2)
	end
	
	def < (arg1)
	end
	
	def <= (arg1)
	end
	
	def to_s ()
	end
	
	def included_modules ()
	end
	
	def private_instance_methods (*args)
	end
	
	def class_variables ()
	end
	
	def method_defined? (arg1)
	end
	
	def > (arg1)
	end
	
	def ancestors ()
	end
	
	def const_set (arg1, arg2)
	end
	
	def private_class_method (*args)
	end
	
	def autoload? (arg1)
	end
	
	def include? (arg1)
	end
	
	def protected_instance_methods (*args)
	end
	
	def protected_method_defined? (arg1)
	end
	
	def const_missing (arg1)
	end
	
	def name ()
	end
	
	def const_get (arg1)
	end
	
	def const_defined? (arg1)
	end
	
	def public_class_method (*args)
	end
	
	class << ::Module
		def constants ()
		end
	
		def nesting ()
		end
	
	end
end

class Object 
		
	include Kernel
	
	class << ::Object
	end
end


module Kernel
	def untaint ()
	end
	
	def kind_of? (arg1)
	end
	
	def id ()
	end
	
	def instance_variable_get (arg1)
	end
	
	def method (arg1)
	end
	
	def nil? ()
	end
	
	def inspect ()
	end
	
	def frozen? ()
	end
	
	def object_id ()
	end
	
	def taint ()
	end
	
	def public_methods (*args)
	end
	
	def =~ (arg1)
	end
	
	def __send__ (*args)
	end
	
	def to_a ()
	end
	
	def instance_eval (*args)
	end
	
	def extend (*args)
	end
	
	def clone ()
	end
	
	def protected_methods (*args)
	end
	
	def hash ()
	end
	
	def freeze ()
	end
	
	def is_a? (arg1)
	end
	
	def type ()
	end
	
	def instance_variable_set (arg1, arg2)
	end
	
	def respond_to? (*args)
	end
	
	def equal? (arg1)
	end
	
	def methods (*args)
	end
	
	def instance_of? (arg1)
	end
	
	def instance_variables ()
	end
	
	def display (*args)
	end
	
	def to_s ()
	end
	
	def dup ()
	end
	
	def private_methods (*args)
	end
	
	def == (arg1)
	end
	
	def send (*args)
	end
	
	def __id__ ()
	end
	
	def === (arg1)
	end
	
	def tainted? ()
	end
	
	def class ()
	end
	
	def eql? (arg1)
	end
	
	def singleton_methods (*args)
	end
	
	class << ::Kernel
		def Float (arg1)
		end
	
		def sleep (*args)
		end
	
		def split (*args)
		end
	
		def caller (*args)
		end
	
		def printf (*args)
		end
	
		def gsub! (*args)
		end
	
		def include? (arg1)
		end
	
		def private_instance_methods (*args)
		end
	
		def srand (*args)
		end
	
		def const_defined? (arg1)
		end
	
		def protected_method_defined? (arg1)
		end
	
		def name ()
		end
	
		def block_given? ()
		end
	
		def throw (*args)
		end
	
		def readline (*args)
		end
	
		def public_class_method (*args)
		end
	
		def autoload (arg1, arg2)
		end
	
		def sub (*args)
		end
	
		def loop ()
		end
	
		def String (arg1)
		end
	
		def callcc ()
		end
	
		def sprintf (*args)
		end
	
		def fork ()
		end
	
		def exit (*args)
		end
	
		def print (*args)
		end
	
		def instance_method (arg1)
		end
	
		def constants ()
		end
	
		def rand (*args)
		end
	
		def chop! ()
		end
	
		def trace_var (*args)
		end
	
		def scan (arg1)
		end
	
		def ancestors ()
		end
	
		def readlines (*args)
		end
	
		def select (*args)
		end
	
		def global_variables ()
		end
	
		def const_missing (arg1)
		end
	
		def private_class_method (*args)
		end
	
		def warn (arg1)
		end
	
		def exec (*args)
		end
	
		def gsub (*args)
		end
	
		def getc ()
		end
	
		def instance_methods (*args)
		end
	
		def Array (arg1)
		end
	
		def method_missing (*args)
		end
	
		def format (*args)
		end
	
		def method_defined? (arg1)
		end
	
		def abort (*args)
		end
	
		def putc (arg1)
		end
	
		def const_get (arg1)
		end
	
		def test (*args)
		end
	
		def chomp! (*args)
		end
	
		def binding ()
		end
	
		def eval (*args)
		end
	
		def untrace_var (*args)
		end
	
		def autoload? (arg1)
		end
	
		def to_s ()
		end
	
		def local_variables ()
		end
	
		def p (*args)
		end
	
		def module_eval (*args)
		end
	
		def class_variables ()
		end
	
		def ` (arg1)
		end
	
		def chop ()
		end
	
		def <=> (arg1)
		end
	
		def raise (*args)
		end
	
		def syscall (*args)
		end
	
		def < (arg1)
		end
	
		def == (arg1)
		end
	
		def public_instance_methods (*args)
		end
	
		def === (arg1)
		end
	
		def public_method_defined? (arg1)
		end
	
		def > (arg1)
		end
	
		def at_exit ()
		end
	
		def included_modules ()
		end
	
		def puts (*args)
		end
	
		def load (*args)
		end
	
		def const_set (arg1, arg2)
		end
	
		def trap (*args)
		end
	
		def >= (arg1)
		end
	
		def set_trace_func (arg1)
		end
	
		def proc ()
		end
	
		def exit! (*args)
		end
	
		def <= (arg1)
		end
	
		def Integer (arg1)
		end
	
		def class_eval (*args)
		end
	
		def system (*args)
		end
	
		def open (*args)
		end
	
		def chomp (*args)
		end
	
		def fail (*args)
		end
	
		def protected_instance_methods (*args)
		end
	
		def sub! (*args)
		end
	
		def private_method_defined? (arg1)
		end
	
		def gets (*args)
		end
	
		def freeze ()
		end
	
		def iterator? ()
		end
	
		def catch (arg1)
		end
	
		def require (arg1)
		end
	
		def lambda ()
		end
	
	end
end

