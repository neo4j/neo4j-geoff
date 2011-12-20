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
package org.neo4j.geoff;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Transaction;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class GEOFF {

	// debug switch used for conditional compilation
	static final boolean DEBUG = false;

	/**
	 * Load a stream of GEOFF data from a Reader into a Neo4j GraphDatabaseService instance.
	 *
	 * @param reader the Reader from which to read GEOFF rules
	 * @param graphDB the database instance to load into
	 * @param params named Nodes and Relationships which can be referenced by Rules
	 * @return set of named entities used during load
	 * @throws GEOFFLoadException if a parsing error occurs
	 * @throws IOException if a read failure occurs
	 */
	public static Map<String, PropertyContainer> loadIntoNeo4j(
			Reader reader,
			GraphDatabaseService graphDB,
			Map<String, ? extends PropertyContainer> params
	)
			throws GEOFFLoadException, IOException {
		Transaction tx = graphDB.beginTx();
		try {
			GEOFFLoader<Neo4jNamespace> loader = new GEOFFLoader<Neo4jNamespace>(
					reader,
					new Neo4jNamespace(graphDB, params)
			);
			tx.success();
			return loader.getNamespace().getEntities();
		} finally {
			tx.finish();
		}
	}

	/**
	 * Load GEOFF data from a Map of Rules (Descriptor:Data pairs) into a Neo4j GraphDatabaseService instance.
	 *
	 * @param rules the set of GEOFF rules to load
	 * @param graphDB the database instance to load into
	 * @param params named Nodes and Relationships which can be referenced by Rules
	 * @return set of named entities used during load
	 * @throws GEOFFLoadException if a parsing error occurs
	 * @throws IOException if a read failure occurs
	 */
	public static Map<String, PropertyContainer> loadIntoNeo4j(
			Map<String, Map<String, Object>> rules,
			GraphDatabaseService graphDB,
			Map<String, ? extends PropertyContainer> params
	)
			throws GEOFFLoadException, IOException {
		Transaction tx = graphDB.beginTx();
		try {
			GEOFFLoader<Neo4jNamespace> loader = new GEOFFLoader<Neo4jNamespace>(
					rules,
					new Neo4jNamespace(graphDB, params)
			);
			tx.success();
			return loader.getNamespace().getEntities();
		} finally {
			tx.finish();
		}
	}

}