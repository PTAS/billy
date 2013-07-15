/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus.entities.mocks;

import java.util.ArrayList;
import java.util.List;

import com.premiumminds.billy.core.persistence.dao.DAOApplication;
import com.premiumminds.billy.core.persistence.dao.DAOContact;
import com.premiumminds.billy.core.persistence.entities.ApplicationEntity;
import com.premiumminds.billy.core.persistence.entities.ContactEntity;
import com.premiumminds.billy.platypus.PlatypusBaseTest;

public class ApplicationTestEntities
		extends
		PlatypusBaseTest {

	public static List<ApplicationEntity> getEntities() {
		List<ApplicationEntity> result = new ArrayList<ApplicationEntity>();
		result.add(getInstance1());
		return result;
	}

	public static ApplicationEntity getInstance1() {
		DAOApplication dao = getInstance(DAOApplication.class);
		DAOContact daoContact = getInstance(DAOContact.class);

		ApplicationEntity result = dao.getApplicationInstance(
				"myapp1", 
				"1", 
				"mydev1", 
				"mydevfinid1", 
				"www.dev1.com", 
				ContactTestEntities.getEntities());
		
		for(ContactEntity c : result.getContacts()) {
			daoContact.create(c);
		}		
		return result;
	}
	
	public static ApplicationEntity getInstance2() {
		DAOApplication dao = getInstance(DAOApplication.class);
		DAOContact daoContact = getInstance(DAOContact.class);

		ApplicationEntity result = dao.getApplicationInstance(
				"myapp2", 
				"2", 
				"mydev2", 
				"mydevfinid2", 
				"www.dev2.com", 
				ContactTestEntities.getEntities());

		for(ContactEntity c : result.getContacts()) {
			daoContact.create(c);
		}		
		return result;
	}
	
	public static ApplicationEntity getInstance3() {
		DAOApplication dao = getInstance(DAOApplication.class);
		DAOContact daoContact = getInstance(DAOContact.class);

		ApplicationEntity result = dao.getApplicationInstance(
				"myapp3", 
				"3", 
				"mydev3", 
				"mydevfinid3", 
				"www.dev3.com", 
				ContactTestEntities.getEntities());

		for(ContactEntity c : result.getContacts()) {
			daoContact.create(c);
		}		
		return result;
	}

}