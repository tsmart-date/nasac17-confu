package org.apache.xerces.dom;

import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.NamedNodeMap;

public class DeferredElementNSImpl
  extends ElementNSImpl
  implements DeferredNode
{
  static final long serialVersionUID = -5001885145370927385L;
  protected transient int fNodeIndex;
  
  DeferredElementNSImpl(DeferredDocumentImpl paramDeferredDocumentImpl, int paramInt)
  {
    super(paramDeferredDocumentImpl, null);
    fNodeIndex = paramInt;
    needsSyncChildren(true);
  }
  
  public final int getNodeIndex()
  {
    return fNodeIndex;
  }
  
  protected final void synchronizeData()
  {
    needsSyncData(false);
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument;
    boolean bool = mutationEvents;
    mutationEvents = false;
    name = localDeferredDocumentImpl.getNodeName(fNodeIndex);
    int i = name.indexOf(':');
    if (i < 0) {
      localName = name;
    } else {
      localName = name.substring(i + 1);
    }
    namespaceURI = localDeferredDocumentImpl.getNodeURI(fNodeIndex);
    type = ((XSTypeDefinition)localDeferredDocumentImpl.getTypeInfo(fNodeIndex));
    setupDefaultAttributes();
    int j = localDeferredDocumentImpl.getNodeExtra(fNodeIndex);
    if (j != -1)
    {
      NamedNodeMap localNamedNodeMap = getAttributes();
      do
      {
        NodeImpl localNodeImpl = (NodeImpl)localDeferredDocumentImpl.getNodeObject(j);
        localNamedNodeMap.setNamedItem(localNodeImpl);
        j = localDeferredDocumentImpl.getPrevSibling(j);
      } while (j != -1);
    }
    mutationEvents = bool;
  }
  
  protected final void synchronizeChildren()
  {
    DeferredDocumentImpl localDeferredDocumentImpl = (DeferredDocumentImpl)ownerDocument();
    localDeferredDocumentImpl.synchronizeChildren(this, fNodeIndex);
  }
}
