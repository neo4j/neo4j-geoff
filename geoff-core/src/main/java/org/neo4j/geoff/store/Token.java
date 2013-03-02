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

public class Token {

	public static enum Type {

		NODE('N'),
		REL('R'),
		INDEX('I'),
		CONNECTS('-'),
		TO('>'),
		FROM('<'),
		IS_ENTRY_IN('^');

		private final char symbol;

		private Type(char symbol) {
			this.symbol = symbol;
		}

		public char getSymbol() {
			return this.symbol;
		}

	}

	private final Type tokenType;

	public Token(Type tokenType) {
		this.tokenType = tokenType;
	}

	public Type getTokenType() {
		return this.tokenType;
	}

	@Override
	public String toString() {
		return this.tokenType.name();
	}
}
