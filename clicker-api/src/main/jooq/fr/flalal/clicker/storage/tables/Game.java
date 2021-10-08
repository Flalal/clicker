/*
 * This file is generated by jOOQ.
 */
package fr.flalal.clicker.storage.tables;


import fr.flalal.clicker.storage.Clicker;
import fr.flalal.clicker.storage.Keys;
import fr.flalal.clicker.storage.tables.records.GameRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Game extends TableImpl<GameRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>clicker.game</code>
     */
    public static final Game GAME = new Game();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GameRecord> getRecordType() {
        return GameRecord.class;
    }

    /**
     * The column <code>clicker.game.id</code>.
     */
    public final TableField<GameRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>clicker.game.player_id</code>.
     */
    public final TableField<GameRecord, UUID> PLAYER_ID = createField(DSL.name("player_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>clicker.game.money</code>.
     */
    public final TableField<GameRecord, BigDecimal> MONEY = createField(DSL.name("money"), SQLDataType.NUMERIC.nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "");

    /**
     * The column <code>clicker.game.manual_click</code>.
     */
    public final TableField<GameRecord, BigDecimal> MANUAL_CLICK = createField(DSL.name("manual_click"), SQLDataType.NUMERIC.nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "");

    /**
     * The column <code>clicker.game.created_at</code>.
     */
    public final TableField<GameRecord, OffsetDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    /**
     * The column <code>clicker.game.updated_at</code>.
     */
    public final TableField<GameRecord, OffsetDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    private Game(Name alias, Table<GameRecord> aliased) {
        this(alias, aliased, null);
    }

    private Game(Name alias, Table<GameRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>clicker.game</code> table reference
     */
    public Game(String alias) {
        this(DSL.name(alias), GAME);
    }

    /**
     * Create an aliased <code>clicker.game</code> table reference
     */
    public Game(Name alias) {
        this(alias, GAME);
    }

    /**
     * Create a <code>clicker.game</code> table reference
     */
    public Game() {
        this(DSL.name("game"), null);
    }

    public <O extends Record> Game(Table<O> child, ForeignKey<O, GameRecord> key) {
        super(child, key, GAME);
    }

    @Override
    public Schema getSchema() {
        return Clicker.CLICKER;
    }

    @Override
    public UniqueKey<GameRecord> getPrimaryKey() {
        return Keys.GAME_PKEY;
    }

    @Override
    public List<UniqueKey<GameRecord>> getKeys() {
        return Arrays.<UniqueKey<GameRecord>>asList(Keys.GAME_PKEY);
    }

    @Override
    public List<ForeignKey<GameRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GameRecord, ?>>asList(Keys.GAME__GAME_PLAYER_ID_FKEY);
    }

    private transient Player _player;

    public Player player() {
        if (_player == null)
            _player = new Player(this, Keys.GAME__GAME_PLAYER_ID_FKEY);

        return _player;
    }

    @Override
    public Game as(String alias) {
        return new Game(DSL.name(alias), this);
    }

    @Override
    public Game as(Name alias) {
        return new Game(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Game rename(String name) {
        return new Game(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Game rename(Name name) {
        return new Game(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, BigDecimal, BigDecimal, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
