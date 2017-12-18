package it.codeSmell.aDoctor.parser;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.InstanceVariableBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:46
 * Description:
 */
public class ClassSerializer {
    public ClassSerializer() {
    }

    public static void serialize(ClassBean pClassBean, String path) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(path + pClassBean.getName() + ".java");
        Throwable var3 = null;

        try {
            out.write("public class " + pClassBean.getName() + " {\n\n");
            Iterator var4 = pClassBean.getInstanceVariables().iterator();

            while(var4.hasNext()) {
                InstanceVariableBean instanceVariable = (InstanceVariableBean)var4.next();
                out.write("\t" + instanceVariable.getVisibility() + " " + instanceVariable.getType() + " " + instanceVariable.getName() + (instanceVariable.getInitialization() != null ? " = " + instanceVariable.getInitialization() : "") + ";\n");
            }

            out.write("\n");
            var4 = pClassBean.getMethods().iterator();

            while(var4.hasNext()) {
                MethodBean methodBean = (MethodBean)var4.next();
                out.write("\t" + methodBean.getTextContent().replace("\n  ", "\n\t\t").replace("}\n", "\t}\n") + "\n");
            }

            out.write("}");
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            if (out != null) {
                if (var3 != null) {
                    try {
                        out.close();
                    } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                    }
                } else {
                    out.close();
                }
            }

        }
    }

    public static void serialize(Collection<ClassBean> pClassBeans, String path) throws FileNotFoundException {
        Iterator var2 = pClassBeans.iterator();

        while(var2.hasNext()) {
            ClassBean classBean = (ClassBean)var2.next();
            serialize(classBean, path);
        }

    }
}