package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Тесты сортировки по email")
class SortByEmailTest {

    private SortByEmail sortByEmail;
    private List<User> users;

    @BeforeEach
    void setUp() {
        sortByEmail = new SortByEmail();
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
        @DisplayName("Сортировка по email в ASC порядке")
        void testSortByEmailAsc() {
            sortByEmail.sort(users, SortOrder.ASC);

            assertEquals("alice@mail.com", users.get(0).getEmail());
            assertEquals("bob@mail.com", users.get(1).getEmail());
            assertEquals("charlie@mail.com", users.get(2).getEmail());
            assertEquals("john@mail.com", users.get(3).getEmail());
        }

        @Test
        @DisplayName("Сортировка по email в DESC порядке")
        void testSortByEmailDesc() {
            sortByEmail.sort(users, SortOrder.DESC);

            assertEquals("john@mail.com", users.get(0).getEmail());
            assertEquals("charlie@mail.com", users.get(1).getEmail());
            assertEquals("bob@mail.com", users.get(2).getEmail());
            assertEquals("alice@mail.com", users.get(3).getEmail());
        }

        @Test
        @DisplayName("Сортировка пустого списка")
        void testSortEmptyList() {
            List<User> emptyList = new ArrayList<>();
            sortByEmail.sort(emptyList, SortOrder.ASC);
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

            sortByEmail.sort(singleList, SortOrder.ASC);
            assertEquals(1, singleList.size());
            assertEquals("john@mail.com", singleList.get(0).getEmail());
        }

        @Test
        @DisplayName("Сортировка списка с одинаковыми email")
        void testSortWithDuplicateEmails() {
            List<User> usersWithDuplicates = new ArrayList<>();
            usersWithDuplicates.add(User.builder()
                    .name("John")
                    .email("same@mail.com")
                    .postCount(5)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("Alice")
                    .email("alice@mail.com")
                    .postCount(3)
                    .build());
            usersWithDuplicates.add(User.builder()
                    .name("Bob")
                    .email("same@mail.com")
                    .postCount(7)
                    .build());

            sortByEmail.sort(usersWithDuplicates, SortOrder.ASC);

            assertEquals("alice@mail.com", usersWithDuplicates.get(0).getEmail());
            assertTrue(usersWithDuplicates.get(1).getEmail().equals("same@mail.com"));
            assertTrue(usersWithDuplicates.get(2).getEmail().equals("same@mail.com"));
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по ASC списка")
        void testSortAlreadySortedAsc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());

            sortByEmail.sort(sortedList, SortOrder.ASC);

            assertEquals("alice@mail.com", sortedList.get(0).getEmail());
            assertEquals("bob@mail.com", sortedList.get(1).getEmail());
            assertEquals("john@mail.com", sortedList.get(2).getEmail());
        }

        @Test
        @DisplayName("Сортировка уже отсортированного по DESC списка")
        void testSortAlreadySortedDesc() {
            List<User> sortedList = new ArrayList<>();
            sortedList.add(User.builder().name("John").email("john@mail.com").postCount(5).build());
            sortedList.add(User.builder().name("Bob").email("bob@mail.com").postCount(7).build());
            sortedList.add(User.builder().name("Alice").email("alice@mail.com").postCount(3).build());

            sortByEmail.sort(sortedList, SortOrder.DESC);

            assertEquals("john@mail.com", sortedList.get(0).getEmail());
            assertEquals("bob@mail.com", sortedList.get(1).getEmail());
            assertEquals("alice@mail.com", sortedList.get(2).getEmail());
        }
    }

    @Nested
    @DisplayName("Тесты метода getDescription()")
    class DescriptionTests {

        @Test
        @DisplayName("Проверка описания сортировки")
        void testGetDescription() {
            String description = sortByEmail.getDescription();
            assertNotNull(description);
            assertEquals("Сортировка по email.", description);
        }

        @Test
        @DisplayName("Проверка что описание не пустое")
        void testDescriptionNotEmpty() {
            assertFalse(sortByEmail.getDescription().isEmpty());
        }
    }

    @Nested
    @DisplayName("Граничные тесты")
    class BoundaryTests {

        @Test
        @DisplayName("Сортировка с null списком")
        void testSortNullList() {
            assertDoesNotThrow(() -> sortByEmail.sort(null, SortOrder.ASC));
        }

        @Test
        @DisplayName("Сортировка с email содержащими спецсимволы")
        void testSortWithSpecialCharactersInEmail() {
            List<User> specialEmails = new ArrayList<>();
            specialEmails.add(User.builder()
                    .name("User1")
                    .email("user+1@mail.com")
                    .postCount(1)
                    .build());
            specialEmails.add(User.builder()
                    .name("User2")
                    .email("user.2@mail.com")
                    .postCount(2)
                    .build());
            specialEmails.add(User.builder()
                    .name("User3")
                    .email("user_3@mail.com")
                    .postCount(3)
                    .build());

            sortByEmail.sort(specialEmails, SortOrder.ASC);

            assertEquals(3, specialEmails.size());
            assertNotNull(specialEmails.get(0).getEmail());
        }

        @Test
        @DisplayName("Сортировка с email в разных регистрах")
        void testSortWithDifferentCaseEmails() {
            List<User> caseEmails = new ArrayList<>();
            caseEmails.add(User.builder()
                    .name("User1")
                    .email("Zebra@mail.com")
                    .postCount(1)
                    .build());
            caseEmails.add(User.builder()
                    .name("User2")
                    .email("alice@mail.com")
                    .postCount(2)
                    .build());
            caseEmails.add(User.builder()
                    .name("User3")
                    .email("BOB@mail.com")
                    .postCount(3)
                    .build());

            sortByEmail.sort(caseEmails, SortOrder.ASC);

            assertEquals(3, caseEmails.size());
        }

        @Test
        @DisplayName("Сортировка большого списка")
        void testSortLargeList() {
            List<User> largeList = new ArrayList<>();
            for (int i = 100; i > 0; i--) {
                String email;
                if (i < 10) {
                    email = "user00" + i + "@mail.com";
                } else if (i < 100) {
                    email = "user0" + i + "@mail.com";
                } else {
                    email = "user" + i + "@mail.com";
                }
                largeList.add(User.builder()
                        .name("User" + i)
                        .email(email)
                        .postCount(i)
                        .build());
            }

            sortByEmail.sort(largeList, SortOrder.ASC);

            for (int i = 0; i < largeList.size() - 1; i++) {
                assertTrue(largeList.get(i).getEmail()
                        .compareTo(largeList.get(i + 1).getEmail()) <= 0);
            }

            assertEquals("user001@mail.com", largeList.get(0).getEmail());
            assertEquals("user100@mail.com", largeList.get(99).getEmail());
            assertEquals(100, largeList.size());
        }

        @Nested
        @DisplayName("Тесты наследования от Sort")
        class InheritanceTests {

            @Test
            @DisplayName("Проверка что SortByEmail является наследником Sort")
            void testInheritance() {
                assertTrue(sortByEmail instanceof Sort);
            }

            @Test
            @DisplayName("Проверка что метод sort переопределен")
            void testSortMethodOverridden() {
                List<User> testList = new ArrayList<>(users);
                sortByEmail.sort(testList, SortOrder.ASC);
                assertEquals("alice@mail.com", testList.get(0).getEmail());
            }
        }
    }
}