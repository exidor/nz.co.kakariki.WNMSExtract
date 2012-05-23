package com.alcatel_lucent.nz.wnmstest;
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
import org.apache.log4j.BasicConfigurator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestSuiteParse {

	public static Test suite() {
		BasicConfigurator.configure();
		TestSuite suite = new TestSuite();

		//suite.addTestSuite(TestParseNodeB.class);
		//suite.addTestSuite(TestParseINode.class);
		//suite.addTestSuite(TestParseRncCn.class);

		//suite.addTestSuite(TestCopyNodeB.class);
		//suite.addTestSuite(TestCopyINode.class);
		//suite.addTestSuite(TestCopyRncCn.class);

		//suite.addTestSuite(TestParseMapRncNodeB.class);
		//suite.addTestSuite(TestParseMapRncVcc.class);
		//suite.addTestSuite(TestParseMapFddBts.class);
		//suite.addTestSuite(TestParseCountNodeBPCM.class);

		//suite.addTestSuite(TestFileUtilities_Extract.class);
		//suite.addTestSuite(TestFileUtilities_Select.class);

		suite.addTestSuite(TestFileProcess_Extract.class);

		return suite;
	}

	/**
	 * Runs the test suite using the textual runner.
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
