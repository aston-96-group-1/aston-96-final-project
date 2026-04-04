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

@DisplayName("Тесты сортировки по количеству постов")
class SortByPostCountTest {

    private SortByPostCount sortByPostCount;
    private List<User> users;

    @BeforeEach
    void setUp() {
        sortByPostCount = new SortByPostCount();
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
        users.add(User.builder()
                .name("Diana")
                .email("diana@mail.com")
                .postCount(5)
                .build());
    }

    @Nested
    @DisplayName("Тесты метода sort()")
    class SortMethodTests {

        @Test
        @DisplayName("Сортировка по количеству постов в ASC порядке")
        void testSortByPostCountAsc() {
            sortByPostCount.sort(users, SortOrder.ASC);

            assertEquals(2, users.get(0).getPostCount());
            assertEquals(3, users.get(1).getPostCount());
            assertEquals(5, users.get(2).getPostCount());
            assertEquals(5, users.get(3).getPostCount());
            assertEquals(7, users.get(4).getPostCount());
        }

        @Test
        @DisplayName("Сортировка по количеству постов в DESC порядке")
        void testSortByPostCountDesc() {
            sortByPostCount.sort(users, SortOrder.DESC);

            assertEquals(7, users.get(0).getPostCount());
            assertEquals(5, users.get(1).getPostCount());
            assertEquals(5, users.get(2).getPostCount());
            assertEquals(3, users.get(3).getPostCount());
            assertEquals(2, users.get(4).getPostCount());
        }

        @Test
        @DisplayName("Сортировка пустого списка")
        void testSortEmptyList() {
            List<User> emptyList = new ArrayList<>();
            sortByPostCount.sort(emptyList, SortOrder.ASC);
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

            sortByPostCount.sort(singleList, SortOrder.ASC);
            assertEquals(1, singleList.size());
            assertEquals(5, singleList.get(0).getPostCount());
        }

        @Test
        @DisplayName("Сортировка списка с одинаковым количеством постов")
        void testSortWithDuplicatePostCounts() {
            List<User> usersWithDuplicates = new ArrayList<>();
            usersWithDuplicates.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(5)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(3)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(5)
                    .build());

            sortByPostCount.sort(usersWithDuplicates, SortOrder.ASC);

