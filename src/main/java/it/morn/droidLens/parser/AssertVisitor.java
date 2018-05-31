package it.morn.droidLens.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:45
 * Description:
 */
public class AssertVisitor extends ASTVisitor {
    private final List<AssertStatement> asserts;

    public AssertVisitor() {
        this.asserts = new ArrayList();
    }

    public boolean visit(AssertStatement node) {
        this.asserts.add(node);
        return super.visit(node);
    }

    public Collection<AssertStatement> getAsserts() {
        return this.asserts;
    }
}