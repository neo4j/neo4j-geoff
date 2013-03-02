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
package org.neo4j.geoff.store;

public abstract class EntityToken extends NameableToken {

	private static String beforeDot(String name) {
		int dot = name.indexOf('.');
		if (dot >= 0) {
			return name.substring(0, dot);
		} else {
			return name;
		}
	}

	private static int afterDot(String name) {
		int dot = name.indexOf('.');
		if (dot >= 0) {
			return Integer.parseInt(name.substring(dot + 1));
		} else {
			return 0;
		}
	}

	protected final int index;
	
	public EntityToken(Type tokenType, String name) {
		super(tokenType, beforeDot(name));
		this.index = afterDot(name);
	}

	public int getIndex() {
		return this.index;
	}

}
