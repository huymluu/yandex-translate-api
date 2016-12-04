package com.unikre.yandex.params;

public enum Language {

    AUTODETECT(""),
    AZERBAIJAN("az"),
    AFRIKAANS("af"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    BASHKIR("ba"),
    BASQUE("eu"),
    BELARUSIAN("be"),
    BENGALI("bn"),
    BOSNIAN("bs"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CEBUANO("ceb"),
    CHINESE("zh"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    GEORGIAN("ka"),
    GERMAN("de"),
    GREEK("el"),
    GUJARATI("gu"),
    HAITIAN("ht"),
    HEBREW("he"),
    HILL_MARI("mrj"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    ICELANDIC("is"),
    INDONESIAN("id"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    JAVANESE("jv"),
    KANNADA("kn"),
    KAZAKH("kk"),
    KOREAN("ko"),
    KYRGYZ("ky"),
    LATIN("la"),
    LATVIAN("lv"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MALTESE("mt"),
    MAORI("mi"),
    MARATHI("mr"),
    MARI("mhr"),
    MONGOLIAN("mn"),
    NEPALI("ne"),
    NORWEGIAN("no"),
    PAPIAMENTO("pap"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PUNJABI("pa"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SCOTTISH("gd"),
    SERBIAN("sr"),
    SINHALA("si"),
    SLOVAKIAN("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    SUNDANESE("su"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAGALOG("tl"),
    TAJIK("tg"),
    TAMIL("ta"),
    TATAR("tt"),
    TELUGU("te"),
    THAI("th"),
    TURKISH("tr"),
    UDMURT("udm"),
    UKRAINIAN("uk"),
    URDU("ur"),
    UZBEK("uz"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    XHOSA("xh"),
    YIDDISH("yi");

    private final String code;

    private Language(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Language byCode(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language code : " + code);
    }
}
