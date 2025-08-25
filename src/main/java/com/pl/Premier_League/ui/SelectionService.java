package com.pl.Premier_League.ui;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@SessionScope
public class SelectionService {
    private final Set<String> selectedNames = new LinkedHashSet<>();

    public Set<String> getSelectedNames() { return selectedNames; }
    public void clear() { selectedNames.clear(); }
    public void add(String name) { if (name != null) selectedNames.add(name); }
    public void addAll(Iterable<String> names) { names.forEach(this::add); }
    public void remove(String name) { selectedNames.remove(name); }
}
