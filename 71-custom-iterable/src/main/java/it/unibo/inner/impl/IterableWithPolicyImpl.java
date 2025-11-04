package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private Predicate<T> filterForIteration;

    private List<T> newArray = new ArrayList<>();

    public IterableWithPolicyImpl(T[] newArray) {
        this(newArray, new Predicate<>() {
            public boolean test(T elem) {
                return true;
            }
        });
    }  

    public IterableWithPolicyImpl(T[] newArray, Predicate<T> filter) {
        this.newArray = new ArrayList<>(Arrays.asList(newArray));
        this.filterForIteration = filter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filterForIteration = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerForIterator();
    }

    private class InnerForIterator implements Iterator<T> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            while(newArray.size() > this.current) {
                if (filterForIteration.test(newArray.get(current))) {
                    return true;
                }
                current = current + 1;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext()) {
                return newArray.get(current++);
            }
            throw new NoSuchElementException("No element respect the condition in filter!");
        }
        
    }
    
}

