/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-gin.
 * 
 * billy-gin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-gin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-gin.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.gin.services.exceptions;

public class ExportServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExportServiceException(){
		super();
	}
	
	public ExportServiceException(String message) {
		super(message);
	}
	
	public ExportServiceException(Throwable cause) {
		super(cause);
	}
	
	public ExportServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}