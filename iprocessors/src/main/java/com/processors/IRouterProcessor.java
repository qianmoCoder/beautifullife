package com.processors;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.iannotation.IRouter;
import com.iannotation.IRouterProvider;
import com.iannotation.model.RouteMeta;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static utils.Consts.NAME_OF_ROUTER;
import static utils.Consts.PACKAGE_OF_GENERATE_FILE;

@AutoService(Processor.class)
public class IRouterProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(IRouter.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routerElements = roundEnvironment.getElementsAnnotatedWith(IRouter.class);
        try {
            if (null != routerElements && !routerElements.isEmpty()) {
                parseRoutes(routerElements);
            }
        } catch (IOException e) {
            printError(e.getMessage());
        }

        return true;
    }

    private void parseRoutes(Set<? extends Element> routerElements) throws IOException {

        TypeName classWithWildcard = ClassName.get(RouteMeta.class);

        TypeName arrayList = ParameterizedTypeName.get(
                ClassName.get(ArrayList.class),
                classWithWildcard);

        FieldSpec routerTable = FieldSpec
                .builder(arrayList, "elements", PUBLIC, FINAL, STATIC)
                .initializer(CodeBlock.builder().add("new $T<>()", ClassName.get(ArrayList.class)).build())
                .build();

        CodeBlock.Builder staticBlock = CodeBlock.builder();

        for (Element element : routerElements) {
            if (!SuperficialValidation.validateElement(element)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            ClassName activity = ClassName.get(typeElement);
            IRouter elementAnnotation = element.getAnnotation(IRouter.class);

            String path = elementAnnotation.path();
            String text = elementAnnotation.text();
            String color = elementAnnotation.color();
            String description = elementAnnotation.description();

            staticBlock.add("elements.add($T.build($S, $S, $S, $S, $T.class));\n", ClassName.get(RouteMeta.class), path, text, color, description, activity);
        }


        TypeName listClass = ParameterizedTypeName.get(ClassName.get(ArrayList.class), classWithWildcard);

        MethodSpec provideMethod = MethodSpec.methodBuilder("getRouters")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Override.class).build())
                .returns(listClass)
                .addStatement("return elements")
                .build();

        TypeSpec routerTableProvider = TypeSpec.classBuilder(NAME_OF_ROUTER)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get(IRouterProvider.class))
                .addField(routerTable)
                .addStaticBlock(staticBlock.build())
                .addMethod(provideMethod)
                .build();

        JavaFile.builder(PACKAGE_OF_GENERATE_FILE, routerTableProvider)
                .build()
                .writeTo(filer);
    }

    private void printError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }
}
