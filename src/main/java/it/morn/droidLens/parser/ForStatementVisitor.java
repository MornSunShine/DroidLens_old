package it.morn.droidLens.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ForStatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:46
 * Description:
 */
public class ForStatementVisitor extends ASTVisitor {
    private final List<ForStatement> forStatements = new ArrayList();

    public boolean visit(ForStatement node) {
        this.forStatements.add(node);
        return super.visit(node);
    }

    public Collection<ForStatement> getForStatements() {
        return this.forStatements;
    }
}