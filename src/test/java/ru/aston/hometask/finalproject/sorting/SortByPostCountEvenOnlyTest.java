package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SortByPostCountEvenOnlyTest {

    private SortByPostCountEvenOnly sortByPostCountEvenOnly;

    @BeforeEach
    void setUp() {
        sortByPostCountEvenOnly = new SortByPostCountEvenOnly();
    }

    @Test
    void testSortEvenOnlyAsc() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
        users.add(User.builder().name("Alice").email("alice@mail.com").postCount(4).build());
        users.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
        users.add(User.builder().name("Charlie").email("charlie@mail.com").postCount(2).build());
        users.add(User.builder().name("Diana").email("diana@mail.com").postCount(8).build());

        sortByPostCountEvenOnly.sort(users, SortOrder.ASC);

        assertEquals(5, users.get(0).getPostCount());
        assertEquals(2, users.get(1).getPostCount());
        assertEquals(7, users.get(2).getPostCount());
        assertEquals(4, users.get(3).getPostCount());
        assertEquals(8, users.get(4).getPostCount());
    }

    @Test
    void testSortEvenOnlyDesc() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
        users.add(User.builder().name("Alice").email("alice@mail.com").postCount(4).build());
        users.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
        users.add(User.builder().name("Charlie").email("charlie@mail.com").postCount(2).build());
        users.add(User.builder().name("Diana").email("diana@mail.com").postCount(8).build());

        sortByPostCountEvenOnly.sort(users, SortOrder.DESC);

        assertEquals(5, users.get(0).getPostCount());
        assertEquals(8, users.get(1).getPostCount());
        assertEquals(7, users.get(2).getPostCount());
        assertEquals(4, users.get(3).getPostCount());
        assertEquals(2, users.get(4).getPostCount());
    }

    @Test
    void testSortAllEven() {
        List<User> allEven = new ArrayList<>();
        allEven.add(User.builder().name("John").email("john@mail.com").postCount(6).build());
        allEven.add(User.builder().name("Alice").email("alice@mail.com").postCount(2).build());
        allEven.add(User.builder().name("Bob").email("bob@mail.com").postCount(8).build());

        sortByPostCountEvenOnly.sort(allEven, SortOrder.ASC);

        assertEquals(2, allEven.get(0).getPostCount());
        assertEquals(6, allEven.get(1).getPostCount());
        assertEquals(8, allEven.get(2).getPostCount());
    }

    @Test
    void testSortAllOdd() {
        List<User> allOdd = new ArrayList<>();
        User john = User.builder().name("John").email("john@mail.com").postCount(5).build();
        User alice = User.builder().name("Alice").email("alice@mail.com").postCount(3).build();

        allOdd.add(john);
        allOdd.add(alice);

        sortByPostCountEvenOnly.sort(allOdd, SortOrder.ASC);

        assertSame(john, allOdd.get(0));
        assertSame(alice, allOdd.get(1));
    }

    @Test
    void testSortEmptyList() {
        List<User> emptyList = new ArrayList<>();
        sortByPostCountEvenOnly.sort(emptyList, SortOrder.ASC);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void testSortNullList() {
        assertDoesNotThrow(() -> sortByPostCountEvenOnly.sort(null, SortOrder.ASC));
    }

    @Test
    void testGetDescription() {
        assertEquals("Сортировка по количеству постов (только четные значения).",
                sortByPostCountEvenOnly.getDescription());
    }

    @Test
    void testInheritance() {
        assertTrue(sortByPostCountEvenOnly instanceof Sort);
    }
}