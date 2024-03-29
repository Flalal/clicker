/*
 * This file is generated by jOOQ.
 */
package fr.flalal.clicker.storage.tables;


import fr.flalal.clicker.storage.Clicker;
import fr.flalal.clicker.storage.Keys;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;

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
public class GameGenerator extends TableImpl<GameGeneratorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>clicker.game_generator</code>
     */
    public static final GameGenerator GAME_GENERATOR = new GameGenerator();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GameGeneratorRecord> getRecordType() {
        return GameGeneratorRecord.class;
    }

    /**
     * The column <code>clicker.game_generator.id_game</code>.
     */
    public final TableField<GameGeneratorRecord, UUID> ID_GAME = createField(DSL.name("id_game"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>clicker.game_generator.id_generator</code>.
     */
    public final TableField<GameGeneratorRecord, UUID> ID_GENERATOR = createField(DSL.name("id_generator"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>clicker.game_generator.level</code>.
     */
    public final TableField<GameGeneratorRecord, Integer> LEVEL = createField(DSL.name("level"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>clicker.game_generator.generated_click</code>.
     */
    public final TableField<GameGeneratorRecord, BigDecimal> GENERATED_CLICK = createField(DSL.name("generated_click"), SQLDataType.NUMERIC.nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "");

    /**
     * The column <code>clicker.game_generator.created_at</code>.
     */
    public final TableField<GameGeneratorRecord, OffsetDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    /**
     * The column <code>clicker.game_generator.updated_at</code>.
     */
    public final TableField<GameGeneratorRecord, OffsetDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    private GameGenerator(Name alias, Table<GameGeneratorRecord> aliased) {
        this(alias, aliased, null);
    }

    private GameGenerator(Name alias, Table<GameGeneratorRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>clicker.game_generator</code> table reference
     */
    public GameGenerator(String alias) {
        this(DSL.name(alias), GAME_GENERATOR);
    }

    /**
     * Create an aliased <code>clicker.game_generator</code> table reference
     */
    public GameGenerator(Name alias) {
        this(alias, GAME_GENERATOR);
    }

    /**
     * Create a <code>clicker.game_generator</code> table reference
     */
    public GameGenerator() {
        this(DSL.name("game_generator"), null);
    }

    public <O extends Record> GameGenerator(Table<O> child, ForeignKey<O, GameGeneratorRecord> key) {
        super(child, key, GAME_GENERATOR);
    }

    @Override
    public Schema getSchema() {
        return Clicker.CLICKER;
    }

    @Override
    public UniqueKey<GameGeneratorRecord> getPrimaryKey() {
        return Keys.GAME_GENERATOR_PKEY;
    }

    @Override
    public List<UniqueKey<GameGeneratorRecord>> getKeys() {
        return Arrays.<UniqueKey<GameGeneratorRecord>>asList(Keys.GAME_GENERATOR_PKEY);
    }

    @Override
    public List<ForeignKey<GameGeneratorRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GameGeneratorRecord, ?>>asList(Keys.GAME_GENERATOR__GAME_GENERATOR_ID_GAME_FKEY, Keys.GAME_GENERATOR__GAME_GENERATOR_ID_GENERATOR_FKEY);
    }

    private transient Game _game;
    private transient Generator _generator;

    public Game game() {
        if (_game == null)
            _game = new Game(this, Keys.GAME_GENERATOR__GAME_GENERATOR_ID_GAME_FKEY);

        return _game;
    }

    public Generator generator() {
        if (_generator == null)
            _generator = new Generator(this, Keys.GAME_GENERATOR__GAME_GENERATOR_ID_GENERATOR_FKEY);

        return _generator;
    }

    @Override
    public GameGenerator as(String alias) {
        return new GameGenerator(DSL.name(alias), this);
    }

    @Override
    public GameGenerator as(Name alias) {
        return new GameGenerator(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GameGenerator rename(String name) {
        return new GameGenerator(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GameGenerator rename(Name name) {
        return new GameGenerator(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, Integer, BigDecimal, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
