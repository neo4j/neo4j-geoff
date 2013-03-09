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
package org.neo4j.geoff.util;

import java.util.Comparator;

import org.neo4j.graphdb.Node;

public class NodeComparatorsHelper {

	static class NodeIdComparator implements Comparator<Node> {

		public int compare(Node n1, Node n2) {
			return new Long(n1.getId()).compareTo(new Long(n2.getId()));
		};
	}

	private static NodeIdComparator nodeIdComparator = new NodeIdComparator();

	static public NodeIdComparator getNodeIdComparator() {
		return nodeIdComparator;
	}
}
