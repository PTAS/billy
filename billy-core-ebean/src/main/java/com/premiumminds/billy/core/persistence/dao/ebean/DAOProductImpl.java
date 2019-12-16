/*
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
package com.premiumminds.billy.core.persistence.dao.ebean;

import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.JPAProductEntity;
import com.premiumminds.billy.core.persistence.entities.ebean.query.QJPAProductEntity;

public class DAOProductImpl extends AbstractDAO<ProductEntity, JPAProductEntity> implements DAOProduct {

    @Override
    public List<ProductEntity> getAllActiveProducts() {
        return this.checkEntityList(this.queryProduct().active.eq(true).findList(), ProductEntity.class);
    }

    private QJPAProductEntity queryProduct() {
        return new QJPAProductEntity();
    }

    @Override
    protected Class<? extends JPAProductEntity> getEntityClass() {
        return JPAProductEntity.class;
    }

    @Override
    public ProductEntity getEntityInstance() {
        return new JPAProductEntity();
    }

}
