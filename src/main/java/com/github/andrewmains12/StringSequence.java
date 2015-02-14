package com.github.andrewmains12;

public class StringSequence implements Sequence<String> {

    private final String format;

    public StringSequence(String format) {
        this.format = format;
    }

    @Override
    public String next(int n) {
        return String.format(format, n);
    }
}
