/*
 * HA-JDBC: High-Availability JDBC
 * Copyright 2004-2009 Paul Ferraro
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.hajdbc.sql;

import java.sql.SQLException;
import java.util.Map;

import net.sf.hajdbc.Database;
import net.sf.hajdbc.invocation.Invoker;

/**
 * @author paul
 *
 */
public class NClobInvocationHandler<Z, D extends Database<Z>, P> extends AbstractClobInvocationHandler<Z, D, P, java.sql.NClob>
{
	public NClobInvocationHandler(P object, SQLProxy<Z, D, P, SQLException> proxy, Invoker<Z, D, P, java.sql.NClob, SQLException> invoker, Map<D, java.sql.NClob> objectMap, boolean updateCopy)
	{
		super(object, proxy, invoker, java.sql.NClob.class, objectMap, updateCopy);
	}
}
