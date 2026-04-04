package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты сортировки по имени")
class SortByNameTest {

    private SortByName sortByName;
    private List<User> users;

    @BeforeEach
    void setUp() {
        sortByName = new SortByName();
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
        users.add(User.builder()
                .name("Charlie")
                .email("charlie@mail.com")
                .postCount(2)
                .build());
    }

    @Nested
    @DisplayName("Тесты метода sort()")
    class SortMethodTests {

        @Test
        @DisplayName("Сортировка по имени в ASC порядке")
        void testSortByNameAsc() {
            sortByName.sort(users, SortOrder.ASC);

            assertEquals("Alice", users.get(0).getName());
            assertEquals("Bob", users.get(1).getName());
            assertEquals("Charlie", users.get(2).getName());
            assertEquals("John", users.get(3).getName());
        }

        @Test
        @DisplayName("Сортировка по имени в DESC порядке")
        void testSortByNameDesc() {
            sortByName.sort(users, SortOrder.DESC);

            assertEquals("John", users.get(0).getName());
            assertEquals("Charlie", users.get(1).getName());
            assertEquals("Bob", users.get(2).getName());
            assertEquals("Alice", users.get(3).getName());
        }

        @Test
        @DisplayName("Сортировка пустого списка")
        void testSortEmptyList() {
            List<User> emptyList = new ArrayList<>();
            sortByName.sort(emptyList, SortOrder.ASC);
            assertTrue(emptyList.isEmpty());
        }

        @Test
        @DisplayName("Сортировка списка с одним элементом")
        void testSortSingleElement() {
            List<User> singleList = new ArrayList<>();
            singleList.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(5)
                    .build());

            sortByName.sort(singleList, SortOrder.ASC);
            assertEquals(1, singleList.size());
            assertEquals("John", singleList.get(0).getName());
        }

        @Test
        @DisplayName("Сортировка списка с одинаковыми именами")
        void testSortWithDuplicateNames() {
            List<User> usersWithDuplicates = new ArrayList<>();
            usersWithDuplicates.add(User.builder()
                    .name("John")
                    .email("john1@mail.com")
                    .postCount(5)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(3)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("John")
                    .email("john2@mail.com")
                    .postCount(7)
                    .build());

            sortByName.sort(usersWithDuplicates, SortOrder.ASC);

            assertEquals("Alice", usersWithDuplicates.get(0).getName());
            assertTrue(usersWithDuplicates.get(1).getName().equals("John"));
            assertTrue(usersWithDuplicates.get(2).getName().equals("John"));
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по ASC списка")
        void testSortAlreadySortedAsc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());

            sortByName.sort(sortedList, SortOrder.ASC);

