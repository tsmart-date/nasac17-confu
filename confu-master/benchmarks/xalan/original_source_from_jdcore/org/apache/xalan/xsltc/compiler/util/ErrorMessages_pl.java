package org.apache.xalan.xsltc.compiler.util;

import java.util.ListResourceBundle;






















































































public class ErrorMessages_pl
  extends ListResourceBundle
{
  public ErrorMessages_pl() {}
  
  public Object[][] getContents()
  {
    return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "W jednym pliku zdefiniowano więcej niż jeden arkusz stylów." }, { "TEMPLATE_REDEF_ERR", "Szablon ''{0}'' został już zdefiniowany w tym arkuszu stylów." }, { "TEMPLATE_UNDEF_ERR", "Szablon ''{0}'' nie został zdefiniowany w tym arkuszu stylów." }, { "VARIABLE_REDEF_ERR", "Zmienna ''{0}'' została zdefiniowana wielokrotnie w tym samym zasięgu." }, { "VARIABLE_UNDEF_ERR", "Nie zdefiniowano zmiennej lub parametru ''{0}''." }, { "CLASS_NOT_FOUND_ERR", "Nie można znaleźć klasy ''{0}''." }, { "METHOD_NOT_FOUND_ERR", "Nie można znaleźć metody zewnętrznej ''{0}'' (musi być zadeklarowana jako public)." }, { "ARGUMENT_CONVERSION_ERR", "Nie można przekształcić typu argumentu lub typu wyniku w wywołaniu metody ''{0}''" }, { "FILE_NOT_FOUND_ERR", "Nie można znaleźć pliku lub identyfikatora URI ''{0}''." }, { "INVALID_URI_ERR", "Niepoprawny identyfikator URI ''{0}''." }, { "FILE_ACCESS_ERR", "Nie można otworzyć pliku lub identyfikatora URI ''{0}''." }, { "MISSING_ROOT_ERR", "Oczekiwano elementu <xsl:stylesheet> lub <xsl:transform>." }, { "NAMESPACE_UNDEF_ERR", "Nie zadeklarowano przedrostka przestrzeni nazw ''{0}''." }, { "FUNCTION_RESOLVE_ERR", "Nie można rozstrzygnąć wywołania funkcji ''{0}''." }, { "NEED_LITERAL_ERR", "Argument funkcji ''{0}'' musi być literałem łańcuchowym." }, { "XPATH_PARSER_ERR", "Błąd podczas analizowania wyrażenia XPath ''{0}''." }, { "REQUIRED_ATTR_ERR", "Brakuje atrybutu wymaganego ''{0}''." }, { "ILLEGAL_CHAR_ERR", "Niedozwolony znak ''{0}'' w wyrażeniu XPath." }, { "ILLEGAL_PI_ERR", "Niedozwolona nazwa ''{0}'' instrukcji przetwarzania." }, { "STRAY_ATTRIBUTE_ERR", "Atrybut ''{0}'' znajduje się poza elementem." }, { "ILLEGAL_ATTRIBUTE_ERR", "Niedozwolony atrybut ''{0}''." }, { "CIRCULAR_INCLUDE_ERR", "Cykliczny import/include. Arkusz stylów ''{0}'' został już załadowany." }, { "RESULT_TREE_SORT_ERR", "Nie można posortować fragmentów drzewa rezultatów (elementy <xsl:sort> są ignorowane). Trzeba sortować węzły podczas tworzenia drzewa rezultatów." }, { "SYMBOLS_REDEF_ERR", "Formatowanie dziesiętne ''{0}'' zostało już zdefiniowane." }, { "XSL_VERSION_ERR", "Wersja XSL ''{0}'' nie jest obsługiwana przez XSLTC." }, { "CIRCULAR_VARIABLE_ERR", "Cykliczne odwołanie do zmiennej lub parametru w ''{0}''." }, { "ILLEGAL_BINARY_OP_ERR", "Nieznany operator wyrażenia dwuargumentowego." }, { "ILLEGAL_ARG_ERR", "Niedozwolone argumenty w wywołaniu funkcji." }, { "DOCUMENT_ARG_ERR", "Drugim argumentem funkcji document() musi być zbiór węzłów." }, { "MISSING_WHEN_ERR", "W <xsl:choose> wymagany jest przynajmniej jeden element <xsl:when>." }, { "MULTIPLE_OTHERWISE_ERR", "W <xsl:choose> dozwolony jest tylko jeden element <xsl:otherwise>." }, { "STRAY_OTHERWISE_ERR", "Elementu <xsl:otherwise> można użyć tylko wewnątrz <xsl:choose>." }, { "STRAY_WHEN_ERR", "Elementu <xsl:when> można użyć tylko wewnątrz <xsl:choose>." }, { "WHEN_ELEMENT_ERR", "Tylko elementy <xsl:when> i <xsl:otherwise> są dozwolone w <xsl:choose>." }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> nie ma atrybutu 'name'." }, { "ILLEGAL_CHILD_ERR", "Niedozwolony element potomny." }, { "ILLEGAL_ELEM_NAME_ERR", "Nie można wywołać elementu ''{0}''" }, { "ILLEGAL_ATTR_NAME_ERR", "Nie można wywołać atrybutu ''{0}''" }, { "ILLEGAL_TEXT_NODE_ERR", "Dane tekstowe poza elementem <xsl:stylesheet> najwyższego poziomu." }, { "SAX_PARSER_CONFIG_ERR", "Analizator składni JAXP nie został poprawnie skonfigurowany." }, { "INTERNAL_ERR", "Nienaprawialny błąd wewnętrzny XSLTC: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "Nieobsługiwany element XSL ''{0}''." }, { "UNSUPPORTED_EXT_ERR", "Nierozpoznane rozszerzenie XSLTC ''{0}''." }, { "MISSING_XSLT_URI_ERR", "Dokument wejściowy nie jest arkuszem stylów (przestrzeń nazw XSL nie została zadeklarowana w elemencie głównym)." }, { "MISSING_XSLT_TARGET_ERR", "Nie można znaleźć elementu docelowego ''{0}'' arkusza stylów." }, { "NOT_IMPLEMENTED_ERR", "Nie zaimplementowano: ''{0}''." }, { "NOT_STYLESHEET_ERR", "Dokument wejściowy nie zawiera arkusza stylów XSL." }, { "ELEMENT_PARSE_ERR", "Nie można zanalizować elementu ''{0}''" }, { "KEY_USE_ATTR_ERR", "Wartością atrybutu use elementu <key> musi być: node, node-set, string lub number." }, { "OUTPUT_VERSION_ERR", "Wyjściowy dokument XML powinien mieć wersję 1.0" }, { "ILLEGAL_RELAT_OP_ERR", "Nieznany operator wyrażenia relacyjnego" }, { "ATTRIBSET_UNDEF_ERR", "Próba użycia nieistniejącego zbioru atrybutów ''{0}''." }, { "ATTR_VAL_TEMPLATE_ERR", "Nie można zanalizować szablonu wartości atrybutu ''{0}''." }, { "UNKNOWN_SIG_TYPE_ERR", "Nieznany typ danych w sygnaturze klasy ''{0}''." }, { "DATA_CONVERSION_ERR", "Nie można przekształcić typu danych ''{0}'' w ''{1}''." }, { "NO_TRANSLET_CLASS_ERR", "Klasa Templates nie zawiera poprawnej definicji klasy transletu." }, { "NO_MAIN_TRANSLET_ERR", "Ta klasa Templates nie zawiera klasy o nazwie ''{0}''." }, { "TRANSLET_CLASS_ERR", "Nie można załadować klasy transletu ''{0}''." }, { "TRANSLET_OBJECT_ERR", "Załadowano klasę transletu, ale nie można utworzyć jego instancji." }, { "ERROR_LISTENER_NULL_ERR", "Próba ustawienia obiektu ErrorListener klasy ''{0}'' na wartość null" }, { "JAXP_UNKNOWN_SOURCE_ERR", "Tylko StreamSource, SAXSource i DOMSource są obsługiwane przez XSLTC" }, { "JAXP_NO_SOURCE_ERR", "Obiekt klasy Source przekazany do ''{0}'' nie ma kontekstu." }, { "JAXP_COMPILE_ERR", "Nie można skompilować arkusza stylów." }, { "JAXP_INVALID_ATTR_ERR", "Klasa TransformerFactory nie rozpoznaje atrybutu ''{0}''." }, { "JAXP_SET_RESULT_ERR", "Przed wywołaniem metody startDocument() należy wywołać metodę setResult()." }, { "JAXP_NO_TRANSLET_ERR", "Obiekt Transformer nie zawiera referencji do obiektu transletu." }, { "JAXP_NO_HANDLER_ERR", "Nie zdefiniowano procedury obsługi wyjścia rezultatów transformacji." }, { "JAXP_NO_RESULT_ERR", "Obiekt Result przekazany do ''{0}'' jest niepoprawny." }, { "JAXP_UNKNOWN_PROP_ERR", "Próba dostępu do niepoprawnej właściwości interfejsu Transformer ''{0}''." }, { "SAX2DOM_ADAPTER_ERR", "Nie można utworzyć adaptera SAX2DOM: ''{0}''." }, { "XSLTC_SOURCE_ERR", "Metoda XSLTCSource.build() została wywołana bez ustawienia wartości systemId." }, { "ER_RESULT_NULL", "Rezultat nie powinien być pusty" }, { "JAXP_INVALID_SET_PARAM_VALUE", "Wartością parametru {0} musi być poprawny obiekt języka Java." }, { "COMPILE_STDIN_ERR", "Z opcją -o trzeba użyć także opcji -i." }, { "COMPILE_USAGE_STR", "SKŁADNIA\n   java org.apache.xalan.xsltc.cmdline.Compile [-o <wyjście>]\n      [-d <katalog>] [-j <plik_jar>] [-p <pakiet>]\n      [-n] [-x] [-u] [-v] [-h] { <arkusz_stylów> | -i }\n\nOPCJE\n   -o <wyjście>    przypisanie nazwy <wyjście> do wygenerowanego\n                   transletu.  Domyślnie nazwa transletu pochodzi \n                   od nazwy <arkusza_stylów>. Opcja ta jest ignorowana \n                   w przypadku kompilowania wielu arkuszy stylów.\n   -d <katalog>    Określenie katalogu docelowego transletu.\n   -j <plik_jar>   Pakowanie klas transletu do pliku jar o nazwie\n                   określonej jako <plik_jar>.\n   -p <pakiet>     Określenie przedrostka nazwy pakietu dla wszystkich\n                   wygenerowanych klas transletów.\n   -n              Włączenie wstawiania szablonów (zachowanie domyślne\n                   zwykle lepsze).\n   -x              Włączenie wypisywania dodatkowych komunikatów debugowania.\n    -u              Interpretowanie argumentów <arkusz_stylów> jako\n                   adresów URL.\n   -i              Wymuszenie odczytywania przez kompilator arkusza stylów\n                   ze standardowego wejścia (stdin).\n   -v              Wypisanie wersji kompilatora.\n   -h              Wypisanie informacji o składni.\n" }, { "TRANSFORM_USAGE_STR", "SKŁADNIA \n   java org.apache.xalan.xsltc.cmdline.Transform [-j <plik_jar>]\n      [-x] [-n <iteracje>] {-u <url_dokumentu> | <dokument>}\n <klasa> [<param1>=<wartość1> ...]\n\n   Użycie transletu <klasa> do transformacji dokumentu XML \n   określonego jako <dokument>. Translet <klasa> znajduje się w\n   ścieżce CLASSPATH użytkownika lub w opcjonalnie podanym pliku <plik_jar>.\nOPCJE\n   -j <plik_jar>   Określenie pliku jar, z którego należy załadować translet.\n   -x              Włączenie wypisywania dodatkowych komunikatów debugowania.\n   -n <iteracje>   Określenie krotności wykonywania transformacji oraz \n                   włączenie wyświetlania informacji z profilowania.\n   -u <url_dokumentu>\n                   Określenie wejściowego dokumentu XML w postaci adresu URL.\n" }, { "STRAY_SORT_ERR", "Elementu <xsl:sort> można użyć tylko wewnątrz <xsl:for-each> lub <xsl:apply-templates>." }, { "UNSUPPORTED_ENCODING", "Kodowanie wyjściowe ''{0}'' nie jest obsługiwane przez tę maszynę wirtualną języka Java." }, { "SYNTAX_ERR", "Błąd składniowy w ''{0}''." }, { "CONSTRUCTOR_NOT_FOUND", "Nie można znaleźć konstruktora zewnętrznego ''{0}''." }, { "NO_JAVA_FUNCT_THIS_REF", "Pierwszy argument funkcji ''{0}'' języka Java (innej niż static) nie jest poprawnym odniesieniem do obiektu." }, { "TYPE_CHECK_ERR", "Błąd podczas sprawdzania typu wyrażenia ''{0}''." }, { "TYPE_CHECK_UNK_LOC_ERR", "Błąd podczas sprawdzania typu wyrażenia w nieznanym położeniu." }, { "ILLEGAL_CMDLINE_OPTION_ERR", "Niepoprawna opcja ''{0}'' wiersza komend." }, { "CMDLINE_OPT_MISSING_ARG_ERR", "Brakuje argumentu wymaganego opcji ''{0}'' wiersza komend." }, { "WARNING_PLUS_WRAPPED_MSG", "OSTRZEŻENIE:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "OSTRZEŻENIE:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "BŁĄD KRYTYCZNY:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "BŁĄD KRYTYCZNY:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "BŁĄD:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "BŁĄD:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "Dokonaj transformacji za pomocą transletu ''{0}'' " }, { "TRANSFORM_WITH_JAR_STR", "Dokonaj transformacji za pomocą transletu ''{0}'' z pliku jar ''{1}''" }, { "COULD_NOT_CREATE_TRANS_FACT", "Nie można utworzyć instancji klasy ''{0}'' interfejsu TransformerFactory." }, { "TRANSLET_NAME_JAVA_CONFLICT", "Nazwy ''{0}'' nie można użyć jako nazwy klasy transletu, ponieważ zawiera ona znaki, które są niedozwolone w nazwach klas języka Java.  Zamiast niej użyto nazwy ''{1}''." }, { "COMPILER_ERROR_KEY", "Błędy kompilatora:" }, { "COMPILER_WARNING_KEY", "Ostrzeżenia kompilatora:" }, { "RUNTIME_ERROR_KEY", "Błędy transletu:" }, { "INVALID_QNAME_ERR", "Atrybut, którego wartością musi być nazwa QName lub lista rozdzielonych odstępami nazw QName, miał wartość ''{0}''" }, { "INVALID_NCNAME_ERR", "Atrybut, którego wartością musi być nazwa NCName, miał wartość ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "Atrybut method elementu <xsl:output> miał wartość ''{0}''.  Wartością może być: ''xml'', ''html'', ''text'' lub nazwa qname nie będąca nazwą ncname." }, { "JAXP_GET_FEATURE_NULL_NAME", "Nazwa opcji nie może mieć wartości null w TransformerFactory.getFeature(String nazwa)." }, { "JAXP_SET_FEATURE_NULL_NAME", "Nazwa opcji nie może mieć wartości null w TransformerFactory.setFeature(String nazwa, boolean wartość)." }, { "JAXP_UNSUPPORTED_FEATURE", "Nie można ustawić opcji ''{0}'' w tej klasie TransformerFactory." } };
  }
}
