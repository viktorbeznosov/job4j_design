package ru.job4j.generic;

import java.util.ArrayList;
import java.util.List;

public class Role extends Base {
    private final String roleName;

    private List<String> permissions;

    public Role(String id, String name) {
        super(id);
        this.roleName = name;
        this.permissions = new ArrayList<>();
    }

    public Role(String id, String name, List<String> permissions) {
        super(id);
        this.roleName = name;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }

    public boolean can(String permission) {
        return this.getPermissions().contains(permission);
    }

}