            assertEquals("Alice", sortedList.get(0).getName());
            assertEquals("Bob", sortedList.get(1).getName());
            assertEquals("John", sortedList.get(2).getName());
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по DESC списка")
        void testSortAlreadySortedDesc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());

            sortByName.sort(sortedList, SortOrder.DESC);

            assertEquals("John", sortedList.get(0).getName());
            assertEquals("Bob", sortedList.get(1).getName());
            assertEquals("Alice", sortedList.get(2).getName());
        }

        @Test
        @DisplayName("Сортировка с null списком")
        void testSortNullList() {
            assertDoesNotThrow(() -> sortByName.sort(null, SortOrder.ASC));
        }
    }

    @Nested
    @DisplayName("Тесты метода getDescription()")
    class DescriptionTests {

        @Test
        @DisplayName("Проверка описания сортировки")
        void testGetDescription() {
            String description = sortByName.getDescription();
            assertNotNull(description);
            assertEquals("Сортировка по имени.", description);
        }

        @Test
        @DisplayName("Проверка что описание не пустое")
        void testDescriptionNotEmpty() {
            assertFalse(sortByName.getDescription().isEmpty());
        }

        @Test
        @DisplayName("Проверка константы DESCRIPTION")
        void testDescriptionConstant() {
            assertEquals(SortByName.DESCRIPTION, sortByName.getDescription());
        }
    }

    @Nested
    @DisplayName("Граничные тесты")
    class BoundaryTests {

        @Test
        @DisplayName("Сортировка с именами содержащими спецсимволы")
        void testSortWithSpecialCharactersInName() {
            List<User> specialNames = new ArrayList<>();
            specialNames.add(User.builder()
                    .name("Jo@hn")
                    .email("john@mail.com")
                    .postCount(1)
                    .build());
            specialNames.add(User.builder()
                    .name("Al_ice")
                    .email("alice@mail.com")
                    .postCount(2)
                    .build());
            specialNames.add(User.builder()
                    .name("Bo-b")
                    .email("bob@mail.com")
                    .postCount(3)
                    .build());

            sortByName.sort(specialNames, SortOrder.ASC);

            assertEquals(3, specialNames.size());
            assertNotNull(specialNames.get(0).getName());
        }

        @Test
        @DisplayName("Сортировка с именами в разных регистрах")
        void testSortWithDifferentCaseNames() {
            List<User> caseNames = new ArrayList<>();
            caseNames.add(User.builder()
                    .name("Zebra")
                    .email("zebra@mail.com")
                    .postCount(1)
                    .build());
            caseNames.add(User.builder()
                    .name("alice")
                    .email("alice@mail.com")
                    .postCount(2)
                    .build());
            caseNames.add(User.builder()
                    .name("BOB")
                    .email("bob@mail.com")
                    .postCount(3)
                    .build());

            sortByName.sort(caseNames, SortOrder.ASC);

            assertEquals(3, caseNames.size());
            assertNotNull(caseNames.get(0).getName());
        }

        @Test
        @DisplayName("Сортировка с пустыми именами")
        void testSortWithEmptyNames() {
            List<User> emptyNames = new ArrayList<>();
            emptyNames.add(User.builder()
                    .name("")
                    .email("empty1@mail.com")
                    .postCount(1)
                    .build());
            emptyNames.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(2)
                    .build());
            emptyNames.add(User.builder()
                    .name("")
                    .email("empty2@mail.com")
                    .postCount(3)
                    .build());

            assertDoesNotThrow(() -> sortByName.sort(emptyNames, SortOrder.ASC));
            assertEquals(3, emptyNames.size());
        }

        @Test
        @DisplayName("Сортировка с именами содержащими цифры")
        void testSortWithNamesContainingNumbers() {
            List<User> numberedNames = new ArrayList<>();
            numberedNames.add(User.builder()
                    .name("User3")
                    .email("user3@mail.com")
                    .postCount(3)
                    .build());
            numberedNames.add(User.builder()
                    .name("User1")
                    .email("user1@mail.com")
                    .postCount(1)
                    .build());
            numberedNames.add(User.builder()
                    .name("User2")
                    .email("user2@mail.com")
                    .postCount(2)
                    .build());

            sortByName.sort(numberedNames, SortOrder.ASC);

            assertEquals("User1", numberedNames.get(0).getName());
            assertEquals("User2", numberedNames.get(1).getName());
            assertEquals("User3", numberedNames.get(2).getName());
        }

        @Test
        @DisplayName("Сортировка большого списка")
        void testSortLargeList() {
            List<User> largeList = new ArrayList<>();
            for (int i = 100; i > 0; i--) {
                largeList.add(User.builder()
                        .name("User" + i)
                        .email("user" + i + "@mail.com")
                        .postCount(i)
                        .build());
            }

            sortByName.sort(largeList, SortOrder.ASC);

            for (int i = 0; i < largeList.size() - 1; i++) {
                assertTrue(largeList.get(i).getName()
                                .compareTo(largeList.get(i + 1).getName()) <= 0,
                        "Список не отсортирован: " + largeList.get(i).getName() +
                                " > " + largeList.get(i + 1).getName());
            }

            assertEquals(100, largeList.size());
        }

        @Test
        @DisplayName("Сортировка с очень длинными именами")
        void testSortWithVeryLongNames() {
            List<User> longNames = new ArrayList<>();
            String veryLongName1 = "A" + "a".repeat(1000);
            String veryLongName2 = "B" + "b".repeat(1000);
            String veryLongName3 = "C" + "c".repeat(1000);

            longNames.add(User.builder()
                    .name(veryLongName2)
                    .email("user2@mail.com")
                    .postCount(2)
                    .build());
            longNames.add(User.builder()
                    .name(veryLongName1)
                    .email("user1@mail.com")
                    .postCount(1)
                    .build());
            longNames.add(User.builder()
                    .name(veryLongName3)
                    .email("user3@mail.com")
                    .postCount(3)
                    .build());

            assertDoesNotThrow(() -> sortByName.sort(longNames, SortOrder.ASC));
            assertEquals(veryLongName1, longNames.get(0).getName());
            assertEquals(veryLongName2, longNames.get(1).getName());
            assertEquals(veryLongName3, longNames.get(2).getName());
        }
    }

    @Nested
    @DisplayName("Тесты наследования от Sort")
    class InheritanceTests {

        @Test
        @DisplayName("Проверка что SortByName является наследником Sort")
        void testInheritance() {
            assertTrue(sortByName instanceof Sort);
        }

        @Test
        @DisplayName("Проверка что метод sort переопределен")
        void testSortMethodOverridden() {
            List<User> testList = new ArrayList<>(users);
            sortByName.sort(testList, SortOrder.ASC);
            assertEquals("Alice", testList.get(0).getName());
        }

        @Test
        @DisplayName("Проверка работы с разными порядками сортировки подряд")
        void testMultipleSortOrders() {
            List<User> testList = new ArrayList<>(users);

            sortByName.sort(testList, SortOrder.ASC);
            assertEquals("Alice", testList.get(0).getName());
            assertEquals("John", testList.get(3).getName());

            sortByName.sort(testList, SortOrder.DESC);
            assertEquals("John", testList.get(0).getName());
            assertEquals("Alice", testList.get(3).getName());
        }
    }
}