package org.apache.xalan.res;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;























































































































































































































public class XSLTErrorResources_ko
  extends ListResourceBundle
{
  public static final int MAX_CODE = 201;
  public static final int MAX_WARNING = 29;
  public static final int MAX_OTHERS = 55;
  public static final int MAX_MESSAGES = 231;
  public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX = "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX";
  public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT = "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT";
  public static final String ER_NO_CURLYBRACE = "ER_NO_CURLYBRACE";
  public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
  public static final String ER_ILLEGAL_ATTRIBUTE = "ER_ILLEGAL_ATTRIBUTE";
  public static final String ER_NULL_SOURCENODE_APPLYIMPORTS = "ER_NULL_SOURCENODE_APPLYIMPORTS";
  public static final String ER_CANNOT_ADD = "ER_CANNOT_ADD";
  public static final String ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES = "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES";
  public static final String ER_NO_NAME_ATTRIB = "ER_NO_NAME_ATTRIB";
  public static final String ER_TEMPLATE_NOT_FOUND = "ER_TEMPLATE_NOT_FOUND";
  public static final String ER_CANT_RESOLVE_NAME_AVT = "ER_CANT_RESOLVE_NAME_AVT";
  public static final String ER_REQUIRES_ATTRIB = "ER_REQUIRES_ATTRIB";
  public static final String ER_MUST_HAVE_TEST_ATTRIB = "ER_MUST_HAVE_TEST_ATTRIB";
  public static final String ER_BAD_VAL_ON_LEVEL_ATTRIB = "ER_BAD_VAL_ON_LEVEL_ATTRIB";
  public static final String ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
  public static final String ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
  public static final String ER_NEED_MATCH_ATTRIB = "ER_NEED_MATCH_ATTRIB";
  public static final String ER_NEED_NAME_OR_MATCH_ATTRIB = "ER_NEED_NAME_OR_MATCH_ATTRIB";
  public static final String ER_CANT_RESOLVE_NSPREFIX = "ER_CANT_RESOLVE_NSPREFIX";
  public static final String ER_ILLEGAL_VALUE = "ER_ILLEGAL_VALUE";
  public static final String ER_NO_OWNERDOC = "ER_NO_OWNERDOC";
  public static final String ER_ELEMTEMPLATEELEM_ERR = "ER_ELEMTEMPLATEELEM_ERR";
  public static final String ER_NULL_CHILD = "ER_NULL_CHILD";
  public static final String ER_NEED_SELECT_ATTRIB = "ER_NEED_SELECT_ATTRIB";
  public static final String ER_NEED_TEST_ATTRIB = "ER_NEED_TEST_ATTRIB";
  public static final String ER_NEED_NAME_ATTRIB = "ER_NEED_NAME_ATTRIB";
  public static final String ER_NO_CONTEXT_OWNERDOC = "ER_NO_CONTEXT_OWNERDOC";
  public static final String ER_COULD_NOT_CREATE_XML_PROC_LIAISON = "ER_COULD_NOT_CREATE_XML_PROC_LIAISON";
  public static final String ER_PROCESS_NOT_SUCCESSFUL = "ER_PROCESS_NOT_SUCCESSFUL";
  public static final String ER_NOT_SUCCESSFUL = "ER_NOT_SUCCESSFUL";
  public static final String ER_ENCODING_NOT_SUPPORTED = "ER_ENCODING_NOT_SUPPORTED";
  public static final String ER_COULD_NOT_CREATE_TRACELISTENER = "ER_COULD_NOT_CREATE_TRACELISTENER";
  public static final String ER_KEY_REQUIRES_NAME_ATTRIB = "ER_KEY_REQUIRES_NAME_ATTRIB";
  public static final String ER_KEY_REQUIRES_MATCH_ATTRIB = "ER_KEY_REQUIRES_MATCH_ATTRIB";
  public static final String ER_KEY_REQUIRES_USE_ATTRIB = "ER_KEY_REQUIRES_USE_ATTRIB";
  public static final String ER_REQUIRES_ELEMENTS_ATTRIB = "ER_REQUIRES_ELEMENTS_ATTRIB";
  public static final String ER_MISSING_PREFIX_ATTRIB = "ER_MISSING_PREFIX_ATTRIB";
  public static final String ER_BAD_STYLESHEET_URL = "ER_BAD_STYLESHEET_URL";
  public static final String ER_FILE_NOT_FOUND = "ER_FILE_NOT_FOUND";
  public static final String ER_IOEXCEPTION = "ER_IOEXCEPTION";
  public static final String ER_NO_HREF_ATTRIB = "ER_NO_HREF_ATTRIB";
  public static final String ER_STYLESHEET_INCLUDES_ITSELF = "ER_STYLESHEET_INCLUDES_ITSELF";
  public static final String ER_PROCESSINCLUDE_ERROR = "ER_PROCESSINCLUDE_ERROR";
  public static final String ER_MISSING_LANG_ATTRIB = "ER_MISSING_LANG_ATTRIB";
  public static final String ER_MISSING_CONTAINER_ELEMENT_COMPONENT = "ER_MISSING_CONTAINER_ELEMENT_COMPONENT";
  public static final String ER_CAN_ONLY_OUTPUT_TO_ELEMENT = "ER_CAN_ONLY_OUTPUT_TO_ELEMENT";
  public static final String ER_PROCESS_ERROR = "ER_PROCESS_ERROR";
  public static final String ER_UNIMPLNODE_ERROR = "ER_UNIMPLNODE_ERROR";
  public static final String ER_NO_SELECT_EXPRESSION = "ER_NO_SELECT_EXPRESSION";
  public static final String ER_CANNOT_SERIALIZE_XSLPROCESSOR = "ER_CANNOT_SERIALIZE_XSLPROCESSOR";
  public static final String ER_NO_INPUT_STYLESHEET = "ER_NO_INPUT_STYLESHEET";
  public static final String ER_FAILED_PROCESS_STYLESHEET = "ER_FAILED_PROCESS_STYLESHEET";
  public static final String ER_COULDNT_PARSE_DOC = "ER_COULDNT_PARSE_DOC";
  public static final String ER_COULDNT_FIND_FRAGMENT = "ER_COULDNT_FIND_FRAGMENT";
  public static final String ER_NODE_NOT_ELEMENT = "ER_NODE_NOT_ELEMENT";
  public static final String ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB = "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB";
  public static final String ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB = "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB";
  public static final String ER_NO_CLONE_OF_DOCUMENT_FRAG = "ER_NO_CLONE_OF_DOCUMENT_FRAG";
  public static final String ER_CANT_CREATE_ITEM = "ER_CANT_CREATE_ITEM";
  public static final String ER_XMLSPACE_ILLEGAL_VALUE = "ER_XMLSPACE_ILLEGAL_VALUE";
  public static final String ER_NO_XSLKEY_DECLARATION = "ER_NO_XSLKEY_DECLARATION";
  public static final String ER_CANT_CREATE_URL = "ER_CANT_CREATE_URL";
  public static final String ER_XSLFUNCTIONS_UNSUPPORTED = "ER_XSLFUNCTIONS_UNSUPPORTED";
  public static final String ER_PROCESSOR_ERROR = "ER_PROCESSOR_ERROR";
  public static final String ER_NOT_ALLOWED_INSIDE_STYLESHEET = "ER_NOT_ALLOWED_INSIDE_STYLESHEET";
  public static final String ER_RESULTNS_NOT_SUPPORTED = "ER_RESULTNS_NOT_SUPPORTED";
  public static final String ER_DEFAULTSPACE_NOT_SUPPORTED = "ER_DEFAULTSPACE_NOT_SUPPORTED";
  public static final String ER_INDENTRESULT_NOT_SUPPORTED = "ER_INDENTRESULT_NOT_SUPPORTED";
  public static final String ER_ILLEGAL_ATTRIB = "ER_ILLEGAL_ATTRIB";
  public static final String ER_UNKNOWN_XSL_ELEM = "ER_UNKNOWN_XSL_ELEM";
  public static final String ER_BAD_XSLSORT_USE = "ER_BAD_XSLSORT_USE";
  public static final String ER_MISPLACED_XSLWHEN = "ER_MISPLACED_XSLWHEN";
  public static final String ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE";
  public static final String ER_MISPLACED_XSLOTHERWISE = "ER_MISPLACED_XSLOTHERWISE";
  public static final String ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE";
  public static final String ER_NOT_ALLOWED_INSIDE_TEMPLATE = "ER_NOT_ALLOWED_INSIDE_TEMPLATE";
  public static final String ER_UNKNOWN_EXT_NS_PREFIX = "ER_UNKNOWN_EXT_NS_PREFIX";
  public static final String ER_IMPORTS_AS_FIRST_ELEM = "ER_IMPORTS_AS_FIRST_ELEM";
  public static final String ER_IMPORTING_ITSELF = "ER_IMPORTING_ITSELF";
  public static final String ER_XMLSPACE_ILLEGAL_VAL = "ER_XMLSPACE_ILLEGAL_VAL";
  public static final String ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL = "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL";
  public static final String ER_SAX_EXCEPTION = "ER_SAX_EXCEPTION";
  public static final String ER_XSLT_ERROR = "ER_XSLT_ERROR";
  public static final String ER_CURRENCY_SIGN_ILLEGAL = "ER_CURRENCY_SIGN_ILLEGAL";
  public static final String ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM = "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM";
  public static final String ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER = "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER";
  public static final String ER_REDIRECT_COULDNT_GET_FILENAME = "ER_REDIRECT_COULDNT_GET_FILENAME";
  public static final String ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT = "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT";
  public static final String ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX = "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX";
  public static final String ER_MISSING_NS_URI = "ER_MISSING_NS_URI";
  public static final String ER_MISSING_ARG_FOR_OPTION = "ER_MISSING_ARG_FOR_OPTION";
  public static final String ER_INVALID_OPTION = "ER_INVALID_OPTION";
  public static final String ER_MALFORMED_FORMAT_STRING = "ER_MALFORMED_FORMAT_STRING";
  public static final String ER_STYLESHEET_REQUIRES_VERSION_ATTRIB = "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB";
  public static final String ER_ILLEGAL_ATTRIBUTE_VALUE = "ER_ILLEGAL_ATTRIBUTE_VALUE";
  public static final String ER_CHOOSE_REQUIRES_WHEN = "ER_CHOOSE_REQUIRES_WHEN";
  public static final String ER_NO_APPLY_IMPORT_IN_FOR_EACH = "ER_NO_APPLY_IMPORT_IN_FOR_EACH";
  public static final String ER_CANT_USE_DTM_FOR_OUTPUT = "ER_CANT_USE_DTM_FOR_OUTPUT";
  public static final String ER_CANT_USE_DTM_FOR_INPUT = "ER_CANT_USE_DTM_FOR_INPUT";
  public static final String ER_CALL_TO_EXT_FAILED = "ER_CALL_TO_EXT_FAILED";
  public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
  public static final String ER_INVALID_UTF16_SURROGATE = "ER_INVALID_UTF16_SURROGATE";
  public static final String ER_XSLATTRSET_USED_ITSELF = "ER_XSLATTRSET_USED_ITSELF";
  public static final String ER_CANNOT_MIX_XERCESDOM = "ER_CANNOT_MIX_XERCESDOM";
  public static final String ER_TOO_MANY_LISTENERS = "ER_TOO_MANY_LISTENERS";
  public static final String ER_IN_ELEMTEMPLATEELEM_READOBJECT = "ER_IN_ELEMTEMPLATEELEM_READOBJECT";
  public static final String ER_DUPLICATE_NAMED_TEMPLATE = "ER_DUPLICATE_NAMED_TEMPLATE";
  public static final String ER_INVALID_KEY_CALL = "ER_INVALID_KEY_CALL";
  public static final String ER_REFERENCING_ITSELF = "ER_REFERENCING_ITSELF";
  public static final String ER_ILLEGAL_DOMSOURCE_INPUT = "ER_ILLEGAL_DOMSOURCE_INPUT";
  public static final String ER_CLASS_NOT_FOUND_FOR_OPTION = "ER_CLASS_NOT_FOUND_FOR_OPTION";
  public static final String ER_REQUIRED_ELEM_NOT_FOUND = "ER_REQUIRED_ELEM_NOT_FOUND";
  public static final String ER_INPUT_CANNOT_BE_NULL = "ER_INPUT_CANNOT_BE_NULL";
  public static final String ER_URI_CANNOT_BE_NULL = "ER_URI_CANNOT_BE_NULL";
  public static final String ER_FILE_CANNOT_BE_NULL = "ER_FILE_CANNOT_BE_NULL";
  public static final String ER_SOURCE_CANNOT_BE_NULL = "ER_SOURCE_CANNOT_BE_NULL";
  public static final String ER_CANNOT_INIT_BSFMGR = "ER_CANNOT_INIT_BSFMGR";
  public static final String ER_CANNOT_CMPL_EXTENSN = "ER_CANNOT_CMPL_EXTENSN";
  public static final String ER_CANNOT_CREATE_EXTENSN = "ER_CANNOT_CREATE_EXTENSN";
  public static final String ER_INSTANCE_MTHD_CALL_REQUIRES = "ER_INSTANCE_MTHD_CALL_REQUIRES";
  public static final String ER_INVALID_ELEMENT_NAME = "ER_INVALID_ELEMENT_NAME";
  public static final String ER_ELEMENT_NAME_METHOD_STATIC = "ER_ELEMENT_NAME_METHOD_STATIC";
  public static final String ER_EXTENSION_FUNC_UNKNOWN = "ER_EXTENSION_FUNC_UNKNOWN";
  public static final String ER_MORE_MATCH_CONSTRUCTOR = "ER_MORE_MATCH_CONSTRUCTOR";
  public static final String ER_MORE_MATCH_METHOD = "ER_MORE_MATCH_METHOD";
  public static final String ER_MORE_MATCH_ELEMENT = "ER_MORE_MATCH_ELEMENT";
  public static final String ER_INVALID_CONTEXT_PASSED = "ER_INVALID_CONTEXT_PASSED";
  public static final String ER_POOL_EXISTS = "ER_POOL_EXISTS";
  public static final String ER_NO_DRIVER_NAME = "ER_NO_DRIVER_NAME";
  public static final String ER_NO_URL = "ER_NO_URL";
  public static final String ER_POOL_SIZE_LESSTHAN_ONE = "ER_POOL_SIZE_LESSTHAN_ONE";
  public static final String ER_INVALID_DRIVER = "ER_INVALID_DRIVER";
  public static final String ER_NO_STYLESHEETROOT = "ER_NO_STYLESHEETROOT";
  public static final String ER_ILLEGAL_XMLSPACE_VALUE = "ER_ILLEGAL_XMLSPACE_VALUE";
  public static final String ER_PROCESSFROMNODE_FAILED = "ER_PROCESSFROMNODE_FAILED";
  public static final String ER_RESOURCE_COULD_NOT_LOAD = "ER_RESOURCE_COULD_NOT_LOAD";
  public static final String ER_BUFFER_SIZE_LESSTHAN_ZERO = "ER_BUFFER_SIZE_LESSTHAN_ZERO";
  public static final String ER_UNKNOWN_ERROR_CALLING_EXTENSION = "ER_UNKNOWN_ERROR_CALLING_EXTENSION";
  public static final String ER_NO_NAMESPACE_DECL = "ER_NO_NAMESPACE_DECL";
  public static final String ER_ELEM_CONTENT_NOT_ALLOWED = "ER_ELEM_CONTENT_NOT_ALLOWED";
  public static final String ER_STYLESHEET_DIRECTED_TERMINATION = "ER_STYLESHEET_DIRECTED_TERMINATION";
  public static final String ER_ONE_OR_TWO = "ER_ONE_OR_TWO";
  public static final String ER_TWO_OR_THREE = "ER_TWO_OR_THREE";
  public static final String ER_COULD_NOT_LOAD_RESOURCE = "ER_COULD_NOT_LOAD_RESOURCE";
  public static final String ER_CANNOT_INIT_DEFAULT_TEMPLATES = "ER_CANNOT_INIT_DEFAULT_TEMPLATES";
  public static final String ER_RESULT_NULL = "ER_RESULT_NULL";
  public static final String ER_RESULT_COULD_NOT_BE_SET = "ER_RESULT_COULD_NOT_BE_SET";
  public static final String ER_NO_OUTPUT_SPECIFIED = "ER_NO_OUTPUT_SPECIFIED";
  public static final String ER_CANNOT_TRANSFORM_TO_RESULT_TYPE = "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE";
  public static final String ER_CANNOT_TRANSFORM_SOURCE_TYPE = "ER_CANNOT_TRANSFORM_SOURCE_TYPE";
  public static final String ER_NULL_CONTENT_HANDLER = "ER_NULL_CONTENT_HANDLER";
  public static final String ER_NULL_ERROR_HANDLER = "ER_NULL_ERROR_HANDLER";
  public static final String ER_CANNOT_CALL_PARSE = "ER_CANNOT_CALL_PARSE";
  public static final String ER_NO_PARENT_FOR_FILTER = "ER_NO_PARENT_FOR_FILTER";
  public static final String ER_NO_STYLESHEET_IN_MEDIA = "ER_NO_STYLESHEET_IN_MEDIA";
  public static final String ER_NO_STYLESHEET_PI = "ER_NO_STYLESHEET_PI";
  public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
  public static final String ER_PROPERTY_VALUE_BOOLEAN = "ER_PROPERTY_VALUE_BOOLEAN";
  public static final String ER_COULD_NOT_FIND_EXTERN_SCRIPT = "ER_COULD_NOT_FIND_EXTERN_SCRIPT";
  public static final String ER_RESOURCE_COULD_NOT_FIND = "ER_RESOURCE_COULD_NOT_FIND";
  public static final String ER_OUTPUT_PROPERTY_NOT_RECOGNIZED = "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED";
  public static final String ER_FAILED_CREATING_ELEMLITRSLT = "ER_FAILED_CREATING_ELEMLITRSLT";
  public static final String ER_VALUE_SHOULD_BE_NUMBER = "ER_VALUE_SHOULD_BE_NUMBER";
  public static final String ER_VALUE_SHOULD_EQUAL = "ER_VALUE_SHOULD_EQUAL";
  public static final String ER_FAILED_CALLING_METHOD = "ER_FAILED_CALLING_METHOD";
  public static final String ER_FAILED_CREATING_ELEMTMPL = "ER_FAILED_CREATING_ELEMTMPL";
  public static final String ER_CHARS_NOT_ALLOWED = "ER_CHARS_NOT_ALLOWED";
  public static final String ER_ATTR_NOT_ALLOWED = "ER_ATTR_NOT_ALLOWED";
  public static final String ER_BAD_VALUE = "ER_BAD_VALUE";
  public static final String ER_ATTRIB_VALUE_NOT_FOUND = "ER_ATTRIB_VALUE_NOT_FOUND";
  public static final String ER_ATTRIB_VALUE_NOT_RECOGNIZED = "ER_ATTRIB_VALUE_NOT_RECOGNIZED";
  public static final String ER_NULL_URI_NAMESPACE = "ER_NULL_URI_NAMESPACE";
  public static final String ER_NUMBER_TOO_BIG = "ER_NUMBER_TOO_BIG";
  public static final String ER_CANNOT_FIND_SAX1_DRIVER = "ER_CANNOT_FIND_SAX1_DRIVER";
  public static final String ER_SAX1_DRIVER_NOT_LOADED = "ER_SAX1_DRIVER_NOT_LOADED";
  public static final String ER_SAX1_DRIVER_NOT_INSTANTIATED = "ER_SAX1_DRIVER_NOT_INSTANTIATED";
  public static final String ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER = "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER";
  public static final String ER_PARSER_PROPERTY_NOT_SPECIFIED = "ER_PARSER_PROPERTY_NOT_SPECIFIED";
  public static final String ER_PARSER_ARG_CANNOT_BE_NULL = "ER_PARSER_ARG_CANNOT_BE_NULL";
  public static final String ER_FEATURE = "ER_FEATURE";
  public static final String ER_PROPERTY = "ER_PROPERTY";
  public static final String ER_NULL_ENTITY_RESOLVER = "ER_NULL_ENTITY_RESOLVER";
  public static final String ER_NULL_DTD_HANDLER = "ER_NULL_DTD_HANDLER";
  public static final String ER_NO_DRIVER_NAME_SPECIFIED = "ER_NO_DRIVER_NAME_SPECIFIED";
  public static final String ER_NO_URL_SPECIFIED = "ER_NO_URL_SPECIFIED";
  public static final String ER_POOLSIZE_LESS_THAN_ONE = "ER_POOLSIZE_LESS_THAN_ONE";
  public static final String ER_INVALID_DRIVER_NAME = "ER_INVALID_DRIVER_NAME";
  public static final String ER_ERRORLISTENER = "ER_ERRORLISTENER";
  public static final String ER_ASSERT_NO_TEMPLATE_PARENT = "ER_ASSERT_NO_TEMPLATE_PARENT";
  public static final String ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR = "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR";
  public static final String ER_NOT_ALLOWED_IN_POSITION = "ER_NOT_ALLOWED_IN_POSITION";
  public static final String ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION = "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION";
  public static final String ER_NAMESPACE_CONTEXT_NULL_NAMESPACE = "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE";
  public static final String ER_NAMESPACE_CONTEXT_NULL_PREFIX = "ER_NAMESPACE_CONTEXT_NULL_PREFIX";
  public static final String ER_XPATH_RESOLVER_NULL_QNAME = "ER_XPATH_RESOLVER_NULL_QNAME";
  public static final String ER_XPATH_RESOLVER_NEGATIVE_ARITY = "ER_XPATH_RESOLVER_NEGATIVE_ARITY";
  public static final String INVALID_TCHAR = "INVALID_TCHAR";
  public static final String INVALID_QNAME = "INVALID_QNAME";
  public static final String INVALID_ENUM = "INVALID_ENUM";
  public static final String INVALID_NMTOKEN = "INVALID_NMTOKEN";
  public static final String INVALID_NCNAME = "INVALID_NCNAME";
  public static final String INVALID_BOOLEAN = "INVALID_BOOLEAN";
  public static final String INVALID_NUMBER = "INVALID_NUMBER";
  public static final String ER_ARG_LITERAL = "ER_ARG_LITERAL";
  public static final String ER_DUPLICATE_GLOBAL_VAR = "ER_DUPLICATE_GLOBAL_VAR";
  public static final String ER_DUPLICATE_VAR = "ER_DUPLICATE_VAR";
  public static final String ER_TEMPLATE_NAME_MATCH = "ER_TEMPLATE_NAME_MATCH";
  public static final String ER_INVALID_PREFIX = "ER_INVALID_PREFIX";
  public static final String ER_NO_ATTRIB_SET = "ER_NO_ATTRIB_SET";
  public static final String ER_FUNCTION_NOT_FOUND = "ER_FUNCTION_NOT_FOUND";
  public static final String ER_CANT_HAVE_CONTENT_AND_SELECT = "ER_CANT_HAVE_CONTENT_AND_SELECT";
  public static final String ER_INVALID_SET_PARAM_VALUE = "ER_INVALID_SET_PARAM_VALUE";
  public static final String ER_SET_FEATURE_NULL_NAME = "ER_SET_FEATURE_NULL_NAME";
  public static final String ER_GET_FEATURE_NULL_NAME = "ER_GET_FEATURE_NULL_NAME";
  public static final String ER_UNSUPPORTED_FEATURE = "ER_UNSUPPORTED_FEATURE";
  public static final String ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING = "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING";
  public static final String WG_FOUND_CURLYBRACE = "WG_FOUND_CURLYBRACE";
  public static final String WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR = "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR";
  public static final String WG_EXPR_ATTRIB_CHANGED_TO_SELECT = "WG_EXPR_ATTRIB_CHANGED_TO_SELECT";
  public static final String WG_NO_LOCALE_IN_FORMATNUMBER = "WG_NO_LOCALE_IN_FORMATNUMBER";
  public static final String WG_LOCALE_NOT_FOUND = "WG_LOCALE_NOT_FOUND";
  public static final String WG_CANNOT_MAKE_URL_FROM = "WG_CANNOT_MAKE_URL_FROM";
  public static final String WG_CANNOT_LOAD_REQUESTED_DOC = "WG_CANNOT_LOAD_REQUESTED_DOC";
  public static final String WG_CANNOT_FIND_COLLATOR = "WG_CANNOT_FIND_COLLATOR";
  public static final String WG_FUNCTIONS_SHOULD_USE_URL = "WG_FUNCTIONS_SHOULD_USE_URL";
  public static final String WG_ENCODING_NOT_SUPPORTED_USING_UTF8 = "WG_ENCODING_NOT_SUPPORTED_USING_UTF8";
  public static final String WG_ENCODING_NOT_SUPPORTED_USING_JAVA = "WG_ENCODING_NOT_SUPPORTED_USING_JAVA";
  public static final String WG_SPECIFICITY_CONFLICTS = "WG_SPECIFICITY_CONFLICTS";
  public static final String WG_PARSING_AND_PREPARING = "WG_PARSING_AND_PREPARING";
  public static final String WG_ATTR_TEMPLATE = "WG_ATTR_TEMPLATE";
  public static final String WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESPACE = "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP";
  public static final String WG_ATTRIB_NOT_HANDLED = "WG_ATTRIB_NOT_HANDLED";
  public static final String WG_NO_DECIMALFORMAT_DECLARATION = "WG_NO_DECIMALFORMAT_DECLARATION";
  public static final String WG_OLD_XSLT_NS = "WG_OLD_XSLT_NS";
  public static final String WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED = "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED";
  public static final String WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE = "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE";
  public static final String WG_ILLEGAL_ATTRIBUTE = "WG_ILLEGAL_ATTRIBUTE";
  public static final String WG_COULD_NOT_RESOLVE_PREFIX = "WG_COULD_NOT_RESOLVE_PREFIX";
  public static final String WG_STYLESHEET_REQUIRES_VERSION_ATTRIB = "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB";
  public static final String WG_ILLEGAL_ATTRIBUTE_NAME = "WG_ILLEGAL_ATTRIBUTE_NAME";
  public static final String WG_ILLEGAL_ATTRIBUTE_VALUE = "WG_ILLEGAL_ATTRIBUTE_VALUE";
  public static final String WG_EMPTY_SECOND_ARG = "WG_EMPTY_SECOND_ARG";
  public static final String WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
  public static final String WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
  public static final String WG_ILLEGAL_ATTRIBUTE_POSITION = "WG_ILLEGAL_ATTRIBUTE_POSITION";
  public static final String NO_MODIFICATION_ALLOWED_ERR = "NO_MODIFICATION_ALLOWED_ERR";
  public static final String BAD_CODE = "BAD_CODE";
  public static final String FORMAT_FAILED = "FORMAT_FAILED";
  public static final String ERROR_STRING = "#error";
  public static final String ERROR_HEADER = "오류: ";
  public static final String WARNING_HEADER = "경고: ";
  public static final String XSL_HEADER = "XSLT ";
  public static final String XML_HEADER = "XML ";
  /**
   * @deprecated
   */
  public static final String QUERY_HEADER = "PATTERN ";
  
  public XSLTErrorResources_ko() {}
  
  public Object[][] getContents()
  {
    return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "오류: 표현식에 '{'가 올 수 없습니다." }, { "ER_ILLEGAL_ATTRIBUTE", "{0}에 유효하지 않은 속성 {1}이(가) 있습니다." }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "xsl:apply-imports에서 sourceNode가 널(null)입니다." }, { "ER_CANNOT_ADD", "{1}에 {0}을(를) 추가할 수 없습니다." }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "handleApplyTemplatesInstruction에서 sourceNode가 널(null)입니다." }, { "ER_NO_NAME_ATTRIB", "{0}에 이름 속성이 있어야 합니다." }, { "ER_TEMPLATE_NOT_FOUND", "{0} 이름의 템플리트를 찾을 수 없습니다." }, { "ER_CANT_RESOLVE_NAME_AVT", "xsl:call-template에 있는 이름 AVT를 분석할 수 없습니다." }, { "ER_REQUIRES_ATTRIB", "{0}은(는) {1} 속성을 필요로 합니다." }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0}에 ''test'' 속성이 있어야 합니다." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "{0} 레벨 속성에 잘못된 값이 있습니다." }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "처리 명령어 이름은 'xml'이 될 수 없습니다." }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "처리 명령어 이름은 유효한 NCName이어야 합니다: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0}에 모드가 있으면 일치 속성이 있어야 합니다." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0}에 이름 또는 일치 속성이 필요합니다." }, { "ER_CANT_RESOLVE_NSPREFIX", "이름 공간 접두부를 분석할 수 없습니다: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space에 잘못된 값이 있습니다: {0}" }, { "ER_NO_OWNERDOC", "하위 노드에 소유자 문서가 없습니다." }, { "ER_ELEMTEMPLATEELEM_ERR", "ElemTemplateElement 오류: {0}" }, { "ER_NULL_CHILD", "널(null) 하위를 추가하려고 합니다." }, { "ER_NEED_SELECT_ATTRIB", "{0}에 선택적 속성이 필요합니다." }, { "ER_NEED_TEST_ATTRIB", "xsl:when에 'test' 속성이 있어야 합니다." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param에 'name' 속성이 있어야 합니다." }, { "ER_NO_CONTEXT_OWNERDOC", "문맥에 소유자 문서가 없습니다." }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "XML TransformerFactory Liaison을 작성할 수 없습니다: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: 프로세스가 실패했습니다." }, { "ER_NOT_SUCCESSFUL", "Xalan:이 실패했습니다." }, { "ER_ENCODING_NOT_SUPPORTED", "인코딩이 지원되지 않습니다: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "TraceListener를 작성할 수 없습니다: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key에 'name' 속성이 필요합니다." }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key에 'match' 속성이 필요합니다." }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key에 'use' 속성이 필요합니다." }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0}에 ''elements'' 속성이 필요합니다." }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} 속성 ''prefix''가 누락되었습니다." }, { "ER_BAD_STYLESHEET_URL", "스타일시트 URL이 잘못되었습니다: {0}" }, { "ER_FILE_NOT_FOUND", "스타일시트 파일을 찾을 수 없습니다: {0}" }, { "ER_IOEXCEPTION", "스타일시트 파일에 입출력 예외가 있습니다: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) {0}의 href 속성을 찾을 수 없습니다." }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0}이(가) 직접 또는 간접적으로 자신을 포함합니다." }, { "ER_PROCESSINCLUDE_ERROR", "StylesheetHandler.processInclude 오류, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} 속성 ''lang''이 누락되었습니다." }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) {0} 요소가 잘못된 위치에 있습니다. 컨테이너 요소 ''component''가 누락되었습니다." }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Element, DocumentFragment, Document 또는 PrintWriter로만 출력할 수 있습니다." }, { "ER_PROCESS_ERROR", "StylesheetRoot.process 오류" }, { "ER_UNIMPLNODE_ERROR", "UnImplNode 오류: {0}" }, { "ER_NO_SELECT_EXPRESSION", "오류. xpath 선택 표현식(-select)을 찾을 수 없습니다." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "XSLProcessor를 직렬화할 수 없습니다." }, { "ER_NO_INPUT_STYLESHEET", "스타일시트 입력을 지정하지 않았습니다." }, { "ER_FAILED_PROCESS_STYLESHEET", "스타일시트를 처리하는 데 실패했습니다." }, { "ER_COULDNT_PARSE_DOC", "{0} 문서를 구문 분석할 수 없습니다." }, { "ER_COULDNT_FIND_FRAGMENT", "단편을 찾을 수 없습니다: {0}" }, { "ER_NODE_NOT_ELEMENT", "단편 ID가 가리키는 노드가 요소가 아닙니다: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each에는 일치 또는 이름 속성이 있어야 합니다." }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "템플리트에는 일치 또는 이름 속성이 있어야 합니다." }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "문서 단편의 복제본이 없습니다." }, { "ER_CANT_CREATE_ITEM", "결과 트리에 항목을 작성할 수 없습니다: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "원본 XML의 xml:space에 유효하지 않은 값이 있습니다: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "{0}에 대한 xsl:key 선언이 없습니다." }, { "ER_CANT_CREATE_URL", "오류. url을 작성할 수 없습니다: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions가 지원되지 않습니다." }, { "ER_PROCESSOR_ERROR", "XSLT TransformerFactory 오류" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) 스타일시트 내에 {0}이(가) 허용되지 않습니다." }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns가 더 이상 지원되지 않습니다. 대신 xsl:output을 사용하십시오." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space가 더 이상 지원되지 않습니다. 대신 xsl:strip-space 또는 xsl:preserve-space를 사용하십시오." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result가 더 이상 지원되지 않습니다. 대신 xsl:output을 사용하십시오." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0}에 유효하지 않은 속성이 있습니다: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "알 수 없는 XSL 요소: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort는 xsl:apply-templates 또는 xsl:for-each와 함께 사용되어야 합니다." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when이 잘못된 위치에 놓여 있습니다." }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when이 xsl:choose의 상위에 있지 않습니다." }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise가 잘못된 위치에 놓여 있습니다." }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise가 xsl:choose의 상위에 있지 않습니다." }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) 템플리트 내에 {0}이(가) 허용되지 않습니다." }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} 확장 이름 공간 접두부 {1}을(를) 알 수 없습니다." }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) 가져오기는 스타일시트에서 첫 번째 요소로만 나타날 수 있습니다." }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0}이(가) 직접 또는 간접적으로 자신을 가져옵니다." }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:공간에 유효하지 않은 값이 있습니다: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet에 실패했습니다." }, { "ER_SAX_EXCEPTION", "SAX 예외" }, { "ER_FUNCTION_NOT_SUPPORTED", "함수가 지원되지 않습니다." }, { "ER_XSLT_ERROR", "XSLT 오류" }, { "ER_CURRENCY_SIGN_ILLEGAL", "포맷 패턴 문자열에 통화 부호가 허용되지 않습니다." }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "스타일시트 DOM에서 Document 함수가 지원되지 않습니다." }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "비접두부 분석자의 접두부를 분석할 수 없습니다." }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Redirect extension: 파일 이름을 가져올 수 없습니다. 파일 또는 선택적 속성은 올바른 문자열을 리턴해야 합니다." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "경로 재지정 확장에 FormatterListener를 빌드할 수 없습니다." }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "exclude-result-prefixes에 있는 접두부가 유효하지 않습니다: {0}" }, { "ER_MISSING_NS_URI", "지정된 접두부의 이름 공간 URI가 누락되었습니다." }, { "ER_MISSING_ARG_FOR_OPTION", "옵션의 인수가 누락되었습니다: {0}" }, { "ER_INVALID_OPTION", "잘못된 옵션: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "잘못 형식화된 포맷 문자열: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet에 'version' 속성이 필요합니다." }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "속성: {0}에 유효하지 않은 값이 있습니다: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose에 xsl:when이 필요합니다." }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports는 xsl:for-each에 허용되지 않습니다." }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "출력 DOM 노드에 DTMLiaison을 사용할 수 없습니다. 대신 org.apache.xpath.DOM2Helper를 전달하십시오." }, { "ER_CANT_USE_DTM_FOR_INPUT", "입력 DOM 노드에 DTMLiaison을 사용할 수 없습니다. 대신 org.apache.xpath.DOM2Helper를 전달하십시오." }, { "ER_CALL_TO_EXT_FAILED", "확장 요소 호출에 실패했습니다: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "접두부는 이름 공간으로 분석되어야 합니다: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "잘못된 UTF-16 대리자(surrogate)가 발견되었습니다: {0}" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0}이(가) 자신을 사용했으므로 무한 루프를 초래합니다." }, { "ER_CANNOT_MIX_XERCESDOM", "비Xerces-DOM 입력과 Xerces-DOM 출력을 혼합할 수 없습니다." }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "{0} 이름의 템플리트가 둘 이상입니다." }, { "ER_INVALID_KEY_CALL", "잘못된 함수 호출: recursive key() 호출이 허용되지 않습니다." }, { "ER_REFERENCING_ITSELF", "{0} 변수는 직접 또는 간접적으로 자신을 참조합니다." }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "newTemplates의 DOMSource에 대한 입력 노드는 널(null)이 될 수 없습니다." }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "{0} 옵션에 대한 클래스 파일이 없습니다." }, { "ER_REQUIRED_ELEM_NOT_FOUND", "필수 요소를 찾을 수 없습니다: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream은 널(null)이 될 수 없습니다." }, { "ER_URI_CANNOT_BE_NULL", "URI는 널(null)이 될 수 없습니다." }, { "ER_FILE_CANNOT_BE_NULL", "파일은 널(null)이 될 수 없습니다." }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource는 널(null)이 될 수 없습니다." }, { "ER_CANNOT_INIT_BSFMGR", "BSF 관리자를 초기화할 수 없습니다." }, { "ER_CANNOT_CMPL_EXTENSN", "확장자를 컴파일할 수 없습니다." }, { "ER_CANNOT_CREATE_EXTENSN", "확장자를 작성할 수 없습니다: {0}, 원인: {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "{0} 메소드에 대한 인스턴스 메소드 호출은 첫 번째 인수로 오브젝트 인스턴스를 필요로 합니다." }, { "ER_INVALID_ELEMENT_NAME", "잘못된 요소 이름이 지정되었습니다: {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "요소 이름 메소드는 static이어야 합니다: {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "확장 함수 {0} : {1}을(를) 알 수 없습니다." }, { "ER_MORE_MATCH_CONSTRUCTOR", "{0}에 대한 생성자에 가장 일치하는 것이 없습니다." }, { "ER_MORE_MATCH_METHOD", "{0} 메소드에 가장 일치하는 것이 없습니다." }, { "ER_MORE_MATCH_ELEMENT", "{0} 요소 메소드에 가장 일치하는 것이 없습니다." }, { "ER_INVALID_CONTEXT_PASSED", "{0}을(를) 평가하는 데 잘못된 문맥이 전달되었습니다." }, { "ER_POOL_EXISTS", "풀이 이미 있습니다." }, { "ER_NO_DRIVER_NAME", "드라이버 이름을 지정하지 않았습니다." }, { "ER_NO_URL", "URL을 지정하지 않았습니다." }, { "ER_POOL_SIZE_LESSTHAN_ONE", "풀 크기가 1 미만입니다." }, { "ER_INVALID_DRIVER", "잘못된 드라이버 이름을 지정했습니다." }, { "ER_NO_STYLESHEETROOT", "스타일시트 루트를 찾을 수 없습니다." }, { "ER_ILLEGAL_XMLSPACE_VALUE", "xml:space에 대해 유효하지 않은 값입니다." }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode가 실패했습니다." }, { "ER_RESOURCE_COULD_NOT_LOAD", "[ {0} ] 자원이 {1} \n {2} \t {3}을 로드할 수 없습니다." }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "버퍼 크기 <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "확장 호출 시 알 수 없는 오류가 발생했습니다." }, { "ER_NO_NAMESPACE_DECL", "{0} 접두부에 해당하는 이름 공간 선언이 없습니다." }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "lang=javaclass {0}에 대해 요소 컨텐츠가 허용되지 않습니다." }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "스타일시트가 종료를 지시했습니다." }, { "ER_ONE_OR_TWO", "1 또는 2" }, { "ER_TWO_OR_THREE", "2 또는 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "{0}(CLASSPATH 확인)을(를) 로드할 수 없으므로 현재 기본값만을 사용하는 중입니다." }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "기본 템플리트를 초기화할 수 없습니다." }, { "ER_RESULT_NULL", "결과는 널(null)이 될 수 없습니다." }, { "ER_RESULT_COULD_NOT_BE_SET", "결과를 설정할 수 없습니다." }, { "ER_NO_OUTPUT_SPECIFIED", "출력을 지정하지 않았습니다." }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "{0} 유형의 결과로 변환할 수 없습니다." }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "{0} 유형의 소스를 변환할 수 없습니다." }, { "ER_NULL_CONTENT_HANDLER", "널(null) 컨텐츠 핸들러" }, { "ER_NULL_ERROR_HANDLER", "널(null) 오류 핸들러" }, { "ER_CANNOT_CALL_PARSE", "ContentHandler를 설정하지 않은 경우에는 parse를 호출할 수 없습니다." }, { "ER_NO_PARENT_FOR_FILTER", "상위 필터가 없습니다." }, { "ER_NO_STYLESHEET_IN_MEDIA", "{0}에 스타일시트가 없습니다. 매체= {1}" }, { "ER_NO_STYLESHEET_PI", "{0}에 xml-스타일시트 PI가 없습니다." }, { "ER_NOT_SUPPORTED", "지원되지 않습니다: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "{0} 특성값은 부울 인스턴스이어야 합니다." }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "{0}에 있는 외부 스크립트에 도달할 수 없습니다." }, { "ER_RESOURCE_COULD_NOT_FIND", "[ {0} ] 자원을 찾을 수 없습니다.\n{1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "출력 특성이 인식되지 않습니다: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "ElemLiteralResult 인스턴스 작성에 실패했습니다." }, { "ER_VALUE_SHOULD_BE_NUMBER", "{0}에 대한 값에 구문 분석 가능한 숫자가 있어야 합니다." }, { "ER_VALUE_SHOULD_EQUAL", "{0}의 값은 yes 또는 no여야 합니다." }, { "ER_FAILED_CALLING_METHOD", "{0} 메소드 호출에 실패했습니다." }, { "ER_FAILED_CREATING_ELEMTMPL", "ElemTemplateElement 인스턴스 작성에 실패했습니다." }, { "ER_CHARS_NOT_ALLOWED", "문서의 이 지점에 문자가 허용되지 않습니다." }, { "ER_ATTR_NOT_ALLOWED", "{1} 요소에 \"{0}\" 속성이 허용되지 않습니다." }, { "ER_BAD_VALUE", "{0} 잘못된 값 {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "{0} 속성값이 없습니다. " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "{0} 속성값이 인식되지 않습니다. " }, { "ER_NULL_URI_NAMESPACE", "널(null) URI로 이름 공간 접두부를 생성하려고 합니다." }, { "ER_NUMBER_TOO_BIG", "최대로 긴 정수보다 큰 숫자를 포맷하려고 합니다." }, { "ER_CANNOT_FIND_SAX1_DRIVER", "SAX1 드라이버 클래스 {0}을(를) 찾을 수 없습니다." }, { "ER_SAX1_DRIVER_NOT_LOADED", "SAX1 드라이버 클래스 {0}이(가) 있으나 로드할 수 없습니다." }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "SAX1 드라이버 클래스 {0}을(를) 로드했으나 인스턴스화할 수 없습니다." }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "SAX1 드라이버 클래스 {0}이(가) org.xml.sax.Parser를 구현하지 않았습니다." }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "시스템 특성 org.xml.sax.parser를 지정하지 않았습니다." }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "구문 분석기 인수는 널(null)이 될 수 없습니다." }, { "ER_FEATURE", "특성: {0}" }, { "ER_PROPERTY", "특성: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "널(null) 엔티티 분석기" }, { "ER_NULL_DTD_HANDLER", "널(null) DTD 핸들러" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "드라이버 이름을 지정하지 않았습니다." }, { "ER_NO_URL_SPECIFIED", "URL을 지정하지 않았습니다." }, { "ER_POOLSIZE_LESS_THAN_ONE", "풀 크기가 1 미만입니다." }, { "ER_INVALID_DRIVER_NAME", "잘못된 드라이버 이름을 지정했습니다." }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "프로그래머 오류. 표현식에 ElemTemplateElement의 상위 항목이 없습니다." }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "RedundentExprEliminator에 있는 프로그래머의 단언문: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0}은(는) 스타일시트의 이 위치에서 허용되지 않습니다." }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "화이트 스페이스가 아닌 텍스트는 스타일시트의 이 위치에서 허용되지 않습니다." }, { "INVALID_TCHAR", "{0} CHAR 속성에 대해 사용된 {1} 값이 유효하지 않습니다. CHAR 유형의 속성은 1자여야 합니다." }, { "INVALID_QNAME", "{0} QNAME 속성에 대해 사용된 {1} 값이 유효하지 않습니다." }, { "INVALID_ENUM", "{0} ENUM 속성에 대해 사용된 {1} 값이 유효하지 않습니다. 유효한 값은 {2}입니다." }, { "INVALID_NMTOKEN", "{0} NMTOKEN 속성에 대해 사용된 {1} 값이 유효하지 않습니다. " }, { "INVALID_NCNAME", "{0} NCNAME 속성에 대해 사용된 {1} 값이 유효하지 않습니다. " }, { "INVALID_BOOLEAN", "{0} 부울 속성에 대해 사용된 {1} 값이 유효하지 않습니다. " }, { "INVALID_NUMBER", "{0} 숫자 속성에 대해 사용된 {1} 값이 유효하지 않습니다. " }, { "ER_ARG_LITERAL", "일치 패턴에서 {0}에 대한 인수는 리터럴이어야 합니다." }, { "ER_DUPLICATE_GLOBAL_VAR", "중복 글로벌 변수 선언입니다." }, { "ER_DUPLICATE_VAR", "중복 변수 선언입니다." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template에 이름 또는 일치 속성(또는 둘 다)이 있어야 합니다." }, { "ER_INVALID_PREFIX", "exclude-result-prefixes에 있는 접두부가 유효하지 않습니다: {0}" }, { "ER_NO_ATTRIB_SET", "이름이 {0}인 attribute-set가 없습니다." }, { "ER_FUNCTION_NOT_FOUND", "이름이 {0}인 함수가 없습니다." }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "{0} 요소에 컨텐츠와 select 속성이 둘 다 있어서는 안됩니다. " }, { "ER_INVALID_SET_PARAM_VALUE", "{0} 매개변수 값은 유효한 Java 오브젝트여야 합니다. " }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "xsl:namespace-alias 요소의 result-prefix 속성이 #default' 값을 갖지만 요소의 범위에 기본 이름 공간에 대한 선언이 없습니다." }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "xsl:namespace-alias 요소의 result-prefix 속성이 ''{0}'' 값을 갖지만 요소의 범위에 접두부 ''{0}''에 대한 이름 공간 선언이 없습니다." }, { "ER_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(문자열 이름, 부울 값)에서 기능 이름이 널(null)이면 안됩니다." }, { "ER_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(문자열 이름)에서 기능 이름이 널(null)이면 안됩니다." }, { "ER_UNSUPPORTED_FEATURE", "이 TransformerFactory에서 ''{0}'' 기능을 설정할 수 없습니다." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "보안 처리 기능이 true로 설정된 경우에는 ''{0}'' 확장 요소를 사용할 수 없습니다." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "널(null) 이름 공간 uri에 대한 접두부를 가져올 수 없습니다." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "널(null) 접두부에 대한 이름 공간 uri를 가져올 수 없습니다." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "함수 이름이 널(null)이면 안됩니다." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "arity가 음수이면 안됩니다." }, { "WG_FOUND_CURLYBRACE", "'}'가 발견되었으나 열린 속성 템플리트가 없습니다." }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "경고: 계수 속성이 xsl:number의 상위 요소와 일치하지 않습니다. 대상 = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "이전 구문: 'expr' 속성의 이름이 'select'로 변경되었습니다." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan이 아직 format-number 함수에 있는 로케일 이름을 처리하지 않습니다." }, { "WG_LOCALE_NOT_FOUND", "경고: xml:lang={0}에 대한 로케일을 찾을 수 없습니다." }, { "WG_CANNOT_MAKE_URL_FROM", "{0}에서 URL을 작성할 수 없습니다." }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "요청된 문서 {0}을(를) 로드할 수 없습니다." }, { "WG_CANNOT_FIND_COLLATOR", "<sort xml:lang={0}에 대한 Collator를 찾을 수 없습니다." }, { "WG_FUNCTIONS_SHOULD_USE_URL", "이전 구문: 함수 명령어는 {0}의 url을 사용해야 합니다." }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "인코딩이 지원되지 않습니다: {0}, UTF-8 사용" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "인코딩이 지원되지 않습니다: {0}, Java {1} 사용" }, { "WG_SPECIFICITY_CONFLICTS", "특성 충돌이 발견되었습니다: {0}. 스타일시트에서 마지막으로 발견된 것이 사용됩니다." }, { "WG_PARSING_AND_PREPARING", "========= 구문 분석 및 준비 {0} ==========" }, { "WG_ATTR_TEMPLATE", "Attr 템플리트, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "xsl:strip-space 및 xsl:preserve-space 사이의 일치 충돌" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan이 아직 {0} 속성을 처리하지 않습니다." }, { "WG_NO_DECIMALFORMAT_DECLARATION", "10진수 포맷에 대한 선언이 없습니다: {0}" }, { "WG_OLD_XSLT_NS", "XSLT 이름 공간이 누락되었거나 유효하지 않습니다. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "하나의 기본 xsl:decimal-format 선언만 허용됩니다." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "xsl:decimal-format 이름이 고유해야 합니다. \"{0}\" 이름이 중복되었습니다." }, { "WG_ILLEGAL_ATTRIBUTE", "{0}에 유효하지 않은 속성 {1}이(가) 있습니다." }, { "WG_COULD_NOT_RESOLVE_PREFIX", "이름 공간 접두부를 분석할 수 없습니다: {0}. 노드가 무시됩니다." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet에 'version' 속성이 필요합니다." }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "유효하지 않은 속성 이름: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "{0} 속성에 대해 사용된 유효하지 않은 값: {1}" }, { "WG_EMPTY_SECOND_ARG", "document 함수 두 번째 인수로부터의 결과 nodeset가 비어 있습니다. 빈 노드 세트를 리턴하십시오." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "xsl:processing-instruction의 'name' 속성값은 'xml'일 수 없습니다." }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "xsl:processing-instruction의 ''name'' 속성값이 유효한 NCName이어야 합니다: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "하위 노드가 생성된 이후 또는 요소가 작성되기 이전에 {0} 속성을 추가할 수 없습니다. 속성이 무시됩니다." }, { "NO_MODIFICATION_ALLOWED_ERR", "수정할 수 없는 오브젝트를 수정하려 했습니다." }, { "ui_language", "ko" }, { "help_language", "ko" }, { "language", "ko" }, { "BAD_CODE", "createMessage에 대한 매개변수가 범위를 벗어났습니다." }, { "FORMAT_FAILED", "messageFormat 호출 중 예외가 발생했습니다." }, { "version", ">>>>>>> Xalan 버전 " }, { "version2", "<<<<<<<" }, { "yes", "예" }, { "line", "행 #" }, { "column", "열 #" }, { "xsldone", "XSLProcessor: 완료" }, { "xslProc_option", "Xalan-J 명령행 프로세스 클래스 옵션:" }, { "xslProc_option", "Xalan-J 명령행 프로세스 클래스 옵션:" }, { "xslProc_invalid_xsltc_option", "{0} 옵션은 XSLTC 모드에서 지원되지 않습니다." }, { "xslProc_invalid_xalan_option", "{0} 옵션은 -XSLTC로만 사용될 수 있습니다." }, { "xslProc_no_input", "오류: 지정된 스타일시트 또는 입력 xml이 없습니다. 사용법 명령어에 대한 옵션없이 이 명령을 실행하십시오." }, { "xslProc_common_options", "-일반 옵션-" }, { "xslProc_xalan_options", "-Xalan에 대한 옵션-" }, { "xslProc_xsltc_options", "-XSLTC에 대한 옵션-" }, { "xslProc_return_to_continue", "(계속하려면 Enter 키를 누르십시오.)" }, { "optionXSLTC", "[-XSLTC(변환에 대해 XSLTC 사용)]" }, { "optionIN", "[-IN inputXMLURL]" }, { "optionXSL", "[-XSL XSLTransformationURL]" }, { "optionOUT", "[-OUT outputFileName]" }, { "optionLXCIN", "[-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "[-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "[-PARSER 구문 분석기 liaison의 완전한 클래스 이름]" }, { "optionE", "[-E(엔티티 ref를 펼치지 않음)]" }, { "optionV", "[-E(엔티티 ref를 펼치지 않음)]" }, { "optionQC", "[-QC(자동 패턴 충돌 경고)]" }, { "optionQ", "[-Q(자동 모드)]" }, { "optionLF", "[-LF(출력에서만 줄바꾸기 사용{기본값은 CR/LF임})]" }, { "optionCR", "[-CR(출력에서만 캐리지 리턴 사용{기본값은 CR/LF임})]" }, { "optionESCAPE", "[-ESCAPE(이스케이프할 문자{기본값은 <>&\"'\\r\\n임})]" }, { "optionINDENT", "[-INDENT(들여쓰기할 공백 수 제어{기본값은 0임})]" }, { "optionTT", "[-TT(템플리트 호출 시 템플리트 추적)]" }, { "optionTG", "[-TG(각 생성 이벤트 추적)]" }, { "optionTS", "[-TS(각 선택 이벤트 추적)]" }, { "optionTTC", "[-TTC(하위 템플리트 처리 시 하위 템플리트 추적)]" }, { "optionTCLASS", "[-TCLASS(추적 확장에 대한 TraceListener 클래스)]" }, { "optionVALIDATE", "[-VALIDATE(유효성 검증 발생 여부 설정. 기본적으로는 유효성 검증이 off로 설정됨.)]" }, { "optionEDUMP", "[-EDUMP{optional filename}(오류 시 stackdump 수행)]" }, { "optionXML", "[-XML(XML 포맷터를 사용하여 XML 머리글 추가)]" }, { "optionTEXT", "[-TEXT(단순 텍스트 포맷터 사용)]" }, { "optionHTML", "[-HTML(HTML 포맷터 사용)]" }, { "optionPARAM", "[-PARAM name expression(스타일시트 매개변수 설정)]" }, { "noParsermsg1", "XSL 프로세스가 실패했습니다." }, { "noParsermsg2", "** 구문 분석기를 찾을 수 없습니다 **" }, { "noParsermsg3", "클래스 경로를 점검하십시오." }, { "noParsermsg4", "Java용 IBM XML 구문 분석기가 없는 경우 다음에서 다운로드할 수 있습니다. " }, { "noParsermsg5", "IBM's AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "[-URIRESOLVER full class name(URIResolver를 사용하여 URI 분석)]" }, { "optionENTITYRESOLVER", "[-ENTITYRESOLVER full class name(EntityResolver를 사용하여 엔티티 분석)]" }, { "optionCONTENTHANDLER", "[-CONTENTHANDLER full class name(ContentHandler를 사용하여 출력 직렬화)]" }, { "optionLINENUMBERS", "[-L 소스 문서에 행 번호 사용]" }, { "optionSECUREPROCESSING", "   [-SECURE (보안 처리 기능을 true로 설정)]" }, { "optionMEDIA", "   [-MEDIA mediaType(매체 속성을 사용하여 문서와 연관된 스타일시트 찾기)]" }, { "optionFLAVOR", "   [-FLAVOR flavorName(명시적으로 s2s=SAX 또는 d2d=DOM을 사용하여 변환 수행)] " }, { "optionDIAG", "   [-DIAG(변환에 소요된 전체 밀리초 인쇄)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL(http://xml.apache.org/xalan/features/incremental을 true로 설정하여 증분 DTM 구성 요청)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE(http://xml.apache.org/xalan/features/optimize를 false로 설정하여 스타일시트 최적화 처리를 요청하지 않음)]" }, { "optionRL", "   [-RL recursionlimit(스타일시트 반복 정도에 대한 숫자 한계 단언)]" }, { "optionXO", "[-XO [transletName](생성된 translet에 이름 지정)]" }, { "optionXD", "[-XD destinationDirectory(translet에 대해 대상 디렉토리 지정)]" }, { "optionXJ", "[-XJ jarfile(이름이 <jarfile>인 jar 파일로 translet 클래스 패키지)]" }, { "optionXP", "[-XP package(생성된 모든 translet 클래스에 대해 패키지 이름 접두부 지정)]" }, { "optionXN", "[-XN(템플리트 인라이닝 사용 가능)]" }, { "optionXX", "[-XX(추가 디버깅 메시지 출력 켜기)]" }, { "optionXT", "[-XT(가능한 경우, translet을 사용하여 변환)]" }, { "diagTiming", "--------- {1}을(를) 통한 {0} 변환에 {2}ms가 소요되었습니다." }, { "recursionTooDeep", "템플리트 중첩이 너무 많습니다. 중첩 = {0}, 템플리트 {1} {2}" }, { "nameIs", "이름" }, { "matchPatternIs", "일치 패턴" } };
  }
  





















































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































  public static final XSLTErrorResources loadResourceBundle(String className)
    throws MissingResourceException
  {
    Locale locale = Locale.getDefault();
    String suffix = getResourceSuffix(locale);
    


    try
    {
      return (XSLTErrorResources)ResourceBundle.getBundle(className + suffix, locale);


    }
    catch (MissingResourceException e)
    {

      try
      {

        return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("ko", "KR"));


      }
      catch (MissingResourceException e2)
      {


        throw new MissingResourceException("Could not load any resource bundles.", className, "");
      }
    }
  }
  










  private static final String getResourceSuffix(Locale locale)
  {
    String suffix = "_" + locale.getLanguage();
    String country = locale.getCountry();
    
    if (country.equals("TW")) {
      suffix = suffix + "_" + country;
    }
    return suffix;
  }
}
