package ru.job4j.generic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {
    @Test
    public void whenAddAndFindRoleNameIsAdmin() {
        Role admin = new Role("1", "Admin");
        Store<Role> store = new RoleStore();
        store.add(admin);
        assertThat(store.findById("1").getRoleName()).isEqualTo("Admin");
    }

    @Test
    public void whenStoreIsEmpty() {
        Store<Role> store = new RoleStore();
        assertThat(store.findById("1")).isNull();
    }

    @Test
    public void whenReplaceRoleOfEmptyStoreIsFalse() {
        Store<Role> store = new RoleStore();
        boolean result = store.replace("1", new Role("1", "User"));
        assertThat(result).isFalse();
    }

    @Test
    public void whenDeleteFromEmptyStoreIsFalse() {
        Store<Role> store = new RoleStore();
        boolean result = store.delete("1");
        assertThat(result).isFalse();
    }

    @Test
    public void whenReplaceIsTrue() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.replace("1", new Role("1", "Operator"));
        assertThat(result).isTrue();
    }

    @Test
    public void whenReplacedRoleIsUser() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "Admin"));
        store.replace("1", new Role("1", "User"));
        assertThat(store.findById("1").getRoleName()).isEqualTo("User");
    }

    @Test
    public void whenReplacedRoleCanDeletePost() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "User", List.of("read post")));
        Role admin = new Role("1", "Admin", List.of("read post", "create post", "edit post", "delete post"));
        store.replace("1", admin);
        assertThat(store.findById("1").can("delete post"));
    }

    @Test void whenReplacedRoleCantCreatePost() {
        Store<Role> store = new RoleStore();
        Role admin = new Role("1", "Admin");
        admin.addPermission("create post");
        store.add(admin);
        store.replace("1", new Role("1", "User"));
        assertThat(store.findById("1").can("create post")).isFalse();
    }

    @Test
    public void whenReplaceNotExistingRoleIsFalse() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.replace("10", new Role("1", "User"));
        assertThat(result).isFalse();
    }

    @Test
    public void whenDeleteIsTrue() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.delete("1");
        assertThat(result).isTrue();
    }

    @Test
    public void whenDeleteNotExisitingRoleIsFalse() {
        Store<Role> store = new RoleStore();
        store.add(new Role("1", "Admin"));
        boolean result = store.delete("10");
        assertThat(result).isFalse();
    }
}