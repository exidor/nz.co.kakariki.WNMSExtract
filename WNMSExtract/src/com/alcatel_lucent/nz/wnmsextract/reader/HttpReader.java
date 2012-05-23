package com.alcatel_lucent.nz.wnmsextract.reader;
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
import java.util.Calendar;


/** 
 * Reader for http requests but defining date ranges. Imlemented by the 
 * BorgSelection and BorgBlock readers. 
 * @author jnramsay
 *
 */
public interface HttpReader extends LogWriter {

	public void readAll();
	public void readAll(Calendar start, Calendar end);
	public void readAll(String start, String end);

}
