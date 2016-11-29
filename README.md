# Usage

```java
// Get free API key at https://tech.yandex.com/translate/
YandexTranslator yandexTranslator = new YandexTranslator("YOUR YANDEX API KEY");

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

# TODO
- Support XML
- Get list of supported lang (https://tech.yandex.com/translate/doc/dg/reference/getLangs-docpage/)
- Detect lang (https://tech.yandex.com/translate/doc/dg/reference/detect-docpage/)
- Support `options` in API request (https://tech.yandex.com/translate/doc/dg/reference/translate-docpage/)
