package ru.cg.webbpm.repository.model.nexus;

import java.util.ArrayList;
import java.util.List;

public class NexusPackages {
    private List<NexusPackage> items = new ArrayList<>();

    public List<NexusPackage> getItems() {
        return items;
    }

    public void setItems(List<NexusPackage> items) {
        this.items = items;
    }
}
