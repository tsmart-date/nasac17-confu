package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;








































































public abstract class Attribute
  implements Cloneable, Node
{
  protected int name_index;
  protected int length;
  protected byte tag;
  protected ConstantPool constant_pool;
  
  Attribute(byte tag, int name_index, int length, ConstantPool constant_pool)
  {
    this.tag = tag;
    this.name_index = name_index;
    this.length = length;
    this.constant_pool = constant_pool;
  }
  






  public abstract void accept(Visitor paramVisitor);
  






  public void dump(DataOutputStream file)
    throws IOException
  {
    file.writeShort(name_index);
    file.writeInt(length);
  }
  
  private static HashMap readers = new HashMap();
  






  public static void addAttributeReader(String name, AttributeReader r)
  {
    readers.put(name, r);
  }
  



  public static void removeAttributeReader(String name)
  {
    readers.remove(name);
  }
  


















  static final Attribute readAttribute(DataInputStream file, ConstantPool constant_pool)
    throws IOException, ClassFormatError, InternalError
  {
    byte tag = -1;
    

    int name_index = file.readUnsignedShort();
    ConstantUtf8 c = (ConstantUtf8)constant_pool.getConstant(name_index, (byte)1);
    
    String name = c.getBytes();
    

    int length = file.readInt();
    

    for (byte i = 0; i < 12; i = (byte)(i + 1)) {
      if (name.equals(org.apache.bcel.Constants.ATTRIBUTE_NAMES[i])) {
        tag = i;
        break;
      }
    }
    

    switch (tag) {
    case -1: 
      AttributeReader r = (AttributeReader)readers.get(name);
      
      if (r != null) {
        return r.createAttribute(name_index, length, file, constant_pool);
      }
      return new Unknown(name_index, length, file, constant_pool);
    
    case 1: 
      return new ConstantValue(name_index, length, file, constant_pool);
    
    case 0: 
      return new SourceFile(name_index, length, file, constant_pool);
    
    case 2: 
      return new Code(name_index, length, file, constant_pool);
    
    case 3: 
      return new ExceptionTable(name_index, length, file, constant_pool);
    
    case 4: 
      return new LineNumberTable(name_index, length, file, constant_pool);
    
    case 5: 
      return new LocalVariableTable(name_index, length, file, constant_pool);
    
    case 6: 
      return new InnerClasses(name_index, length, file, constant_pool);
    
    case 7: 
      return new Synthetic(name_index, length, file, constant_pool);
    
    case 8: 
      return new Deprecated(name_index, length, file, constant_pool);
    
    case 9: 
      return new PMGClass(name_index, length, file, constant_pool);
    
    case 10: 
      return new Signature(name_index, length, file, constant_pool);
    
    case 11: 
      return new StackMap(name_index, length, file, constant_pool);
    }
    
    throw new InternalError("Ooops! default case reached.");
  }
  


  public final int getLength()
  {
    return length;
  }
  

  public final void setLength(int length)
  {
    this.length = length;
  }
  


  public final void setNameIndex(int name_index)
  {
    this.name_index = name_index;
  }
  

  public final int getNameIndex()
  {
    return name_index;
  }
  

  public final byte getTag()
  {
    return tag;
  }
  

  public final ConstantPool getConstantPool()
  {
    return constant_pool;
  }
  


  public final void setConstantPool(ConstantPool constant_pool)
  {
    this.constant_pool = constant_pool;
  }
  





  public Object clone()
  {
    Object o = null;
    try
    {
      o = super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    
    return o;
  }
  



  public abstract Attribute copy(ConstantPool paramConstantPool);
  


  public String toString()
  {
    return org.apache.bcel.Constants.ATTRIBUTE_NAMES[tag];
  }
}
