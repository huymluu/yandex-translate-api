# Yandex Translate API usage

## Register API key
Get free API key at https://tech.yandex.com/translate/

## Initialization

```java
YandexTranslator yandexTranslator = new YandexTranslator("YOUR YANDEX API KEY");
```

## Get supported languages
```java
List<Language> supportedLanguages = yandexTranslator.getSupportedLanguages();
```

## Translate
```java
// Translate single term - explicit 'from' & 'to' languages
String translated = yandexTranslator.translate("Hello world", Language.ENGLISH, Language.GERMAN);

// Translate single term - auto detect 'from' language
String translated = yandexTranslator.translate("Hello world", Language.GERMAN);

// Translate list of terms
List<String> input = new ArrayList<String>();
input.add("Hello");
input.add("World");
List<String> translated = yandexTranslator.translate(input, Language.GERMAN);
```

## Detect language
```java
// Auto detect best language
Language detectedLanguage = yandexTranslator.detectLanguage("Hello world");

// Detect best language with hint
Language detectedLanguage = yandexTranslator.detectLanguage("Hallo", Language.GERMAN);

// Detect best language with multiple hint
Language detectedLanguage = yandexTranslator.detectLanguage("Hallo", Language.ENGLISH, Language.GERMAN);
```

# Yandex Dictionary API usage

## Register API key
Get free API key at https://tech.yandex.com/dictionary/

## Initialization
```java
YandexDictionary yandexDictionary = new YandexDictionary("YOUR YANDEX API KEY");
```

## Get supported lookup directions
```java
Map<Language, List<Language>> directions = yandexDictionary.getSupportedTranslateDirections();
```

## Lookup
```java
// Simple lookup
List<Definition> definitions = yandexDictionary.lookup("time", Language.ENGLISH, Language.GERMAN);

// Lookup with flags (not sure what it means, read https://tech.yandex.com/dictionary/doc/dg/reference/lookup-docpage/)
List<Definition> definitions = yandexDictionary.lookup("time", Language.ENGLISH, Language.GERMAN, LookupFlag.FAMILY, LookupFlag.MORPHO, LookupFlag.POS_FILTER);
```

# TODO
- Support XML (no need, just JSON is enough)
- Support more request params: `options`, `ui`
