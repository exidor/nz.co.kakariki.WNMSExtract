package com.alcatel_lucent.nz.wnmsextract.parser;
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
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.TransformerException;

/**
 * Parser interface defining setters and transform action
 * @author jnramsay
 *
 */
public interface ALUParser {
	
	public void setParseFile(String pfs);
	public void setTransformFile(String tfs);
	public void setResultFile(String rfs);

	public void transform(OutputStream os) throws TransformerException;
	
	public void setParseFile(File pfile);
	public void setTransformFile(File tfile);
	public void setResultFile(File tfile);
	
	public void setTransformFile(InputStream tis);
}
