/*
 * This file is generated by jOOQ.
 */
package fr.flalal.clicker.storage;


import fr.flalal.clicker.storage.tables.Player;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Clicker extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>clicker</code>
     */
    public static final Clicker CLICKER = new Clicker();

    /**
     * The table <code>clicker.player</code>.
     */
    public final Player PLAYER = Player.PLAYER;

    /**
     * No further instances allowed
     */
    private Clicker() {
        super("clicker", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Player.PLAYER);
    }
}
