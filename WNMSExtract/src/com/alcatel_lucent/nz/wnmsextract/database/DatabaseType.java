package com.alcatel_lucent.nz.wnmsextract.database;

/*
 * This file is part of wnmsextract.
 *
 * wnmsextract is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * wnmsextract is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Current crop of selectable database types. Add and remove from this enum as required
 * remembering the definitions enum may need to be updated if you are not using a supported
 * database.
 */
public enum DatabaseType {
	NPO33(ALUConnectionDefinitions.POSTGRESQL),
	NPO48(ALUConnectionDefinitions.POSTGRESQL),
	TNZ_NZRSDB(ALUConnectionDefinitions.POSTGRESQL),
	GTA_NZRSDB(ALUConnectionDefinitions.POSTGRESQL),
	MPM(ALUConnectionDefinitions.POSTGRESQL),
	TEST(ALUConnectionDefinitions.POSTGRESQL);

	private ALUConnectionDefinitions connectiondefinitions;

	DatabaseType(ALUConnectionDefinitions connectiondefinitions)	{
		this.connectiondefinitions = connectiondefinitions;
	}
	public ALUConnectionDefinitions getConnectionDefinitions(){
		return connectiondefinitions;
	}
};