package com.objectpartners.plummer.junit5;

import java.util.Collection;

public interface StatesService {
    State findByName(String name);
    Collection<State> getAll();
    Collection<State> getAllMatching(String pattern);
}
