package com.github.aleksandermielczarek.observablecache.service.processor.method.base;

import com.github.aleksandermielczarek.observablecache.service.processor.method.WritableMethod;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */

public interface BaseMethod extends WritableMethod {

    default boolean isBaseMethod(ExecutableElement method, ProcessingEnvironment processingEnvironment) {
        if (!method.getParameters().isEmpty()) {
            return false;
        }
        TypeElement returnedElement = (TypeElement) processingEnvironment.getTypeUtils().asElement(method.getReturnType());
        if (!returnedElement.getQualifiedName().toString().contains(returnedType())) {
            return false;
        }
        return true;
    }

    default String keyName(ExecutableElement method) {
        String camelCaseName = method.getSimpleName().toString();
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelCaseName);
    }

    default String keyValue(TypeElement type, ExecutableElement method) {
        return type.getQualifiedName().toString() + "." + method.getSimpleName().toString();
    }

    @Override
    default void writeMethod(TypeSpec.Builder builder, ExecutableElement method, String keyName) {
        builder.addMethod(MethodSpec.overriding(method)
                .addStatement("return observableCache." + cacheMethod() + "(" + keyName + ")")
                .build());
    }

    String returnedType();

    String cacheMethod();
}
