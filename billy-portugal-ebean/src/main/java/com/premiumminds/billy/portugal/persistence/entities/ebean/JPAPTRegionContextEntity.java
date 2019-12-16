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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.premiumminds.billy.core.persistence.entities.ebean.JPAContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.services.entities.PTRegionContext;

@Entity
@DiscriminatorValue("JPAPTRegionContextEntity")
public class JPAPTRegionContextEntity extends JPAContextEntity implements PTRegionContextEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "REGION_CODE")
    protected String regionCode;

    public JPAPTRegionContextEntity() {
    }

    @Override
    public String getRegionCode() {
        return this.regionCode;
    }

    @Override
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public PTRegionContext getParentContext() {
        return (PTRegionContext) super.getParentContext();
    }

}
