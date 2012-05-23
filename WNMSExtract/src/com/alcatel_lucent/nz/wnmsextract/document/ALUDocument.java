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
import java.io.InputStream;

import com.alcatel_lucent.nz.wnmsextract.database.ALUJDCConnector;

/**
 * ALU Document defines the interface of different parsed documents 
 * @author jnramsay
 *
 */
public interface ALUDocument {

	/** 
	 * Based on transformertype request and implementing class returns 
	 * string name of transformer XSL
	 * @param transformertype 
	 * @return Name of XSL
	 */
	public String getTransformer(TransformerType transformertype);

	/**
	 * Processes an input stream, usually from file, into a the transformer
	 * as set in the implementing class
	 * @param is Input stream of file to be read by implementing classes matching XML bean 
	 */
	public void process(InputStream is);

	/**
	 * Deletes sources/files if appropriate and if allowed by clean flag
	 * in doctype
	 * @param dt
	 */
	public void clean(DocumentType dt);

	/**
	 * Sets the DB connector to be passed into the document hierarchy 
	 * for inserts
	 * @param ajc
	 */
	public void setConnector(ALUJDCConnector ajc);

}

