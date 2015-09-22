package com.jbjohn.jsonUpdate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class MutableJson {

    private final Map<String, Object> json;

    public MutableJson(Map<String, Object> json) {
        this.json = json;
    }

    public Map<String, Object> map() {
        return json;
    }

    public void update(String path, Object newValue) {
        Path parser = new Path();
        updateJson(this.json, parser.parse(path), newValue);
    }

    public void parse(String path, Class<?> parseToType) {
        Path parser = new Path();
        parseJson(this.json, parser.parse(path), parseToType);
    }

    private void updateJson(Map<String, Object> data, Iterator<Token> path, Object newValue) {
        Token token = path.next();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!token.accept(entry.getKey(), entry.getValue())) {
                continue;
            }

            if (path.hasNext()) {
                Object value = token.value(entry.getValue());
                if (value instanceof Map) {
                    updateJson((Map<String, Object>) value, path, newValue);
                }
            } else {
                token.update(entry, newValue);
            }
        }
    }

    private void parseJson(Map<String, Object> data, Iterator<Token> path, Class<?> parseToType) {
        Token token = path.next();
        for (Map.Entry<String, Object> entry : data.entrySet()) {

            //TODO - if the type matches do nothing
            if (!token.accept(entry.getKey(), entry.getValue())) {
                continue;
            }

            if (path.hasNext()) {
                Object value = token.value(entry.getValue());
                if (value instanceof Map) {
                    parseJson((Map<String, Object>) value, path, parseToType);
                }
            } else {
                token.parse(entry, parseToType);
            }
        }
    }

    class Path {
        public Iterator<Token> parse(String path) {
            if (path.isEmpty()) {
                return Collections.<Token>emptyList().iterator();
            }
            if (path.startsWith("$.")) {
                path = path.substring(2);
            }

            List<Token> tokens = new ArrayList<>();
            for (String part : path.split("\\.")) {
                if (part.matches("\\w+\\[\\d+\\]")) {
                    String fieldName = part.substring(0, part.indexOf('['));
                    int index = Integer.parseInt(part.substring(part.indexOf('[')+1, part.indexOf(']')));
                    tokens.add(new ArrayToken(fieldName, index));
                } else {
                    tokens.add(new FieldToken(part));
                }
            };

            return tokens.iterator();
        }
    }

    abstract class Token {

        protected final String fieldName;

        Token(String fieldName) {
            this.fieldName = fieldName;
        }

        public abstract Object value(Object value);

        public abstract boolean accept(String key, Object value);

        public abstract void update(Map.Entry<String, Object> entry, Object newValue);

        public abstract void parse(Map.Entry<String, Object> entry, Class<?> parseToType);
    }

    class FieldToken extends Token {

        FieldToken(String fieldName) {
            super(fieldName);
        }

        @Override
        public Object value(Object value) {
            return value;
        }

        @Override
        public boolean accept(String key, Object value) {
            return fieldName.equals(key);
        }

        @Override
        public void update(Map.Entry<String, Object> entry, Object newValue) {
            entry.setValue(newValue);
        }

        @Override
        public void parse(Map.Entry<String, Object> entry, Class<?> parseToType) {
            try {
                entry.setValue(new ObjectMapper().readValue(entry.getValue().toString(), parseToType));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ArrayToken extends Token {

        private final int index;

        ArrayToken(String fieldName, int index) {
            super(fieldName);
            this.index = index;
        }

        @Override
        public Object value(Object value) {
            return ((List) value).get(index);
        }

        @Override
        public boolean accept(String key, Object value) {
            return fieldName.equals(key) && value instanceof List && ((List) value).size() > index;
        }

        @Override
        public void update(Map.Entry<String, Object> entry, Object newValue) {
            List list = (List) entry.getValue();
            list.set(index, newValue);
        }

        @Override
        public void parse(Map.Entry<String, Object> entry, Class<?> parseToType) {
            List list = (List) entry.getValue();
            list.get(index);
            try {
                list.set(index, new ObjectMapper().readValue(list.get(index).toString(), parseToType));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}