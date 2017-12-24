package it.codeSmell.aDoctor.parser;

import it.codeSmell.aDoctor.beans.MethodBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:51
 * Description:
 */
public class InvocationParser {
    public static MethodBean parse(String pInvocationName) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pInvocationName);
        return methodBean;
    }
}