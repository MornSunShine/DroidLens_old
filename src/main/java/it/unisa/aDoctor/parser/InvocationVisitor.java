package it.unisa.aDoctor.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:51
 * Description:
 */
public class InvocationVisitor extends ASTVisitor {
    private final Collection<String> invocations;

    public InvocationVisitor(Collection<String> pInvocations) {
        this.invocations = pInvocations;
    }

    public boolean visit(MethodInvocation pInvocationNode) {
        this.invocations.add(pInvocationNode.getName().toString());
        return true;
    }

    public boolean visit(TypeDeclaration pClassNode) {
        return false;
    }

    private boolean isLocalInvocation(MethodInvocation pInvocationNode) {
        String invocation = pInvocationNode.toString();
        String invocationName = pInvocationNode.getName().toString();
        int index = invocation.indexOf(invocationName);
        if (index == 0) {
            return true;
        } else {
            return index >= 5 && invocation.substring(index - 5, index - 1).equals("this");
        }
    }
}