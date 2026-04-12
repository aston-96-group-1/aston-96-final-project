package ru.aston.hometask.finalproject.context;

import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.services.UserCounter;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class AppContext {
    private final Scanner scanner;
    private final Map<String, IUserProvider> providerMap;
    private final Map<String, Sort> sortMap;
    private final Dotenv dotenv;
    private final FileWriter fileWriter;
    private final UserCounter userCounter;

    public AppContext(final Builder builder) {
        this.scanner = builder.scanner;
        this.providerMap = builder.providerMap;
        this.sortMap = builder.sortMap;
        this.dotenv = builder.dotenv;
        this.fileWriter = builder.fileWriter;
        this.userCounter = builder.userCounter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Scanner scanner;
        private Map<String, IUserProvider> providerMap;
        private Map<String, Sort> sortMap;
        private Dotenv dotenv;
        private FileWriter fileWriter;
        private UserCounter userCounter;

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

        public Builder dotenv(final Dotenv dotenv) {
            this.dotenv = dotenv;
            return this;
        }

        public Builder fileWriter(final FileWriter fileWriter) {
            this.fileWriter = fileWriter;
            return this;
        }

        public Builder userCounter(final UserCounter userCounter) {
            this.userCounter = userCounter;
            return this;
        }

        public AppContext build() {
            Objects.requireNonNull(List.of(scanner, providerMap, sortMap, dotenv, fileWriter, userCounter), "Fields must not be null");
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

    public Dotenv getDotenv() {
        return dotenv;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public UserCounter getUserCounter() {
        return userCounter;
    }
}