package core.runner;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//模式数据库发生器
/**
 * Examples:
 * When generating a complete pattern database (i.e. no dummy tiles):
 *  java PatternDatabaseGenerator 8 1,2,3,4,5,6,7,8,0 8-puzzle.db
 *
 * When generating a disjoint additive pattern database (i.e. dummy tiles 'x'):
 *  java PatternDatabaseGenerator 15 0,2,3,4,x,x,x,x,x,x,x,x,x,x,x,0     15-puzzle-663-0.db
 *  java PatternDatabaseGenerator 15 1,x,x,x,5,6,x,x,9,10,x,x,13,x,x,0   15-puzzle-663-1.db
 *  java PatternDatabaseGenerator 15 x,x,x,x,x,x,7,8,x,x,11,12,x,14,15,0 15-puzzle-663-2.db
 */
public final class PuzzleConfiguration {

    public static final byte[] costTable_15_puzzle_0 = new byte[4096],
            costTable_15_puzzle_1 = new byte[16777216],
            costTable_15_puzzle_2 = new byte[16777216];

    static {
        loadStreamCostTable("database1.db", costTable_15_puzzle_0);
        loadStreamCostTable("database2.db", costTable_15_puzzle_1);
        loadStreamCostTable("database3.db", costTable_15_puzzle_2);
    }

    private PuzzleConfiguration() { }

    private static void loadStreamCostTable(final String filename,
                                            final byte[] costTable) {
        InputStream is = PuzzleConfiguration.class.getResourceAsStream(filename);
        DataInputStream dis = null;
        try {
            if (is == null) {
                is = new FileInputStream(filename);
            }
            dis = new DataInputStream(new BufferedInputStream(is));
            int i = 0;
            while (true) {
                costTable[i++] = dis.readByte();
            }
        } catch (final EOFException eofe) {

        } catch (final FileNotFoundException fnfe) {
            System.err.println("Error: Cannot find file " + filename + ".");
            System.exit(1);
        } catch (final IOException ioe) {
            System.err.println("Error: Cannot read from file " + filename + ".");
            System.exit(1);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (final IOException ioe) { }
        }
    }
}