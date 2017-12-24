package it.codeSmell.aDoctor.parser;

import it.codeSmell.aDoctor.beans.InstanceVariableBean;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:48
 * Description:
 */
public class InstanceVariableParser {
    public static InstanceVariableBean parse(FieldDeclaration pInstanceVariableNode) {
        InstanceVariableBean instanceVariableBean = new InstanceVariableBean();
        String visibility = getVisibilityModifier(pInstanceVariableNode);
        if (visibility != null) {
            instanceVariableBean.setVisibility(visibility);
        } else {
            instanceVariableBean.setVisibility("default");
        }

        instanceVariableBean.setType(pInstanceVariableNode.getType().toString());
        String[] fragments = pInstanceVariableNode.fragments().get(0).toString().split("=");
        instanceVariableBean.setName(fragments[0]);
        if (fragments.length == 1) {
            instanceVariableBean.setInitialization(null);
        } else {
            instanceVariableBean.setInitialization(fragments[1]);
        }
        return instanceVariableBean;
    }

    private static String getVisibilityModifier(FieldDeclaration pInstanceVariableNode) {
        for (Object modifier : pInstanceVariableNode.modifiers()) {
            if (modifier.equals("public")) {
                return "public";
            } else if (modifier.equals("protected")) {
                return "protected";
            } else if (modifier.equals("private")) {
                return "private";
            }
        }
        return null;
    }
}