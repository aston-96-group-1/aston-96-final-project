package ru.aston.hometask.finalproject.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class SessionContextTest {
    @Mock
    IUserProvider mockProvider;


    @Mock
    Sort mockSorter;

    @Mock
    List<User> mockUsers;

    private SessionContext sessionContext;

    @BeforeEach
    void setup() {
        sessionContext = new SessionContext();
    }


    @Test
    void isUserProviderReady_WhenBothFieldsPresent_ShouldReturnTrue() {
        sessionContext.setProvider(mockProvider);
        sessionContext.setSize(2);
        assertTrue(sessionContext.isUserProviderReady());
    }

    @Test
    void isUserProviderReady_WhenOneFieldMissing_ShouldReturnFalse() {
        sessionContext.setProvider(mockProvider);
        assertFalse(sessionContext.isUserProviderReady());
    }

    @Test
    void isUserProviderReady_WhenBothFieldsMissing_ShouldReturnFalse() {
        assertFalse(sessionContext.isUserProviderReady());
    }

    @Test
    void isSorterReady_WhenBothFieldsPresent_ShouldReturnTrue() {
        sessionContext.setSorter(mockSorter);
        sessionContext.setSortOrder(SortOrder.ASC);
        assertTrue(sessionContext.isSorterReady());
    }

    @Test
    void isSorterReady_WhenOneFieldMissing_ShouldReturnFalse() {
        sessionContext.setSorter(mockSorter);
        assertFalse(sessionContext.isSorterReady());
    }

    @Test
    void isSorterReady_WhenBothFieldsMissing_ShouldReturnFalse() {
        assertFalse(sessionContext.isSorterReady());
    }

    @Test
    void isLoadUsersReady_WhenBothFieldsPresent_ShouldReturnTrue() {
        sessionContext.setProvider(mockProvider);
        sessionContext.setSize(1);
        assertTrue(sessionContext.isLoadUsersReady());
    }

    @Test
    void isLoadUsersReady_WhenOneFieldMissing_ShouldReturnFalse() {
        sessionContext.setProvider(mockProvider);
        assertFalse(sessionContext.isLoadUsersReady());
    }

    @Test
    void isLoadUsersReady_WhenBothFieldsMissing_ShouldReturnFalse() {
        assertFalse(sessionContext.isLoadUsersReady());
    }

    @Test
    void isShowUsersReady_WhenUsersPresent_ShouldReturnTrue() {
        sessionContext.setUsers(mockUsers);
        assertTrue(sessionContext.isShowUsersReady());
    }

    @Test
    void isShowUsersReady_WhenUsersMissing_ShouldReturnFalse() {
        assertFalse(sessionContext.isShowUsersReady());
    }

    @Test
    void isSortUsersReady_WhenAllFieldsPresent_ShouldReturnTrue() {
        sessionContext.setUsers(mockUsers);
        sessionContext.setSorter(mockSorter);
        sessionContext.setSortOrder(SortOrder.ASC);
        assertTrue(sessionContext.isSortUsersReady());
    }

    @Test
    void isSortUsersReady_WhenAllFieldsMissing_ShouldReturnFalse() {
        assertFalse(sessionContext.isSortUsersReady());
    }

    @Test
    void isSortUsersReady_WhenOneFieldMissing_ShouldReturnFalse() {
        sessionContext.setUsers(mockUsers);
        sessionContext.setSorter(mockSorter);
        assertFalse(sessionContext.isSortUsersReady());
    }
}
