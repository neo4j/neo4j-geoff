/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.geoff.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.geoff.GEOFFLoader;
import org.neo4j.test.ImpermanentGraphDatabase;

public class GraphDescriptionTest
{
    private ImpermanentGraphDatabase db;


    @Test
    public void canCreateGraphFromSingleString() throws Exception
    {
        Reader reader = new StringReader( "" +
        		"(doc) {\"name\": \"doctor\"}\n" +
        		"(dal) {\"name\": \"dalek\"}\n" +
        		"(doc)-[:ENEMY_OF]->(dal) {\"since\":\"forever\"}\n" +
        		"{People}->(doc)     {\"name\": \"The Doctor\"}\n" +
                "" );
        GEOFFLoader.load( reader, db );
        assertTrue(db.index().existsForNodes( "People" ));
        assertTrue(db.index().forNodes("People" ).get( "name", "The Doctor" ).hasNext());
        assertEquals("doctor", db.index().forNodes("People" ).get( "name", "The Doctor" ).getSingle().getProperty( "name" ));
    }

    
    @Before
    public void setUp() throws Exception {
        db = new ImpermanentGraphDatabase();
    }

}
