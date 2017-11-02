package org.apache.xalan.xsltc.compiler.util;

import java.util.ListResourceBundle;






















































































public class ErrorMessages_ja
  extends ListResourceBundle
{
  public ErrorMessages_ja() {}
  
  public Object[][] getContents()
  {
    return new Object[][] { { "MULTIPLE_STYLESHEET_ERR", "複数のスタイルシートが同じファイル内に定義されています。" }, { "TEMPLATE_REDEF_ERR", "テンプレート ''{0}'' はこのスタイルシート内にすでに定義されています。" }, { "TEMPLATE_UNDEF_ERR", "テンプレート ''{0}'' はこのスタイルシート内に定義されていません。" }, { "VARIABLE_REDEF_ERR", "変数 ''{0}'' は同一スコープに複数回定義されています。" }, { "VARIABLE_UNDEF_ERR", "変数またはパラメーター ''{0}'' が未定義です。" }, { "CLASS_NOT_FOUND_ERR", "クラス ''{0}'' が見つかりません。" }, { "METHOD_NOT_FOUND_ERR", "外部メソッド ''{0}'' が見つかりません (public でなければなりません)" }, { "ARGUMENT_CONVERSION_ERR", "メソッド ''{0}'' への呼び出しの引数/戻り値の型を変換できません。" }, { "FILE_NOT_FOUND_ERR", "ファイルまたは URI ''{0}'' が見つかりません。" }, { "INVALID_URI_ERR", "URI ''{0}'' が無効です。" }, { "FILE_ACCESS_ERR", "ファイルまたは URI ''{0}'' をオープンできません。" }, { "MISSING_ROOT_ERR", "<xsl:stylesheet> または <xsl:transform> 要素が必要でした。" }, { "NAMESPACE_UNDEF_ERR", "名前空間接頭部 ''{0}'' が宣言されていません。" }, { "FUNCTION_RESOLVE_ERR", "関数 ''{0}'' への呼び出しを解決できません。" }, { "NEED_LITERAL_ERR", "''{0}'' への引数はリテラル・ストリングでなければなりません。" }, { "XPATH_PARSER_ERR", "XPath 式 ''{0}'' を構文解析中にエラーが発生しました。" }, { "REQUIRED_ATTR_ERR", "必要属性 ''{0}'' がありません。" }, { "ILLEGAL_CHAR_ERR", "XPath 式内に正しくない文字 ''{0}'' があります。" }, { "ILLEGAL_PI_ERR", "処理命令の名前 ''{0}'' が正しくありません。" }, { "STRAY_ATTRIBUTE_ERR", "属性 ''{0}'' が要素の外側です。" }, { "ILLEGAL_ATTRIBUTE_ERR", "属性 ''{0}'' が正しくありません。" }, { "CIRCULAR_INCLUDE_ERR", "import/include が相互依存しています。スタイルシート ''{0}'' はすでにロードされています。" }, { "RESULT_TREE_SORT_ERR", "結果ツリー・フラグメントをソートできません (<xsl:sort> 要素は無視されます)。このノードは結果ツリーの作成時にソートしなければなりません。" }, { "SYMBOLS_REDEF_ERR", "10 進数フォーマット ''{0}'' はすでに定義されています。" }, { "XSL_VERSION_ERR", "XSL バージョン ''{0}'' は XSLTC によってサポートされていません。" }, { "CIRCULAR_VARIABLE_ERR", "''{0}'' の変数/パラメーターの参照が相互依存しています。" }, { "ILLEGAL_BINARY_OP_ERR", "2 進式の演算子が不明です。" }, { "ILLEGAL_ARG_ERR", "関数呼び出しの引数 (1 つ以上) が正しくありません。" }, { "DOCUMENT_ARG_ERR", "document() 関数への 2 つ目の引数はノード・セットでなければなりません。" }, { "MISSING_WHEN_ERR", "少なくとも 1 つの <xsl:when> 要素が <xsl:choose> 内に必要です。" }, { "MULTIPLE_OTHERWISE_ERR", "<xsl:choose> で許可されている <xsl:otherwise> 要素は 1 つだけです。" }, { "STRAY_OTHERWISE_ERR", "<xsl:otherwise> を使用できるのは <xsl:choose> 内だけです。" }, { "STRAY_WHEN_ERR", "<xsl:when> を使用できるのは <xsl:choose> 内だけです。" }, { "WHEN_ELEMENT_ERR", "<xsl:choose> で許可されているのは <xsl:when> および <xsl:otherwise> 要素だけです。" }, { "UNNAMED_ATTRIBSET_ERR", "<xsl:attribute-set> に 'name' 属性がありません。" }, { "ILLEGAL_CHILD_ERR", "子要素が正しくありません。" }, { "ILLEGAL_ELEM_NAME_ERR", "要素に ''{0}'' という名前を付けることはできません。" }, { "ILLEGAL_ATTR_NAME_ERR", "属性に ''{0}'' という名前を付けることはできません。" }, { "ILLEGAL_TEXT_NODE_ERR", "テキスト・データが最上位の <xsl:stylesheet> 要素の外側にあります。" }, { "SAX_PARSER_CONFIG_ERR", "JAXP パーサーは正しく構成されていません" }, { "INTERNAL_ERR", "リカバリー不能 XSLTC 内部エラー: ''{0}''" }, { "UNSUPPORTED_XSL_ERR", "XSL 要素 ''{0}'' はサポートされていません。" }, { "UNSUPPORTED_EXT_ERR", "XSLTC 拡張機能 ''{0}'' は認識されていません。" }, { "MISSING_XSLT_URI_ERR", "入力文書はスタイルシートではありません (XSL 名前空間はルート要素内で宣言されていません)" }, { "MISSING_XSLT_TARGET_ERR", "スタイルシート・ターゲット ''{0}'' が見つかりませんでした。" }, { "NOT_IMPLEMENTED_ERR", "''{0}'' が実装されていません。" }, { "NOT_STYLESHEET_ERR", "入力文書には XSL スタイルシートが入っていません。" }, { "ELEMENT_PARSE_ERR", "要素 ''{0}'' を構文解析できませんでした。" }, { "KEY_USE_ATTR_ERR", "<key> の use 属性は node、node-set、string、または number でなければなりません。" }, { "OUTPUT_VERSION_ERR", "出力 XML 文書のバージョンは 1.0 になっているはずです" }, { "ILLEGAL_RELAT_OP_ERR", "関係式の演算子が不明です" }, { "ATTRIBSET_UNDEF_ERR", "存在しない属性セット ''{0}'' を使用しようとしています。" }, { "ATTR_VAL_TEMPLATE_ERR", "属性値テンプレート ''{0}'' を構文解析できません。" }, { "UNKNOWN_SIG_TYPE_ERR", "クラス ''{0}'' のシグニチャー内のデータ型が不明です。" }, { "DATA_CONVERSION_ERR", "データ型 ''{0}'' を ''{1}'' に変換できません。" }, { "NO_TRANSLET_CLASS_ERR", "このテンプレートには有効な translet クラス定義が入っていません。" }, { "NO_MAIN_TRANSLET_ERR", "この Templates には名前が ''{0}'' のクラスは含まれていません。" }, { "TRANSLET_CLASS_ERR", "translet クラス ''{0}'' をロードできませんでした。" }, { "TRANSLET_OBJECT_ERR", "translet クラスがロードされましたが、translet インスタンスを作成できません。" }, { "ERROR_LISTENER_NULL_ERR", "''{0}'' の ErrorListener をヌルに設定しようとしています。" }, { "JAXP_UNKNOWN_SOURCE_ERR", "XSLTC がサポートしているのは StreamSource、SAXSource、および DOMSource だけです" }, { "JAXP_NO_SOURCE_ERR", "''{0}'' に渡された Source オブジェクトには内容がありません。" }, { "JAXP_COMPILE_ERR", "スタイルシートをコンパイルできませんでした" }, { "JAXP_INVALID_ATTR_ERR", "TransformerFactory は属性 ''{0}'' を認識しません。" }, { "JAXP_SET_RESULT_ERR", "setResult() は startDocument() の前に呼び出されていなければなりません。" }, { "JAXP_NO_TRANSLET_ERR", "変換プログラムにはカプセル化された translet オブジェクトがありません。" }, { "JAXP_NO_HANDLER_ERR", "変換結果の出力ハンドラーが定義されていません。" }, { "JAXP_NO_RESULT_ERR", "''{0}'' に渡された Result オブジェクトが無効です。" }, { "JAXP_UNKNOWN_PROP_ERR", "無効な Transformer プロパティー ''{0}'' にアクセスしようとしています。" }, { "SAX2DOM_ADAPTER_ERR", "SAX2DOM アダプター ''{0}'' を作成できませんでした。" }, { "XSLTC_SOURCE_ERR", "XSLTCSource.build() が systemId を設定しないで呼び出されています。" }, { "ER_RESULT_NULL", "結果はヌルにはならないはずです。" }, { "JAXP_INVALID_SET_PARAM_VALUE", "param {0} の値は有効な Java オブジェクトである必要があります" }, { "COMPILE_STDIN_ERR", "-i オプションは -o オプションと一緒に使用しなければなりません。" }, { "COMPILE_USAGE_STR", "形式\n   java org.apache.xalan.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-u] [-v] [-h] { <stylesheet> | -i }\n\nオプション\n   -o <output>    名前 <output> を生成される translet に\n                  割り当てます。デフォルトでは、translet 名は <stylesheet>\n                  名から派生します。このオプションは複数のスタイルシートを\n                コンパイルする場合は無視されます。\n-d <directory> translet の宛先ディレクトリーを指定します\n -j <jarfile>   translet クラスを <jarfile> として指定された\n                名前の JAR ファイルにパッケージします\n -p <package>   生成後のすべての translet クラスにパッケージ名\n                接頭部を指定します。\n-n             テンプレートのインライン化を使用可能にします (テンプレートのイン\n                ライン化で平均として良好なパフォーマンスを得ることができます)\n-x             追加のデバッグ・メッセージ出力をオンにします\n   -u             <stylesheet> 引数を URL として解釈します\n   -i             スタイルシートを標準入力から読み取るようコンパイラーに強制します\n   -v             コンパイラーのバージョンを印刷します\n   -h             この使用法を印刷します\n" }, { "TRANSFORM_USAGE_STR", "形式\n   java org.apache.xalan.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   translet <class> を使用して <document> で指定された\n   XML 文書を変換します。translet <class> はユーザーの CLASSPATH\n またはオプションで指定される <jarfile> に入っています。\nオプション\n   -j <jarfile>    ロードする translet が入っている JAR ファイルを指定します\n   -x              追加のデバッグ・メッセージ出力をオンにします\n   -n <iterations> は変換を <iterations> 回実行して\n                   プロファイル情報を表示します\n   -u <document_url> は XML 入力文書を URL として指定します\n" }, { "STRAY_SORT_ERR", "<xsl:sort> を使用できるのは <xsl:for-each> または <xsl:apply-templates> 内だけです。" }, { "UNSUPPORTED_ENCODING", "出力エンコード ''{0}'' はこの JVM ではサポートされていません。" }, { "SYNTAX_ERR", "''{0}'' に構文エラーがあります。" }, { "CONSTRUCTOR_NOT_FOUND", "外部コンストラクター ''{0}'' が見つかりません。" }, { "NO_JAVA_FUNCT_THIS_REF", "非 static Java 関数 ''{0}'' への先頭の引数は有効なオブジェクト参照でありません。" }, { "TYPE_CHECK_ERR", "式 ''{0}'' の型を検査中にエラーが発生しました。" }, { "TYPE_CHECK_UNK_LOC_ERR", "不明なロケーションで式の型を検査中にエラーが発生しました。" }, { "ILLEGAL_CMDLINE_OPTION_ERR", "コマンド行オプション ''{0}'' が無効です。" }, { "CMDLINE_OPT_MISSING_ARG_ERR", "コマンド行オプション ''{0}'' に必要な引数がありません。" }, { "WARNING_PLUS_WRAPPED_MSG", "警告:  ''{0}''\n       :{1}" }, { "WARNING_MSG", "警告:  ''{0}''" }, { "FATAL_ERR_PLUS_WRAPPED_MSG", "致命的エラー:  ''{0}''\n           :{1}" }, { "FATAL_ERR_MSG", "致命的エラー:  ''{0}''" }, { "ERROR_PLUS_WRAPPED_MSG", "エラー:  ''{0}''\n     :{1}" }, { "ERROR_MSG", "エラー:  ''{0}''" }, { "TRANSFORM_WITH_TRANSLET_STR", "translet ''{0}'' を使用する変換" }, { "TRANSFORM_WITH_JAR_STR", "JAR ファイル ''{1}'' の translet ''{0}'' を使用する変換" }, { "COULD_NOT_CREATE_TRANS_FACT", "TransformerFactory クラス ''{0}'' のインスタンスを作成できませんでした。" }, { "TRANSLET_NAME_JAVA_CONFLICT", "名前 ''{0}'' は translet クラスの名前として使用できません。Java クラスの名前に許可されていない文字が含まれているためです。代わりに、名前 ''{1}'' が使用されました。" }, { "COMPILER_ERROR_KEY", "コンパイラー・エラー:" }, { "COMPILER_WARNING_KEY", "コンパイラー警告:" }, { "RUNTIME_ERROR_KEY", "Translet エラー:" }, { "INVALID_QNAME_ERR", "値が QName または空白で区切られた QNames のリストでなければならない属性に、値 ''{0}'' がありました。" }, { "INVALID_NCNAME_ERR", "値が NCName でなければならない属性に、値 ''{0}'' がありました。" }, { "INVALID_METHOD_IN_OUTPUT", "<xsl:output> 要素の method 属性に、値 ''{0}'' がありました。値は ''xml''、''html''、''text''、または qname-but-not-ncname のいずれかである必要があります" }, { "JAXP_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(String name) の機能名をヌルにはできません。" }, { "JAXP_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(String name, boolean value) の機能名をヌルにはできません。" }, { "JAXP_UNSUPPORTED_FEATURE", "機能 ''{0}'' はこの TransformerFactory に設定できません。" } };
  }
}
