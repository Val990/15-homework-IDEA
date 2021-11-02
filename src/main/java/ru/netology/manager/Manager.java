package ru.netology.manager;

import ru.netology.domain.*;
import ru.netology.repository.Repository;
import java.util.List;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

@Data
public class Manager {

    private Repository repository;

    public Manager(Repository repository) {
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.save(issue);
    }

    public List<Issue> isOpened () {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getStatus().equals(Status.opened)) {
                result.add(issue);
            }
        }
        return result;
    }

    public List<Issue> isClosed () {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getStatus().equals(Status.closed)) {
                result.add(issue);
            }
        }
        return result;
    }


    private List<Issue> filterBy(Predicate<Issue> predicate, Comparator<Issue> issueComparator) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (predicate.test(issue)) {
                tmp.add(issue);
            }
        }
        tmp.sort(issueComparator);
        return tmp;
    }

    public List<Issue> filterByAuthor(AuthorInfo author, Comparator<Issue> issueComparator) {
        Predicate<Issue> authorPredicate = issue -> issue.getAuthor().contains(author);
        return filterBy(authorPredicate, issueComparator);
    }

    public List<Issue> filterByAssignee(Assignee assignee, Comparator<Issue> issueComparator) {
        Predicate<Issue> assigneePredicate = issue -> issue.getAssignee().contains(assignee);
        return filterBy(assigneePredicate, issueComparator);
    }

    public List<Issue> filterByLabel(Label label, Comparator<Issue> issueComparator) {
        Predicate<Issue> labelPredicate = issue -> issue.getLabel().contains(label);
        return filterBy(labelPredicate, issueComparator);
    }

}

