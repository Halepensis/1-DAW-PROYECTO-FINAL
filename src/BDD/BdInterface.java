package src.BDD;



public interface BdInterface <T>{
    void create(T objeto);
    T get(int id);
    void readAll();
    void update(T objeto);
    void delete(T objeto);


}
