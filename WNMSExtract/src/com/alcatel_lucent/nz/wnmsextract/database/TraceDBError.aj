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
import java.sql.SQLException;

import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

/**
 * Incomplete Aspect for XML parse and DB insert inspection
 * @author jnramsay
 *
 */
public aspect TraceDBError {

	pointcut reportErrorStateSE(SQLException e1) :
		handler(SQLException) && args(e1);

	pointcut reportErrorStateXE(XmlValueOutOfRangeException e2) :
		handler(XmlValueOutOfRangeException) && args(e2);
/*
	before(SQLException e) : reportErrorStateSE(e) {
		System.out.println("SQL Error"+e.toString());
	}

	before(XmlValueOutOfRangeException e) : reportErrorStateXE(e) {
		System.out.println("XML Error"+e.toString());
	}


*/


}
