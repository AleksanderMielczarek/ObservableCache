package com.github.aleksandermielczarek.observablecache.service.processor.method.extend;

import com.github.aleksandermielczarek.observablecache.service.processor.method.base.BaseMethod;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksander Mielczarek on 06.11.2016.
 */

public interface FromCacheMethod extends ExtendMethod {

    @Override
    default boolean isExtendMethod(ExecutableElement baseMethodElement, BaseMethod baseMethod, ExecutableElement extendMethod, ProcessingEnvironment processingEnvironment) {
        if (!extendMethod.getParameters().isEmpty()) {
            return false;
        }
        Element element = processingEnvironment.getTypeUtils().asElement(extendMethod.getReturnType());
        if (element == null || !element.getKind().equals(ElementKind.CLASS)) {
            return false;
        }
        TypeElement returnedElement = (TypeElement) element;
        if (!returnedElement.getQualifiedName().toString().contains(returnedType())) {
            return false;
        }
        if (!returnedBaseType().equals(baseMethod.returnedType())) {
            return false;
        }
        return ExtendMethod.super.isExtendMethod(baseMethodElement, baseMethod, extendMethod, processingEnvironment);
    }

    @Override
    default void writeMethod(TypeSpec.Builder builder, ExecutableElement method, String keyName) {
        builder.addMethod(MethodSpec.overriding(method)
                .addStatement("return observableCache." + fromCacheMethod() + "(" + keyName + ")")
                .build());
    }

    String returnedBaseType();

    String returnedType();

    String fromCacheMethod();
}
