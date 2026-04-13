package ru.aston.hometask.finalproject.context;

import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.services.ConsoleService;
import ru.aston.hometask.finalproject.services.LogService;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class AppContext {
    private final Scanner scanner;
    private final Map<String, IUserProvider> providerMap;
    private final Map<String, Sort> sortMap;
    private final ConsoleService consoleService;
    private final LogService logService;

    public AppContext(final Builder builder) {
        this.scanner = builder.scanner;
        this.providerMap = builder.providerMap;
        this.sortMap = builder.sortMap;
        this.consoleService = builder.consoleService;
        this.logService = builder.logService;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Scanner scanner;
        private Map<String, IUserProvider> providerMap;
        private Map<String, Sort> sortMap;
        private ConsoleService consoleService;
        private LogService logService;

        public Builder scanner(final Scanner scanner) {
            this.scanner = scanner;
            return this;
        }

        public Builder providerMap(final Map<String, IUserProvider> providerMap) {
            this.providerMap = providerMap;
            return this;
        }

        public Builder sortMap(final Map<String, Sort> sortMap) {
            this.sortMap = sortMap;
            return this;
        }

        public Builder consoleService(final ConsoleService consoleService) {
            this.consoleService = consoleService;
            return this;
        }

        public Builder logService(final LogService logService) {
            this.logService = logService;
            return this;
        }

        public AppContext build() {
            Objects.requireNonNull(List.of(scanner, providerMap, sortMap, consoleService, logService), "Fields must not be null");
            return new AppContext(this);
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Map<String, IUserProvider> getProviderMap() {
        return providerMap;
    }

    public Map<String, Sort> getSortMap() {
        return sortMap;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }

    public LogService getLogService() {
        return logService;
    }
}