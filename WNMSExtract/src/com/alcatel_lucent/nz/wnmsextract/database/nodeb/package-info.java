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
 * NodeB package is a top level package mapping the WNMS NodeB. Its
 * basic structure is the following:
 * <table>
 * <tr><td>NodeBEquipment</td><td></td><td></td><td></td></tr>
 * <tr><td>&rarr;</td><td>BTSCell</td><td></td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>HSDPAService</td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>HSUPAService</td><td></td></tr>
 * <tr><td>&rarr;</td><td>PassiveComponent</td><td></td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>PA</td><td></td></tr>
 * <tr><td>&rarr;</td><td>IMAGroup</td><td></td><td></td></tr>
 * <tr><td>&rarr;</td><td>IPRan</td><td></td><td></td></tr>
 * <tr><td>&rarr;</td><td>Board</td><td></td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>Cem</td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>Ccm</td><td></td></tr>
 * </table>
 */
package com.alcatel_lucent.nz.wnmsextract.database.nodeb;