package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Тесты абстрактного класса Sort")
class SortTest {

    private TestSortImpl sortImpl;
    private List<User> users;

    @BeforeEach
    void setUp() {
        sortImpl = new TestSortImpl();
        users = new ArrayList<>();
        users.add(User.builder()
                .name("John")
                .email("john@mail.com")
                .postCount(5)
                .build());
        users.add(User.builder()
                .name("Alice")
                .email("alice@mail.com")
                .postCount(3)
                .build());
        users.add(User.builder()
                .name("Bob")
                .email("bob@mail.com")
                .postCount(7)
                .build());
    }

    @Test
    @DisplayName("Тест быстрой сортировки с ASC порядком")
    void testQuickSortAsc() {
        sortImpl.setSortOrder(SortOrder.ASC);
        Comparator<User> comparator = Comparator.comparing(User::getName);
        sortImpl.testQuickSort(users, comparator);

        assertEquals("Alice", users.get(0).getName());
        assertEquals("Bob", users.get(1).getName());
        assertEquals("John", users.get(2).getName());
    }

    @Test
    @DisplayName("Тест быстрой сортировки с DESC порядком")
    void testQuickSortDesc() {
        sortImpl.setSortOrder(SortOrder.DESC);
        Comparator<User> comparator = Comparator.comparing(User::getName);
        sortImpl.testQuickSort(users, comparator);

        assertEquals("John", users.get(0).getName());
        assertEquals("Bob", users.get(1).getName());
        assertEquals("Alice", users.get(2).getName());
    }

    @Test
    @DisplayName("Тест быстрой сортировки с пустым списком")
    void testQuickSortEmptyList() {
        List<User> emptyList = new ArrayList<>();
        sortImpl.testQuickSort(emptyList, Comparator.comparing(User::getName));
        assertTrue(emptyList.isEmpty());
    }

    @Test
    @DisplayName("Тест быстрой сортировки с null списком")
    void testQuickSortNullList() {
        assertDoesNotThrow(() -> sortImpl.testQuickSort(null, Comparator.comparing(User::getName)));
    }

    @Test
    @DisplayName("Тест быстрой сортировки с одним элементом")
    void testQuickSortSingleElement() {
        List<User> singleList = new ArrayList<>();
        singleList.add(User.builder()
                .name("John")
                .email("john@mail.com")
                .postCount(5)
                .build());

        sortImpl.testQuickSort(singleList, Comparator.comparing(User::getName));
        assertEquals(1, singleList.size());
        assertEquals("John", singleList.get(0).getName());
    }

    @Test
    @DisplayName("Тест установки порядка сортировки")
    void testSetSortOrder() {
        sortImpl.setSortOrder(SortOrder.DESC);
        assertEquals(SortOrder.DESC, sortImpl.getSortOrder());

        sortImpl.setSortOrder(SortOrder.ASC);
        assertEquals(SortOrder.ASC, sortImpl.getSortOrder());
    }

    private static class TestSortImpl extends Sort {
        private Comparator<User> testComparator;

        @Override
        public void sort(List<User> users, SortOrder sortOrder) {
            setSortOrder(sortOrder);
            quickSort(users, testComparator);
        }

        @Override
        public String getDescription() {
            return "Test implementation";
        }

        public void testQuickSort(List<User> users, Comparator<User> comparator) {
            this.testComparator = comparator;
            quickSort(users, comparator);
        }

        public SortOrder getSortOrder() {
            try {
                var field = Sort.class.getDeclaredField("sortOrder");
                field.setAccessible(true);
                return (SortOrder) field.get(this);
            } catch (Exception e) {
                return null;
            }
        }
    }
}