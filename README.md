### 相比原版aDoctor的改进
1. 未考虑enum枚举类的情况,跳过
2. SmellRule抽象类，统一Smell的共性功能
3. 以Java 8的语言规范进行AST构建，原来是Java 4,向上兼容
4. Eclipse和Android Studio两种版本下AndroidManifest.xml文件的快速检测
5. 对目标文件的快速定位
6. 