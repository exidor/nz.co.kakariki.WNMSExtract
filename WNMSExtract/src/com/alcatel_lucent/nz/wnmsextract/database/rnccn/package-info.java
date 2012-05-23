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
 * RncCn is a top level package mapping the WNMS RncCn component. Its structure 
 * is simple but densely packed with UtranCell containing the majority of the 
 * cell level data used in the application.
 * Its structure is as follows:
 * <table>
 * <tr><td>RNCFunction</td><td></td></tr>
 * <tr><td>&rarr;</td><td>NeighbouringRnc</td></tr>
 * <tr><td>&rarr;</td><td>UtranCell</td></tr>
 * </table>
 */
package com.alcatel_lucent.nz.wnmsextract.database.rnccn;