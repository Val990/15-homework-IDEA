package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
public class Issue {
    public int id;
    private String name;
    private Status status;
    private Set<Label> label = new HashSet<>();
    private Set<Assignee> assignee = new HashSet<>();
    private Set<AuthorInfo> author = new HashSet<>();

    public Issue(int id, String name, Status status, Label label, Assignee assignee, AuthorInfo author) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.label.add(label);
        this.assignee.add(assignee);
        this.author.add(author);
    }
}

