package org.apache.xerces.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class DocumentFragmentImpl
  extends ParentNode
  implements DocumentFragment
{
  static final long serialVersionUID = -7596449967279236746L;
  
  public DocumentFragmentImpl(CoreDocumentImpl paramCoreDocumentImpl)
  {
    super(paramCoreDocumentImpl);
  }
  
  public DocumentFragmentImpl() {}
  
  public short getNodeType()
  {
    return 11;
  }
  
  public String getNodeName()
  {
    return "#document-fragment";
  }
  
  public void normalize()
  {
    if (isNormalized()) {
      return;
    }
    if (needsSyncChildren()) {
      synchronizeChildren();
    }
    Object localObject2;
    for (Object localObject1 = firstChild; localObject1 != null; localObject1 = localObject2)
    {
      localObject2 = nextSibling;
      if (((NodeImpl)localObject1).getNodeType() == 3) {
        if ((localObject2 != null) && (((NodeImpl)localObject2).getNodeType() == 3))
        {
          ((Text)localObject1).appendData(((NodeImpl)localObject2).getNodeValue());
          removeChild((Node)localObject2);
          localObject2 = localObject1;
        }
        else if ((((NodeImpl)localObject1).getNodeValue() == null) || (((NodeImpl)localObject1).getNodeValue().length() == 0))
        {
          removeChild((Node)localObject1);
        }
      }
      ((NodeImpl)localObject1).normalize();
    }
    isNormalized(true);
  }
}
