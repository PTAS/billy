/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core Ebean.
 *
 * billy core Ebean is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core Ebean is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core Ebean. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.persistence.dao.jpa;

import java.util.List;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPABusinessEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.JPAGenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.query.QJPABusinessEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.query.QJPAGenericInvoiceEntity;

public abstract class AbstractDAOGenericInvoiceImpl<TInterface extends GenericInvoiceEntity, TEntity extends JPAGenericInvoiceEntity>
        extends AbstractDAO<TInterface, TEntity> implements AbstractDAOGenericInvoice<TInterface> {

    @SuppressWarnings("unchecked")
    @Override
    public TInterface getLatestInvoiceFromSeries(String series, String businessUID) {
        JPABusinessEntity business = this.queryBusiness(businessUID).findOne();
        if (business == null) {
            throw new BillyRuntimeException("No Business found for id: " + businessUID);
        }

        List<JPAGenericInvoiceEntity> invoiceList =
                this.queryInvoice(series, business).orderBy().seriesNumber.desc().findList();
        return (TInterface) (invoiceList.isEmpty() ? null : invoiceList.get(0));
    }

    private QJPABusinessEntity queryBusiness(String businessUID) {
        return new QJPABusinessEntity().uid.eq(businessUID);
    }

    private QJPAGenericInvoiceEntity queryInvoice(String series, JPABusinessEntity business) {
        return new QJPAGenericInvoiceEntity().series.eq(series).and().business.equalTo(business);
    }
}
