
package org.crosswire.common.util;

import java.util.*;
import java.util.NoSuchElementException;

/**
* Convert a JDK 1.2 Iterator into a JDK 1.1 Enumeration.
* The only real difference between the 2 is the naming and
* that Enumeration does not have the delete method.
*
* <table border='1' cellPadding='3' cellSpacing='0' width="100%">
* <tr><td bgColor='white'class='TableRowColor'><font size='-7'>
* Distribution Licence:<br />
* Project B is free software; you can redistribute it
* and/or modify it under the terms of the GNU General Public License,
* version 2 as published by the Free Software Foundation.<br />
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* General Public License for more details.<br />
* The License is available on the internet
* <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, by writing to
* <i>Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
* MA 02111-1307, USA</i>, Or locally at the Licence link below.<br />
* The copyright to this program is held by it's authors.
* </font></td></tr></table>
* @see <a href='http://www.eireneh.com/servlets/Web'>Project B Home</a>
* @see <{docs.Licence}>
* @author Joe Walker
* @version D9.I9.T5
*/
public final class IteratorEnumeration implements Enumeration
{
    /**
    * Create an Enumeration that proxies to an Iterator
    */
    public IteratorEnumeration(Iterator it)
    {
        this.it = it;
    }

    /**
    * Returns true if the iteration has more elements
    */
    public final boolean hasMoreElements()
    {
        return it.hasNext();
    }

    /**
    *  Returns the next element in the interation
    */
    public final Object nextElement() throws NoSuchElementException
    {
        return it.next();
    }

    /** The Iterator that we are proxying to */
    private Iterator it;
}
