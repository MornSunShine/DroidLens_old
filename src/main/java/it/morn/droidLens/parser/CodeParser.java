package it.morn.droidLens.parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:46
 * Description: 创建代码解析器，通过Class的路径，结合
 */
public class CodeParser {
    private char[] charClass;
    private CompilationUnit unit;
    private ASTParser parser;
    public CodeParser(){}

    /**
     *
     * @param pClassToAnalyze
     */
    public CodeParser(String pClassToAnalyze) {
        this.charClass = pClassToAnalyze.toCharArray();
    }

    /**
     * 创建解析器
     * @return
     */
    public CompilationUnit createParser() {
        this.parser = ASTParser.newParser(AST.JLS8);
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
        this.parser.setSource(this.charClass);
        this.parser.setResolveBindings(true);
        return (CompilationUnit)this.parser.createAST(null);
    }

    /**
     *
     * @param pMethod
     * @param pType
     * @return
     */
    public TypeDeclaration createParser(String pMethod, int pType) {
        this.parser = ASTParser.newParser(AST.JLS8);
        this.parser.setKind(pType);
        this.parser.setSource(pMethod.toCharArray());
        return (TypeDeclaration)this.parser.createAST(null);
    }

    /**
     *
     * @param pClass
     * @return
     */
    public CompilationUnit createParser(String pClass) {
        this.parser = ASTParser.newParser(AST.JLS8);
        this.parser.setKind(ASTParser.K_COMPILATION_UNIT);
        this.parser.setSource(pClass.toCharArray());
        this.parser.setResolveBindings(true);
        return (CompilationUnit)this.parser.createAST(null);
    }

    public CompilationUnit getCompilationUnit() {
        return this.unit;
    }
}