package com.github.aleksandermielczarek.observablecache.service.processor;

import com.github.aleksandermielczarek.observablecache.service.annotations.ObservableCacheService;
import com.github.aleksandermielczarek.observablecache.service.processor.api.AbstractObservableCacheServiceProcessor;
import com.github.aleksandermielczarek.observablecache.service.processor.api.method.base.BaseMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend.ExtendMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.api.method.extend.RemoveExtendMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.base.CompletableBaseMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.base.ObservableBaseMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.base.SingleBaseMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.extend.CompletableExtendMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.extend.ObservableExtendMethod;
import com.github.aleksandermielczarek.observablecache.service.processor.method.extend.SingleExtendMethod;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Created by Aleksander Mielczarek on 30.10.2016.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.github.aleksandermielczarek.observablecache.service.annotations.ObservableCacheService")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ObservableCacheServiceProcessor extends AbstractObservableCacheServiceProcessor {

    @Override
    protected List<BaseMethod> baseMethods() {
        return Arrays.asList(new CompletableBaseMethod(), new ObservableBaseMethod(), new SingleBaseMethod());
    }

    @Override
    protected List<ExtendMethod> extendMethods() {
        return Arrays.asList(new CompletableExtendMethod(), new ObservableExtendMethod(), new RemoveExtendMethod(), new SingleExtendMethod());
    }

    @Override
    protected Class<? extends Annotation> serviceAnnotation() {
        return ObservableCacheService.class;
    }

    @Override
    protected String observableCachePackage() {
        return "com.github.aleksandermielczarek.observablecache";
    }

    @Override
    protected String servicePackage() {
        return "com.github.aleksandermielczarek.observablecache.service";
    }

}
