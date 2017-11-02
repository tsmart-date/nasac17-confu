package org.apache.xalan.xsltc.compiler.util;

import java.util.ListResourceBundle;






















































































public class ErrorMessages_tr
  extends ListResourceBundle
{
  public ErrorMessages_tr() {}
  
  public Object[][] getContents()
  {
    return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "Aynı dosyada birden çok biçem yaprağı tanımlandı." }, { "TEMPLATE_REDEF_ERR", "Biçem yaprağında ''{0}'' şablonu zaten tanımlı." }, { "TEMPLATE_UNDEF_ERR", "Bu biçem yaprağında ''{0}'' şablonu tanımlı değil." }, { "VARIABLE_REDEF_ERR", "''{0}'' değişkeni aynı kapsamda bir kereden çok tanımlandı." }, { "VARIABLE_UNDEF_ERR", "''{0}'' değişkeni ya da değiştirgesi tanımlı değil." }, { "CLASS_NOT_FOUND_ERR", "''{0}'' sınıfı bulunamıyor." }, { "METHOD_NOT_FOUND_ERR", "''{0}'' dış yöntemi bulunamıyor (public olmalı)." }, { "ARGUMENT_CONVERSION_ERR", "''{0}'' yöntemi çağrısında bağımsız değişken/dönüş tipi dönüştürülemiyor." }, { "FILE_NOT_FOUND_ERR", "Dosya ya da URI ''{0}'' bulunamadı." }, { "INVALID_URI_ERR", "Geçersiz URI ''{0}''." }, { "FILE_ACCESS_ERR", "Dosya ya da URI ''{0}'' açılamıyor." }, { "MISSING_ROOT_ERR", "<xsl:stylesheet> ya da <xsl:transform> öğesi bekleniyor." }, { "NAMESPACE_UNDEF_ERR", "Ad alanı öneki ''{0}'' bildirilmemiş." }, { "FUNCTION_RESOLVE_ERR", "''{0}'' işlevi çağrısı çözülemiyor." }, { "NEED_LITERAL_ERR", "''{0}'' işlevine ilişkin bağımsız değişken bir hazır bilgi dizgisi olmalıdır." }, { "XPATH_PARSER_ERR", "XPath ifadesi ''{0}'' ayrıştırılırken hata oluştu." }, { "REQUIRED_ATTR_ERR", "Gerekli ''{0}'' özniteliği eksik." }, { "ILLEGAL_CHAR_ERR", "XPath ifadesinde geçersiz ''{0}'' karakteri var." }, { "ILLEGAL_PI_ERR", "İşleme yönergesi için ''{0}'' adı geçersiz." }, { "STRAY_ATTRIBUTE_ERR", "''{0}'' özniteliği öğenin dışında." }, { "ILLEGAL_ATTRIBUTE_ERR", "''{0}'' özniteliği geçersiz." }, { "CIRCULAR_INCLUDE_ERR", "Çevrimsel import/include. ''{0}'' biçem yaprağı zaten yüklendi." }, { "RESULT_TREE_SORT_ERR", "Sonuç ağacı parçaları sıralanamıyor (<xsl:sort> öğeleri yok sayıldı). Düğümleri sonuç ağacını yaratırken sıralamalısınız." }, { "SYMBOLS_REDEF_ERR", "Onlu biçimleme biçemi ''{0}'' zaten tanımlı." }, { "XSL_VERSION_ERR", "XSL sürümü ''{0}'' XSLTC tarafından desteklenmiyor." }, { "CIRCULAR_VARIABLE_ERR", "''{0}'' içinde çevrimsel değişken/değiştirge başvurusu." }, { "ILLEGAL_BINARY_OP_ERR", "İkili ifadede bilinmeyen işleç." }, { "ILLEGAL_ARG_ERR", "İşlev çağrısı için geçersiz sayıda bağımsız değişken." }, { "DOCUMENT_ARG_ERR", "document() işlevinin ikinci bağımsız değişkeni düğüm kümesi olmalıdır." }, { "MISSING_WHEN_ERR", "<xsl:choose> içinde en az bir <xsl:when> öğesi gereklidir." }, { "MULTIPLE_OTHERWISE_ERR", "<xsl:choose> içinde tek bir <xsl:otherwise> öğesine izin verilir." }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> yalnızca <xsl:choose> içinde kullanılabilir." }, { "STRAY_WHEN_ERR", "<xsl:when> yalnızca <xsl:choose> içinde kullanılabilir." }, { "WHEN_ELEMENT_ERR", "<xsl:choose> içinde yalnızca <xsl:when> ve <xsl:otherwise> öğeleri kullanılabilir." }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> öğesinde 'name' özniteliği eksik." }, { "ILLEGAL_CHILD_ERR", "Geçersiz alt öğe." }, { "ILLEGAL_ELEM_NAME_ERR", "Bir öğeye ''{0}'' adı verilemez." }, { "ILLEGAL_ATTR_NAME_ERR", "Bir özniteliğe ''{0}'' adı verilemez." }, { "ILLEGAL_TEXT_NODE_ERR", "Üst düzey <xsl:stylesheet> öğesi dışında metin verisi." }, { "SAX_PARSER_CONFIG_ERR", "JAXP ayrıştırıcısı doğru yapılandırılmamış" }, { "INTERNAL_ERR", "Kurtarılamaz XSLTC iç hatası: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "XSL öğesi ''{0}'' desteklenmiyor." }, { "UNSUPPORTED_EXT_ERR", "XSLTC eklentisi ''{0}'' tanınmıyor." }, { "MISSING_XSLT_URI_ERR", "Giriş belgesi bir biçem yaprağı değil (XSL ad alanı kök öğede bildirilmedi)." }, { "MISSING_XSLT_TARGET_ERR", "Biçem yaprağı hedefi ''{0}'' bulunamadı." }, { "NOT_IMPLEMENTED_ERR", "Gerçekleştirilmedi: ''{0}''." }, { "NOT_STYLESHEET_ERR", "Giriş belgesi bir XSL biçem yaprağı içermiyor." }, { "ELEMENT_PARSE_ERR", "''{0}'' öğesi ayrıştırılamadı." }, { "KEY_USE_ATTR_ERR", "<key> ile ilgili use özniteliği node, node-set, string ya da number olmalıdır." }, { "OUTPUT_VERSION_ERR", "Çıkış XML belgesi sürümü 1.0 olmalıdır." }, { "ILLEGAL_RELAT_OP_ERR", "İlişkisel ifade için bilinmeyen işleç" }, { "ATTRIBSET_UNDEF_ERR", "Varolmayan ''{0}'' öznitelik kümesini kullanma girişimi." }, { "ATTR_VAL_TEMPLATE_ERR", "Öznitelik değeri şablonu ''{0}'' ayrıştırılamıyor." }, { "UNKNOWN_SIG_TYPE_ERR", "''{0}'' sınıfına ilişkin imzada bilinmeyen veri tipi." }, { "DATA_CONVERSION_ERR", "''{0}'' veri tipi ''{1}'' tipine dönüştürülemez." }, { "NO_TRANSLET_CLASS_ERR", "Bu Templates geçerli bir derleme sonucu sınıf tanımı içermiyor." }, { "NO_MAIN_TRANSLET_ERR", "Bu Templates ''{0}'' adında bir sınıf içermiyor." }, { "TRANSLET_CLASS_ERR", "Derleme sonucu sınıfı ''{0}'' yüklenemedi." }, { "TRANSLET_OBJECT_ERR", "Derleme sonucu sınıfı yüklendi, ancak derleme sonucu sınıfının somut kopyası yaratılamıyor." }, { "ERROR_LISTENER_NULL_ERR", "''{0}'' ile ilgili ErrorListener nesnesini boş değer (null) olarak ayarlama girişimi." }, { "JAXP_UNKNOWN_SOURCE_ERR", "XSLTC yalnızca StreamSource, SAXSource ve DOMSource arabirimlerini destekler." }, { "JAXP_NO_SOURCE_ERR", "''{0}'' yöntemine aktarılan Source nesnesinin içeriği yok." }, { "JAXP_COMPILE_ERR", "Biçem yaprağı derlenemedi." }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory ''{0}'' özniteliğini tanımıyor." }, { "JAXP_SET_RESULT_ERR", "startDocument() yönteminden önce setResult() çağrılmalıdır." }, { "JAXP_NO_TRANSLET_ERR", "Transformer, derleme sonucu sınıf dosyası nesnesine başvuru içermiyor." }, { "JAXP_NO_HANDLER_ERR", "Dönüştürme sonucu için tanımlı çıkış işleyicisi yok." }, { "JAXP_NO_RESULT_ERR", "''{0}'' yöntemine aktarılan Result nesnesi geçersiz." }, { "JAXP_UNKNOWN_PROP_ERR", "Geçersiz ''{0}'' Transformer özelliğine (property) erişme girişimi." }, { "SAX2DOM_ADAPTER_ERR", "SAX2DOM bağdaştırıcısı yaratılamadı: ''{0}''." }, { "XSLTC_SOURCE_ERR", "XSLTCSource.build() yöntemi systemId tanımlanmadan çağrıldı." }, { "ER_RESULT_NULL", "Sonuç boş değerli olmamalı" }, { "JAXP_INVALID_SET_PARAM_VALUE", "{0} değiştirgesinin değeri geçerli bir Java nesnesi olmalıdır" }, { "COMPILE_STDIN_ERR", "-i seçeneği -o seçeneğiyle birlikte kullanılmalıdır." }, { "COMPILE_USAGE_STR", "ÖZET\n   java org.apache.xalan.xsltc.cmdline.Compile [-o <çıkış>]\n      [-d <dizin>] [-j <jardosyası>] [-p <paket>]\n      [-n] [-x] [-u] [-v] [-h] { <biçemyaprağı> | -i }\n\nSEÇENEKLER\n   -o <çıkış>    derleme sonucu sınıf dosyasına <çıkış>\n                  adını atar. Varsayılan olarak, derleme sonucu sınıf dosyası\n                  adı <biçemyaprağı> adından alınır.  Birden çok biçem yaprağı derleniyorsa\n                  bu seçenek dikkate alınmaz.\n   -d <dizin> derleme sonucu sınıf dosyası için hedef dizini belirtir.\n   -j <jardosyası>   derleme sonucu sınıf dosyalarını\n                  <jardosyası> dosyasında paketler.\n   -p <paket>   derleme sonucu üretilen tüm sınıf dosyaları için\n                  bir paket adı öneki belirtir.\n   -n             şablona doğrudan yerleştirmeyi etkinleştirir (ortalama olarak\n                  daha yüksek başarım sağlar).\n   -x             ek hata ayıklama iletisi çıkışını etkinleştirir\n   -u             <biçemyaprağı> bağımsız değişkenlerini URL olarak yorumlars\n   -i             derleyiciyi stdin'den biçem yaprağını okumaya zorlar\n   -v             derleyici sürümünü yazdırır.\n   -h             bu kullanım bilgilerini yazdırır\n" }, { "TRANSFORM_USAGE_STR", "ÖZET \n   java org.apache.xalan.xsltc.cmdline.Transform [-j <jardosyası>]\n      [-x] [-n <yinelemesayısı>] {-u <belge_url> | <belge>}\n      <sınıf> [<değiştirge1>=<değer1> ...]\n\n   <belge> ile belirtilen XML belgesini dönüştürmek için <sınıf>\n   sınıf dosyasını kullanır. <sınıf> sınıf dosyası\n   kullanıcının CLASSPATH değişkeninde ya da isteğe bağlı olarak belirtilen <jardosyası> dosyasındadır.\nSEÇENEKLER\n   -j <jardosyası>    derleme sonucu sınıf dosyasının hangi jar dosyasından yükleneceğini belirtir\n   -x              ek hata ayıklama iletisi çıkışını etkinleştirir\n   -n <yinelemesayısı> dönüştürmeyi <yineleme sayısı> ile belirtilen sayı kadar çalıştırır\n                   ve yakalama bilgilerini görüntüler\n   -u <belge_url> XML giriş belgesini URL olarak belirtir\n" }, { "STRAY_SORT_ERR", "<xsl:sort> yalnızca <xsl:for-each> ya da <xsl:apply-templates> içinde kullanılabilir." }, { "UNSUPPORTED_ENCODING", "''{0}'' çıkış kodlaması bu JVM üzerinde desteklenmiyor." }, { "SYNTAX_ERR", "''{0}'' ifadesinde sözdizimi hatası." }, { "CONSTRUCTOR_NOT_FOUND", "Dış oluşturucu ''{0}'' bulunamıyor." }, { "NO_JAVA_FUNCT_THIS_REF", "Durağan (static) olmayan ''{0}'' Java işlevine ilişkin ilk bağımsız değişken geçerli bir nesne başvurusu değil." }, { "TYPE_CHECK_ERR", "''{0}'' ifadesinin tipi denetlenirken hata saptandı." }, { "TYPE_CHECK_UNK_LOC_ERR", "Bilinmeyen bir yerdeki bir ifadenin tipi denetlenirken hata saptandı." }, { "ILLEGAL_CMDLINE_OPTION_ERR", "Komut satırı seçeneği ''{0}'' geçerli değil." }, { "CMDLINE_OPT_MISSING_ARG_ERR", "''{0}'' komut satırı seçeneğinde gerekli bir bağımsız değişken eksik." }, { "WARNING_PLUS_WRAPPED_MSG", "UYARI:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "UYARI:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "ONULMAZ HATA:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "ONULMAZ HATA:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "HATA:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "HATA:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "''{0}'' sınıfını kullanarak dönüştür " }, { "TRANSFORM_WITH_JAR_STR", "''{1}'' jar dosyasından ''{0}'' derleme sonucu sınıf dosyasını kullanarak dönüştür" }, { "COULD_NOT_CREATE_TRANS_FACT", "''{0}'' TransformerFactory sınıfının somut örneği yaratılamadı." }, { "TRANSLET_NAME_JAVA_CONFLICT", "''{0}'' adı, derleme sonucu sınıf dosyası adı olarak kullanılamadı; bir Java sınıfında kullanılmasına izin verilmeyen karakterler içeriyor.  Onun yerine ''{1}'' adı kullanıldı." }, { "COMPILER_ERROR_KEY", "Derleyici hataları:" }, { "COMPILER_WARNING_KEY", "Derleyici uyarıları:" }, { "RUNTIME_ERROR_KEY", "Derleme sonusu sınıf dosyası hataları:" }, { "INVALID_QNAME_ERR", "Değerinin bir QName ya da beyaz alanla ayrılmış QName listesi olması gereken bir özniteliğin değeri ''{0}''" }, { "INVALID_NCNAME_ERR", "Değerinin bir NCName olması gereken özniteliğin değeri ''{0}''" }, { "INVALID_METHOD_IN_OUTPUT", "Bir <xsl:output> öğesinin yöntem özniteliğinin değeri ''{0}''.  Değer ''xml'', ''html'', ''text'' ya da ncname olmayan bir qname olmalıdır" }, { "JAXP_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(dizgi adı) içinde özellik (feature) adı boş değerli olamaz." }, { "JAXP_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(dizgi adı, boole değer) içinde özellik (feature) adı boş değerli olamaz." }, { "JAXP_UNSUPPORTED_FEATURE", "Bu TransformerFactory üzerinde ''{0}'' özelliği tanımlanamaz." } };
  }
}
