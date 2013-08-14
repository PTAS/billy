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
package com.premiumminds.billy.portugal.test.util;

import java.util.Currency;
import java.util.Date;

import com.google.inject.Injector;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTCreditNoteEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;

public class PTCreditNoteTestUtil {

	private final Boolean billed = false;
	private final Boolean cancelled = false;
	private final Boolean selfBill = false;
	private final String sourceID = "SOURCE";
	private final String serie = "B";
	private final String number = "NC C/1";
	private final Integer seriesNumber = 1;

	private Injector injector;
	private PTCreditNoteEntryTestUtil creditNoteEntry;

	public PTCreditNoteTestUtil(Injector injector) {
		this.injector = injector;
		this.creditNoteEntry = new PTCreditNoteEntryTestUtil(injector);
	}

	public PTCreditNoteEntity getCreditNoteEntity(TYPE type,
			PTInvoiceEntity reference) {
		PTCreditNote.Builder creditNoteBuilder = this.injector
				.getInstance(PTCreditNote.Builder.class);

		PTCreditNoteEntry.Builder creditNoteEntryBuilder = this.creditNoteEntry
				.getCreditNoteEntryBuilder(reference);

		PTBusinessEntity business = (PTBusinessEntity) injector.getInstance(
				DAOPTBusiness.class).create(
				new PTBusinessTestUtil(injector).getBusinessEntity());

		PTCustomerEntity customer = (PTCustomerEntity) injector.getInstance(
				DAOPTCustomer.class).create(
				new PTCustomerTestUtil(injector).getCustomerEntity());

		creditNoteBuilder.setBilled(this.billed).setCancelled(this.cancelled)
				.setSelfBilled(this.selfBill).setDate(new Date())
				.setSourceId(this.sourceID).addEntry(creditNoteEntryBuilder)
				.setBusinessUID(business.getUID())
				.setSourceBilling(SourceBilling.P)
				.setCustomerUID(customer.getUID());

		PTCreditNoteEntity creditNote = (PTCreditNoteEntity) creditNoteBuilder
				.build();
		creditNote.setSeries(this.serie);
		creditNote.setSeriesNumber(this.seriesNumber);
		creditNote.setNumber(this.number);
		creditNote.setType(type);
		creditNote.setCurrency(Currency.getInstance("EUR"));

		PTCreditNoteEntryEntity creditNoteEntry = (PTCreditNoteEntryEntity) creditNote
				.getEntries().get(0);
		creditNoteEntry.getDocumentReferences().add(creditNote);

		return creditNote;
	}

	public PTCreditNoteEntity getCreditNoteEntity(PTInvoiceEntity reference) {
		return this.getCreditNoteEntity(TYPE.NC, reference);
	}
}
