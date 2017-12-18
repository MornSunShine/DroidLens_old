package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.parser.CodeParser;
import it.codeSmell.aDoctor.parser.ForStatementVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class SlowLoopRule implements CodeSmellRule {
    public boolean isSlowLoop(ClassBean pClassBean) {
        CodeParser parser = new CodeParser();
        CompilationUnit compilationUnit = parser.createParser(pClassBean.getTextContent());
        ForStatementVisitor forVisitor = new ForStatementVisitor();
        compilationUnit.accept(forVisitor);
        return !forVisitor.getForStatements().isEmpty();
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isSlowLoop(pClass);
    }
}