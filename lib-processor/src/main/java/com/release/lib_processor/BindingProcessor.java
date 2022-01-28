package com.release.lib_processor;

import com.release.lib_annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class BindingProcessor extends AbstractProcessor {

    //操作文件的工具
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    /**
     *
     * @param annotations 一组Annotation数据
     * @param roundEnv 这一轮的上下文
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //Maintivity 相当于根元素 (并行多个类,就有多个根元素)
        for (Element rootElement : roundEnv.getRootElements()) {
            String packageStr = rootElement.getEnclosingElement().toString();
            String classStr = rootElement.getSimpleName().toString();
            //类的包名类名
            ClassName className = ClassName.get(packageStr, classStr + "Binding");
            MethodSpec.Builder contructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageStr, classStr), "activity");
            boolean hasBinding = false;
            //Maintivity中的成员
            for (Element enclosedElement : rootElement.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    BindView bindView = enclosedElement.getAnnotation(BindView.class);
                    if (bindView != null) {
                        hasBinding = true;
                        //增加不带返回值的语句  $N Name名字  $L Literal值
                        contructorBuilder.addStatement("activity.$N = activity.findViewById($L)",
                                enclosedElement.getSimpleName(), bindView.value());
                    }
                }
            }
            //创建类的信息
            TypeSpec buildClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(contructorBuilder.build()).build();
            if (hasBinding){
                try {
                    //创建此类
                    JavaFile.builder(packageStr,buildClass)
                            .build()
                            .writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}