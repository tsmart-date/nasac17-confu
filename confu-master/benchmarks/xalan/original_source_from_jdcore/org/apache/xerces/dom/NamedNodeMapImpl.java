package org.apache.xerces.dom;

import java.io.Serializable;
import java.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapImpl
  implements NamedNodeMap, Serializable
{
  static final long serialVersionUID = -7039242451046758020L;
  protected short flags;
  protected static final short READONLY = 1;
  protected static final short CHANGED = 2;
  protected static final short HASDEFAULTS = 4;
  protected Vector nodes;
  protected NodeImpl ownerNode;
  
  protected NamedNodeMapImpl(NodeImpl paramNodeImpl)
  {
    ownerNode = paramNodeImpl;
  }
  
  public int getLength()
  {
    return nodes != null ? nodes.size() : 0;
  }
  
  public Node item(int paramInt)
  {
    return (nodes != null) && (paramInt < nodes.size()) ? (Node)nodes.elementAt(paramInt) : null;
  }
  
  public Node getNamedItem(String paramString)
  {
    int i = findNamePoint(paramString, 0);
    return i < 0 ? null : (Node)nodes.elementAt(i);
  }
  
  public Node getNamedItemNS(String paramString1, String paramString2)
  {
    int i = findNamePoint(paramString1, paramString2);
    return i < 0 ? null : (Node)nodes.elementAt(i);
  }
  
  public Node setNamedItem(Node paramNode)
    throws DOMException
  {
    CoreDocumentImpl localCoreDocumentImpl = ownerNode.ownerDocument();
    if (errorChecking)
    {
      String str;
      if (isReadOnly())
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, str);
      }
      if (paramNode.getOwnerDocument() != localCoreDocumentImpl)
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
        throw new DOMException((short)4, str);
      }
    }
    int i = findNamePoint(paramNode.getNodeName(), 0);
    NodeImpl localNodeImpl = null;
    if (i >= 0)
    {
      localNodeImpl = (NodeImpl)nodes.elementAt(i);
      nodes.setElementAt(paramNode, i);
    }
    else
    {
      i = -1 - i;
      if (null == nodes) {
        nodes = new Vector(5, 10);
      }
      nodes.insertElementAt(paramNode, i);
    }
    return localNodeImpl;
  }
  
  public Node setNamedItemNS(Node paramNode)
    throws DOMException
  {
    CoreDocumentImpl localCoreDocumentImpl = ownerNode.ownerDocument();
    if (errorChecking)
    {
      String str;
      if (isReadOnly())
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
        throw new DOMException((short)7, str);
      }
      if (paramNode.getOwnerDocument() != localCoreDocumentImpl)
      {
        str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
        throw new DOMException((short)4, str);
      }
    }
    int i = findNamePoint(paramNode.getNamespaceURI(), paramNode.getLocalName());
    NodeImpl localNodeImpl = null;
    if (i >= 0)
    {
      localNodeImpl = (NodeImpl)nodes.elementAt(i);
      nodes.setElementAt(paramNode, i);
    }
    else
    {
      i = findNamePoint(paramNode.getNodeName(), 0);
      if (i >= 0)
      {
        localNodeImpl = (NodeImpl)nodes.elementAt(i);
        nodes.insertElementAt(paramNode, i);
      }
      else
      {
        i = -1 - i;
        if (null == nodes) {
          nodes = new Vector(5, 10);
        }
        nodes.insertElementAt(paramNode, i);
      }
    }
    return localNodeImpl;
  }
  
  public Node removeNamedItem(String paramString)
    throws DOMException
  {
    if (isReadOnly())
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
      throw new DOMException((short)7, str);
    }
    int i = findNamePoint(paramString, 0);
    if (i < 0)
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
      throw new DOMException((short)8, (String)localObject);
    }
    Object localObject = (NodeImpl)nodes.elementAt(i);
    nodes.removeElementAt(i);
    return localObject;
  }
  
  public Node removeNamedItemNS(String paramString1, String paramString2)
    throws DOMException
  {
    if (isReadOnly())
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
      throw new DOMException((short)7, str);
    }
    int i = findNamePoint(paramString1, paramString2);
    if (i < 0)
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_FOUND_ERR", null);
      throw new DOMException((short)8, (String)localObject);
    }
    Object localObject = (NodeImpl)nodes.elementAt(i);
    nodes.removeElementAt(i);
    return localObject;
  }
  
  public NamedNodeMapImpl cloneMap(NodeImpl paramNodeImpl)
  {
    NamedNodeMapImpl localNamedNodeMapImpl = new NamedNodeMapImpl(paramNodeImpl);
    localNamedNodeMapImpl.cloneContent(this);
    return localNamedNodeMapImpl;
  }
  
  protected void cloneContent(NamedNodeMapImpl paramNamedNodeMapImpl)
  {
    Vector localVector = nodes;
    if (localVector != null)
    {
      int i = localVector.size();
      if (i != 0)
      {
        if (nodes == null) {
          nodes = new Vector(i);
        }
        nodes.setSize(i);
        for (int j = 0; j < i; j++)
        {
          NodeImpl localNodeImpl1 = (NodeImpl)nodes.elementAt(j);
          NodeImpl localNodeImpl2 = (NodeImpl)localNodeImpl1.cloneNode(true);
          localNodeImpl2.isSpecified(localNodeImpl1.isSpecified());
          nodes.setElementAt(localNodeImpl2, j);
        }
      }
    }
  }
  
  void setReadOnly(boolean paramBoolean1, boolean paramBoolean2)
  {
    isReadOnly(paramBoolean1);
    if ((paramBoolean2) && (nodes != null)) {
      for (int i = nodes.size() - 1; i >= 0; i--) {
        ((NodeImpl)nodes.elementAt(i)).setReadOnly(paramBoolean1, paramBoolean2);
      }
    }
  }
  
  boolean getReadOnly()
  {
    return isReadOnly();
  }
  
  protected void setOwnerDocument(CoreDocumentImpl paramCoreDocumentImpl)
  {
    if (nodes != null) {
      for (int i = 0; i < nodes.size(); i++) {
        ((NodeImpl)item(i)).setOwnerDocument(paramCoreDocumentImpl);
      }
    }
  }
  
  final boolean isReadOnly()
  {
    return (flags & 0x1) != 0;
  }
  
  final void isReadOnly(boolean paramBoolean)
  {
    flags = (paramBoolean ? (short)(flags | 0x1) : (short)(flags & 0xFFFFFFFE));
  }
  
  final boolean changed()
  {
    return (flags & 0x2) != 0;
  }
  
  final void changed(boolean paramBoolean)
  {
    flags = (paramBoolean ? (short)(flags | 0x2) : (short)(flags & 0xFFFFFFFD));
  }
  
  final boolean hasDefaults()
  {
    return (flags & 0x4) != 0;
  }
  
  final void hasDefaults(boolean paramBoolean)
  {
    flags = (paramBoolean ? (short)(flags | 0x4) : (short)(flags & 0xFFFFFFFB));
  }
  
  protected int findNamePoint(String paramString, int paramInt)
  {
    int i = 0;
    if (nodes != null)
    {
      int j = paramInt;
      int k = nodes.size() - 1;
      while (j <= k)
      {
        i = (j + k) / 2;
        int m = paramString.compareTo(((Node)nodes.elementAt(i)).getNodeName());
        if (m == 0) {
          return i;
        }
        if (m < 0) {
          k = i - 1;
        } else {
          j = i + 1;
        }
      }
      if (j > i) {
        i = j;
      }
    }
    return -1 - i;
  }
  
  protected int findNamePoint(String paramString1, String paramString2)
  {
    if (nodes == null) {
      return -1;
    }
    if (paramString2 == null) {
      return -1;
    }
    for (int i = 0; i < nodes.size(); i++)
    {
      NodeImpl localNodeImpl = (NodeImpl)nodes.elementAt(i);
      String str1 = localNodeImpl.getNamespaceURI();
      String str2 = localNodeImpl.getLocalName();
      if (paramString1 == null)
      {
        if ((str1 == null) && ((paramString2.equals(str2)) || ((str2 == null) && (paramString2.equals(localNodeImpl.getNodeName()))))) {
          return i;
        }
      }
      else if ((paramString1.equals(str1)) && (paramString2.equals(str2))) {
        return i;
      }
    }
    return -1;
  }
  
  protected boolean precedes(Node paramNode1, Node paramNode2)
  {
    if (nodes != null) {
      for (int i = 0; i < nodes.size(); i++)
      {
        Node localNode = (Node)nodes.elementAt(i);
        if (localNode == paramNode1) {
          return true;
        }
        if (localNode == paramNode2) {
          return false;
        }
      }
    }
    return false;
  }
  
  protected void removeItem(int paramInt)
  {
    if ((nodes != null) && (paramInt < nodes.size())) {
      nodes.removeElementAt(paramInt);
    }
  }
  
  protected Object getItem(int paramInt)
  {
    if (nodes != null) {
      return nodes.elementAt(paramInt);
    }
    return null;
  }
  
  protected int addItem(Node paramNode)
  {
    int i = findNamePoint(paramNode.getNamespaceURI(), paramNode.getLocalName());
    if (i >= 0)
    {
      nodes.setElementAt(paramNode, i);
    }
    else
    {
      i = findNamePoint(paramNode.getNodeName(), 0);
      if (i >= 0)
      {
        nodes.insertElementAt(paramNode, i);
      }
      else
      {
        i = -1 - i;
        if (null == nodes) {
          nodes = new Vector(5, 10);
        }
        nodes.insertElementAt(paramNode, i);
      }
    }
    return i;
  }
  
  protected Vector cloneMap(Vector paramVector)
  {
    if (paramVector == null) {
      paramVector = new Vector(5, 10);
    }
    paramVector.setSize(0);
    if (nodes != null) {
      for (int i = 0; i < nodes.size(); i++) {
        paramVector.insertElementAt(nodes.elementAt(i), i);
      }
    }
    return paramVector;
  }
  
  protected int getNamedItemIndex(String paramString1, String paramString2)
  {
    return findNamePoint(paramString1, paramString2);
  }
  
  public void removeAll()
  {
    if (nodes != null) {
      nodes.removeAllElements();
    }
  }
}
