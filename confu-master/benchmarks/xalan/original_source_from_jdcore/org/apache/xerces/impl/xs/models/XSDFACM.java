package org.apache.xerces.impl.xs.models;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;
import org.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.xni.QName;

public class XSDFACM
  implements XSCMValidator
{
  private static final boolean DEBUG = false;
  private static final boolean DEBUG_VALIDATE_CONTENT = false;
  private Object[] fElemMap = null;
  private int[] fElemMapType = null;
  private int[] fElemMapId = null;
  private int fElemMapSize = 0;
  private boolean[] fFinalStateFlags = null;
  private CMStateSet[] fFollowList = null;
  private CMNode fHeadNode = null;
  private int fLeafCount = 0;
  private XSCMLeaf[] fLeafList = null;
  private int[] fLeafListType = null;
  private int[][] fTransTable = null;
  private int fTransTableSize = 0;
  private static long time = 0L;
  
  public XSDFACM(CMNode paramCMNode, int paramInt)
  {
    fLeafCount = paramInt;
    buildDFA(paramCMNode);
  }
  
  public boolean isFinalState(int paramInt)
  {
    return paramInt < 0 ? 0 : fFinalStateFlags[paramInt];
  }
  
  public Object oneTransition(QName paramQName, int[] paramArrayOfInt, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    int i = paramArrayOfInt[0];
    if ((i == -1) || (i == -2))
    {
      if (i == -1) {
        paramArrayOfInt[0] = -2;
      }
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    int j = 0;
    int k = 0;
    Object localObject = null;
    while (k < fElemMapSize)
    {
      j = fTransTable[i][k];
      if (j != -1)
      {
        int m = fElemMapType[k];
        if (m == 1)
        {
          localObject = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, (XSElementDecl)fElemMap[k]);
          if (localObject != null) {
            break;
          }
        }
        else if ((m == 2) && (((XSWildcardDecl)fElemMap[k]).allowNamespace(uri)))
        {
          localObject = fElemMap[k];
          break;
        }
      }
      k++;
    }
    if (k == fElemMapSize)
    {
      paramArrayOfInt[1] = paramArrayOfInt[0];
      paramArrayOfInt[0] = -1;
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    paramArrayOfInt[0] = j;
    return localObject;
  }
  
  Object findMatchingDecl(QName paramQName, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    XSElementDecl localXSElementDecl = null;
    for (int i = 0; i < fElemMapSize; i++)
    {
      int j = fElemMapType[i];
      if (j == 1)
      {
        localXSElementDecl = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, (XSElementDecl)fElemMap[i]);
        if (localXSElementDecl != null) {
          return localXSElementDecl;
        }
      }
      else if ((j == 2) && (((XSWildcardDecl)fElemMap[i]).allowNamespace(uri)))
      {
        return fElemMap[i];
      }
    }
    return null;
  }
  
  public int[] startContentModel()
  {
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = 0;
    return arrayOfInt;
  }
  
  public boolean endContentModel(int[] paramArrayOfInt)
  {
    return fFinalStateFlags[paramArrayOfInt[0]];
  }
  
  private void buildDFA(CMNode paramCMNode)
  {
    int i = fLeafCount;
    XSCMLeaf localXSCMLeaf = new XSCMLeaf(1, null, -1, fLeafCount++);
    fHeadNode = new XSCMBinOp(102, paramCMNode, localXSCMLeaf);
    fLeafList = new XSCMLeaf[fLeafCount];
    fLeafListType = new int[fLeafCount];
    postTreeBuildInit(fHeadNode);
    fFollowList = new CMStateSet[fLeafCount];
    for (int j = 0; j < fLeafCount; j++) {
      fFollowList[j] = new CMStateSet(fLeafCount);
    }
    calcFollowList(fHeadNode);
    fElemMap = new Object[fLeafCount];
    fElemMapType = new int[fLeafCount];
    fElemMapId = new int[fLeafCount];
    fElemMapSize = 0;
    for (int k = 0; k < fLeafCount; k++)
    {
      fElemMap[k] = null;
      int m = 0;
      n = fLeafList[k].getParticleId();
      while (m < fElemMapSize)
      {
        if (n == fElemMapId[m]) {
          break;
        }
        m++;
      }
      if (m == fElemMapSize)
      {
        fElemMap[fElemMapSize] = fLeafList[k].getLeaf();
        fElemMapType[fElemMapSize] = fLeafListType[k];
        fElemMapId[fElemMapSize] = n;
        fElemMapSize += 1;
      }
    }
    fElemMapSize -= 1;
    int[] arrayOfInt1 = new int[fLeafCount + fElemMapSize];
    int n = 0;
    for (int i1 = 0; i1 < fElemMapSize; i1++)
    {
      i2 = fElemMapId[i1];
      for (int i3 = 0; i3 < fLeafCount; i3++) {
        if (i2 == fLeafList[i3].getParticleId()) {
          arrayOfInt1[(n++)] = i3;
        }
      }
      arrayOfInt1[(n++)] = -1;
    }
    int i2 = fLeafCount * 4;
    Object localObject = new CMStateSet[i2];
    fFinalStateFlags = new boolean[i2];
    fTransTable = new int[i2][];
    CMStateSet localCMStateSet1 = fHeadNode.firstPos();
    int i4 = 0;
    int i5 = 0;
    fTransTable[i5] = makeDefStateList();
    localObject[i5] = localCMStateSet1;
    i5++;
    Hashtable localHashtable = new Hashtable();
    while (i4 < i5)
    {
      localCMStateSet1 = localObject[i4];
      int[] arrayOfInt2 = fTransTable[i4];
      fFinalStateFlags[i4] = localCMStateSet1.getBit(i);
      i4++;
      CMStateSet localCMStateSet2 = null;
      int i6 = 0;
      for (int i7 = 0; i7 < fElemMapSize; i7++)
      {
        if (localCMStateSet2 == null) {
          localCMStateSet2 = new CMStateSet(fLeafCount);
        } else {
          localCMStateSet2.zeroBits();
        }
        for (int i8 = arrayOfInt1[(i6++)]; i8 != -1; i8 = arrayOfInt1[(i6++)]) {
          if (localCMStateSet1.getBit(i8)) {
            localCMStateSet2.union(fFollowList[i8]);
          }
        }
        if (!localCMStateSet2.isEmpty())
        {
          Integer localInteger = (Integer)localHashtable.get(localCMStateSet2);
          int i9 = localInteger == null ? i5 : localInteger.intValue();
          if (i9 == i5)
          {
            localObject[i5] = localCMStateSet2;
            fTransTable[i5] = makeDefStateList();
            localHashtable.put(localCMStateSet2, new Integer(i5));
            i5++;
            localCMStateSet2 = null;
          }
          arrayOfInt2[i7] = i9;
          if (i5 == i2)
          {
            int i10 = (int)(i2 * 1.5D);
            CMStateSet[] arrayOfCMStateSet = new CMStateSet[i10];
            boolean[] arrayOfBoolean = new boolean[i10];
            int[][] arrayOfInt = new int[i10][];
            System.arraycopy(localObject, 0, arrayOfCMStateSet, 0, i2);
            System.arraycopy(fFinalStateFlags, 0, arrayOfBoolean, 0, i2);
            System.arraycopy(fTransTable, 0, arrayOfInt, 0, i2);
            i2 = i10;
            localObject = arrayOfCMStateSet;
            fFinalStateFlags = arrayOfBoolean;
            fTransTable = arrayOfInt;
          }
        }
      }
    }
    fHeadNode = null;
    fLeafList = null;
    fFollowList = null;
    fLeafListType = null;
    fElemMapId = null;
  }
  
  private void calcFollowList(CMNode paramCMNode)
  {
    if (paramCMNode.type() == 101)
    {
      calcFollowList(((XSCMBinOp)paramCMNode).getLeft());
      calcFollowList(((XSCMBinOp)paramCMNode).getRight());
    }
    else
    {
      CMStateSet localCMStateSet1;
      CMStateSet localCMStateSet2;
      int i;
      if (paramCMNode.type() == 102)
      {
        calcFollowList(((XSCMBinOp)paramCMNode).getLeft());
        calcFollowList(((XSCMBinOp)paramCMNode).getRight());
        localCMStateSet1 = ((XSCMBinOp)paramCMNode).getLeft().lastPos();
        localCMStateSet2 = ((XSCMBinOp)paramCMNode).getRight().firstPos();
        for (i = 0; i < fLeafCount; i++) {
          if (localCMStateSet1.getBit(i)) {
            fFollowList[i].union(localCMStateSet2);
          }
        }
      }
      else if ((paramCMNode.type() == 4) || (paramCMNode.type() == 6))
      {
        calcFollowList(((XSCMUniOp)paramCMNode).getChild());
        localCMStateSet1 = paramCMNode.firstPos();
        localCMStateSet2 = paramCMNode.lastPos();
        for (i = 0; i < fLeafCount; i++) {
          if (localCMStateSet2.getBit(i)) {
            fFollowList[i].union(localCMStateSet1);
          }
        }
      }
      else if (paramCMNode.type() == 5)
      {
        calcFollowList(((XSCMUniOp)paramCMNode).getChild());
      }
    }
  }
  
  private void dumpTree(CMNode paramCMNode, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      System.out.print("   ");
    }
    int j = paramCMNode.type();
    switch (j)
    {
    case 101: 
    case 102: 
      if (j == 101) {
        System.out.print("Choice Node ");
      } else {
        System.out.print("Seq Node ");
      }
      if (paramCMNode.isNullable()) {
        System.out.print("Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      dumpTree(((XSCMBinOp)paramCMNode).getLeft(), paramInt + 1);
      dumpTree(((XSCMBinOp)paramCMNode).getRight(), paramInt + 1);
      break;
    case 4: 
    case 5: 
    case 6: 
      System.out.print("Rep Node ");
      if (paramCMNode.isNullable()) {
        System.out.print("Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      dumpTree(((XSCMUniOp)paramCMNode).getChild(), paramInt + 1);
      break;
    case 1: 
      System.out.print("Leaf: (pos=" + ((XSCMLeaf)paramCMNode).getPosition() + "), " + "(elemIndex=" + ((XSCMLeaf)paramCMNode).getLeaf() + ") ");
      if (paramCMNode.isNullable()) {
        System.out.print(" Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      break;
    case 2: 
      System.out.print("Any Node: ");
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      break;
    default: 
      throw new RuntimeException("ImplementationMessages.VAL_NIICM");
    }
  }
  
  private int[] makeDefStateList()
  {
    int[] arrayOfInt = new int[fElemMapSize];
    for (int i = 0; i < fElemMapSize; i++) {
      arrayOfInt[i] = -1;
    }
    return arrayOfInt;
  }
  
  private void postTreeBuildInit(CMNode paramCMNode)
    throws RuntimeException
  {
    paramCMNode.setMaxStates(fLeafCount);
    XSCMLeaf localXSCMLeaf = null;
    int i = 0;
    if (paramCMNode.type() == 2)
    {
      localXSCMLeaf = (XSCMLeaf)paramCMNode;
      i = localXSCMLeaf.getPosition();
      fLeafList[i] = localXSCMLeaf;
      fLeafListType[i] = 2;
    }
    else if ((paramCMNode.type() == 101) || (paramCMNode.type() == 102))
    {
      postTreeBuildInit(((XSCMBinOp)paramCMNode).getLeft());
      postTreeBuildInit(((XSCMBinOp)paramCMNode).getRight());
    }
    else if ((paramCMNode.type() == 4) || (paramCMNode.type() == 6) || (paramCMNode.type() == 5))
    {
      postTreeBuildInit(((XSCMUniOp)paramCMNode).getChild());
    }
    else if (paramCMNode.type() == 1)
    {
      localXSCMLeaf = (XSCMLeaf)paramCMNode;
      i = localXSCMLeaf.getPosition();
      fLeafList[i] = localXSCMLeaf;
      fLeafListType[i] = 1;
    }
    else
    {
      throw new RuntimeException("ImplementationMessages.VAL_NIICM");
    }
  }
  
  public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler paramSubstitutionGroupHandler)
    throws XMLSchemaException
  {
    byte[][] arrayOfByte = new byte[fElemMapSize][fElemMapSize];
    for (int i = 0; (i < fTransTable.length) && (fTransTable[i] != null); i++) {
      for (j = 0; j < fElemMapSize; j++) {
        for (k = j + 1; k < fElemMapSize; k++) {
          if ((fTransTable[i][j] != -1) && (fTransTable[i][k] != -1) && (arrayOfByte[j][k] == 0)) {
            arrayOfByte[j][k] = (XSConstraints.overlapUPA(fElemMap[j], fElemMap[k], paramSubstitutionGroupHandler) ? 1 : -1);
          }
        }
      }
    }
    for (int j = 0; j < fElemMapSize; j++) {
      for (k = 0; k < fElemMapSize; k++) {
        if (arrayOfByte[j][k] == 1) {
          throw new XMLSchemaException("cos-nonambig", new Object[] { fElemMap[j].toString(), fElemMap[k].toString() });
        }
      }
    }
    for (int k = 0; k < fElemMapSize; k++) {
      if (fElemMapType[k] == 2)
      {
        XSWildcardDecl localXSWildcardDecl = (XSWildcardDecl)fElemMap[k];
        if ((fType == 3) || (fType == 2)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public Vector whatCanGoHere(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    if (i < 0) {
      i = paramArrayOfInt[1];
    }
    Vector localVector = new Vector();
    for (int j = 0; j < fElemMapSize; j++) {
      if (fTransTable[i][j] != -1) {
        localVector.addElement(fElemMap[j]);
      }
    }
    return localVector;
  }
}
