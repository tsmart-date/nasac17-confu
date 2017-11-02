package org.apache.xalan.transformer;

import java.util.Stack;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.BoolStack;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.ObjectStack;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;














































































































/**
 * @deprecated
 */
class TransformSnapshotImpl
  implements TransformSnapshot
{
  private VariableStack m_variableStacks;
  private IntStack m_currentNodes;
  private IntStack m_currentExpressionNodes;
  private Stack m_contextNodeLists;
  private DTMIterator m_contextNodeList;
  private Stack m_axesIteratorStack;
  private BoolStack m_currentTemplateRuleIsNull;
  private ObjectStack m_currentTemplateElements;
  private Stack m_currentMatchTemplates;
  private NodeVector m_currentMatchNodes;
  private CountersTable m_countersTable;
  private Stack m_attrSetStack;
  boolean m_nsContextPushed;
  private NamespaceMappings m_nsSupport;
  
  /**
   * @deprecated
   */
  TransformSnapshotImpl(TransformerImpl transformer)
  {
    try
    {
      SerializationHandler rtf = transformer.getResultTreeHandler();
      


      m_nsSupport = ((NamespaceMappings)rtf.getNamespaceMappings().clone());
      



      XPathContext xpc = transformer.getXPathContext();
      
      m_variableStacks = ((VariableStack)xpc.getVarStack().clone());
      m_currentNodes = ((IntStack)xpc.getCurrentNodeStack().clone());
      m_currentExpressionNodes = ((IntStack)xpc.getCurrentExpressionNodeStack().clone());
      
      m_contextNodeLists = ((Stack)xpc.getContextNodeListsStack().clone());
      
      if (!m_contextNodeLists.empty()) {
        m_contextNodeList = ((DTMIterator)xpc.getContextNodeList().clone());
      }
      
      m_axesIteratorStack = ((Stack)xpc.getAxesIteratorStackStacks().clone());
      m_currentTemplateRuleIsNull = ((BoolStack)m_currentTemplateRuleIsNull.clone());
      
      m_currentTemplateElements = ((ObjectStack)m_currentTemplateElements.clone());
      
      m_currentMatchTemplates = ((Stack)m_currentMatchTemplates.clone());
      
      m_currentMatchNodes = ((NodeVector)m_currentMatchedNodes.clone());
      
      m_countersTable = ((CountersTable)transformer.getCountersTable().clone());
      

      if (m_attrSetStack != null) {
        m_attrSetStack = ((Stack)m_attrSetStack.clone());
      }
    }
    catch (CloneNotSupportedException cnse) {
      throw new WrappedRuntimeException(cnse);
    }
  }
  









  /**
   * @deprecated
   */
  void apply(TransformerImpl transformer)
  {
    try
    {
      SerializationHandler rtf = transformer.getResultTreeHandler();
      
      if (rtf != null)
      {

        rtf.setNamespaceMappings((NamespaceMappings)m_nsSupport.clone());
      }
      
      XPathContext xpc = transformer.getXPathContext();
      
      xpc.setVarStack((VariableStack)m_variableStacks.clone());
      xpc.setCurrentNodeStack((IntStack)m_currentNodes.clone());
      xpc.setCurrentExpressionNodeStack((IntStack)m_currentExpressionNodes.clone());
      
      xpc.setContextNodeListsStack((Stack)m_contextNodeLists.clone());
      
      if (m_contextNodeList != null) {
        xpc.pushContextNodeList((DTMIterator)m_contextNodeList.clone());
      }
      xpc.setAxesIteratorStackStacks((Stack)m_axesIteratorStack.clone());
      
      m_currentTemplateRuleIsNull = ((BoolStack)m_currentTemplateRuleIsNull.clone());
      
      m_currentTemplateElements = ((ObjectStack)m_currentTemplateElements.clone());
      
      m_currentMatchTemplates = ((Stack)m_currentMatchTemplates.clone());
      
      m_currentMatchedNodes = ((NodeVector)m_currentMatchNodes.clone());
      
      m_countersTable = ((CountersTable)m_countersTable.clone());
      
      if (m_attrSetStack != null) {
        m_attrSetStack = ((Stack)m_attrSetStack.clone());
      }
    }
    catch (CloneNotSupportedException cnse) {
      throw new WrappedRuntimeException(cnse);
    }
  }
}
