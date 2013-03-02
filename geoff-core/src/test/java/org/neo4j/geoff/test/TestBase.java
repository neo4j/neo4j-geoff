/**
 * Copyright (c) 2002-2013 "Neo Technology,"
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

import org.junit.After;
import org.junit.Before;
import org.neo4j.geoff.except.SyntaxError;
import org.neo4j.graphdb.PropertyContainer;

import java.io.IOException;
import java.util.Map;

public class TestBase {

	public final String ALICE = "(A) {\"name\": \"Alice Allison\"}";
	public final String BOB   = "(B) {\"name\": \"Bob Robertson\"}";
	public final String CAROL = "(C) {\"name\": \"Carol Carlson\"}";
	public final String ALICE_KNOWS_BOB = "(A)-[AB:KNOWS]->(B) {\"since\": 1977}";

	protected TestDatabase db;

	@Before
	public void setUp() throws Exception {
		db = new TestDatabase();
	}

	@After
	public void tearDown() throws Exception {
		db.shutdown();
	}

	public void dumpParams(Map<String, PropertyContainer> params) {
		for (Map.Entry<String, PropertyContainer> entry : params.entrySet()) {
			StringBuilder str = new StringBuilder(entry.getKey());
			PropertyContainer entity = entry.getValue();
			str.append(": ");
			for (String key : entity.getPropertyKeys()) {
				str.append(' ');
				str.append(key);
				str.append('=');
				str.append(entity.getProperty(key));
			}
			System.out.println(str.toString());
		}
	}

	public static TestTransaction[] getTestTransactions(int count) throws IOException, SyntaxError {
		TestTransaction[] transactions = new TestTransaction[count];
		for (int i = 0; i < count; i++) {
			transactions[i] = new TestTransaction(1000001 + i);
		}
		return transactions;
	}

}
