package com.kapoorlabs.kiara.test.objects;

import com.kapoorlabs.kiara.domain.annotations.CommaSeperatedStrings;
import com.kapoorlabs.kiara.domain.annotations.IgnoreIndex;

import lombok.Data;

@Data
public class BooksTestObject {

    public enum Language {
        eng,
        spa,
        fre,
        nl,
        ara,
        por,
        ger,
        nor,
        jpn,
        vie,
        ind,
        pol,
        tur,
        dan,
        fil,
        ita
    }

    @IgnoreIndex
    private int id;

    private String isbn;

    @CommaSeperatedStrings
    private String authors;

    private Integer year;

    private String title;

    private Language language;

    private Double rating;


}
