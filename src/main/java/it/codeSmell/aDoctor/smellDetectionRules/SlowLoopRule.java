package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.parser.CodeParser;
import it.codeSmell.aDoctor.parser.ForStatementVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ForStatement;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: SL的检测规则
 */
public class SlowLoopRule implements CodeSmellRule {
    public String isSlowLoop(ClassBean pClassBean) {
        CodeParser parser = new CodeParser();
        CompilationUnit compilationUnit = parser.createParser(pClassBean.getTextContent());
        ForStatementVisitor forVisitor = new ForStatementVisitor();
        compilationUnit.accept(forVisitor);
        StringBuilder result=new StringBuilder();
        if(!forVisitor.getForStatements().isEmpty()){
            for(ForStatement forStatement:forVisitor.getForStatements()){
                result.append(String.valueOf(compilationUnit.getLineNumber(forStatement.getStartPosition())));
                result.append(';');
            }
            result.deleteCharAt(result.length()-1);
        }else{
            result.append("null");
        }
        return result.toString();
    }

    @Override
    public String parser(ClassBean pClass) {
        return isSlowLoop(pClass);
    }
}