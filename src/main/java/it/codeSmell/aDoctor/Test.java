package it.codeSmell.aDoctor;

import it.codeSmell.aDoctor.ui.SmellData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/12/17
 * Time: 21:14
 * Description:
 */
public class Test {
    interface C{
        void run();
    }

    class A implements C {
        public void a(){
            System.out.println("A");
        }

        @Override
        public void run(){
            a();
        }
    }

    class B implements C {
        public void b(){
            System.out.println("B");
        }

        @Override
        public void run(){
            b();
        }
    }

    public void test(){
        ArrayList<C> temp=new ArrayList();
        temp.add(new A());
        temp.add(new B());
        temp.get(0).run();
        temp.get(1).run();
    }

    public static void main(String[] args){
        ArrayList<Object> data=new ArrayList<>();
        for(int i=0;i<5;i++){
            data.add("a");
        }
        System.out.println(data.get(0).equals("a"));
    }
}
