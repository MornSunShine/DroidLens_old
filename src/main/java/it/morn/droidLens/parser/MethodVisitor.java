package it.morn.droidLens.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:48
 * Description:
 */
public class MethodVisitor extends ASTVisitor {
    private final Collection<MethodDeclaration> methodNodes;
    private boolean firstTime;

    public MethodVisitor(Collection<MethodDeclaration> pMethodNodes) {
        this.methodNodes = pMethodNodes;
        this.firstTime = true;
    }

    public boolean visit(MethodDeclaration pMethodNode) {
        this.methodNodes.add(pMethodNode);
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