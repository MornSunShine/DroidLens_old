package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.parser.CodeParser;
import it.unisa.aDoctor.parser.ForStatementVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class SlowLoopRule {
    public SlowLoopRule() {
    }

    public boolean isSlowLoop(ClassBean pClassBean) throws IOException {
        CodeParser parser = new CodeParser();
        CompilationUnit compilationUnit = parser.createParser(pClassBean.getTextContent());
        ForStatementVisitor forVisitor = new ForStatementVisitor();
        compilationUnit.accept(forVisitor);
        return !forVisitor.getForStatements().isEmpty();
    }
}