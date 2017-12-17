package it.unisa.aDoctor.parser;

import it.unisa.aDoctor.beans.InstanceVariableBean;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:48
 * Description:
 */
public class InstanceVariableParser {
    public InstanceVariableParser() {
    }

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
            instanceVariableBean.setInitialization((String)null);
        } else {
            instanceVariableBean.setInitialization(fragments[1]);
        }

        return instanceVariableBean;
    }

    private static String getVisibilityModifier(FieldDeclaration pInstanceVariableNode) {
        Iterator it = pInstanceVariableNode.modifiers().iterator();

        while(it.hasNext()) {
            String modifier = it.next().toString();
            byte var4 = -1;
            switch(modifier.hashCode()) {
                case -977423767:
                    if (modifier.equals("public")) {
                        var4 = 2;
                    }
                    break;
                case -608539730:
                    if (modifier.equals("protected")) {
                        var4 = 1;
                    }
                    break;
                case -314497661:
                    if (modifier.equals("private")) {
                        var4 = 0;
                    }
            }

            switch(var4) {
                case 0:
                    return "private";
                case 1:
                    return "protected";
                case 2:
                    return "public";
            }
        }

        return null;
    }
}