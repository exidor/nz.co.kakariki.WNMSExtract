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
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Aspect tapping into individual raw table inserts
 * @author jnramsay
 *
 */
public aspect InspectDBInsert {

	/**
	 * Targets executeUpdate calls on prepared statements. Since these occur in DB tanble insters its a good place to 
	 * repeat the query and to trap errors arising from malformed queries and out of bounds errors 
	 * on certain data fields eg too long for varchar(n)
	 * @param ps
	 */
	pointcut inspectInsertCall(PreparedStatement ps) :
		call(* PreparedStatement.executeUpdate())
		&& target(ps);

	after(PreparedStatement ps) : inspectInsertCall(ps) {

		try {
			System.out.println("Attempting Insert with "+ps.toString()+(ps.getConnection().isClosed()?"DOWN":"UP"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
