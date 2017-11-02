package org.apache.xerces.dom;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.dom.events.EventImpl;
import org.apache.xerces.dom.events.MutationEventImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.ranges.DocumentRange;
import org.w3c.dom.ranges.Range;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.TreeWalker;

public class DocumentImpl
  extends CoreDocumentImpl
  implements DocumentTraversal, DocumentEvent, DocumentRange
{
  static final long serialVersionUID = 515687835542616694L;
  protected Vector iterators;
  protected Vector ranges;
  protected Hashtable eventListeners;
  protected boolean mutationEvents = false;
  EnclosingAttr savedEnclosingAttr;
  
  public DocumentImpl() {}
  
  public DocumentImpl(boolean paramBoolean)
  {
    super(paramBoolean);
  }
  
  public DocumentImpl(DocumentType paramDocumentType)
  {
    super(paramDocumentType);
  }
  
  public DocumentImpl(DocumentType paramDocumentType, boolean paramBoolean)
  {
    super(paramDocumentType, paramBoolean);
  }
  
  public Node cloneNode(boolean paramBoolean)
  {
    DocumentImpl localDocumentImpl = new DocumentImpl();
    callUserDataHandlers(this, localDocumentImpl, (short)1);
    cloneNode(localDocumentImpl, paramBoolean);
    mutationEvents = mutationEvents;
    return localDocumentImpl;
  }
  
  public DOMImplementation getImplementation()
  {
    return DOMImplementationImpl.getDOMImplementation();
  }
  
  public NodeIterator createNodeIterator(Node paramNode, short paramShort, NodeFilter paramNodeFilter)
  {
    return createNodeIterator(paramNode, paramShort, paramNodeFilter, true);
  }
  
  public NodeIterator createNodeIterator(Node paramNode, int paramInt, NodeFilter paramNodeFilter, boolean paramBoolean)
  {
    if (paramNode == null)
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, (String)localObject);
    }
    Object localObject = new NodeIteratorImpl(this, paramNode, paramInt, paramNodeFilter, paramBoolean);
    if (iterators == null) {
      iterators = new Vector();
    }
    iterators.addElement(localObject);
    return localObject;
  }
  
  public TreeWalker createTreeWalker(Node paramNode, short paramShort, NodeFilter paramNodeFilter)
  {
    return createTreeWalker(paramNode, paramShort, paramNodeFilter, true);
  }
  
  public TreeWalker createTreeWalker(Node paramNode, int paramInt, NodeFilter paramNodeFilter, boolean paramBoolean)
  {
    if (paramNode == null)
    {
      String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
      throw new DOMException((short)9, str);
    }
    return new TreeWalkerImpl(paramNode, paramInt, paramNodeFilter, paramBoolean);
  }
  
  void removeNodeIterator(NodeIterator paramNodeIterator)
  {
    if (paramNodeIterator == null) {
      return;
    }
    if (iterators == null) {
      return;
    }
    iterators.removeElement(paramNodeIterator);
  }
  
  public Range createRange()
  {
    if (ranges == null) {
      ranges = new Vector();
    }
    RangeImpl localRangeImpl = new RangeImpl(this);
    ranges.addElement(localRangeImpl);
    return localRangeImpl;
  }
  
  void removeRange(Range paramRange)
  {
    if (paramRange == null) {
      return;
    }
    if (ranges == null) {
      return;
    }
    ranges.removeElement(paramRange);
  }
  
  void replacedText(NodeImpl paramNodeImpl)
  {
    if (ranges != null)
    {
      int i = ranges.size();
      for (int j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).receiveReplacedText(paramNodeImpl);
      }
    }
  }
  
  void deletedText(NodeImpl paramNodeImpl, int paramInt1, int paramInt2)
  {
    if (ranges != null)
    {
      int i = ranges.size();
      for (int j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).receiveDeletedText(paramNodeImpl, paramInt1, paramInt2);
      }
    }
  }
  
  void insertedText(NodeImpl paramNodeImpl, int paramInt1, int paramInt2)
  {
    if (ranges != null)
    {
      int i = ranges.size();
      for (int j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).receiveInsertedText(paramNodeImpl, paramInt1, paramInt2);
      }
    }
  }
  
  void splitData(Node paramNode1, Node paramNode2, int paramInt)
  {
    if (ranges != null)
    {
      int i = ranges.size();
      for (int j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).receiveSplitData(paramNode1, paramNode2, paramInt);
      }
    }
  }
  
  public Event createEvent(String paramString)
    throws DOMException
  {
    if ((paramString.equalsIgnoreCase("Events")) || ("Event".equals(paramString))) {
      return new EventImpl();
    }
    if ((paramString.equalsIgnoreCase("MutationEvents")) || ("MutationEvent".equals(paramString))) {
      return new MutationEventImpl();
    }
    String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
    throw new DOMException((short)9, str);
  }
  
  void setMutationEvents(boolean paramBoolean)
  {
    mutationEvents = paramBoolean;
  }
  
  boolean getMutationEvents()
  {
    return mutationEvents;
  }
  
  protected void setEventListeners(NodeImpl paramNodeImpl, Vector paramVector)
  {
    if (eventListeners == null) {
      eventListeners = new Hashtable();
    }
    if (paramVector == null)
    {
      eventListeners.remove(paramNodeImpl);
      if (eventListeners.isEmpty()) {
        mutationEvents = false;
      }
    }
    else
    {
      eventListeners.put(paramNodeImpl, paramVector);
      mutationEvents = true;
    }
  }
  
  protected Vector getEventListeners(NodeImpl paramNodeImpl)
  {
    if (eventListeners == null) {
      return null;
    }
    return (Vector)eventListeners.get(paramNodeImpl);
  }
  
  protected void addEventListener(NodeImpl paramNodeImpl, String paramString, EventListener paramEventListener, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.equals("")) || (paramEventListener == null)) {
      return;
    }
    removeEventListener(paramNodeImpl, paramString, paramEventListener, paramBoolean);
    Vector localVector = getEventListeners(paramNodeImpl);
    if (localVector == null)
    {
      localVector = new Vector();
      setEventListeners(paramNodeImpl, localVector);
    }
    localVector.addElement(new LEntry(paramString, paramEventListener, paramBoolean));
    LCount localLCount = LCount.lookup(paramString);
    if (paramBoolean)
    {
      captures += 1;
      total += 1;
    }
    else
    {
      bubbles += 1;
      total += 1;
    }
  }
  
  protected void removeEventListener(NodeImpl paramNodeImpl, String paramString, EventListener paramEventListener, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.equals("")) || (paramEventListener == null)) {
      return;
    }
    Vector localVector = getEventListeners(paramNodeImpl);
    if (localVector == null) {
      return;
    }
    for (int i = localVector.size() - 1; i >= 0; i--)
    {
      LEntry localLEntry = (LEntry)localVector.elementAt(i);
      if ((useCapture == paramBoolean) && (listener == paramEventListener) && (type.equals(paramString)))
      {
        localVector.removeElementAt(i);
        if (localVector.size() == 0) {
          setEventListeners(paramNodeImpl, null);
        }
        LCount localLCount = LCount.lookup(paramString);
        if (paramBoolean)
        {
          captures -= 1;
          total -= 1;
          break;
        }
        bubbles -= 1;
        total -= 1;
        break;
      }
    }
  }
  
  protected void copyEventListeners(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2)
  {
    Vector localVector = getEventListeners(paramNodeImpl1);
    if (localVector == null) {
      return;
    }
    setEventListeners(paramNodeImpl2, (Vector)localVector.clone());
  }
  
  protected boolean dispatchEvent(NodeImpl paramNodeImpl, Event paramEvent)
  {
    if (paramEvent == null) {
      return false;
    }
    EventImpl localEventImpl = (EventImpl)paramEvent;
    if ((!initialized) || (type == null) || (type.equals("")))
    {
      localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "UNSPECIFIED_EVENT_TYPE_ERR", null);
      throw new EventException((short)0, (String)localObject1);
    }
    Object localObject1 = LCount.lookup(localEventImpl.getType());
    if (total == 0) {
      return preventDefault;
    }
    target = paramNodeImpl;
    stopPropagation = false;
    preventDefault = false;
    Vector localVector1 = new Vector(10, 10);
    Object localObject2 = paramNodeImpl;
    for (Node localNode = ((Node)localObject2).getParentNode(); localNode != null; localNode = localNode.getParentNode())
    {
      localVector1.addElement(localNode);
      localObject2 = localNode;
    }
    Object localObject3;
    if (captures > 0)
    {
      eventPhase = 1;
      for (int i = localVector1.size() - 1; i >= 0; i--)
      {
        if (stopPropagation) {
          break;
        }
        localObject3 = (NodeImpl)localVector1.elementAt(i);
        currentTarget = ((EventTarget)localObject3);
        Vector localVector3 = getEventListeners((NodeImpl)localObject3);
        if (localVector3 != null)
        {
          Vector localVector4 = (Vector)localVector3.clone();
          int n = localVector4.size();
          for (int i1 = 0; i1 < n; i1++)
          {
            LEntry localLEntry1 = (LEntry)localVector4.elementAt(i1);
            if ((useCapture) && (type.equals(type)) && (localVector3.contains(localLEntry1))) {
              try
              {
                listener.handleEvent(localEventImpl);
              }
              catch (Exception localException2) {}
            }
          }
        }
      }
    }
    if (bubbles > 0)
    {
      eventPhase = 2;
      currentTarget = paramNodeImpl;
      Vector localVector2 = getEventListeners(paramNodeImpl);
      int k;
      Object localObject4;
      if ((!stopPropagation) && (localVector2 != null))
      {
        localObject3 = (Vector)localVector2.clone();
        k = ((Vector)localObject3).size();
        for (int m = 0; m < k; m++)
        {
          localObject4 = (LEntry)((Vector)localObject3).elementAt(m);
          if ((!useCapture) && (type.equals(type)) && (localVector2.contains(localObject4))) {
            try
            {
              listener.handleEvent(localEventImpl);
            }
            catch (Exception localException1) {}
          }
        }
      }
      if (bubbles)
      {
        eventPhase = 3;
        int j = localVector1.size();
        for (k = 0; k < j; k++)
        {
          if (stopPropagation) {
            break;
          }
          NodeImpl localNodeImpl = (NodeImpl)localVector1.elementAt(k);
          currentTarget = localNodeImpl;
          localVector2 = getEventListeners(localNodeImpl);
          if (localVector2 != null)
          {
            localObject4 = (Vector)localVector2.clone();
            int i2 = ((Vector)localObject4).size();
            for (int i3 = 0; i3 < i2; i3++)
            {
              LEntry localLEntry2 = (LEntry)((Vector)localObject4).elementAt(i3);
              if ((!useCapture) && (type.equals(type)) && (localVector2.contains(localLEntry2))) {
                try
                {
                  listener.handleEvent(localEventImpl);
                }
                catch (Exception localException3) {}
              }
            }
          }
        }
      }
    }
    if ((defaults > 0) && (cancelable) && (!preventDefault)) {}
    return preventDefault;
  }
  
  protected void dispatchEventToSubtree(Node paramNode, Event paramEvent)
  {
    ((NodeImpl)paramNode).dispatchEvent(paramEvent);
    if (paramNode.getNodeType() == 1)
    {
      NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
      for (int i = localNamedNodeMap.getLength() - 1; i >= 0; i--) {
        dispatchingEventToSubtree(localNamedNodeMap.item(i), paramEvent);
      }
    }
    dispatchingEventToSubtree(paramNode.getFirstChild(), paramEvent);
  }
  
  protected void dispatchingEventToSubtree(Node paramNode, Event paramEvent)
  {
    if (paramNode == null) {
      return;
    }
    ((NodeImpl)paramNode).dispatchEvent(paramEvent);
    if (paramNode.getNodeType() == 1)
    {
      NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
      for (int i = localNamedNodeMap.getLength() - 1; i >= 0; i--) {
        dispatchingEventToSubtree(localNamedNodeMap.item(i), paramEvent);
      }
    }
    dispatchingEventToSubtree(paramNode.getFirstChild(), paramEvent);
    dispatchingEventToSubtree(paramNode.getNextSibling(), paramEvent);
  }
  
  protected void dispatchAggregateEvents(NodeImpl paramNodeImpl, EnclosingAttr paramEnclosingAttr)
  {
    if (paramEnclosingAttr != null) {
      dispatchAggregateEvents(paramNodeImpl, node, oldvalue, (short)1);
    } else {
      dispatchAggregateEvents(paramNodeImpl, null, null, (short)0);
    }
  }
  
  protected void dispatchAggregateEvents(NodeImpl paramNodeImpl, AttrImpl paramAttrImpl, String paramString, short paramShort)
  {
    NodeImpl localNodeImpl = null;
    MutationEventImpl localMutationEventImpl;
    if (paramAttrImpl != null)
    {
      localLCount = LCount.lookup("DOMAttrModified");
      localNodeImpl = (NodeImpl)paramAttrImpl.getOwnerElement();
      if ((total > 0) && (localNodeImpl != null))
      {
        localMutationEventImpl = new MutationEventImpl();
        localMutationEventImpl.initMutationEvent("DOMAttrModified", true, false, paramAttrImpl, paramString, paramAttrImpl.getNodeValue(), paramAttrImpl.getNodeName(), paramShort);
        localNodeImpl.dispatchEvent(localMutationEventImpl);
      }
    }
    LCount localLCount = LCount.lookup("DOMSubtreeModified");
    if (total > 0)
    {
      localMutationEventImpl = new MutationEventImpl();
      localMutationEventImpl.initMutationEvent("DOMSubtreeModified", true, false, null, null, null, null, (short)0);
      if (paramAttrImpl != null)
      {
        dispatchEvent(paramAttrImpl, localMutationEventImpl);
        if (localNodeImpl != null) {
          dispatchEvent(localNodeImpl, localMutationEventImpl);
        }
      }
      else
      {
        dispatchEvent(paramNodeImpl, localMutationEventImpl);
      }
    }
  }
  
  protected void saveEnclosingAttr(NodeImpl paramNodeImpl)
  {
    savedEnclosingAttr = null;
    LCount localLCount = LCount.lookup("DOMAttrModified");
    if (total > 0)
    {
      NodeImpl localNodeImpl = paramNodeImpl;
      for (;;)
      {
        if (localNodeImpl == null) {
          return;
        }
        int i = localNodeImpl.getNodeType();
        if (i == 2)
        {
          EnclosingAttr localEnclosingAttr = new EnclosingAttr();
          node = ((AttrImpl)localNodeImpl);
          oldvalue = node.getNodeValue();
          savedEnclosingAttr = localEnclosingAttr;
          return;
        }
        if (i == 5)
        {
          localNodeImpl = localNodeImpl.parentNode();
        }
        else
        {
          if (i != 3) {
            break;
          }
          localNodeImpl = localNodeImpl.parentNode();
        }
      }
      return;
    }
  }
  
  void modifyingCharacterData(NodeImpl paramNodeImpl, boolean paramBoolean)
  {
    if ((mutationEvents) && (!paramBoolean)) {
      saveEnclosingAttr(paramNodeImpl);
    }
  }
  
  void modifiedCharacterData(NodeImpl paramNodeImpl, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((mutationEvents) && (!paramBoolean))
    {
      LCount localLCount = LCount.lookup("DOMCharacterDataModified");
      if (total > 0)
      {
        MutationEventImpl localMutationEventImpl = new MutationEventImpl();
        localMutationEventImpl.initMutationEvent("DOMCharacterDataModified", true, false, null, paramString1, paramString2, null, (short)0);
        dispatchEvent(paramNodeImpl, localMutationEventImpl);
      }
      dispatchAggregateEvents(paramNodeImpl, savedEnclosingAttr);
    }
  }
  
  void replacedCharacterData(NodeImpl paramNodeImpl, String paramString1, String paramString2)
  {
    modifiedCharacterData(paramNodeImpl, paramString1, paramString2, false);
  }
  
  void insertingNode(NodeImpl paramNodeImpl, boolean paramBoolean)
  {
    if ((mutationEvents) && (!paramBoolean)) {
      saveEnclosingAttr(paramNodeImpl);
    }
  }
  
  void insertedNode(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2, boolean paramBoolean)
  {
    if (mutationEvents)
    {
      LCount localLCount = LCount.lookup("DOMNodeInserted");
      Object localObject1;
      if (total > 0)
      {
        localObject1 = new MutationEventImpl();
        ((MutationEventImpl)localObject1).initMutationEvent("DOMNodeInserted", true, false, paramNodeImpl1, null, null, null, (short)0);
        dispatchEvent(paramNodeImpl2, (Event)localObject1);
      }
      localLCount = LCount.lookup("DOMNodeInsertedIntoDocument");
      if (total > 0)
      {
        localObject1 = paramNodeImpl1;
        if (savedEnclosingAttr != null) {
          localObject1 = (NodeImpl)savedEnclosingAttr.node.getOwnerElement();
        }
        if (localObject1 != null)
        {
          Object localObject2 = localObject1;
          while (localObject2 != null)
          {
            localObject1 = localObject2;
            if (((NodeImpl)localObject2).getNodeType() == 2) {
              localObject2 = (NodeImpl)((AttrImpl)localObject2).getOwnerElement();
            } else {
              localObject2 = ((NodeImpl)localObject2).parentNode();
            }
          }
          if (((NodeImpl)localObject1).getNodeType() == 9)
          {
            MutationEventImpl localMutationEventImpl = new MutationEventImpl();
            localMutationEventImpl.initMutationEvent("DOMNodeInsertedIntoDocument", false, false, null, null, null, null, (short)0);
            dispatchEventToSubtree(paramNodeImpl2, localMutationEventImpl);
          }
        }
      }
      if (!paramBoolean) {
        dispatchAggregateEvents(paramNodeImpl1, savedEnclosingAttr);
      }
    }
    if (ranges != null)
    {
      int i = ranges.size();
      for (int j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).insertedNodeFromDOM(paramNodeImpl2);
      }
    }
  }
  
  void removingNode(NodeImpl paramNodeImpl1, NodeImpl paramNodeImpl2, boolean paramBoolean)
  {
    int i;
    int j;
    if (iterators != null)
    {
      i = iterators.size();
      for (j = 0; j != i; j++) {
        ((NodeIteratorImpl)iterators.elementAt(j)).removeNode(paramNodeImpl2);
      }
    }
    if (ranges != null)
    {
      i = ranges.size();
      for (j = 0; j != i; j++) {
        ((RangeImpl)ranges.elementAt(j)).removeNode(paramNodeImpl2);
      }
    }
    if (mutationEvents)
    {
      if (!paramBoolean) {
        saveEnclosingAttr(paramNodeImpl1);
      }
      LCount localLCount = LCount.lookup("DOMNodeRemoved");
      Object localObject;
      if (total > 0)
      {
        localObject = new MutationEventImpl();
        ((MutationEventImpl)localObject).initMutationEvent("DOMNodeRemoved", true, false, paramNodeImpl1, null, null, null, (short)0);
        dispatchEvent(paramNodeImpl2, (Event)localObject);
      }
      localLCount = LCount.lookup("DOMNodeRemovedFromDocument");
      if (total > 0)
      {
        localObject = this;
        if (savedEnclosingAttr != null) {
          localObject = (NodeImpl)savedEnclosingAttr.node.getOwnerElement();
        }
        if (localObject != null)
        {
          for (NodeImpl localNodeImpl = ((NodeImpl)localObject).parentNode(); localNodeImpl != null; localNodeImpl = localNodeImpl.parentNode()) {
            localObject = localNodeImpl;
          }
          if (((NodeImpl)localObject).getNodeType() == 9)
          {
            MutationEventImpl localMutationEventImpl = new MutationEventImpl();
            localMutationEventImpl.initMutationEvent("DOMNodeRemovedFromDocument", false, false, null, null, null, null, (short)0);
            dispatchEventToSubtree(paramNodeImpl2, localMutationEventImpl);
          }
        }
      }
    }
  }
  
  void removedNode(NodeImpl paramNodeImpl, boolean paramBoolean)
  {
    if ((mutationEvents) && (!paramBoolean)) {
      dispatchAggregateEvents(paramNodeImpl, savedEnclosingAttr);
    }
  }
  
  void replacingNode(NodeImpl paramNodeImpl)
  {
    if (mutationEvents) {
      saveEnclosingAttr(paramNodeImpl);
    }
  }
  
  void replacingData(NodeImpl paramNodeImpl)
  {
    if (mutationEvents) {
      saveEnclosingAttr(paramNodeImpl);
    }
  }
  
  void replacedNode(NodeImpl paramNodeImpl)
  {
    if (mutationEvents) {
      dispatchAggregateEvents(paramNodeImpl, savedEnclosingAttr);
    }
  }
  
  void modifiedAttrValue(AttrImpl paramAttrImpl, String paramString)
  {
    if (mutationEvents) {
      dispatchAggregateEvents(paramAttrImpl, paramAttrImpl, paramString, (short)1);
    }
  }
  
  void setAttrNode(AttrImpl paramAttrImpl1, AttrImpl paramAttrImpl2)
  {
    if (mutationEvents) {
      if (paramAttrImpl2 == null) {
        dispatchAggregateEvents(ownerNode, paramAttrImpl1, null, (short)2);
      } else {
        dispatchAggregateEvents(ownerNode, paramAttrImpl1, paramAttrImpl2.getNodeValue(), (short)1);
      }
    }
  }
  
  void removedAttrNode(AttrImpl paramAttrImpl, NodeImpl paramNodeImpl, String paramString)
  {
    if (mutationEvents)
    {
      LCount localLCount = LCount.lookup("DOMAttrModified");
      if (total > 0)
      {
        MutationEventImpl localMutationEventImpl = new MutationEventImpl();
        localMutationEventImpl.initMutationEvent("DOMAttrModified", true, false, paramAttrImpl, paramAttrImpl.getNodeValue(), null, paramString, (short)3);
        dispatchEvent(paramNodeImpl, localMutationEventImpl);
      }
      dispatchAggregateEvents(paramNodeImpl, null, null, (short)0);
    }
  }
  
  void renamedAttrNode(Attr paramAttr1, Attr paramAttr2) {}
  
  void renamedElement(Element paramElement1, Element paramElement2) {}
  
  class EnclosingAttr
    implements Serializable
  {
    private static final long serialVersionUID = 5208387723391647216L;
    AttrImpl node;
    String oldvalue;
    
    EnclosingAttr() {}
  }
  
  class LEntry
    implements Serializable
  {
    private static final long serialVersionUID = -8426757059492421631L;
    String type;
    EventListener listener;
    boolean useCapture;
    
    LEntry(String paramString, EventListener paramEventListener, boolean paramBoolean)
    {
      type = paramString;
      listener = paramEventListener;
      useCapture = paramBoolean;
    }
  }
}
