package com.processors;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.iannotation.ICodeLabsElement;
import com.iannotation.ICodeLabsElementProvider;
import com.iannotation.MultiHashMap;
import com.iannotation.model.CodeLabsData;
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
import static utils.Consts.NAME_OF_CODE_LABS_ELEMENT;
import static utils.Consts.PACKAGE_OF_GENERATE_FILE;

@AutoService(Processor.class)
public class ICodeLabsElementProcessor extends AbstractProcessor {
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
        types.add(ICodeLabsElement.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> codeLabsElements = roundEnvironment.getElementsAnnotatedWith(ICodeLabsElement.class);
        try {
            if (null != codeLabsElements && !codeLabsElements.isEmpty()) {
                parseCodeLabsElement(codeLabsElements);
            }
        } catch (IOException e) {
            printError(e.getMessage());
        }

        return true;
    }

    private void parseCodeLabsElement(Set<? extends Element> elements) throws IOException {

        TypeName classWithWildcard = ClassName.get(CodeLabsData.class);

        TypeName arrayList = ParameterizedTypeName.get(
                ClassName.get(MultiHashMap.class),
                ParameterizedTypeName.get(String.class),
                classWithWildcard);

        FieldSpec routerTable = FieldSpec
                .builder(arrayList, "elements", PUBLIC, FINAL, STATIC)
                .initializer(CodeBlock.builder().add("new $T<>()", ClassName.get(MultiHashMap.class)).build())
                .build();

        CodeBlock.Builder staticBlock = CodeBlock.builder();

        for (Element element : elements) {
            if (!SuperficialValidation.validateElement(element)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            ClassName fragment = ClassName.get(typeElement);
            ICodeLabsElement elementAnnotation = element.getAnnotation(ICodeLabsElement.class);

            String path = elementAnnotation.path();
            String parentId = elementAnnotation.parentId();
            String parentContent = elementAnnotation.parentContent();
            String id = elementAnnotation.id();
            String content = elementAnnotation.content();
            String classType = elementAnnotation.classType();

            staticBlock.add("elements.put($S, $T.build($S, $S, $S, $S, $S, $T.class, $S));\n",
                    path, ClassName.get(CodeLabsData.class), path, parentId, parentContent, id, content, fragment, classType);
        }


        TypeName listClass = ParameterizedTypeName.get(ClassName.get(ArrayList.class), classWithWildcard);

        MethodSpec provideMethod = MethodSpec.methodBuilder("getCodeLabsData")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Override.class).build())
                .addParameter(String.class, "url")
                .returns(listClass)
                .addStatement("return elements.get(url)")
                .build();

        TypeSpec codeLabsTableProvider = TypeSpec.classBuilder(NAME_OF_CODE_LABS_ELEMENT)
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get(ICodeLabsElementProvider.class))
                .addField(routerTable)
                .addStaticBlock(staticBlock.build())
                .addMethod(provideMethod)
                .build();

        JavaFile.builder(PACKAGE_OF_GENERATE_FILE, codeLabsTableProvider)
                .build()
                .writeTo(filer);
    }

    private void printError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }
}
