package com.github.aleksandermielczarek.observablecache.service.processor.api.method;

import com.google.common.collect.Lists;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import javax.lang.model.element.ExecutableElement;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */

public interface WritableMethod {

    void writeMethod(TypeSpec.Builder builder, ExecutableElement method, String keyName);

    default List<String> nameTokens(ExecutableElement method) {
        return Lists.newArrayList(StringUtils.splitByCharacterTypeCamelCase(method.getSimpleName().toString()));
    }
}
