/*
 * This file is generated by jOOQ.
 */
package fr.flalal.clicker.storage;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in clicker.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>clicker.book_id_seq</code>
     */
    public static final Sequence<Integer> BOOK_ID_SEQ = Internal.createSequence("book_id_seq", Clicker.CLICKER, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);
}
