package com.processors;

import com.google.auto.service.AutoService;
import com.iannotation.IElement;
import com.iannotation.MultiHashMap;
import com.iannotation.IElementProvider;
import com.iannotation.Tuple;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

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
import static utils.Consts.NAME_OF_ELEMENT;
import static utils.Consts.PACKAGE_OF_GENERATE_FILE;

@AutoService(Processor.class)
public class IElementProcessor extends AbstractProcessor {
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
        types.add(IElement.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(IElement.class);
        try {
            if (null != elements && !elements.isEmpty()) {
                parseRoutes(elements);
            }
        } catch (IOException e) {
            printError(e.getMessage());
        }

        return true;
    }

    private void parseRoutes(Set<? extends Element> elements) throws IOException {
        TypeName classWithWildcard = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(Object.class));

        TypeName tupleWithWildcard = ParameterizedTypeName.get(ClassName.get(Tuple.class),
                ParameterizedTypeName.get(String.class), classWithWildcard);

        TypeName hashMap = ParameterizedTypeName.get(
                ClassName.get(MultiHashMap.class),
                ParameterizedTypeName.get(String.class),
                tupleWithWildcard);

        FieldSpec routerTable = FieldSpec
                .builder(hashMap, "elements", PUBLIC, FINAL, STATIC)
                .initializer(CodeBlock.builder().add("new $T<>()", ClassName.get(MultiHashMap.class)).build())
                .build();

        CodeBlock.Builder staticBlock = CodeBlock.builder();

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            ClassName activity = ClassName.get(typeElement);
            IElement elementAnnotation = element.getAnnotation(IElement.class);
            String value = elementAnnotation.value();
            String description = elementAnnotation.description();

            staticBlock.add("elements.put($S,new $T($S, $T.class));\n", value, ClassName.get(Tuple.class), description, activity);
        }


        TypeName listClass = ParameterizedTypeName.get(ClassName.get(ArrayList.class), tupleWithWildcard);

        MethodSpec provideMethod = MethodSpec.methodBuilder("provide")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Override.class).build())
                .addParameter(String.class, "url")
                .returns(listClass)
                .addStatement("return elements.get(url)")
                .build();

        TypeSpec routerTableProvider = TypeSpec.classBuilder(NAME_OF_ELEMENT)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get(IElementProvider.class))
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
