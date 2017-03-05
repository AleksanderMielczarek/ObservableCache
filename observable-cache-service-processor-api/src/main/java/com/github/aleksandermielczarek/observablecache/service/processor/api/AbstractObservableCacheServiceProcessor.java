package com.github.aleksandermielczarek.observablecache.service.processor.api;


import com.github.aleksandermielczarek.observablecache.service.processor.api.exception.ObservableCacheServiceException;
import com.github.aleksandermielczarek.observablecache.service.processor.api.method.base.BaseMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend.ExtendMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */

public abstract class AbstractObservableCacheServiceProcessor extends AbstractProcessor {

    private List<BaseMethod> baseMethods;
    private List<ExtendMethod> extendMethods;

    protected abstract List<BaseMethod> baseMethods();

    protected abstract List<ExtendMethod> extendMethods();

    protected abstract Class<? extends Annotation> serviceAnnotation();

    protected abstract String observableCachePackage();

    protected abstract String servicePackage();

    protected abstract String observableCacheClassName();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        baseMethods = baseMethods();
        extendMethods = extendMethods();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            processThrowing(annotations, roundEnv);
        } catch (Throwable e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        return true;
    }

    private void processThrowing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<TypeElement> services = roundEnv.getElementsAnnotatedWith(serviceAnnotation()).stream()
                .filter(element -> element.getKind().equals(ElementKind.INTERFACE))
                .map(element -> (TypeElement) element)
                .peek(observableCacheServiceInterfaceTypeElement -> {
                    TypeSpec.Builder observableCacheServiceImplBuilder = TypeSpec.classBuilder(observableCacheServiceInterfaceTypeElement.getSimpleName() + "Impl");
                    implementMethods(observableCacheServiceImplBuilder, observableCacheServiceInterfaceTypeElement);
                    writeService(observableCacheServiceImplBuilder, observableCacheServiceInterfaceTypeElement);
                })
                .collect(Collectors.toList());
        if (!services.isEmpty()) {
            writeCreator(services);
        }
    }

    private void implementMethods(TypeSpec.Builder builder, TypeElement observableCacheServiceInterfaceTypeElement) {
        List<ExecutableElement> methods = observableCacheServiceInterfaceTypeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.METHOD))
                .map(element -> (ExecutableElement) element)
                .collect(Collectors.toList());
        Deque<ExecutableElement> methodsQueue = new ArrayDeque<>(methods);

        for (int i = 1; i <= methods.size(); i++) {
            ExecutableElement baseElement = methodsQueue.pop();

            Optional<BaseMethod> baseMethodOptional = baseMethods.stream()
                    .filter(baseMethod -> baseMethod.isBaseMethod(baseElement, processingEnv))
                    .findFirst();

            if (baseMethodOptional.isPresent()) {
                BaseMethod baseMethod = baseMethodOptional.get();

                String keyValue = baseMethod.keyValue(observableCacheServiceInterfaceTypeElement, baseElement);
                String keyName = baseMethod.keyName(baseElement);
                baseMethod.writeMethod(builder, baseElement, keyName);
                builder.addField(FieldSpec.builder(String.class, keyName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", keyValue)
                        .build());

                Iterator<ExecutableElement> methodQueueIterator = methodsQueue.iterator();
                while (methodQueueIterator.hasNext()) {
                    ExecutableElement methodToImplement = methodQueueIterator.next();
                    extendMethods.stream()
                            .filter(extendMethod -> extendMethod.isExtendMethod(baseElement, baseMethod, methodToImplement, processingEnv))
                            .findFirst()
                            .ifPresent(extendMethod -> {
                                methodQueueIterator.remove();
                                extendMethod.writeMethod(builder, methodToImplement, keyName);
                            });
                }
            } else {
                methodsQueue.add(baseElement);
            }
            if (methodsQueue.isEmpty()) {
                break;
            }
        }
        if (!methodsQueue.isEmpty()) {
            throw new ObservableCacheServiceException("Unable to implement methods for " + observableCacheServiceInterfaceTypeElement.getQualifiedName().toString());
        }
    }

    private void writeService(TypeSpec.Builder builder, TypeElement observableCacheServiceInterfaceTypeElement) {
        builder.addSuperinterface(TypeName.get(observableCacheServiceInterfaceTypeElement.asType()))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(FieldSpec.builder(ClassName.get(observableCachePackage(), observableCacheClassName()), "observableCache")
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ClassName.get(observableCachePackage(), observableCacheClassName()), "observableCache")
                        .addStatement("this.$N = $N", "observableCache", "observableCache")
                        .build());

        PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(observableCacheServiceInterfaceTypeElement);
        JavaFile observableCacheServiceImplFile = JavaFile.builder(packageElement.getQualifiedName().toString(), builder.build()).build();
        try {
            observableCacheServiceImplFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            throw new ObservableCacheServiceException("Cannot write class" + observableCacheServiceInterfaceTypeElement.getSimpleName(), e);
        }
    }

    private void writeCreator(List<TypeElement> services) {
        TypeElement observableCacheServiceCreatorTypeElement = processingEnv.getElementUtils().getTypeElement(servicePackage() + ".ObservableCacheServiceCreator");
        observableCacheServiceCreatorTypeElement.getInterfaces().stream()
                .map(superInterface -> processingEnv.getTypeUtils().asElement(superInterface))
                .filter(element -> element.getKind().equals(ElementKind.INTERFACE))
                .map(element -> (TypeElement) element)
                .map(TypeElement::getEnclosedElements)
                .flatMap(Collection::stream)
                .filter(element -> element.getKind().equals(ElementKind.METHOD))
                .map(element -> (ExecutableElement) element)
                .findAny()
                .ifPresent(methodToOverride -> {
                    MethodSpec.Builder methodBuilder = MethodSpec.overriding(methodToOverride, processingEnv.getTypeUtils().getDeclaredType(observableCacheServiceCreatorTypeElement), processingEnv.getTypeUtils());
                    services.forEach(service -> {
                        PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(service);
                        methodBuilder.beginControlFlow("if(arg0.equals($T.class))", TypeName.get(service.asType()))
                                .addStatement("return (S) new $T(arg1)", ClassName.get(packageElement.getQualifiedName().toString(), service.getSimpleName() + "Impl"))
                                .endControlFlow();
                    });
                    JavaFile observableCacheServiceCreatorImplFile = JavaFile.builder(servicePackage(),
                            TypeSpec.classBuilder("ObservableCacheServiceCreatorImpl")
                                    .addSuperinterface(ClassName.get(servicePackage(), "ObservableCacheServiceCreator"))
                                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                    .addMethod(methodBuilder
                                            .addStatement("throw new $T($S + arg0)", IllegalArgumentException.class, "Cannot create service for ")
                                            .build())
                                    .build())
                            .build();
                    try {
                        observableCacheServiceCreatorImplFile.writeTo(processingEnv.getFiler());
                    } catch (IOException e) {
                        throw new ObservableCacheServiceException("Cannot write class ObservableCacheServiceCreator", e);
                    }
                });
    }
}
