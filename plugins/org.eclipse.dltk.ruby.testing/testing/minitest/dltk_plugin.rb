#
# Copyright (c) 2016 Red Hat Inc. and others
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Red Hat Inc. - initial API and Implementation
#

require 'minitest/autorun'
require 'minitest/spec'
require 'socket'

module Minitest
  module EnvVars
    # environment variable name to pass communication port number
    # to the launched script
    PORT = "RUBY_TESTING_PORT"
    PATH = "RUBY_TESTING_PATH"
  end

  module MessageIds
    # Notification that a test run has started.
    # MessageIds.TEST_RUN_START + testCount.toString + " " + version
    TEST_RUN_START = "%TESTC  "

    # Notification that a test run has ended.
    # TEST_RUN_END + elapsedTime.toString().
    TEST_RUN_END   = "%RUNTIME"

    # Notification about a test inside the test suite.
    # TEST_TREE + testId + "," + testName + "," + isSuite + "," + testcount
    # isSuite = "true" or "false"
    TEST_TREE      = "%TSTTREE"

    #Notification that a test has started.
    # MessageIds.TEST_START + testID + "," + testName
    TEST_START     = "%TESTS  "

    # Notification that a test has ended.
    # TEST_END + testID + "," + testName
    TEST_END       = "%TESTE  "

    # Notification that a test had a error.
    # TEST_ERROR + testID + "," + testName.
    # After the notification follows the stack trace.
    TEST_ERROR     = "%ERROR  "

    # Notification that a test had a failure.
    # TEST_FAILED + testID + "," + testName.
    # After the notification follows the stack trace.
    TEST_FAILED    = "%FAILED "

    # Notification that a test trace has started.
    # The end of the trace is signaled by a TRACE_END
    # message. In between the TRACE_START and TRACE_END
    # the stack trace is submitted as multiple lines.
    TRACE_START    = "%TRACES "

    # Notification that a trace ends.
    TRACE_END      = "%TRACEE "

    # Notification that the expected result has started.
    # The end of the expected result is signaled by a EXPECTED_END.
    EXPECTED_START = "%EXPECTS"

    # Notification that an expected result ends.
    EXPECTED_END   = "%EXPECTE"

    # Notification that the actual result has started.
    # The end of the actual result is signaled by a ACTUAL_END.
    ACTUAL_START   = "%ACTUALS"

    # Notification that an actual result ends.
    ACTUAL_END     = "%ACTUALE"

    #Test identifier prefix for ignored tests.
    IGNORED_TEST_PREFIX = "@Ignore: "

  end # of MessageIds

  def self.plugin_dltk_init(options)
    Minitest.reporter.reporters.clear
    self.reporter << DLTKReporter.new(options[:io], options)
  end

  class DLTKReporter < Minitest::StatisticsReporter
    def start
      connectSocket ENV[EnvVars::PORT].to_i
      @testsByName = {}
      DLTKReporter.sendMessage MessageIds::TEST_RUN_START + 0.to_s + " v2"
      super
    end

    def record(result)
      notifyTestError result if result.error?
      notifyTestFailure result if result.failure and !result.error?
      DLTKReporter.sendMessage MessageIds::TEST_END + result.class.name+result.name + "," + result.name
    end

    def report
      super
      DLTKReporter.sendMessage MessageIds::TEST_RUN_END + (@total_time.to_i * 1000).to_s
    end

    def connectSocket(port)
      return false unless port > 0
      #debug "Opening socket on #{port}"
      for i in 1..10
        #debug "Iteration #{i}"
        begin
          @@socket = TCPSocket.new('localhost', port)
          #debug "Socket opened"
          return true
        rescue
          #debug $!.to_s
        end
        sleep 1
      end
      false
    end

    def disconnect(result)
      if @@socket
        #debug "Closing socket"
        begin
          @@socket.close
        rescue
          debug $!.to_s
        end
        @@socket = nil
        #debug "Socket closed"
      end
    end

    def notifyTestFailure(result)
      DLTKReporter.sendMessage MessageIds::TEST_FAILED + result.class.name+result.name + "," + result.name
      DLTKReporter.sendMessage MessageIds::TRACE_START
      DLTKReporter.sendMessage result.failure.to_s
      DLTKReporter.sendMessage result.failure.location
      DLTKReporter.sendMessage MessageIds::TRACE_END
    end

    def notifyTestError(result)
      location, line = result.method(result.name).source_location
      DLTKReporter.sendMessage MessageIds::TEST_ERROR + result.class.name+result.name + "," + result.name
      DLTKReporter.sendMessage MessageIds::TRACE_START
      DLTKReporter.sendMessage result.failure.to_s
      DLTKReporter.sendMessage result.failure.location
      DLTKReporter.sendMessage MessageIds::TRACE_END
    end

    def self.sendMessage(message)
      #debug message
      if @@socket
        @@socket.puts message
      end
    end

  end

  class Runnable
    def self.run_one_method klass, method_name, reporter
      DLTKReporter.sendMessage MessageIds::TEST_START + klass.name+method_name + ',' + method_name
      reporter.record Minitest.run_one_method(klass, method_name)
    end
  end

end