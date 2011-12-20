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

import org.junit.Before;
import org.junit.Test;
import org.neo4j.geoff.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.test.ImpermanentGraphDatabase;

import java.io.StringReader;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

public class IndexReflectionRuleTest {

	private ImpermanentGraphDatabase db;

	@Before
	public void setUp() throws Exception {
		db = new ImpermanentGraphDatabase();
	}

	@Test
	public void testNodeIndexReflectionRule() throws Exception {
		String source = "(A):=|People| {\"name\": \"Alice\"}";
		Rule rule = Rule.from(source);
		assertNotNull(rule);
		assertEquals("N=I", rule.getDescriptor().getPattern());
		assertTrue(rule.getDescriptor().getToken(0) instanceof NodeToken);
		assertTrue(rule.getDescriptor().getToken(2) instanceof IndexToken);
		NodeToken nodeToken = (NodeToken) rule.getDescriptor().getToken(0);
		assertEquals(Token.Type.NODE, nodeToken.getTokenType());
		assertTrue(nodeToken.hasName());
		assertEquals("A", nodeToken.getName());
		IndexToken indexToken = (IndexToken) rule.getDescriptor().getToken(2);
		assertEquals(Token.Type.INDEX, indexToken.getTokenType());
		assertTrue(indexToken.hasName());
		assertEquals("People", indexToken.getName());
		assertTrue(rule.getData().containsKey("name"));
		assertEquals("Alice", rule.getData().get("name"));
	}

	@Test
	public void testRelationshipIndexReflectionRule() throws Exception {
		String source = "[R]:=|Friends| {\"names\": \"Alice & Bob\"}";
		Rule rule = Rule.from(source);
		assertNotNull(rule);
		assertEquals("R=I", rule.getDescriptor().getPattern());
		assertTrue(rule.getDescriptor().getToken(0) instanceof RelToken);
		assertTrue(rule.getDescriptor().getToken(2) instanceof IndexToken);
		RelToken relToken = (RelToken) rule.getDescriptor().getToken(0);
		assertEquals(Token.Type.REL, relToken.getTokenType());
		assertTrue(relToken.hasName());
		assertEquals("R", relToken.getName());
		assertFalse(relToken.hasType());
		IndexToken indexToken = (IndexToken) rule.getDescriptor().getToken(2);
		assertEquals(Token.Type.INDEX, indexToken.getTokenType());
		assertTrue(indexToken.hasName());
		assertEquals("Friends", indexToken.getName());
		assertTrue(rule.getData().containsKey("names"));
		assertEquals("Alice & Bob", rule.getData().get("names"));
	}

	@Test
	public void testLoadingNodeIndexReflectionRule() throws Exception {
		// perform first call to inject node and add index entry
		String source =
				"(A) {\"name\": \"Alice\"}\n" +
				"(A)<=|People| {\"name\": \"Allison, Alice\"}\n" +
				"";
		Map<String, PropertyContainer> out = GEOFF.loadIntoNeo4j(new StringReader(source), db, null);
		assertNotNull(out);
		// make second call to query index entry
		source = "(A):=|People| {\"name\": \"Allison, Alice\"}";
		GEOFF.loadIntoNeo4j(new StringReader(source), db, null);
		// check results
		assertTrue(db.index().existsForNodes("People"));
		Index<Node> people = db.index().forNodes("People");
		assertTrue(people.get("name", "Allison, Alice").hasNext());
		assertEquals("Alice", people.get("name", "Allison, Alice").getSingle().getProperty("name"));
	}

	@Test
	public void testLoadingRelationshipIndexReflectionRule() throws Exception {
		// perform first call to inject relationship and add index entry
		String source =
				"(A) {\"name\": \"Alice\"}\n" +
				"(B) {\"name\": \"Bob\"}\n" +
				"(A)-[R:KNOWS]->(B) {\"since\": 1977}\n" +
				"[R]<=|Friends| {\"names\": \"Alice & Bob\"}\n" +
				"";
		Map<String, PropertyContainer> out = GEOFF.loadIntoNeo4j(new StringReader(source), db, null);
		assertNotNull(out);
		// make second call to query index entry
		source = "[R]:=|Friends| {\"names\": \"Alice & Bob\"}";
		GEOFF.loadIntoNeo4j(new StringReader(source), db, null);
		// check results
		assertTrue(db.index().existsForRelationships("Friends"));
		Index<Relationship> friends = db.index().forRelationships("Friends");
		assertTrue(friends.get("names", "Alice & Bob").hasNext());
		assertEquals(1977, friends.get("names", "Alice & Bob").getSingle().getProperty("since"));
	}

}