package it.unisa.aDoctor.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:52
 * Description:
 */
public class InstanceVariableVisitor extends ASTVisitor {
    private final Collection<FieldDeclaration> instanceVariableNodes;
    private boolean firstTime;

    public InstanceVariableVisitor(Collection<FieldDeclaration> pInstanceVariableNodes) {
        this.instanceVariableNodes = pInstanceVariableNodes;
        this.firstTime = true;
    }

    public boolean visit(FieldDeclaration pInstanceVariableNode) {
        this.instanceVariableNodes.add(pInstanceVariableNode);
        return true;
    }

    public boolean visit(TypeDeclaration pClassNode) {
        if (this.firstTime) {
            this.firstTime = false;
            return true;
        } else {
            return this.firstTime;
        }
    }
}