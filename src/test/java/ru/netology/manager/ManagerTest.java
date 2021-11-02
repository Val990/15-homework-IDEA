package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.*;
import ru.netology.repository.Repository;
import ru.netology.comporator.IssueComparator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ManagerTest {

    private Repository repository = new Repository();
    private Manager manager = new Manager(repository);
    private IssueComparator comparator = new IssueComparator();

    private Issue issue1 = new Issue(1,"name1", Status.opened, Label.bug, new Assignee(3, "Petrov"), new AuthorInfo(1, "Ivanov"));
    private Issue issue2 = new Issue(2,"name2", Status.closed, Label.question, new Assignee(4, "Smirnov"), new AuthorInfo(2, "Sidorov"));
    private Issue issue3 = new Issue(3,"name3", Status.closed, Label.enhancement, new Assignee(5, "Andreev"), new AuthorInfo(3, "Petrov"));
    private Issue issue4 = new Issue(4,"name4", Status.opened, Label.test, new Assignee(2, "Sidorov"), new AuthorInfo(4, "Smirnov"));
    private Issue issue5 = new Issue(5,"name5", Status.closed, Label.bug, new Assignee(1, "Ivanov"), new AuthorInfo(5, "Andreev"));

    @BeforeEach
    public void add() {

        manager.add(issue1);
        manager.add(issue2);
        manager.add(issue3);
        manager.add(issue4);
        manager.add(issue5);
    }

    @Test
    void shouldSaveAll() {

        assertEquals(5, repository.getAll().size());
    }

    @Test
    void shouldFindByAuthor() {

        List<Issue> expected = Arrays.asList(issue1);
        List<Issue> actual = manager.filterByAuthor(new AuthorInfo(1, "Ivanov"), comparator);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByAssignee() {

        List<Issue> expected = Arrays.asList(issue4);
        List<Issue> actual = manager.filterByAssignee(new Assignee(2, "Sidorov"),comparator);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByLabel() {

        List<Issue> expected = Arrays.asList(issue4);
        List<Issue> actual = manager.filterByLabel(Label.test, comparator);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindOpened() {

        List<Issue> expected = Arrays.asList(issue1, issue4);
        List<Issue> actual = manager.isOpened();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClosed() {

        List<Issue> expected = Arrays.asList(issue2, issue3, issue5);
        List<Issue> actual = manager.isClosed();

        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveById() {

        repository.removeById(3);

        List<Issue> expected = Arrays.asList(issue1, issue2, issue4, issue5);
        List<Issue> actual = repository.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {

        Issue actual = repository.findById(2);

        assertEquals(issue2, actual);
    }

    @Test
    void shouldFindByIdNotExist() {

        repository.save(issue1);
        repository.save(issue2);
        repository.save(issue3);
        repository.save(issue4);
        repository.save(issue5);

        assertThrows(NotFoundException.class, () -> {
            repository.findById(70);
        });
    }

    @Test
    void shouldFindByNegativeId() {

        repository.save(issue1);
        repository.save(issue2);
        repository.save(issue3);
        repository.save(issue4);
        repository.save(issue5);

        assertThrows(NegativeIdException.class, () -> {
            repository.findById(-70);
        });
    }

    @Test
    void shouldOpenById() {

        repository.openById(3);

        Issue id = repository.findById(3);

        assertEquals(id.getStatus(), Status.opened);
    }

    @Test
    void shouldCloseById() {

        repository.closeById(2);

        Issue id = repository.findById(2);

        assertEquals(id.getStatus(), Status.closed);
    }

    @Test
    void shouldRemoveByIdNotExist() {

        repository.save(issue1);
        repository.save(issue2);
        repository.save(issue3);
        repository.save(issue4);
        repository.save(issue5);

        assertThrows(NotFoundException.class, () -> {
            repository.removeById(10);
        });
    }

    @Test
    void shouldRemoveByNegativeId() {

        repository.save(issue1);
        repository.save(issue2);
        repository.save(issue3);
        repository.save(issue4);
        repository.save(issue5);

        assertThrows(NegativeIdException.class, () -> {
            repository.removeById(-20);
        });
    }
}