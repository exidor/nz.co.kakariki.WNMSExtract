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
 * INodeVcc package is a top level package mapping the WNMS INodeVcc
 * file. It is basically a copy of the vanilla INode file but contains VCC 
 * data. It doesnt have much of a structure:
 * <table>
 * <tr><td>RNCEquipment</td><td></td><td></td><td></td></tr>
 * <tr><td>&rarr;</td><td>INode</td><td></td><td></td></tr>
 * <tr><td></td>      <td>&rarr;</td><td>ATMPort</td><td></td></tr>
 * <tr><td></td><td></td>            <td>&rarr;</td><td>VCC</td></tr>
 * </table>
 */

package com.alcatel_lucent.nz.wnmsextract.database.inodevcc;