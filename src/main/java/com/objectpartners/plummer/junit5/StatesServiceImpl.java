package com.objectpartners.plummer.junit5;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StatesServiceImpl implements StatesService {

    private static final Collection<State> STATES;
    static {
        STATES = Collections.unmodifiableCollection(
                Utilities.readFromCsv("/data.csv").stream()
                .map(values -> new State(values[0], values[1]))
                .collect(Collectors.toList())
        );
    }

    @Override
    public State findByName(@NotNull String name) {
        Assert.notNull(name, "Name cannot be null");
        return STATES.stream()
                .filter(state -> name.equals(state.getName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<State> getAll() {
        return new ArrayList<>(STATES);
    }

    @Override
    public Collection<State> getAllMatching(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return STATES.stream()
                .filter(state -> pattern.matcher(state.getName()).find())
                .collect(Collectors.toList());
    }
}
