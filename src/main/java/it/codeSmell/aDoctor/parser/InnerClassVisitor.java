package it.codeSmell.aDoctor.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:47
 * Description:
 */
public class InnerClassVisitor extends ASTVisitor {
    private final Collection<TypeDeclaration> innerClasses = new ArrayList();

    public boolean visit(TypeDeclaration typeDeclarationStatement) {
        if (!typeDeclarationStatement.isPackageMemberTypeDeclaration()) {
            this.innerClasses.add(typeDeclarationStatement);
        }
        return true;
    }

    public Collection<TypeDeclaration> getInnerClasses() {
        return this.innerClasses;
    }
}