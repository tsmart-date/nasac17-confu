package org.apache.xerces.impl.xs.util;

import java.util.Vector;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.XSModelImpl;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.util.XMLGrammarPoolImpl.Entry;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xs.XSModel;

public class XSGrammarPool
  extends XMLGrammarPoolImpl
{
  public XSGrammarPool() {}
  
  public XSModel toXSModel()
  {
    Vector localVector = new Vector();
    for (int i = 0; i < fGrammars.length; i++) {
      for (XMLGrammarPoolImpl.Entry localEntry = fGrammars[i]; localEntry != null; localEntry = next) {
        if (desc.getGrammarType().equals("http://www.w3.org/2001/XMLSchema")) {
          localVector.addElement(grammar);
        }
      }
    }
    int j = localVector.size();
    if (j == 0) {
      return null;
    }
    SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[j];
    for (int k = 0; k < j; k++) {
      arrayOfSchemaGrammar[k] = ((SchemaGrammar)localVector.elementAt(k));
    }
    return new XSModelImpl(arrayOfSchemaGrammar);
  }
}
