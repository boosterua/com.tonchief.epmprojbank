package model.entity;

public interface Entity {

    int     getId();
    void    setId(int id);

    String  getName();
    void    setName(String name);

    String  toString();

    /* toString for debug/logging purposes
    - takes vararg as arguments and combines them to plain HR string*/
    default String toString(Object ... params){
        StringBuilder strOut = new StringBuilder();
        for(Object o : params)
            strOut.append(o.toString()+"; ");
        strOut.delete(strOut.length()-2, strOut.length());
        return strOut.toString();
    }
}
