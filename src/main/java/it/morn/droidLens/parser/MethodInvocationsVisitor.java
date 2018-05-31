package it.morn.droidLens.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:54
 * Description:
 */
public class MethodInvocationsVisitor extends ASTVisitor {
    private final List<MethodInvocation> methods = new ArrayList();

    public boolean visit(MethodInvocation node) {
        this.methods.add(node);
        return super.visit(node);
    }

    public Collection<MethodInvocation> getMethods() {
        return this.methods;
    }
}