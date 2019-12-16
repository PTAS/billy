/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities.ebean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.premiumminds.billy.core.persistence.entities.ebean.JPAPaymentEntity;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.portugal.persistence.entities.PTPaymentEntity;

@Entity
@DiscriminatorValue("JPAPTPaymentEntity")
public class JPAPTPaymentEntity extends JPAPaymentEntity implements PTPaymentEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "PAYMENT_AMOUNT")
    protected BigDecimal paymentAmount;

    @Override
    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    @Override
    public void setPaymentAmount(BigDecimal amount) {
        this.paymentAmount = amount;
    }

    @Override
    public PaymentMechanism getPaymentMethod() {
        return PaymentMechanism.valueOf(this.paymentMethod);
    }

}
