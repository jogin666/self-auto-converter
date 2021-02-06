package com.zy.auto.converter.utils;

import com.zy.auto.converter.annotation.TargetConvertInfo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @Author Jong
 * @Date 2021/1/21 9:41
 */
public class ConverterCodeGenerator {

    private String targetClassSuffix;
    private String sourceClassSuffix;

    public void beginGenerate(Class<?> clazz,String targetClassSuffix,String sourceClassSuffix) {
        this.targetClassSuffix = targetClassSuffix;
        this.sourceClassSuffix = sourceClassSuffix;
        this.generateCode(clazz, "target", "source");
    }

    protected void generateCode(Class<?> clazz, String target, String source) {
        int layer = 1;
        String className = acquireTargetClassName(clazz.getSimpleName());
        String initName = acquireTargetClassName(clazz.getSimpleName());

        this.printlnCode("public static " + className + " convert(" + clazz.getSimpleName() + " source){", layer - 1);
        this.printlnCode("if(Objects.isNull(" + source + ")){", layer);
        this.printlnCode("return null;", layer + 1);
        this.printlnCode("}", layer);
        this.printlnCode(className + " " + target + " = new " + initName + "();", layer);
        generateCode(clazz, target, source, layer);
        this.printlnCode("return " + target + ";", layer);
        this.printlnCode("}", layer - 1);
    }

