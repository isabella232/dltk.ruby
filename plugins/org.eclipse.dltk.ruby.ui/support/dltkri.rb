require 'rdoc'

class DLTKRi < RDoc::RI::Driver
  def display_name name
    out = RDoc::Markup::Document.new
    
    add_method out, name
    
    puts (out.accept formatter(RDoc::Markup::ToAnsi.new))
  end
end

endMarker = "DLTKDOCEND"
ri = DLTKRi.new(RDoc::RI::Driver.process_args(%w[-T --format=ansi --doc-dir=/usr/share/ri/system/]))
ri.use_stdout = true

while true do
	s = STDIN.gets
	if (s.nil?) 
	  break;
	end
	s = s.chop!
	begin
		ri.display_name(s)
		STDOUT.puts "\n" + endMarker
		STDOUT.flush
	rescue RDoc::RI::Driver::Error => e
		STDOUT.puts "#{e}"
		STDOUT.puts "\n" + endMarker
		STDOUT.flush
		next
	end
end
