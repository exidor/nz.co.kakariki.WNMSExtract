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

/**
 * Factory class selecting Document implementations from DocumentType. Defaults to null
 * since no selection would be invalid
 */
public class ALUDocumentFactory {
	
	public static DocumentType DEFTYPE = DocumentType.WNMS_NodeB;

	private DocumentType currentType;
	
	public ALUDocumentFactory(){
		this(DEFTYPE);
	}
	
	//public ALUDocumentFactory(ALUDocument doc){
	//	this(interrogateType(doc));
	//}
	
	public ALUDocumentFactory(String type){
		this(DocumentType.valueOf(type));
	}
	public ALUDocumentFactory(DocumentType type){
		this.currentType = type;
	}
	
	/**
	 * Fetches a new instance of a doc based on doctype argument
	 * @return
	 */
	public ALUDocument getInstance(){
		switch(currentType){
		case WNMS_NodeB:
			return new WNMSNodeBDocument();
		case WNMS_INode:
			return new WNMSINodeDocument();
		case WNMS_RncCn:
			return new WNMSRncCnDocument();
		case WNMS_INodeVcc:
			return new WNMSINodeVccDocument();
		case WIPS_mRNb:
			return new WIPSMapRncNodeBDocument();
		case WIPS_mRVcc:
			return new WIPSMapRncVccDocument();
		case WIPS_mFB:
			return new WIPSMapFddBtsDocument();
		case WIPS_cNbP:
				return new WIPSCountNodeBPCMDocument();
		default:
			return null;
		}
	}
	/*
	 * possible future method deciding on doctype based on header of actual document
	 */
	
	/*
	private static DocumentType interrogateType(ALUDocument doc){
		String suff = doc.getSuffix();
    String head = doc.getXMLHeader();
    if("xcm".compareTo(suff)==0 || head.indexOf("RNC")>0){
    	//snapshots are usually xcm suffix'd
      return DType.WIPS_mRNb;
    }
    else
    	//wnms files have no suffix!
      return DType.WNMS_NodeB;
    }
    */
}
