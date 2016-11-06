package com.github.aleksandermielczarek.observablecache.service.processor.method.extend;

import com.github.aleksandermielczarek.observablecache.service.processor.method.WritableMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.base.BaseMethod;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by Aleksander Mielczarek on 05.11.2016.
 */

public interface ExtendMethod extends WritableMethod {

    default boolean isExtendMethod(ExecutableElement baseMethodElement, BaseMethod baseMethod, ExecutableElement extendMethod, ProcessingEnvironment processingEnvironment) {
        List<String> baseMethodTokens = nameTokens(baseMethodElement);
        List<String> extendMethodTokens = nameTokens(extendMethod);
        if (baseMethodTokens.size() >= extendMethodTokens.size()) {
            return false;
        }
        boolean baseHasTokens = false;
        for (int i = 0; i < extendMethodTokens.size(); i++) {
            String token = extendMethodTokens.remove(i);
            String lowerCaseToken = token.toLowerCase();
            String newFirstItem = extendMethodTokens.get(0);
            String newFirstItemLowerCase = newFirstItem.toLowerCase();
            extendMethodTokens.set(0, newFirstItemLowerCase);
            if (lowerCaseToken.equals(additionalToken()) && extendMethodTokens.equals(baseMethodTokens)) {
                baseHasTokens = true;
            }
            extendMethodTokens.set(0, newFirstItem);
            extendMethodTokens.add(i, token);
            if (baseHasTokens) {
                break;
            }
        }
        return baseHasTokens;
    }

    String additionalToken();

}
