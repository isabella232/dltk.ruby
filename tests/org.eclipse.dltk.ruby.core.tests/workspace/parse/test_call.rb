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

class TestCall < Test::Unit::TestCase
  def aaa(a, b=100, *rest)
    res = [a, b]
    res += rest if rest
    return res
  end

  def test_call
    assert_raises(ArgumentError) {aaa()}
    assert_raises(ArgumentError) {aaa}

    assert_equal([1, 100], aaa(1))
    assert_equal([1, 2], aaa(1, 2))
    assert_equal([1, 2, 3, 4], aaa(1, 2, 3, 4))
    assert_equal([1, 2, 3, 4], aaa(1, *[2, 3, 4]))
  end
end
