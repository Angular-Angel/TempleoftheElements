
package templeoftheelements;

public interface GenerationProcedure<T> {
    
    public T generate();
    
    public T modify(T t);
    
    public boolean isApplicable(T t);
    
}
