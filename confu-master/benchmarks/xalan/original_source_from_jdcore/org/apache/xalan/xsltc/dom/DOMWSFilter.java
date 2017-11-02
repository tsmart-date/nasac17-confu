package org.apache.xalan.xsltc.dom;

import org.apache.xalan.xsltc.DOM;
import org.apache.xalan.xsltc.DOMEnhancedForDTM;
import org.apache.xalan.xsltc.StripFilter;
import org.apache.xalan.xsltc.runtime.AbstractTranslet;
import org.apache.xalan.xsltc.runtime.Hashtable;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMWSFilter;






































public class DOMWSFilter
  implements DTMWSFilter
{
  private AbstractTranslet m_translet;
  private StripFilter m_filter;
  private Hashtable m_mappings;
  private DTM m_currentDTM;
  private short[] m_currentMapping;
  
  public DOMWSFilter(AbstractTranslet translet)
  {
    m_translet = translet;
    m_mappings = new Hashtable();
    
    if ((translet instanceof StripFilter)) {
      m_filter = ((StripFilter)translet);
    }
  }
  











  public short getShouldStripSpace(int node, DTM dtm)
  {
    if ((m_filter != null) && ((dtm instanceof DOM))) {
      DOM dom = (DOM)dtm;
      int type = 0;
      
      if ((dtm instanceof DOMEnhancedForDTM)) {
        DOMEnhancedForDTM mappableDOM = (DOMEnhancedForDTM)dtm;
        short[] mapping;
        short[] mapping;
        if (dtm == m_currentDTM) {
          mapping = m_currentMapping;
        }
        else {
          mapping = (short[])m_mappings.get(dtm);
          if (mapping == null) {
            mapping = mappableDOM.getMapping(m_translet.getNamesArray(), m_translet.getUrisArray(), m_translet.getTypesArray());
            


            m_mappings.put(dtm, mapping);
            m_currentDTM = dtm;
            m_currentMapping = mapping;
          }
        }
        
        int expType = mappableDOM.getExpandedTypeID(node);
        





        if ((expType >= 0) && (expType < mapping.length)) {
          type = mapping[expType];
        } else {
          type = -1;
        }
      }
      else {
        return 3;
      }
      
      if (m_filter.stripSpace(dom, node, type)) {
        return 2;
      }
      return 1;
    }
    
    return 1;
  }
}