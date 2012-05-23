package com.alcatel_lucent.nz.wnmsextract.document;

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

import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

//TODO replace doctype table names with static links to ALUDocument table names

/**
 * Enum for each different filestream/doc type. Contains regex's of file names
 * a list of affected tables and whether its safe to delete the doc files once
 * they've been processed. (This makes sense for WiPS docs which are snapshot only
 * but maybe not WNMSDocuments which may need to be reprocessed if there is 
 * an error)
 * NB. In the INodeVcc file the ".vcc" pre-suffix is added artificially to 
 * distinguish it from a regular INode file
 */
public enum DocumentType {
	WNMS_NodeB("^A(\\d{8})\\.\\d{4}\\+\\d{4}\\-\\d{4}\\+\\d{4}_NodeB-\\w*\\.xml",
			Arrays.asList("raw_nodeb_ipran","raw_nodeb_pa","raw_nodeb_ccm","raw_nodeb_cem","raw_nodeb_btscell","raw_nodeb_hsdpaservice","raw_nodeb_imagroup","raw_nodeb_hsupaservice","raw_nodeb_nodebequipment"),
			false),
	WNMS_INode("^A(\\d{8})\\.\\d{4}\\+\\d{4}\\-\\d{4}\\+\\d{4}_INode-\\w*\\.xml",
			Arrays.asList("raw_inode_atmport","raw_inode_ethernet","raw_inode_lp","raw_inode_ap"),
			false),
	WNMS_INodeVcc("^A(\\d{8})\\.\\d{4}\\+\\d{4}\\-\\d{4}\\+\\d{4}_INode-\\w*\\.vcc\\.xml",
			Arrays.asList("raw_inode_vcc"),
			false),
	WNMS_RncCn("^A(\\d{8})\\.\\d{4}\\+\\d{4}\\-\\d{4}\\+\\d{4}_RNCCN-\\w*\\.xml",
			Arrays.asList("raw_rnccn_utrancell",
			"raw_rnccn_neighbouringrnc","raw_rnccn_rncfunction"),
			false),
	WIPS_mRNb ("UTRAN-SNAP\\d{14}\\.xml",
			Arrays.asList("snap_rnc_nodeb"),
			true),
	WIPS_mRVcc("UTRAN-SNAP\\d{14}\\.xml",
			Arrays.asList("snap_rnc_vcc"),
			true),
	WIPS_mFB  ("UTRAN-SNAP\\d{14}\\.xml",
			Arrays.asList("snap_fdd_local","snap_bts_local"),
			true),
	WIPS_cNbP ("UTRAN-SNAP\\d{14}\\.xml",
			Arrays.asList("snap_nodeb_pcm"),
			true);


	private FileFilter filefilter;
	private List<String> tlist;
	private boolean clean;

	DocumentType(String pattern,List<String> tlist,boolean clean){
		this.filefilter = new ALUFileFilter(pattern);
		this.tlist = tlist;
		this.clean = clean;
	}
	
	public FileFilter getFileFilter(){return this.filefilter;}
	
	/**
	 * When file doctypes cannot be distinguished by name a prefix is 
	 * added. Prefix is the Doctype excluding the type descriptor part
	 * e.g. WNMS_RncCn -> RncCn
	 */
	public String getPrefix(){
		String prefix = this.toString();
		return prefix.substring(5, prefix.length());
	}
	
	/** 
	 * Return list of tables that need to be updated when this doctype 
	 * is loaded
	 */
	public List<String> getTList(){return this.tlist;}
	
	
	/** 
	 * Returns flag indicating whether this files of this doctype 
	 * can be deleted
	 */
	public boolean clean(){return this.clean;}

}
