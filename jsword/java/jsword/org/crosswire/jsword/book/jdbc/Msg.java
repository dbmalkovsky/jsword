
package org.crosswire.jsword.book.jdbc;

import org.crosswire.common.util.MsgBase;

/**
 * Compile safe Msg resource settings.
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
class Msg extends MsgBase
{
    protected static final Msg FILTER_FAIL = new Msg("Filtering input data failed.");

    protected static final Msg BIBLE_LOAD = new Msg("Failed to load any of the JDBC Drivers. (Tried {0} drivers)");
    protected static final Msg BIBLE_CONNECT = new Msg("Failed to connect to ODBC Database.");
    protected static final Msg BIBLE_DB = new Msg("Database Error.");
    protected static final Msg BIBLE_VERSE = new Msg("Must be 3 parts to the reference.");
    protected static final Msg BIBLE_LOST = new Msg("Can't find that verse in the database.");

    protected static final Msg DRIVER_FIND = new Msg("No Bibles found at \"{0}\".");
    protected static final Msg DRIVER_CONF = new Msg("Error finding configuration file.");
    protected static final Msg DRIVER_SAVE = new Msg("Error saving configuration file \"{0}\".");
    protected static final Msg DRIVER_READONLY = new Msg("The JDBC Version is read only. Sorry.");

    protected static final Msg SEARCH_FAIL = new Msg("Could not start search engine");

    /** Initialise any resource bundles */
    static
    {
        init(Msg.class.getName());
    }

    /** Passthrough ctor */
    private Msg(String name)
    {
        super(name);
    }
}