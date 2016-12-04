package com.unikre.yandex.definition;

import lombok.Data;

import java.util.List;

@Data
public class Definition {
    private String pos;
    private String text;
    private String transcription;
    private List<Translation> translations;
}
