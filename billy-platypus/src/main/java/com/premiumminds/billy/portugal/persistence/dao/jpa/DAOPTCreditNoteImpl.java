/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.premiumminds.billy.core.persistence.dao.jpa.AbstractDAO;
import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.persistence.entities.BusinessOfficeEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.AddressEntityImpl;
import com.premiumminds.billy.core.persistence.entities.jpa.CustomerEntity;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;

public class DAOPTCreditNoteImpl extends AbstractDAO<IPTCreditNoteEntity, PTCreditNoteEntity> implements
		DAOPTCreditNote {

	@Inject
	public DAOPTCreditNoteImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}
	
	@Override
	public IPTCreditNoteEntity getPTCreditNoteInstance(
			IPTBusinessEntity business,
			BusinessOfficeEntity office,
			CustomerEntity customer,
			List<IPTFinancialDocumentEntryEntity> documentEntries,
			List<IPTTaxEntity> documentTaxes,
			Date issueDate,
			String settlementDescription,
			Date settlementDate,
			BigDecimal netTotal,
			BigDecimal taxTotal,
			BigDecimal settlementTotal,
			BigDecimal grossTotal,
			Currency currency,
			boolean selfBilling,
			byte[] hash,
			String sequenceId,
			long sequentialNumber,
			DocumentState state,
			PaymentMechanism paymentMechanism,
			String deliveryOriginId,
			AddressEntity deliveryOriginAddress,
			Date deliveryShippingDate,
			String deliveryId,
			AddressEntity deliveryDestinationAddress,
			Date deliveryDate,
			String reasonForCredit,
			IPTRegionContextEntity ptRegionContext) {

		return new PTCreditNoteEntity(
				checkEntity(business, PTBusinessEntity.class), 
				checkEntity(office, BusinessOfficeEntity.class), 
				checkEntity(customer, CustomerEntity.class), 
				checkEntityList(documentEntries, PTFinancialDocumentEntryEntity.class), 
				checkEntityList(documentTaxes, PTTaxEntity.class), 
				issueDate,
				settlementDescription,
				settlementDate,
				netTotal,
				taxTotal,
				settlementTotal,
				grossTotal,
				currency,
				selfBilling, 
				hash, 
				sequenceId, 
				sequentialNumber, 
				state, 
				paymentMechanism, 
				deliveryOriginId,
				checkEntity(deliveryOriginAddress, AddressEntity.class), 
				deliveryShippingDate, 
				deliveryId, 
				checkEntity(deliveryDestinationAddress, AddressEntity.class), 
				deliveryDate, 
				checkEntity(ptRegionContext, PTRegionContextEntity.class),
				reasonForCredit);
	}
	
	@Override
	public IPTCreditNoteEntity getLastIssuedCreditNote(UUID businessUID,
			String sequenceID) {
		BillyValidator.validate(businessUID, sequenceID);

		boolean inTransaction = isTransactionActive();
		if(!inTransaction) {
			beginTransaction();
		}

		PTCreditNoteEntity result = null;
		try {
			result = getEntityManager().createQuery(
					"select i from PTCreditNoteEntity i " +
							"where i.business.uuid=:uuidBusiness " +
							"and i.active=true " +
							"and i.sequenceID=:sequenceID " +
							"order by i.sequencialNumber desc",
							PTCreditNoteEntity.class)
							.setParameter("uuidBusiness", businessUID.toString())
							.setParameter("sequenceID", sequenceID)
							.setMaxResults(1)
							.getSingleResult();
		} catch(NoResultException e) {
			if(!inTransaction) {
				rollback();
			}
			return null;
		} catch (Exception e) {
			if(!inTransaction) {
				rollback();
			}
			throw new PersistenceException(e);
		}

		if(!inTransaction) {
			commit();
		}
		return result;				
	}
	
	@Override
	public List<IPTCreditNoteEntity> getBusinessCreditNotesForSAFTPT(UUID businessUUID,
			Date fromDate, Date toDate) {
		BillyValidator.validate(businessUUID, fromDate, toDate);

		boolean inTransaction = isTransactionActive();
		if(!inTransaction) {
			beginTransaction();
		}

		List<PTCreditNoteEntity> result = null;
		try {
			result = getEntityManager().createQuery(
					"select i from PTCreditNoteEntity i " +
							"where i.business.uuid=:uuidBusiness " +
							"and i.active=true " +
							"and DATE(i.issueDate) between DATE(:fromDate) and DATE(:toDate) " +
							"order by i.sequenceID ASC i.sequencialNumber ASC",
							PTCreditNoteEntity.class)
							.setParameter("uuidBusiness", businessUUID.toString())
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.getResultList();
		} catch(NoResultException e) {
			if(!inTransaction) {
				rollback();
			}
			return null;
		} catch (Exception e) {
			if(!inTransaction) {
				rollback();
			}
			throw new PersistenceException(e);
		}

		if(!inTransaction) {
			commit();
		}
		return new ArrayList<IPTCreditNoteEntity>(result);				
	}

	@Override
	protected Class<PTCreditNoteEntity> getEntityClass() {
		return PTCreditNoteEntity.class;
	}

}