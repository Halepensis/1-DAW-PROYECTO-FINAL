package src.BDD;


import java.util.ArrayList;

public interface BdInterface <T>{
    void create(T objeto);
        T get(int id);
    ArrayList<T> getAll();
    void update(T objeto);
    void delete(T objeto);


}
