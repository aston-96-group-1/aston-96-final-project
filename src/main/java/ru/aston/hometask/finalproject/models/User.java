package ru.aston.hometask.finalproject.models;

import java.util.Objects;

public class User {
    private final String name;
    private final String password;
    private final String email;
    private final int postCount;

    public User(final Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
        this.postCount = builder.postCount;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPostCount() {
        return postCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && postCount == user.postCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email, postCount);
    }

    @Override
    public String toString() {
        return String.format("name: %s; password: %s; email: %s; postCount: %d", name, password, email, postCount);
    }

    public static class Builder {
        private String name;
        private String password;
        private String email;
        private int postCount;

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder postCount(final int postCount) {
            this.postCount = postCount;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}