    protected void generateCode(Class<?> clazz, String target, String source, int layer) {
        if (layer > 10) {
            return;
        }
        if (Objects.isNull(clazz) || clazz.equals(Object.class)) {
            return;
        }
        //父类
        generateCode(clazz.getSuperclass(), target, source, layer);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class<?> targetFieldType = field.getType();

            String originFieldName = field.getName();
            String targetFiledName = field.getName();

            if (!field.isAnnotationPresent(TargetConvertInfo.class)) {
                continue;
            }

            TargetConvertInfo convertInfo = field.getAnnotation(TargetConvertInfo.class);
            boolean isClassInnerNotStatic = convertInfo.isInnerNotStatic();
            if (convertInfo.targetFieldName().length() > 0) {
                targetFiledName = convertInfo.targetFieldName();
            }

            String setMethodName = this.acquireTargetSetMethodName(targetFiledName);
            String getMethodName = this.acquireOriginGetMethodName(originFieldName);
            //基本类型
            if (judgeIfNotNeedComplexHandle(targetFieldType)) {
                this.printlnSimpleSetAndGetCode(target, setMethodName, getMethodName, source, layer);
            }
            //数组类型
            if (targetFieldType.isArray()) {
                handleArrayType(target, source, isClassInnerNotStatic, targetFieldType,
                        getMethodName, setMethodName, layer);
            }
            // 集合类型
            if (Collection.class.isAssignableFrom(targetFieldType)) {
                handleCollection(field, target, source, isClassInnerNotStatic, targetFieldType,
                        getMethodName, setMethodName, layer);
            }
        }
    }

    protected void handleArrayType(String target, String source, boolean isClassInnerNotStatic, Class<?> targetFieldType,
                                   String getMethodName, String setMethodName, int layer) {

        Class<?> arrayType = targetFieldType.getComponentType();
        if (judgeIfNotNeedComplexHandle(arrayType)) {
            this.printlnSimpleSetAndGetCode(target, setMethodName, getMethodName, source, layer);
            return;
        }

        String subTargetClassName = acquireTargetClassName(arrayType.getSimpleName());
        String subTargetClassFullName = acquireTargetClassName(arrayType.getName());
        String subSource = source + "." + getMethodName + "()";
        String varName = "list" + subTargetClassName;
        String lamdaTarget = acquireTargetSetVarName(subTargetClassName);
        String lamdaSource = "n" + layer;

        this.printlnCode("if(!Objects.isNull(" + subSource + ") && " + subSource + ".length != 0){", layer);
        this.printlnCode("List<" + subTargetClassFullName + "> " + varName + " = Stream.of(" + subSource
                                                                        + ").map(" + lamdaSource + " ->{", layer + 1);

        if (arrayType.getName().contains("$") && isClassInnerNotStatic) {
            // 动态内部类
            this.printlnCode(subTargetClassFullName + " " + lamdaTarget + "=" + target + ".new " + subTargetClassName + "();", layer + 2);
        } else {
            // 外部类或者静态内部类
            this.printlnCode(subTargetClassFullName + " " + lamdaTarget + "=" + "new " + subTargetClassName + "();", layer + 2);
        }
        generateCode(arrayType, lamdaTarget, lamdaSource, layer + 2);
        this.printlnCode("return " + lamdaTarget + ";", layer + 2);
        this.printlnCode("}).collect(Collectors.toList());", layer + 1);
        this.printlnCode(subTargetClassFullName + "[] array = " + varName + ".toArray(new " + subTargetClassFullName + " []{});", layer + 1);
        this.printlnCode(target + "." + setMethodName + "(array);", layer + 1);
        this.printlnCode("}", layer);
    }

    protected void handleCollection(Field field, String target, String source, boolean isClassInnerNotStatic, Class<?> targetFieldType,
                                    String getMethodName, String setMethodName, int layer) {

        //获取泛型
        Type genericType = field.getGenericType();
        if (Objects.isNull(genericType)) {
            return;
        }
        // 如果是泛型参数的类型
        if (genericType instanceof ParameterizedType) {
            // 获取泛型的真实类型
            ParameterizedType pt = (ParameterizedType) genericType;
            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];

            if (judgeIfNotNeedComplexHandle(genericClazz)) {
                this.printlnSimpleSetAndGetCode(target, setMethodName, getMethodName, source, layer);
                return;
            }
            String subTargetClassFullName = acquireTargetClassName(genericClazz.getName());
            String subTargetClassName = acquireTargetClassName(genericClazz.getSimpleName());

            //默认 List
            String subTarget = "list" + subTargetClassName;
            String codeStartStr = "List<" + subTargetClassFullName + "> " + subTarget + ";";
            String codeEndStr = "}).collect(Collectors.toList());";

            if (Set.class.isAssignableFrom(targetFieldType)) {
                subTarget = "set" + subTargetClassName;
                codeStartStr = "Set<" + subTargetClassFullName + "> " + subTarget + ";";
                codeEndStr = "}).collect(Collectors.toSet());";
            }
            String subSource = source + "." + getMethodName + "()";
            String lamdaTarget = acquireTargetSetVarName(subTargetClassName);
            String lamdaSource = "n" + layer;

            this.printlnCode("if(!Objects.isNull(" + subSource + ") && !" + subSource + ".isEmpty()){", layer);
            this.printlnCode(codeStartStr, layer + 1);
            this.printlnCode(subTarget + "=" + subSource + ".stream().map(" + lamdaSource + "-> {", layer + 1);
            if (genericClazz.getName().contains("$") && isClassInnerNotStatic) {
                // 动态内部类
                this.printlnCode(subTargetClassFullName + " " + lamdaTarget + "=" + target + ".new " + subTargetClassName + "();", layer + 2);
            } else {
                //外部类或者静态内部类
                this.printlnCode(subTargetClassFullName + " " + lamdaTarget + "=" + "new " + subTargetClassName + "();", layer + 2);
            }
            generateCode(genericClazz, lamdaTarget, lamdaSource, layer + 2);
            this.printlnCode("return " + lamdaTarget + ";", layer + 2);
            this.printlnCode(codeEndStr, layer + 1);
            this.printlnSimpleSetAndGetCode(target, setMethodName, subTarget, layer + 1);
            this.printlnCode("}", layer);
        }
    }

    protected void handleClassType() {
        // TODO
    }

    protected void handleMapType() {
        // TODO
    }


    protected void printlnCode(String code, int layer) {
        for (int i = 0; i < layer; i++) {
            System.out.printf("  ");
        }
        System.out.println(code);
    }

    protected boolean judgeIfNotNeedComplexHandle(Class<?> targetFieldType) {
        return targetFieldType.isPrimitive()
                || targetFieldType.equals(String.class)
                || targetFieldType.equals(Date.class)
                || targetFieldType.equals(Boolean.class)
                || Number.class.isAssignableFrom(targetFieldType);

    }

    protected void printlnSimpleSetAndGetCode(String target, String setMethodName, String getMethodName, String source, int layer) {
        this.printlnCode(target + "." + setMethodName + "(" + source + "." + getMethodName + "());", layer);
    }

    protected void printlnSimpleSetAndGetCode(String target, String setMethodName, String var, int layer) {
        this.printlnCode(target + "." + setMethodName + "(" + var + ");", layer);
    }

    protected String acquireTargetSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    protected String acquireTargetSetVarName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    protected String acquireOriginGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    protected String acquireTargetClassName(String className) {
        if (className.contains(".")) {
            String[] classNames = className.split("\\.");
            String realClassName = classNames[classNames.length - 1];
            // 内部类
            if (realClassName.contains("$")) {
                String[] realClassNames = realClassName.split("\\$");
                StringBuilder result = new StringBuilder();
                for (String name : realClassNames) {
                    if (name.endsWith(sourceClassSuffix)) {
                        result.append(name, 0, name.length() - sourceClassSuffix.length()).append(targetClassSuffix).append(".");
                    }
                    result.append(name).append(targetClassSuffix).append(".");
                }
                return result.substring(0, result.length() - 1);
            }
        }
        if (className.endsWith(sourceClassSuffix)) {
            return className.substring(0, className.length() - sourceClassSuffix.length()) + targetClassSuffix;
        }
        return className + targetClassSuffix;
    }

}
