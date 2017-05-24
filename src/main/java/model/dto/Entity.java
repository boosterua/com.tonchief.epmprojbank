package model.dto;

/**
 * Created by tonchief on 05/20/2017.
 */
public interface Entity {

    int     getId();
    void    setId(int id);

    String  getName();
    void    setName(String name);

    @Override
    String  toString();


}
