package it.unisa.aDoctor.parser;

import it.unisa.aDoctor.beans.InstanceVariableBean;
import it.unisa.aDoctor.beans.MethodBean;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:50
 * Description:
 */
public class MethodParser {
    public MethodParser() {
    }

    public static MethodBean parse(MethodDeclaration pMethodNode, Collection<InstanceVariableBean> pClassInstanceVariableBeans) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pMethodNode.getName().toString());
        methodBean.setParameters(pMethodNode.parameters());
        methodBean.setReturnType(pMethodNode.getReturnType2());
        methodBean.setTextContent(pMethodNode.toString());
        if (pMethodNode.toString().contains("static ")) {
            methodBean.setStatic(true);
        } else {
            methodBean.setStatic(false);
        }

        Collection<String> names = new HashSet();
        pMethodNode.accept(new NameVisitor(names));
        Collection<InstanceVariableBean> usedInstanceVariableBeans = getUsedInstanceVariable(names, pClassInstanceVariableBeans);
        methodBean.setUsedInstanceVariables(usedInstanceVariableBeans);
        Collection<String> invocations = new HashSet();
        pMethodNode.accept(new InvocationVisitor(invocations));
        Collection<MethodBean> invocationBeans = new ArrayList();
        Iterator var7 = invocations.iterator();

        while(var7.hasNext()) {
            String invocation = (String)var7.next();
            invocationBeans.add(InvocationParser.parse(invocation));
        }

        methodBean.setMethodCalls(invocationBeans);
        return methodBean;
    }

    private static Collection<InstanceVariableBean> getUsedInstanceVariable(Collection<String> pNames, Collection<InstanceVariableBean> pClassInstanceVariableBeans) {
        Collection<InstanceVariableBean> usedInstanceVariableBeans = new ArrayList();
        Iterator var3 = pClassInstanceVariableBeans.iterator();

        while(var3.hasNext()) {
            InstanceVariableBean classInstanceVariableBean = (InstanceVariableBean)var3.next();
            if (pNames.remove(classInstanceVariableBean.getName())) {
                usedInstanceVariableBeans.add(classInstanceVariableBean);
            }
        }

        return usedInstanceVariableBeans;
    }
}