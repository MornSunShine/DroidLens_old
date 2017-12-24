package it.codeSmell.aDoctor.parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:46
 * Description:
 */
public class CodeParser {
    private char[] charClass;
    private CompilationUnit unit;
    private ASTParser parser;
    public CodeParser(){}

    public CodeParser(String pClassToAnalyze) {
        this.charClass = pClassToAnalyze.toCharArray();
    }

    public CompilationUnit createParser() {
        this.parser = ASTParser.newParser(4);
        this.parser.setKind(8);
        this.parser.setSource(this.charClass);
        this.parser.setResolveBindings(true);
        return (CompilationUnit)this.parser.createAST(null);
    }

    public TypeDeclaration createParser(String pMethod, int pType) {
        this.parser = ASTParser.newParser(4);
        this.parser.setKind(pType);
        this.parser.setSource(pMethod.toCharArray());
        return (TypeDeclaration)this.parser.createAST(null);
    }

    public CompilationUnit createParser(String pClass) {
        this.parser = ASTParser.newParser(4);
        this.parser.setKind(8);
        this.parser.setSource(pClass.toCharArray());
        this.parser.setResolveBindings(true);
        return (CompilationUnit)this.parser.createAST(null);
    }

    public CompilationUnit getCompilationUnit() {
        return this.unit;
    }
}