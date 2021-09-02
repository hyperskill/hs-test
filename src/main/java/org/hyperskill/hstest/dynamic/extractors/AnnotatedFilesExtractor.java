package org.hyperskill.hstest.dynamic.extractors;

import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.util.Map;

public class AnnotatedFilesExtractor extends AnnotatedExtractor<Map<String, String>> {

    public AnnotatedFilesExtractor(String name, Object obj) {
        super(name, obj);
    }

    @Override
    protected Map<String, String> convert(Object rawData) {
        if (rawData == null) {
            throw new UnexpectedError(provider + " should not return null, found null");
        }

        if (!(rawData instanceof Map)) {
            throw new UnexpectedError(provider + " should return Map, found " + rawData.getClass());
        }

        Map<?, ?> data = (Map<?, ?>) rawData;

        for (Object key : data.keySet()) {
            Object value = data.get(key);

            if (!(key instanceof String)) {
                throw new UnexpectedError(provider + " should return Map<String, String>, " +
                    "found class " + rawData.getClass() + " as key");
            }

            if (!(value instanceof String)) {
                throw new UnexpectedError(provider + " should return Map<String, String>, " +
                    "found class " + rawData.getClass() + " as value");
            }
        }

        return (Map<String, String>) data;
    }
}