            assertEquals(3, usersWithDuplicates.get(0).getPostCount());
            assertEquals(5, usersWithDuplicates.get(1).getPostCount());
            assertEquals(5, usersWithDuplicates.get(2).getPostCount());
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по ASC списка")
        void testSortAlreadySortedAsc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("Charlie").email("charlie@mail.com").postCount(2).build());
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());

            sortByPostCount.sort(sortedList, SortOrder.ASC);

            assertEquals(2, sortedList.get(0).getPostCount());
            assertEquals(3, sortedList.get(1).getPostCount());
            assertEquals(5, sortedList.get(2).getPostCount());
            assertEquals(7, sortedList.get(3).getPostCount());
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по DESC списка")
        void testSortAlreadySortedDesc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());
            sortedList.add(User.builder().name("Charlie").email("charlie@mail.com").postCount(2).build());

            sortByPostCount.sort(sortedList, SortOrder.DESC);

            assertEquals(7, sortedList.get(0).getPostCount());
            assertEquals(5, sortedList.get(1).getPostCount());
            assertEquals(3, sortedList.get(2).getPostCount());
            assertEquals(2, sortedList.get(3).getPostCount());
        }

        @Test
        @DisplayName("Сортировка с null списком")
        void testSortNullList() {
            assertDoesNotThrow(() -> sortByPostCount.sort(null, SortOrder.ASC));
        }

        @Test
        @DisplayName("Сортировка с отрицательными значениями postCount")
        void testSortWithNegativePostCounts() {
            List<User> negativeCounts = new ArrayList<>();
            negativeCounts.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(-5)
                    .build());
            negativeCounts.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(-10)
                    .build());
            negativeCounts.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(0)
                    .build());

            sortByPostCount.sort(negativeCounts, SortOrder.ASC);

            assertEquals(-10, negativeCounts.get(0).getPostCount());
            assertEquals(-5, negativeCounts.get(1).getPostCount());
            assertEquals(0, negativeCounts.get(2).getPostCount());
        }

        @Test
        @DisplayName("Сортировка с нулевыми значениями")
        void testSortWithZeroPostCounts() {
            List<User> zeroCounts = new ArrayList<>();
            zeroCounts.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(0)
                    .build());
            zeroCounts.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(0)
                    .build());
            zeroCounts.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(0)
                    .build());

            assertDoesNotThrow(() -> sortByPostCount.sort(zeroCounts, SortOrder.ASC));
            assertEquals(3, zeroCounts.size());
            assertEquals(0, zeroCounts.get(0).getPostCount());
            assertEquals(0, zeroCounts.get(1).getPostCount());
            assertEquals(0, zeroCounts.get(2).getPostCount());
        }
    }

    @Nested
    @DisplayName("Тесты метода getDescription()")
    class DescriptionTests {

        @Test
        @DisplayName("Проверка описания сортировки")
        void testGetDescription() {
            String description = sortByPostCount.getDescription();
            assertNotNull(description);
            assertEquals("Сортировка по количеству постов.", description);
        }

        @Test
        @DisplayName("Проверка что описание не пустое")
        void testDescriptionNotEmpty() {
            assertFalse(sortByPostCount.getDescription().isEmpty());
        }

        @Test
        @DisplayName("Проверка константы DESCRIPTION")
        void testDescriptionConstant() {
            assertEquals(SortByPostCount.DESCRIPTION, sortByPostCount.getDescription());
        }
    }

    @Nested
    @DisplayName("Граничные тесты")
    class BoundaryTests {

        @Test
        @DisplayName("Сортировка с максимальными значениями int")
        void testSortWithMaxIntValues() {
            List<User> maxIntValues = new ArrayList<>();
            maxIntValues.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(Integer.MAX_VALUE)
                    .build());
            maxIntValues.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(Integer.MAX_VALUE - 1)
                    .build());
            maxIntValues.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(Integer.MAX_VALUE - 2)
                    .build());

            sortByPostCount.sort(maxIntValues, SortOrder.ASC);

            assertEquals(Integer.MAX_VALUE - 2, maxIntValues.get(0).getPostCount());
            assertEquals(Integer.MAX_VALUE - 1, maxIntValues.get(1).getPostCount());
            assertEquals(Integer.MAX_VALUE, maxIntValues.get(2).getPostCount());
        }

        @Test
        @DisplayName("Сортировка с минимальными значениями int")
        void testSortWithMinIntValues() {
            List<User> minIntValues = new ArrayList<>();
            minIntValues.add(User.builder()
                    .name("John")
                    .email("john@mail.com")
                    .postCount(Integer.MIN_VALUE)
                    .build());
            minIntValues.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(Integer.MIN_VALUE + 1)
                    .build());
            minIntValues.add(User.builder()
                    .name("Bob")
                    .email("bob@mail.com")
                    .postCount(Integer.MIN_VALUE + 2)
                    .build());

            sortByPostCount.sort(minIntValues, SortOrder.ASC);

            assertEquals(Integer.MIN_VALUE, minIntValues.get(0).getPostCount());
            assertEquals(Integer.MIN_VALUE + 1, minIntValues.get(1).getPostCount());
            assertEquals(Integer.MIN_VALUE + 2, minIntValues.get(2).getPostCount());
        }

        @Test
        @DisplayName("Сортировка большого списка")
        void testSortLargeList() {
            List<User> largeList = new ArrayList<>();
            for (int i = 1000; i > 0; i--) {
                largeList.add(User.builder()
                        .name("User" + i)
                        .email("user" + i + "@mail.com")
                        .postCount(i)
                        .build());
            }

            sortByPostCount.sort(largeList, SortOrder.ASC);

            for (int i = 0; i < largeList.size() - 1; i++) {
                assertTrue(largeList.get(i).getPostCount() <= largeList.get(i + 1).getPostCount(),
                        "Список не отсортирован: " + largeList.get(i).getPostCount() +
                                " > " + largeList.get(i + 1).getPostCount());
            }

            assertEquals(1, largeList.get(0).getPostCount());
            assertEquals(1000, largeList.get(999).getPostCount());
            assertEquals(1000, largeList.size());
        }

        @Test
        @DisplayName("Сортировка списка со случайными значениями")
        void testSortWithRandomValues() {
            List<User> randomList = new ArrayList<>();
            randomList.add(User.builder().name("User1").email("user1@mail.com").postCount(42).build());
            randomList.add(User.builder().name("User2").email("user2@mail.com").postCount(7).build());
            randomList.add(User.builder().name("User3").email("user3@mail.com").postCount(99).build());
            randomList.add(User.builder().name("User4").email("user4@mail.com").postCount(1).build());
            randomList.add(User.builder().name("User5").email("user5@mail.com").postCount(23).build());

            sortByPostCount.sort(randomList, SortOrder.ASC);

            assertEquals(1, randomList.get(0).getPostCount());
            assertEquals(7, randomList.get(1).getPostCount());
            assertEquals(23, randomList.get(2).getPostCount());
            assertEquals(42, randomList.get(3).getPostCount());
            assertEquals(99, randomList.get(4).getPostCount());
        }

        @Test
        @DisplayName("Сортировка списка с одинаковыми значениями")
        void testSortAllSameValues() {
            List<User> sameValues = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                sameValues.add(User.builder()
                        .name("User" + i)
                        .email("user" + i + "@mail.com")
                        .postCount(10)
                        .build());
            }

            sortByPostCount.sort(sameValues, SortOrder.ASC);

            for (User user : sameValues) {
                assertEquals(10, user.getPostCount());
            }
            assertEquals(50, sameValues.size());
        }
    }

    @Nested
    @DisplayName("Тесты наследования от Sort")
    class InheritanceTests {

        @Test
        @DisplayName("Проверка что SortByPostCount является наследником Sort")
        void testInheritance() {
            assertTrue(sortByPostCount instanceof Sort);
        }

        @Test
        @DisplayName("Проверка что метод sort переопределен")
        void testSortMethodOverridden() {
            List<User> testList = new ArrayList<>(users);
            sortByPostCount.sort(testList, SortOrder.ASC);
            assertEquals(2, testList.get(0).getPostCount());
        }

        @Test
        @DisplayName("Проверка работы с разными порядками сортировки подряд")
        void testMultipleSortOrders() {
            List<User> testList = new ArrayList<>(users);

            sortByPostCount.sort(testList, SortOrder.ASC);
            assertEquals(2, testList.get(0).getPostCount());
            assertEquals(7, testList.get(4).getPostCount());

            sortByPostCount.sort(testList, SortOrder.DESC);
            assertEquals(7, testList.get(0).getPostCount());
            assertEquals(2, testList.get(4).getPostCount());
        }

        @Test
        @DisplayName("Проверка что сортировка стабильна для одинаковых значений")
        void testStableSortForEqualValues() {
            List<User> equalValues = new ArrayList<>();
            User user1 = User.builder().name("First").email("first@mail.com").postCount(5).build();
            User user2 = User.builder().name("Second").email("second@mail.com").postCount(5).build();
            User user3 = User.builder().name("Third").email("third@mail.com").postCount(3).build();

            equalValues.add(user1);
            equalValues.add(user2);
            equalValues.add(user3);

            sortByPostCount.sort(equalValues, SortOrder.ASC);

            assertEquals(3, equalValues.get(0).getPostCount());
            assertEquals(5, equalValues.get(1).getPostCount());
            assertEquals(5, equalValues.get(2).getPostCount());
        }
    }
}