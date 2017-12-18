package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

public interface CodeSmellRule {
    boolean parser(ClassBean pClass);
}
