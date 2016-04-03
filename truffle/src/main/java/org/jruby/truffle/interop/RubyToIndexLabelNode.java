/*
 * Copyright (c) 2013, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.core.Layouts;
import org.jruby.truffle.language.RubyNode;

@NodeChild(value = "value", type = RubyNode.class)
public abstract class RubyToIndexLabelNode extends RubyNode {

    public RubyToIndexLabelNode(RubyContext context, SourceSection sourceSection) {
        super(context, sourceSection);
    }

    public abstract Object executeWithTarget(VirtualFrame frame, Object obj);

    @CompilerDirectives.TruffleBoundary
    @Specialization(guards = "isRubyString(index)")
    public Object doRubyString(DynamicObject index) {
        return index.toString();
    }

    @Specialization(guards = "isRubySymbol(index)")
    public Object doRubySymbol(DynamicObject index) {
        return Layouts.SYMBOL.getString(index);
    }

    @Specialization(guards = "!isRubySymbol(index)")
    public Object doObject(Object index) {
        return index;
    }

}
