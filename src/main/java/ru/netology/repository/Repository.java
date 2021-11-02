package ru.netology.repository;

import ru.netology.domain.Issue;
import ru.netology.domain.NegativeIdException;
import ru.netology.domain.NotFoundException;
import ru.netology.domain.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repository {

    private List<Issue> issues = new ArrayList<>();

    public void save(Issue issue) {
        issues.add(issue);
    }

    public List<Issue> getAll() {
        return issues;
    }

    public boolean addAll(Collection<? extends Issue> issues) {
        return this.issues.addAll(issues);
    }

    public Issue findById(int id) {
        for (Issue issue : issues) {
            if (id < 0) {
                throw new NegativeIdException("Can not use negative id: " + id);
            }
            if (issue.getId() == id) {
                return issue;
            }
        }
        throw new NotFoundException("Element with id: " + id + " not found");
    }

    public void removeById(int id) {
        if (id < 0) {
            throw new NegativeIdException("Can not use negative id: " + id);
        }
        if (findById(id) == null) {
            throw new NotFoundException("Element with id: " + id + " not found");
        }
        issues.removeIf(element -> element.getId() == id);
    }

    public void openById(int id) {
        for (Issue issue : issues) {
            if (issue.getId() == id) {
                issue.setStatus(Status.opened);
            }
        }
    }

    public void closeById(int id) {
        for (Issue issue : issues) {
            if (issue.getId() == id) {
                issue.setStatus(Status.closed);
            }
        }
    }

}
