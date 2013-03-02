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

import org.junit.Test;
import org.neo4j.geoff.Subgraph;
import org.neo4j.geoff.except.SyntaxError;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SubgraphTest {
	
	@Test
	public void canBuildSimpleSubgraph() throws IOException, SyntaxError {
		Subgraph subgraph = new Subgraph();
		subgraph.add("(A) {\"name\": \"Alice Allison\"}");
		assertEquals(1, subgraph.size());
	}
	
}
