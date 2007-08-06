/*
 * $Id: AbstractTool.java 49 2007-05-19 19:24:42Z chammer $
 * Copyright (c) 2007 Bruno Lowagie
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.lowagie.rups.factories;

import java.util.ArrayList;

import com.lowagie.text.pdf.IntHashtable;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;

/**
 * A store containing all the indirect objects in a PDF file.
 */
public class IndirectObjectStore {

	/** A list of all the indirect objects in a PDF file. */
	protected ArrayList<PdfObject> objects = new ArrayList<PdfObject>();
	/** Mapping between the index in the objects list and the reference number in the xref table.  */
	protected IntHashtable idxToRef = new IntHashtable();
	/** Mapping between the reference number in the xref table and the index in the objects list .  */
	protected IntHashtable refToIdx = new IntHashtable();
	
	/** Creates a list containing all the indirect objects in a PDF document. */
	public IndirectObjectStore(PdfReader reader) {
		int n = reader.getXrefSize();
		PdfObject object;
		int idx = 0;
		for (int ref = 0; ref < n; ref++) {
			object = reader.getPdfObjectRelease(ref);
			if (object != null) {
				idxToRef.put(idx, ref);
				refToIdx.put(ref, idx);
				objects.add(object);
				idx++;
			}
		}
	}
	
	/**
	 * Gets the number of indirect objects in the PDF file.
	 * @return the total number of indirect objects in the PDF.
	 */
	public int size() {
		return objects.size();
	}
	
	/**
	 * Gets the index of an object based on its number in the xref table.
	 * @param ref	a number in the xref table
	 * @return	the index in the list of indirect objects
	 */
	public int getIndexByRef(int ref) {
		return refToIdx.get(ref);
	}
	
	/**
	 * Gets the reference number in the xref table based on the index in the
	 * indirect object list.
	 * @param i		the index of an object in the indirect object list
	 * @return	the corresponding reference number in the xref table
	 */
	public int getRefByIndex(int i) {
		return idxToRef.get(i);
	}
	
	/**
	 * Gets an object based on its index in the indirect object list.
	 * @param i		an index in the indirect object list	
	 * @return	a PDF object
	 */
	public PdfObject getObjectByIndex(int i) {
		return getObjectByReference(getRefByIndex(i));
	}

	/**
	 * Gets an object based on its reference number in the xref table.
	 * @param ref	a reference number in the xref table.
	 * @return	a PDF object
	 */
	public PdfObject getObjectByReference(int ref) {
		return objects.get(getIndexByRef(ref));
	}
}