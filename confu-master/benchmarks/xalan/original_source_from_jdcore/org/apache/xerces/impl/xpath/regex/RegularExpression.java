package org.apache.xerces.impl.xpath.regex;

import java.io.Serializable;
import java.text.CharacterIterator;

public class RegularExpression
  implements Serializable
{
  private static final long serialVersionUID = 6242499334195006401L;
  static final boolean DEBUG = false;
  String regex;
  int options;
  int nofparen;
  Token tokentree;
  boolean hasBackReferences = false;
  transient int minlength;
  transient Op operations = null;
  transient int numberOfClosures;
  transient Context context = null;
  transient RangeToken firstChar = null;
  transient String fixedString = null;
  transient int fixedStringOptions;
  transient BMPattern fixedStringTable = null;
  transient boolean fixedStringOnly = false;
  static final int IGNORE_CASE = 2;
  static final int SINGLE_LINE = 4;
  static final int MULTIPLE_LINES = 8;
  static final int EXTENDED_COMMENT = 16;
  static final int USE_UNICODE_CATEGORY = 32;
  static final int UNICODE_WORD_BOUNDARY = 64;
  static final int PROHIBIT_HEAD_CHARACTER_OPTIMIZATION = 128;
  static final int PROHIBIT_FIXED_STRING_OPTIMIZATION = 256;
  static final int XMLSCHEMA_MODE = 512;
  static final int SPECIAL_COMMA = 1024;
  private static final int WT_IGNORE = 0;
  private static final int WT_LETTER = 1;
  private static final int WT_OTHER = 2;
  static final int LINE_FEED = 10;
  static final int CARRIAGE_RETURN = 13;
  static final int LINE_SEPARATOR = 8232;
  static final int PARAGRAPH_SEPARATOR = 8233;
  
  private synchronized void compile(Token paramToken)
  {
    if (operations != null) {
      return;
    }
    numberOfClosures = 0;
    operations = compile(paramToken, null, false);
  }
  
  private Op compile(Token paramToken, Op paramOp, boolean paramBoolean)
  {
    Object localObject;
    switch (type)
    {
    case 11: 
      localObject = Op.createDot();
      next = paramOp;
      break;
    case 0: 
      localObject = Op.createChar(paramToken.getChar());
      next = paramOp;
      break;
    case 8: 
      localObject = Op.createAnchor(paramToken.getChar());
      next = paramOp;
      break;
    case 4: 
    case 5: 
      localObject = Op.createRange(paramToken);
      next = paramOp;
      break;
    case 1: 
      localObject = paramOp;
      int i;
      if (!paramBoolean) {
        for (i = paramToken.size() - 1; i >= 0; i--) {
          localObject = compile(paramToken.getChild(i), (Op)localObject, false);
        }
      } else {
        for (i = 0; i < paramToken.size(); i++) {
          localObject = compile(paramToken.getChild(i), (Op)localObject, true);
        }
      }
      break;
    case 2: 
      Op.UnionOp localUnionOp = Op.createUnion(paramToken.size());
      for (int j = 0; j < paramToken.size(); j++) {
        localUnionOp.addElement(compile(paramToken.getChild(j), paramOp, paramBoolean));
      }
      localObject = localUnionOp;
      break;
    case 3: 
    case 9: 
      Token localToken = paramToken.getChild(0);
      int k = paramToken.getMin();
      int m = paramToken.getMax();
      int n;
      if ((k >= 0) && (k == m))
      {
        localObject = paramOp;
        for (n = 0; n < k; n++) {
          localObject = compile(localToken, (Op)localObject, paramBoolean);
        }
      }
      else
      {
        if ((k > 0) && (m > 0)) {
          m -= k;
        }
        if (m > 0)
        {
          localObject = paramOp;
          for (n = 0; n < m; n++)
          {
            Op.ChildOp localChildOp2 = Op.createQuestion(type == 9);
            next = paramOp;
            localChildOp2.setChild(compile(localToken, (Op)localObject, paramBoolean));
            localObject = localChildOp2;
          }
        }
        else
        {
          Op.ChildOp localChildOp1;
          if (type == 9) {
            localChildOp1 = Op.createNonGreedyClosure();
          } else if (localToken.getMinLength() == 0) {
            localChildOp1 = Op.createClosure(numberOfClosures++);
          } else {
            localChildOp1 = Op.createClosure(-1);
          }
          next = paramOp;
          localChildOp1.setChild(compile(localToken, localChildOp1, paramBoolean));
          localObject = localChildOp1;
        }
        if (k > 0) {
          for (int i1 = 0; i1 < k; i1++) {
            localObject = compile(localToken, (Op)localObject, paramBoolean);
          }
        }
      }
      break;
    case 7: 
      localObject = paramOp;
      break;
    case 10: 
      localObject = Op.createString(paramToken.getString());
      next = paramOp;
      break;
    case 12: 
      localObject = Op.createBackReference(paramToken.getReferenceNumber());
      next = paramOp;
      break;
    case 6: 
      if (paramToken.getParenNumber() == 0)
      {
        localObject = compile(paramToken.getChild(0), paramOp, paramBoolean);
      }
      else if (paramBoolean)
      {
        paramOp = Op.createCapture(paramToken.getParenNumber(), paramOp);
        paramOp = compile(paramToken.getChild(0), paramOp, paramBoolean);
        localObject = Op.createCapture(-paramToken.getParenNumber(), paramOp);
      }
      else
      {
        paramOp = Op.createCapture(-paramToken.getParenNumber(), paramOp);
        paramOp = compile(paramToken.getChild(0), paramOp, paramBoolean);
        localObject = Op.createCapture(paramToken.getParenNumber(), paramOp);
      }
      break;
    case 20: 
      localObject = Op.createLook(20, paramOp, compile(paramToken.getChild(0), null, false));
      break;
    case 21: 
      localObject = Op.createLook(21, paramOp, compile(paramToken.getChild(0), null, false));
      break;
    case 22: 
      localObject = Op.createLook(22, paramOp, compile(paramToken.getChild(0), null, true));
      break;
    case 23: 
      localObject = Op.createLook(23, paramOp, compile(paramToken.getChild(0), null, true));
      break;
    case 24: 
      localObject = Op.createIndependent(paramOp, compile(paramToken.getChild(0), null, paramBoolean));
      break;
    case 25: 
      localObject = Op.createModifier(paramOp, compile(paramToken.getChild(0), null, paramBoolean), ((Token.ModifierToken)paramToken).getOptions(), ((Token.ModifierToken)paramToken).getOptionsMask());
      break;
    case 26: 
      Token.ConditionToken localConditionToken = (Token.ConditionToken)paramToken;
      int i2 = refNumber;
      Op localOp1 = condition == null ? null : compile(condition, null, paramBoolean);
      Op localOp2 = compile(yes, paramOp, paramBoolean);
      Op localOp3 = no == null ? null : compile(no, paramOp, paramBoolean);
      localObject = Op.createCondition(paramOp, i2, localOp1, localOp2, localOp3);
      break;
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    default: 
      throw new RuntimeException("Unknown token type: " + type);
    }
    return localObject;
  }
  
  public boolean matches(char[] paramArrayOfChar)
  {
    return matches(paramArrayOfChar, 0, paramArrayOfChar.length, (Match)null);
  }
  
  public boolean matches(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    return matches(paramArrayOfChar, paramInt1, paramInt2, (Match)null);
  }
  
  public boolean matches(char[] paramArrayOfChar, Match paramMatch)
  {
    return matches(paramArrayOfChar, 0, paramArrayOfChar.length, paramMatch);
  }
  
  public boolean matches(char[] paramArrayOfChar, int paramInt1, int paramInt2, Match paramMatch)
  {
    synchronized (this)
    {
      if (operations == null) {
        prepare();
      }
      if (context == null) {
        context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (context)
    {
      localContext1 = context.inuse ? new Context() : context;
      localContext1.reset(paramArrayOfChar, paramInt1, paramInt2, numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(nofparen);
      paramMatch.setSource(paramArrayOfChar);
    }
    else if (hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(nofparen);
    }
    match = paramMatch;
    if (isSet(options, 512))
    {
      i = matchCharArray(localContext1, operations, start, 1, options);
      if (i == limit)
      {
        if (match != null)
        {
          match.setBeginning(0, start);
          match.setEnd(0, i);
        }
        inuse = false;
        return true;
      }
      return false;
    }
    if (fixedStringOnly)
    {
      i = fixedStringTable.matches(paramArrayOfChar, start, limit);
      if (i >= 0)
      {
        if (match != null)
        {
          match.setBeginning(0, i);
          match.setEnd(0, i + fixedString.length());
        }
        inuse = false;
        return true;
      }
      inuse = false;
      return false;
    }
    if (fixedString != null)
    {
      i = fixedStringTable.matches(paramArrayOfChar, start, limit);
      if (i < 0)
      {
        inuse = false;
        return false;
      }
    }
    int i = limit - minlength;
    int k = -1;
    int j;
    int n;
    if ((operations != null) && (operations.type == 7) && (operations.getChild().type == 0))
    {
      if (isSet(options, 4))
      {
        j = start;
        k = matchCharArray(localContext1, operations, start, 1, options);
      }
      else
      {
        int m = 1;
        for (j = start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if (isEOLChar(n))
          {
            m = 1;
          }
          else
          {
            if ((m != 0) && (0 <= (k = matchCharArray(localContext1, operations, j, 1, options)))) {
              break;
            }
            m = 0;
          }
        }
      }
    }
    else if (firstChar != null)
    {
      RangeToken localRangeToken = firstChar;
      if (isSet(options, 2))
      {
        localRangeToken = firstChar.getCaseInsensitiveToken();
        for (j = start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < limit))
          {
            n = REUtil.composeFromSurrogates(n, paramArrayOfChar[(j + 1)]);
            if (!localRangeToken.match(n)) {
              continue;
            }
          }
          else if (!localRangeToken.match(n))
          {
            char c = Character.toUpperCase((char)n);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (k = matchCharArray(localContext1, operations, j, 1, options))) {
            break;
          }
        }
      }
      else
      {
        for (j = start; j <= i; j++)
        {
          n = paramArrayOfChar[j];
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < limit)) {
            n = REUtil.composeFromSurrogates(n, paramArrayOfChar[(j + 1)]);
          }
          if ((localRangeToken.match(n)) && (0 <= (k = matchCharArray(localContext1, operations, j, 1, options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (j = start; j <= i; j++) {
        if (0 <= (k = matchCharArray(localContext1, operations, j, 1, options))) {
          break;
        }
      }
    }
    if (k >= 0)
    {
      if (match != null)
      {
        match.setBeginning(0, j);
        match.setEnd(0, k);
      }
      inuse = false;
      return true;
    }
    inuse = false;
    return false;
  }
  
  private int matchCharArray(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    char[] arrayOfChar = charTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > limit) || (paramInt1 < start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (!matchIgnoreCase(i, arrayOfChar[paramInt1]))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (!matchIgnoreCase(i, arrayOfChar[j]))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (i != arrayOfChar[paramInt1])) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (i != arrayOfChar[j])) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = arrayOfChar[paramInt1];
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              i = REUtil.composeFromSurrogates(i, arrayOfChar[(++paramInt1)]);
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          j = arrayOfChar[i];
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(arrayOfChar[(--i)], j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = arrayOfChar[paramInt1];
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
            i = REUtil.composeFromSurrogates(i, arrayOfChar[(++paramInt1)]);
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          k = arrayOfChar[i];
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(arrayOfChar[(--i)], k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(arrayOfChar[(paramInt1 - 1)])))) {
              return -1;
            }
          }
          else if (paramInt1 != start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(arrayOfChar[(paramInt1 - 1)])))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != limit) && ((paramInt1 >= limit) || (!isEOLChar(arrayOfChar[paramInt1])))) {
              return -1;
            }
          }
          else if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(arrayOfChar[paramInt1]))) && ((paramInt1 + 2 != limit) || (arrayOfChar[paramInt1] != '\r') || (arrayOfChar[(paramInt1 + 1)] != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(arrayOfChar[paramInt1]))) && ((paramInt1 + 2 != limit) || (arrayOfChar[paramInt1] != '\r') || (arrayOfChar[(paramInt1 + 1)] != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != limit) {
            return -1;
          }
          break;
        case 98: 
          if (length == 0) {
            return -1;
          }
          k = getWordType(arrayOfChar, start, limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(arrayOfChar, start, limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(arrayOfChar, start, limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(arrayOfChar, start, limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((length == 0) || (paramInt1 == limit)) {
            return -1;
          }
          if ((getWordType(arrayOfChar, start, limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(arrayOfChar, start, limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((length == 0) || (paramInt1 == start)) {
            return -1;
          }
          if ((getWordType(arrayOfChar, start, limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(arrayOfChar, start, limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((match.getBeginning(k) < 0) || (match.getEnd(k) < 0)) {
          return -1;
        }
        n = match.getBeginning(k);
        i1 = match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(arrayOfChar, paramInt1, limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(arrayOfChar, paramInt1 - i1, limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1, limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1 - i1, limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = next;
        break;
      case 6: 
        String str = paramOp.getString();
        n = str.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(arrayOfChar, paramInt1, limit, str, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(arrayOfChar, paramInt1 - n, limit, str, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1, limit, str, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(arrayOfChar, paramInt1 - n, limit, str, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            offsets[m] = paramInt1;
          }
          else
          {
            offsets[m] = -1;
            paramOp = next;
            continue;
          }
        }
        n = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = next;
        break;
      case 9: 
        m = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = next;
        break;
      case 8: 
      case 10: 
        m = matchCharArray(paramContext, next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchCharArray(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((match != null) && (n > 0))
        {
          i1 = match.getBeginning(n);
          match.setBeginning(n, paramInt1);
          i2 = matchCharArray(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((match != null) && (n < 0))
        {
          i1 = -n;
          i2 = match.getEnd(i1);
          match.setEnd(i1, paramInt1);
          int i3 = matchCharArray(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = next;
        break;
      case 20: 
        if (0 > matchCharArray(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 21: 
        if (0 <= matchCharArray(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 22: 
        if (0 > matchCharArray(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 23: 
        if (0 <= matchCharArray(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 24: 
        i1 = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchCharArray(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (refNumber > 0)
        {
          if (refNumber >= nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + refNumber);
          }
          i2 = (match.getBeginning(refNumber) >= 0) && (match.getEnd(refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchCharArray(paramContext, condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = yes;
        } else if (no != null) {
          paramOp = no;
        } else {
          paramOp = next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + type);
  }
  
  private static final int getPreviousWordType(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramArrayOfChar, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramArrayOfChar, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramArrayOfChar[paramInt3], paramInt4);
  }
  
  private static final boolean regionMatches(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0) {
      if (paramArrayOfChar[(paramInt1++)] != paramString.charAt(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatches(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0) {
      if (paramArrayOfChar[(paramInt1++)] != paramArrayOfChar[(i++)]) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(char[] paramArrayOfChar, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0)
    {
      char c1 = paramArrayOfChar[(paramInt1++)];
      char c2 = paramString.charAt(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0)
    {
      char c1 = paramArrayOfChar[(paramInt1++)];
      char c2 = paramArrayOfChar[(i++)];
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean matches(String paramString)
  {
    return matches(paramString, 0, paramString.length(), (Match)null);
  }
  
  public boolean matches(String paramString, int paramInt1, int paramInt2)
  {
    return matches(paramString, paramInt1, paramInt2, (Match)null);
  }
  
  public boolean matches(String paramString, Match paramMatch)
  {
    return matches(paramString, 0, paramString.length(), paramMatch);
  }
  
  public boolean matches(String paramString, int paramInt1, int paramInt2, Match paramMatch)
  {
    synchronized (this)
    {
      if (operations == null) {
        prepare();
      }
      if (context == null) {
        context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (context)
    {
      localContext1 = context.inuse ? new Context() : context;
      localContext1.reset(paramString, paramInt1, paramInt2, numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(nofparen);
      paramMatch.setSource(paramString);
    }
    else if (hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(nofparen);
    }
    match = paramMatch;
    if (isSet(options, 512))
    {
      i = matchString(localContext1, operations, start, 1, options);
      if (i == limit)
      {
        if (match != null)
        {
          match.setBeginning(0, start);
          match.setEnd(0, i);
        }
        inuse = false;
        return true;
      }
      return false;
    }
    if (fixedStringOnly)
    {
      i = fixedStringTable.matches(paramString, start, limit);
      if (i >= 0)
      {
        if (match != null)
        {
          match.setBeginning(0, i);
          match.setEnd(0, i + fixedString.length());
        }
        inuse = false;
        return true;
      }
      inuse = false;
      return false;
    }
    if (fixedString != null)
    {
      i = fixedStringTable.matches(paramString, start, limit);
      if (i < 0)
      {
        inuse = false;
        return false;
      }
    }
    int i = limit - minlength;
    int k = -1;
    int j;
    int n;
    if ((operations != null) && (operations.type == 7) && (operations.getChild().type == 0))
    {
      if (isSet(options, 4))
      {
        j = start;
        k = matchString(localContext1, operations, start, 1, options);
      }
      else
      {
        int m = 1;
        for (j = start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if (isEOLChar(n))
          {
            m = 1;
          }
          else
          {
            if ((m != 0) && (0 <= (k = matchString(localContext1, operations, j, 1, options)))) {
              break;
            }
            m = 0;
          }
        }
      }
    }
    else if (firstChar != null)
    {
      RangeToken localRangeToken = firstChar;
      if (isSet(options, 2))
      {
        localRangeToken = firstChar.getCaseInsensitiveToken();
        for (j = start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < limit))
          {
            n = REUtil.composeFromSurrogates(n, paramString.charAt(j + 1));
            if (!localRangeToken.match(n)) {
              continue;
            }
          }
          else if (!localRangeToken.match(n))
          {
            char c = Character.toUpperCase((char)n);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (k = matchString(localContext1, operations, j, 1, options))) {
            break;
          }
        }
      }
      else
      {
        for (j = start; j <= i; j++)
        {
          n = paramString.charAt(j);
          if ((REUtil.isHighSurrogate(n)) && (j + 1 < limit)) {
            n = REUtil.composeFromSurrogates(n, paramString.charAt(j + 1));
          }
          if ((localRangeToken.match(n)) && (0 <= (k = matchString(localContext1, operations, j, 1, options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (j = start; j <= i; j++) {
        if (0 <= (k = matchString(localContext1, operations, j, 1, options))) {
          break;
        }
      }
    }
    if (k >= 0)
    {
      if (match != null)
      {
        match.setBeginning(0, j);
        match.setEnd(0, k);
      }
      inuse = false;
      return true;
    }
    inuse = false;
    return false;
  }
  
  private int matchString(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    String str1 = strTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > limit) || (paramInt1 < start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (!matchIgnoreCase(i, str1.charAt(paramInt1)))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (!matchIgnoreCase(i, str1.charAt(j)))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (i != str1.charAt(paramInt1))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (i != str1.charAt(j))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = str1.charAt(paramInt1);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              i = REUtil.composeFromSurrogates(i, str1.charAt(++paramInt1));
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          j = str1.charAt(i);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(str1.charAt(--i), j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = str1.charAt(paramInt1);
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
            i = REUtil.composeFromSurrogates(i, str1.charAt(++paramInt1));
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          k = str1.charAt(i);
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(str1.charAt(--i), k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(str1.charAt(paramInt1 - 1))))) {
              return -1;
            }
          }
          else if (paramInt1 != start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(str1.charAt(paramInt1 - 1))))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != limit) && ((paramInt1 >= limit) || (!isEOLChar(str1.charAt(paramInt1))))) {
              return -1;
            }
          }
          else if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(str1.charAt(paramInt1)))) && ((paramInt1 + 2 != limit) || (str1.charAt(paramInt1) != '\r') || (str1.charAt(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(str1.charAt(paramInt1)))) && ((paramInt1 + 2 != limit) || (str1.charAt(paramInt1) != '\r') || (str1.charAt(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != limit) {
            return -1;
          }
          break;
        case 98: 
          if (length == 0) {
            return -1;
          }
          k = getWordType(str1, start, limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(str1, start, limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(str1, start, limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(str1, start, limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((length == 0) || (paramInt1 == limit)) {
            return -1;
          }
          if ((getWordType(str1, start, limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(str1, start, limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((length == 0) || (paramInt1 == start)) {
            return -1;
          }
          if ((getWordType(str1, start, limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(str1, start, limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((match.getBeginning(k) < 0) || (match.getEnd(k) < 0)) {
          return -1;
        }
        n = match.getBeginning(k);
        i1 = match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(str1, paramInt1, limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(str1, paramInt1 - i1, limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1, limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1 - i1, limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = next;
        break;
      case 6: 
        String str2 = paramOp.getString();
        n = str2.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(str1, paramInt1, limit, str2, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(str1, paramInt1 - n, limit, str2, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1, limit, str2, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(str1, paramInt1 - n, limit, str2, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            offsets[m] = paramInt1;
          }
          else
          {
            offsets[m] = -1;
            paramOp = next;
            continue;
          }
        }
        n = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = next;
        break;
      case 9: 
        m = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = next;
        break;
      case 8: 
      case 10: 
        m = matchString(paramContext, next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchString(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((match != null) && (n > 0))
        {
          i1 = match.getBeginning(n);
          match.setBeginning(n, paramInt1);
          i2 = matchString(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((match != null) && (n < 0))
        {
          i1 = -n;
          i2 = match.getEnd(i1);
          match.setEnd(i1, paramInt1);
          int i3 = matchString(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = next;
        break;
      case 20: 
        if (0 > matchString(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 21: 
        if (0 <= matchString(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 22: 
        if (0 > matchString(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 23: 
        if (0 <= matchString(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 24: 
        i1 = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchString(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (refNumber > 0)
        {
          if (refNumber >= nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + refNumber);
          }
          i2 = (match.getBeginning(refNumber) >= 0) && (match.getEnd(refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchString(paramContext, condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = yes;
        } else if (no != null) {
          paramOp = no;
        } else {
          paramOp = next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + type);
  }
  
  private static final int getPreviousWordType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramString, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramString, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramString.charAt(paramInt3), paramInt4);
  }
  
  private static final boolean regionMatches(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    return paramString1.regionMatches(paramInt1, paramString2, 0, paramInt3);
  }
  
  private static final boolean regionMatches(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    return paramString.regionMatches(paramInt1, paramString, paramInt3, paramInt4);
  }
  
  private static final boolean regionMatchesIgnoreCase(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    return paramString1.regionMatches(true, paramInt1, paramString2, 0, paramInt3);
  }
  
  private static final boolean regionMatchesIgnoreCase(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    return paramString.regionMatches(true, paramInt1, paramString, paramInt3, paramInt4);
  }
  
  public boolean matches(CharacterIterator paramCharacterIterator)
  {
    return matches(paramCharacterIterator, (Match)null);
  }
  
  public boolean matches(CharacterIterator paramCharacterIterator, Match paramMatch)
  {
    int i = paramCharacterIterator.getBeginIndex();
    int j = paramCharacterIterator.getEndIndex();
    synchronized (this)
    {
      if (operations == null) {
        prepare();
      }
      if (context == null) {
        context = new Context();
      }
    }
    Context localContext1 = null;
    synchronized (context)
    {
      localContext1 = context.inuse ? new Context() : context;
      localContext1.reset(paramCharacterIterator, i, j, numberOfClosures);
    }
    if (paramMatch != null)
    {
      paramMatch.setNumberOfGroups(nofparen);
      paramMatch.setSource(paramCharacterIterator);
    }
    else if (hasBackReferences)
    {
      paramMatch = new Match();
      paramMatch.setNumberOfGroups(nofparen);
    }
    match = paramMatch;
    if (isSet(options, 512))
    {
      k = matchCharacterIterator(localContext1, operations, start, 1, options);
      if (k == limit)
      {
        if (match != null)
        {
          match.setBeginning(0, start);
          match.setEnd(0, k);
        }
        inuse = false;
        return true;
      }
      return false;
    }
    if (fixedStringOnly)
    {
      k = fixedStringTable.matches(paramCharacterIterator, start, limit);
      if (k >= 0)
      {
        if (match != null)
        {
          match.setBeginning(0, k);
          match.setEnd(0, k + fixedString.length());
        }
        inuse = false;
        return true;
      }
      inuse = false;
      return false;
    }
    if (fixedString != null)
    {
      k = fixedStringTable.matches(paramCharacterIterator, start, limit);
      if (k < 0)
      {
        inuse = false;
        return false;
      }
    }
    int k = limit - minlength;
    int n = -1;
    int m;
    int i2;
    if ((operations != null) && (operations.type == 7) && (operations.getChild().type == 0))
    {
      if (isSet(options, 4))
      {
        m = start;
        n = matchCharacterIterator(localContext1, operations, start, 1, options);
      }
      else
      {
        int i1 = 1;
        for (m = start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if (isEOLChar(i2))
          {
            i1 = 1;
          }
          else
          {
            if ((i1 != 0) && (0 <= (n = matchCharacterIterator(localContext1, operations, m, 1, options)))) {
              break;
            }
            i1 = 0;
          }
        }
      }
    }
    else if (firstChar != null)
    {
      RangeToken localRangeToken = firstChar;
      if (isSet(options, 2))
      {
        localRangeToken = firstChar.getCaseInsensitiveToken();
        for (m = start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if ((REUtil.isHighSurrogate(i2)) && (m + 1 < limit))
          {
            i2 = REUtil.composeFromSurrogates(i2, paramCharacterIterator.setIndex(m + 1));
            if (!localRangeToken.match(i2)) {
              continue;
            }
          }
          else if (!localRangeToken.match(i2))
          {
            char c = Character.toUpperCase((char)i2);
            if ((!localRangeToken.match(c)) && (!localRangeToken.match(Character.toLowerCase(c)))) {
              continue;
            }
          }
          if (0 <= (n = matchCharacterIterator(localContext1, operations, m, 1, options))) {
            break;
          }
        }
      }
      else
      {
        for (m = start; m <= k; m++)
        {
          i2 = paramCharacterIterator.setIndex(m);
          if ((REUtil.isHighSurrogate(i2)) && (m + 1 < limit)) {
            i2 = REUtil.composeFromSurrogates(i2, paramCharacterIterator.setIndex(m + 1));
          }
          if ((localRangeToken.match(i2)) && (0 <= (n = matchCharacterIterator(localContext1, operations, m, 1, options)))) {
            break;
          }
        }
      }
    }
    else
    {
      for (m = start; m <= k; m++) {
        if (0 <= (n = matchCharacterIterator(localContext1, operations, m, 1, options))) {
          break;
        }
      }
    }
    if (n >= 0)
    {
      if (match != null)
      {
        match.setBeginning(0, m);
        match.setEnd(0, n);
      }
      inuse = false;
      return true;
    }
    inuse = false;
    return false;
  }
  
  private int matchCharacterIterator(Context paramContext, Op paramOp, int paramInt1, int paramInt2, int paramInt3)
  {
    CharacterIterator localCharacterIterator = ciTarget;
    for (;;)
    {
      if (paramOp == null) {
        return (isSet(paramInt3, 512)) && (paramInt1 != limit) ? -1 : paramInt1;
      }
      if ((paramInt1 > limit) || (paramInt1 < start)) {
        return -1;
      }
      int i;
      int j;
      int k;
      int n;
      int i1;
      int m;
      int i2;
      switch (type)
      {
      case 1: 
        if (isSet(paramInt3, 2))
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (!matchIgnoreCase(i, localCharacterIterator.setIndex(paramInt1)))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (!matchIgnoreCase(i, localCharacterIterator.setIndex(j)))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        else
        {
          i = paramOp.getData();
          if (paramInt2 > 0)
          {
            if ((paramInt1 >= limit) || (i != localCharacterIterator.setIndex(paramInt1))) {
              return -1;
            }
            paramInt1++;
          }
          else
          {
            j = paramInt1 - 1;
            if ((j >= limit) || (j < 0) || (i != localCharacterIterator.setIndex(j))) {
              return -1;
            }
            paramInt1 = j;
          }
        }
        paramOp = next;
        break;
      case 0: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = localCharacterIterator.setIndex(paramInt1);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              paramInt1++;
            }
          }
          else
          {
            if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
              i = REUtil.composeFromSurrogates(i, localCharacterIterator.setIndex(++paramInt1));
            }
            if (isEOLChar(i)) {
              return -1;
            }
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          j = localCharacterIterator.setIndex(i);
          if (isSet(paramInt3, 4))
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              i--;
            }
          }
          else
          {
            if ((REUtil.isLowSurrogate(j)) && (i - 1 >= 0)) {
              j = REUtil.composeFromSurrogates(localCharacterIterator.setIndex(--i), j);
            }
            if (!isEOLChar(j)) {
              return -1;
            }
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 3: 
      case 4: 
        if (paramInt2 > 0)
        {
          if (paramInt1 >= limit) {
            return -1;
          }
          i = localCharacterIterator.setIndex(paramInt1);
          if ((REUtil.isHighSurrogate(i)) && (paramInt1 + 1 < limit)) {
            i = REUtil.composeFromSurrogates(i, localCharacterIterator.setIndex(++paramInt1));
          }
          RangeToken localRangeToken1 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken1 = localRangeToken1.getCaseInsensitiveToken();
            if (!localRangeToken1.match(i))
            {
              if (i >= 65536) {
                return -1;
              }
              char c1;
              if ((!localRangeToken1.match(c1 = Character.toUpperCase((char)i))) && (!localRangeToken1.match(Character.toLowerCase(c1)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken1.match(i))
          {
            return -1;
          }
          paramInt1++;
        }
        else
        {
          i = paramInt1 - 1;
          if ((i >= limit) || (i < 0)) {
            return -1;
          }
          k = localCharacterIterator.setIndex(i);
          if ((REUtil.isLowSurrogate(k)) && (i - 1 >= 0)) {
            k = REUtil.composeFromSurrogates(localCharacterIterator.setIndex(--i), k);
          }
          RangeToken localRangeToken2 = paramOp.getToken();
          if (isSet(paramInt3, 2))
          {
            localRangeToken2 = localRangeToken2.getCaseInsensitiveToken();
            if (!localRangeToken2.match(k))
            {
              if (k >= 65536) {
                return -1;
              }
              char c2;
              if ((!localRangeToken2.match(c2 = Character.toUpperCase((char)k))) && (!localRangeToken2.match(Character.toLowerCase(c2)))) {
                return -1;
              }
            }
          }
          else if (!localRangeToken2.match(k))
          {
            return -1;
          }
          paramInt1 = i;
        }
        paramOp = next;
        break;
      case 5: 
        i = 0;
        switch (paramOp.getData())
        {
        case 94: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1 - 1))))) {
              return -1;
            }
          }
          else if (paramInt1 != start) {
            return -1;
          }
          break;
        case 64: 
          if ((paramInt1 != start) && ((paramInt1 <= start) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1 - 1))))) {
            return -1;
          }
          break;
        case 36: 
          if (isSet(paramInt3, 8))
          {
            if ((paramInt1 != limit) && ((paramInt1 >= limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1))))) {
              return -1;
            }
          }
          else if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1)))) && ((paramInt1 + 2 != limit) || (localCharacterIterator.setIndex(paramInt1) != '\r') || (localCharacterIterator.setIndex(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 65: 
          if (paramInt1 != start) {
            return -1;
          }
          break;
        case 90: 
          if ((paramInt1 != limit) && ((paramInt1 + 1 != limit) || (!isEOLChar(localCharacterIterator.setIndex(paramInt1)))) && ((paramInt1 + 2 != limit) || (localCharacterIterator.setIndex(paramInt1) != '\r') || (localCharacterIterator.setIndex(paramInt1 + 1) != '\n'))) {
            return -1;
          }
          break;
        case 122: 
          if (paramInt1 != limit) {
            return -1;
          }
          break;
        case 98: 
          if (length == 0) {
            return -1;
          }
          k = getWordType(localCharacterIterator, start, limit, paramInt1, paramInt3);
          if (k == 0) {
            return -1;
          }
          n = getPreviousWordType(localCharacterIterator, start, limit, paramInt1, paramInt3);
          if (k == n) {
            return -1;
          }
          break;
        case 66: 
          if (length == 0)
          {
            i = 1;
          }
          else
          {
            k = getWordType(localCharacterIterator, start, limit, paramInt1, paramInt3);
            i = (k == 0) || (k == getPreviousWordType(localCharacterIterator, start, limit, paramInt1, paramInt3)) ? 1 : 0;
          }
          if (i == 0) {
            return -1;
          }
          break;
        case 60: 
          if ((length == 0) || (paramInt1 == limit)) {
            return -1;
          }
          if ((getWordType(localCharacterIterator, start, limit, paramInt1, paramInt3) != 1) || (getPreviousWordType(localCharacterIterator, start, limit, paramInt1, paramInt3) != 2)) {
            return -1;
          }
          break;
        case 62: 
          if ((length == 0) || (paramInt1 == start)) {
            return -1;
          }
          if ((getWordType(localCharacterIterator, start, limit, paramInt1, paramInt3) != 2) || (getPreviousWordType(localCharacterIterator, start, limit, paramInt1, paramInt3) != 1)) {
            return -1;
          }
          break;
        }
        paramOp = next;
        break;
      case 16: 
        k = paramOp.getData();
        if ((k <= 0) || (k >= nofparen)) {
          throw new RuntimeException("Internal Error: Reference number must be more than zero: " + k);
        }
        if ((match.getBeginning(k) < 0) || (match.getEnd(k) < 0)) {
          return -1;
        }
        n = match.getBeginning(k);
        i1 = match.getEnd(k) - n;
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(localCharacterIterator, paramInt1, limit, n, i1)) {
              return -1;
            }
            paramInt1 += i1;
          }
          else
          {
            if (!regionMatches(localCharacterIterator, paramInt1 - i1, limit, n, i1)) {
              return -1;
            }
            paramInt1 -= i1;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1, limit, n, i1)) {
            return -1;
          }
          paramInt1 += i1;
        }
        else
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1 - i1, limit, n, i1)) {
            return -1;
          }
          paramInt1 -= i1;
        }
        paramOp = next;
        break;
      case 6: 
        String str = paramOp.getString();
        n = str.length();
        if (!isSet(paramInt3, 2))
        {
          if (paramInt2 > 0)
          {
            if (!regionMatches(localCharacterIterator, paramInt1, limit, str, n)) {
              return -1;
            }
            paramInt1 += n;
          }
          else
          {
            if (!regionMatches(localCharacterIterator, paramInt1 - n, limit, str, n)) {
              return -1;
            }
            paramInt1 -= n;
          }
        }
        else if (paramInt2 > 0)
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1, limit, str, n)) {
            return -1;
          }
          paramInt1 += n;
        }
        else
        {
          if (!regionMatchesIgnoreCase(localCharacterIterator, paramInt1 - n, limit, str, n)) {
            return -1;
          }
          paramInt1 -= n;
        }
        paramOp = next;
        break;
      case 7: 
        m = paramOp.getData();
        if (m >= 0)
        {
          n = offsets[m];
          if ((n < 0) || (n != paramInt1))
          {
            offsets[m] = paramInt1;
          }
          else
          {
            offsets[m] = -1;
            paramOp = next;
            continue;
          }
        }
        n = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          offsets[m] = -1;
        }
        if (n >= 0) {
          return n;
        }
        paramOp = next;
        break;
      case 9: 
        m = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = next;
        break;
      case 8: 
      case 10: 
        m = matchCharacterIterator(paramContext, next, paramInt1, paramInt2, paramInt3);
        if (m >= 0) {
          return m;
        }
        paramOp = paramOp.getChild();
        break;
      case 11: 
        for (m = 0; m < paramOp.size(); m++)
        {
          n = matchCharacterIterator(paramContext, paramOp.elementAt(m), paramInt1, paramInt2, paramInt3);
          if (n >= 0) {
            return n;
          }
        }
        return -1;
      case 15: 
        n = paramOp.getData();
        if ((match != null) && (n > 0))
        {
          i1 = match.getBeginning(n);
          match.setBeginning(n, paramInt1);
          i2 = matchCharacterIterator(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i2 < 0) {
            match.setBeginning(n, i1);
          }
          return i2;
        }
        if ((match != null) && (n < 0))
        {
          i1 = -n;
          i2 = match.getEnd(i1);
          match.setEnd(i1, paramInt1);
          int i3 = matchCharacterIterator(paramContext, next, paramInt1, paramInt2, paramInt3);
          if (i3 < 0) {
            match.setEnd(i1, i2);
          }
          return i3;
        }
        paramOp = next;
        break;
      case 20: 
        if (0 > matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 21: 
        if (0 <= matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, 1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 22: 
        if (0 > matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 23: 
        if (0 <= matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, -1, paramInt3)) {
          return -1;
        }
        paramOp = next;
        break;
      case 24: 
        i1 = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, paramInt3);
        if (i1 < 0) {
          return i1;
        }
        paramInt1 = i1;
        paramOp = next;
        break;
      case 25: 
        i1 = paramInt3;
        i1 |= paramOp.getData();
        i1 &= (paramOp.getData2() ^ 0xFFFFFFFF);
        i2 = matchCharacterIterator(paramContext, paramOp.getChild(), paramInt1, paramInt2, i1);
        if (i2 < 0) {
          return i2;
        }
        paramInt1 = i2;
        paramOp = next;
        break;
      case 26: 
        Op.ConditionOp localConditionOp = (Op.ConditionOp)paramOp;
        i2 = 0;
        if (refNumber > 0)
        {
          if (refNumber >= nofparen) {
            throw new RuntimeException("Internal Error: Reference number must be more than zero: " + refNumber);
          }
          i2 = (match.getBeginning(refNumber) >= 0) && (match.getEnd(refNumber) >= 0) ? 1 : 0;
        }
        else
        {
          i2 = 0 <= matchCharacterIterator(paramContext, condition, paramInt1, paramInt2, paramInt3) ? 1 : 0;
        }
        if (i2 != 0) {
          paramOp = yes;
        } else if (no != null) {
          paramOp = no;
        } else {
          paramOp = next;
        }
        break;
      }
    }
    throw new RuntimeException("Unknown operation type: " + type);
  }
  
  private static final int getPreviousWordType(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = getWordType(paramCharacterIterator, paramInt1, paramInt2, --paramInt3, paramInt4); i == 0; i = getWordType(paramCharacterIterator, paramInt1, paramInt2, --paramInt3, paramInt4)) {}
    return i;
  }
  
  private static final int getWordType(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 < paramInt1) || (paramInt3 >= paramInt2)) {
      return 2;
    }
    return getWordType0(paramCharacterIterator.setIndex(paramInt3), paramInt4);
  }
  
  private static final boolean regionMatches(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0) {
      if (paramCharacterIterator.setIndex(paramInt1++) != paramString.charAt(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatches(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0) {
      if (paramCharacterIterator.setIndex(paramInt1++) != paramCharacterIterator.setIndex(i++)) {
        return false;
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt3) {
      return false;
    }
    int i = 0;
    while (paramInt3-- > 0)
    {
      char c1 = paramCharacterIterator.setIndex(paramInt1++);
      char c2 = paramString.charAt(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static final boolean regionMatchesIgnoreCase(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 0) {
      return false;
    }
    if (paramInt2 - paramInt1 < paramInt4) {
      return false;
    }
    int i = paramInt3;
    while (paramInt4-- > 0)
    {
      char c1 = paramCharacterIterator.setIndex(paramInt1++);
      char c2 = paramCharacterIterator.setIndex(i++);
      if (c1 != c2)
      {
        char c3 = Character.toUpperCase(c1);
        char c4 = Character.toUpperCase(c2);
        if ((c3 != c4) && (Character.toLowerCase(c3) != Character.toLowerCase(c4))) {
          return false;
        }
      }
    }
    return true;
  }
  
  void prepare()
  {
    compile(tokentree);
    minlength = tokentree.getMinLength();
    firstChar = null;
    Object localObject;
    if ((!isSet(options, 128)) && (!isSet(options, 512)))
    {
      localObject = Token.createRange();
      int i = tokentree.analyzeFirstCharacter((RangeToken)localObject, options);
      if (i == 1)
      {
        ((RangeToken)localObject).compactRanges();
        firstChar = ((RangeToken)localObject);
      }
    }
    if ((operations != null) && ((operations.type == 6) || (operations.type == 1)) && (operations.next == null))
    {
      fixedStringOnly = true;
      if (operations.type == 6)
      {
        fixedString = operations.getString();
      }
      else if (operations.getData() >= 65536)
      {
        fixedString = REUtil.decomposeToSurrogates(operations.getData());
      }
      else
      {
        localObject = new char[1];
        localObject[0] = ((char)operations.getData());
        fixedString = new String((char[])localObject);
      }
      fixedStringOptions = options;
      fixedStringTable = new BMPattern(fixedString, 256, isSet(fixedStringOptions, 2));
    }
    else if ((!isSet(options, 256)) && (!isSet(options, 512)))
    {
      localObject = new Token.FixedStringContainer();
      tokentree.findFixedString((Token.FixedStringContainer)localObject, options);
      fixedString = (token == null ? null : token.getString());
      fixedStringOptions = options;
      if ((fixedString != null) && (fixedString.length() < 2)) {
        fixedString = null;
      }
      if (fixedString != null) {
        fixedStringTable = new BMPattern(fixedString, 256, isSet(fixedStringOptions, 2));
      }
    }
  }
  
  private static final boolean isSet(int paramInt1, int paramInt2)
  {
    return (paramInt1 & paramInt2) == paramInt2;
  }
  
  public RegularExpression(String paramString)
    throws ParseException
  {
    setPattern(paramString, null);
  }
  
  public RegularExpression(String paramString1, String paramString2)
    throws ParseException
  {
    setPattern(paramString1, paramString2);
  }
  
  RegularExpression(String paramString, Token paramToken, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    regex = paramString;
    tokentree = paramToken;
    nofparen = paramInt1;
    options = paramInt2;
    hasBackReferences = paramBoolean;
  }
  
  public void setPattern(String paramString)
    throws ParseException
  {
    setPattern(paramString, options);
  }
  
  private void setPattern(String paramString, int paramInt)
    throws ParseException
  {
    regex = paramString;
    options = paramInt;
    RegexParser localRegexParser = isSet(options, 512) ? new ParserForXMLSchema() : new RegexParser();
    tokentree = localRegexParser.parse(regex, options);
    nofparen = parennumber;
    hasBackReferences = hasBackReferences;
    operations = null;
    context = null;
  }
  
  public void setPattern(String paramString1, String paramString2)
    throws ParseException
  {
    setPattern(paramString1, REUtil.parseOptions(paramString2));
  }
  
  public String getPattern()
  {
    return regex;
  }
  
  public String toString()
  {
    return tokentree.toString(options);
  }
  
  public String getOptions()
  {
    return REUtil.createOptionString(options);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof RegularExpression)) {
      return false;
    }
    RegularExpression localRegularExpression = (RegularExpression)paramObject;
    return (regex.equals(regex)) && (options == options);
  }
  
  boolean equals(String paramString, int paramInt)
  {
    return (regex.equals(paramString)) && (options == paramInt);
  }
  
  public int hashCode()
  {
    return (regex + "/" + getOptions()).hashCode();
  }
  
  public int getNumberOfGroups()
  {
    return nofparen;
  }
  
  private static final int getWordType0(char paramChar, int paramInt)
  {
    if (!isSet(paramInt, 64))
    {
      if (isSet(paramInt, 32)) {
        return Token.getRange("IsWord", true).match(paramChar) ? 1 : 2;
      }
      return isWordChar(paramChar) ? 1 : 2;
    }
    switch (Character.getType(paramChar))
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
      return 1;
    case 6: 
    case 7: 
    case 16: 
      return 0;
    case 15: 
      switch (paramChar)
      {
      case '\t': 
      case '\n': 
      case '\013': 
      case '\f': 
      case '\r': 
        return 2;
      }
      return 0;
    }
    return 2;
  }
  
  private static final boolean isEOLChar(int paramInt)
  {
    return (paramInt == 10) || (paramInt == 13) || (paramInt == 8232) || (paramInt == 8233);
  }
  
  private static final boolean isWordChar(int paramInt)
  {
    if (paramInt == 95) {
      return true;
    }
    if (paramInt < 48) {
      return false;
    }
    if (paramInt > 122) {
      return false;
    }
    if (paramInt <= 57) {
      return true;
    }
    if (paramInt < 65) {
      return false;
    }
    if (paramInt <= 90) {
      return true;
    }
    return paramInt >= 97;
  }
  
  private static final boolean matchIgnoreCase(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return true;
    }
    if ((paramInt1 > 65535) || (paramInt2 > 65535)) {
      return false;
    }
    char c1 = Character.toUpperCase((char)paramInt1);
    char c2 = Character.toUpperCase((char)paramInt2);
    if (c1 == c2) {
      return true;
    }
    return Character.toLowerCase(c1) == Character.toLowerCase(c2);
  }
  
  static final class Context
  {
    CharacterIterator ciTarget;
    String strTarget;
    char[] charTarget;
    int start;
    int limit;
    int length;
    Match match;
    boolean inuse = false;
    int[] offsets;
    
    Context() {}
    
    private void resetCommon(int paramInt)
    {
      length = (limit - start);
      inuse = true;
      match = null;
      if ((offsets == null) || (offsets.length != paramInt)) {
        offsets = new int[paramInt];
      }
      for (int i = 0; i < paramInt; i++) {
        offsets[i] = -1;
      }
    }
    
    void reset(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, int paramInt3)
    {
      ciTarget = paramCharacterIterator;
      start = paramInt1;
      limit = paramInt2;
      resetCommon(paramInt3);
    }
    
    void reset(String paramString, int paramInt1, int paramInt2, int paramInt3)
    {
      strTarget = paramString;
      start = paramInt1;
      limit = paramInt2;
      resetCommon(paramInt3);
    }
    
    void reset(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
    {
      charTarget = paramArrayOfChar;
      start = paramInt1;
      limit = paramInt2;
      resetCommon(paramInt3);
    }
  }
}
