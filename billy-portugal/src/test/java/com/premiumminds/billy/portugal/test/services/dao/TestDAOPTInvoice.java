/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.test.services.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;

public class TestDAOPTInvoice extends PTPersistencyAbstractTest {

	@Test
	public void testLastInvoiceNumber() {
		getNewIssuedInvoice();
		getNewIssuedInvoice();
		PTInvoiceEntity resultInvoice2 = getNewIssuedInvoice();
		assertEquals(resultInvoice2.getSeriesNumber(), new Integer(3));
	}

	@Test(expected = BillyRuntimeException.class)
	public void testWithNoInvoice() {
		getInstance(DAOPTInvoice.class).getLatestInvoiceFromSeries(
				"NON EXISTING SERIES");
	}
}
