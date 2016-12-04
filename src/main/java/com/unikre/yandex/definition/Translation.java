package com.unikre.yandex.definition;

import lombok.Data;

import java.util.List;

@Data
public class Translation {
    private String pos;
    private String text;
    private String gen;

    private List<Mean> means;
    private List<Example> examples;
    private List<Synonym> synonyms;
}
