package com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend;


import com.github.aleksandermielczarek.observablecache.service.processor.api.method.base.BaseMethod;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */
public class RemoveExtendMethod implements ExtendMethod {

    @Override
    public boolean isExtendMethod(ExecutableElement baseMethodElement, BaseMethod baseMethod, ExecutableElement extendMethod, ProcessingEnvironment processingEnvironment) {
        if (!extendMethod.getParameters().isEmpty()) {
            return false;
        }
        if (!extendMethod.getReturnType().getKind().equals(TypeKind.BOOLEAN)) {
            return false;
        }
        return ExtendMethod.super.isExtendMethod(baseMethodElement, baseMethod, extendMethod, processingEnvironment);
    }

    @Override
    public String additionalToken() {
        return "remove";
    }

    @Override
    public void writeMethod(TypeSpec.Builder builder, ExecutableElement method, String keyName) {
        builder.addMethod(MethodSpec.overriding(method)
                .addStatement("return observableCache.remove(" + keyName + ")")
                .build());
    }

}
