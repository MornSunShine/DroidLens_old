package it.unisa.aDoctor.parser;

import it.unisa.aDoctor.beans.MethodBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:51
 * Description:
 */
public class InvocationParser {
    public InvocationParser() {
    }

    public static MethodBean parse(String pInvocationName) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pInvocationName);
        return methodBean;
    }
}