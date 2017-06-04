package model.entity;

public abstract class Entity {

    public abstract int     getId();
    public abstract void    setId(int id);

    public abstract String  getName();
    public abstract void    setName(String name);

    /* toString for debug/logging purposes
    - takes vararg as arguments and combines them to plain HR string*/
    public String toString(Object ... params){
        StringBuilder strOut = new StringBuilder();
        for(Object o : params)
            strOut.append(o.toString()+"; ");
        strOut.delete(strOut.length()-2, strOut.length());
        return strOut.toString();
    }
}
