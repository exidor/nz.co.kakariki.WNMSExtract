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
 * INode package is a top level package mapping the WNMS INode
 * file. It is structured as follows:
 * <table>
 * <tr><td>RNCEquipment</td><td></td><td></td><td></td></tr>
 * <tr><td>&rarr;</td><td>INode</td><td></td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>ATMPort</td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>LP</td><td></td></tr>
 * <tr><td></td><td></td>            <td>&rarr;</td><td>AP</td></tr>
 * <tr><td></td><td></td>            <td>&rarr;</td><td>Ethernet</td></tr>
 * </table>
 */

package com.alcatel_lucent.nz.wnmsextract.database.inode;