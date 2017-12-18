package it.codeSmell.aDoctor.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:46
 * Description:
 */
public class ClassVisitor extends ASTVisitor {
    private final Collection<TypeDeclaration> classNodes;

    public ClassVisitor(Collection<TypeDeclaration> pClassNodes) {
        this.classNodes = pClassNodes;
    }

    public boolean visit(TypeDeclaration pClassNode) {
        this.classNodes.add(pClassNode);
        return true;
    }
}