/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * Copyright: 2008
 *     The copyright to this program is held by it's authors.
 *
 */
package org.crosswire.common.options;

/**
 * An ArgumentType indicates whether and/or how an Option is followed by an
 * argument.
 * 
 * @see gnu.lgpl.License for license details.<br>
 *      The copyright to this program is held by it's authors.
 * @author DM Smith
 */
public enum ArgumentType {
    /**
     * The option is not followed by an argument.
     */
    NO_ARGUMENT  ("NO"),

    /**
     * The option is followed by an argument.
     */
    REQUIRED_ARGUMENT  ("Required"),

    /**
     * The option may be followed by an argument.
     */
    OPTIONAL_ARGUMENT ("Optional");

    /**
     * @param name
     *            The name of the ArgumentType
     */
    private ArgumentType(String name) {
        this.name = name;
    }

    /**
     * Lookup method to find an ArgumentType by name
     * 
     * @param name the name of the ArgumentType
     * @return the ArgumentType or null
     */
    public static ArgumentType fromString(String name) {
        for (ArgumentType v : values()) {
            if (v.name.equalsIgnoreCase(name)) {
                return v;
            }
        }

        // cannot get here
        assert false;
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * The name of the DataType
     */
    private String name;
}
