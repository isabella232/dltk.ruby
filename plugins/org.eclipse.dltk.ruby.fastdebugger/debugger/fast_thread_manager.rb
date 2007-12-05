require 'dbgp/thread_manager'

class Thread
    class << self 
        @@debugger = nil
        
        def log(message)
            @@logger.log(message)
        end        
		
        alias __internal_ruby_debugger_new :new
		def new(*args, &block)
		    log('\\\\\\\\\\\\\\\\\\   creating APPLICATION thread   /////////////////////////')
		    thread = __internal_ruby_debugger_new do
		        # HACK: Without this sometimes debugger become stopped when
		        # thread exits. Especially, when thread exits with unhandled exception.  
		        Debugger.start
                
                @@debugger.current_context.suspend	
		        block.call(args)
		    end 
            @@debugger.thread_manager.add_thread(thread) 
		    thread
		end
        
	    alias start :new
	    alias fork :new
	       
	    def set_debugger(debugger)
	        @@debugger = debugger
	    end
	    def set_logger(new_logger)
	        @@logger = new_logger
	    end
    end
end

class Debugger::DebugThread < Thread
    class <<self 
	    def new(*args, &block)
	       log('========>>>>>>>>>>>> CREATING DEBUG THREAD <<<<<<<<<<<<<<<<<<=================')
	       __internal_ruby_debugger_new(*args, &block)
	    end
	    alias start :new
	    alias fork :new      
	end
end

module XoredDebugger
    
	class FastThreadManager < ThreadManager	    
	    def initialize(debugger)
            Thread.set_debugger(debugger)
            Thread.set_logger(self)	        
            super(debugger)	  
	    end                       
	end

end