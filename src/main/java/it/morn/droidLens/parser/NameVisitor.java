package it.morn.droidLens.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:51
 * Description:
 */
public class NameVisitor extends ASTVisitor {
    private final Collection<String> names;

    public NameVisitor(Collection<String> pNames) {
        this.names = pNames;
    }

    public boolean visit(SimpleName pNameNode) {
        this.names.add(pNameNode.toString());
        return true;
    }

    public boolean visit(TypeDeclaration pClassNode) {
        return false;
    }
}