/*
 * This file is generated by jOOQ.
 */
package fr.flalal.clicker.storage.enums;


import fr.flalal.clicker.storage.Clicker;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum UserRoleType implements EnumType {

    ADMIN("ADMIN"),

    USER("USER");

    private final String literal;

    private UserRoleType(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Clicker.CLICKER;
    }

    @Override
    public String getName() {
        return "user_role_type";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
