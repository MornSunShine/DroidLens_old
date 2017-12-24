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
    public static void serialize(ClassBean pClassBean, String path) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(path + pClassBean.getName() + ".java");
        out.write("public class " + pClassBean.getName() + " {\n\n");
        for (InstanceVariableBean var : pClassBean.getInstanceVariables()) {
            out.write("\t" + var.getVisibility() +
                    " " + var.getType() +
                    " " + var.getName() +
                    (var.getInitialization() != null ? " = " + var.getInitialization() : "") + ";\n");
        }
        out.write("\n");
        for (MethodBean methodBean : pClassBean.getMethods()) {
            out.write("\t" + methodBean.getTextContent().
                    replace("\n  ", "\n\t\t").
                    replace("}\n", "\t}\n") + "\n");
        }
        out.write("}");
        out.close();
    }
    public static void serialize(Collection<ClassBean> pClassBeans, String path) throws FileNotFoundException {
        for (ClassBean classBean : pClassBeans) {
            serialize(classBean, path);
        }
    }
}