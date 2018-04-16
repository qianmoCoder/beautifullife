package com.processors;

import com.google.auto.service.AutoService;
import com.iannotation.ContentType;
import com.iannotation.MultiHashMap;
import com.iannotation.Provider;
import com.iannotation.Router;
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

@AutoService(Processor.class)
public class IProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    private int moduleAnnotationSize = 0;

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
        types.add(ContentType.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> urlAnnotations = roundEnvironment.getElementsAnnotatedWith(ContentType.class);
        Set<? extends Element> moduleAnnotations = roundEnvironment.getElementsAnnotatedWith(Router.class);
        moduleAnnotationSize += moduleAnnotations.size();

        for (Element element : moduleAnnotations) {
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            try {
                generateRoutingTable(packageName, element.getSimpleName().toString(), urlAnnotations);
            } catch (IOException e) {
                printError(e.getMessage());
            }
        }

        if (roundEnvironment.processingOver()) {
            if (moduleAnnotationSize == 0) {
                printError("You need to add a class that is annotated by @Router to your module!");
            }
        }

        return true;
    }

    private void generateRoutingTable(String packageName, String className, Set<? extends Element> urlAnnotations) throws IOException {


        TypeName classWithWildcard = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(Object.class));

        TypeName listClass = ParameterizedTypeName.get(ClassName.get(ArrayList.class), classWithWildcard);

        TypeName hashMap = ParameterizedTypeName.get(
                ClassName.get(MultiHashMap.class),
                ParameterizedTypeName.get(String.class),
                classWithWildcard);

        FieldSpec routerTable = FieldSpec
                .builder(hashMap, "table", PUBLIC, FINAL, STATIC)
                .initializer(CodeBlock.builder().add("new $T<>()", ClassName.get(MultiHashMap.class)).build())
                .build();

        CodeBlock.Builder staticBlock = CodeBlock.builder();

        for (Element element : urlAnnotations) {
            TypeElement typeElement = (TypeElement) element;
            ClassName activity = ClassName.get(typeElement);
            String url = element.getAnnotation(ContentType.class).value();
            staticBlock.add("table.put($S,$T.class);\n", url, activity);
        }

        MethodSpec provideMethod = MethodSpec.methodBuilder("provide")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Override.class).build())
                .addParameter(String.class, "url")
                .returns(listClass)
                .addStatement("return table.get(url)")
                .build();

        TypeSpec routerTableProvider = TypeSpec.classBuilder(className + "Provider")
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get(Provider.class))
                .addField(routerTable)
                .addStaticBlock(staticBlock.build())
                .addMethod(provideMethod)
                .build();

        JavaFile.builder(packageName, routerTableProvider)
                .build()
                .writeTo(filer);
    }

    private void printError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void printWaring(String waring) {
        messager.printMessage(Diagnostic.Kind.WARNING, waring);
    }
}
