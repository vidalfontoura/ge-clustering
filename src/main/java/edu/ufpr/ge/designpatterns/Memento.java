package edu.ufpr.grammaticalevolution.designpatterns;

public interface Memento<T> {
    
    public void restore(T originator);
    
    public void setState(T originator);
}