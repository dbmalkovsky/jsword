
package org.crosswire.common.util;

import java.util.*;
import java.io.*;

import org.crosswire.common.util.*;

/**
 * A generic class of Properties utils.
 * It would be good if we could put this stuff in java.lang ...
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
 */
public class PropertiesUtil
{
    /**
     * Write a Properties to a Stream an leave the stream in tact
     */
    public static void save(Properties prop, OutputStream out, String title) throws IOException
    {
        // In JDK 1.2 this should change to store. The quashed exception
        // problem is probably not an issue here, since it would get
        // re-raised by the following line.
        saveInternal(prop, out, title);
        out.write((DONE+SEPARATOR).getBytes());
    }

    /**
     * Writes this property list (key and element pairs) in this
     * <code>Properties</code> table to the output stream in a format suitable
     * for loading into a <code>Properties</code> table using the
     * <code>load</code> method.
     * @param out an output stream.
     * @param header a description of the property list.
     */
    private static synchronized void saveInternal(Properties prop, OutputStream out, String header) throws IOException
    {
        BufferedWriter awriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
        if (header != null)
            writeln(awriter, "#" + header);

        writeln(awriter, "#" + new Date().toString());
        for (Enumeration e = prop.keys(); e.hasMoreElements();)
        {
            String key = (String) e.nextElement();
            String val = (String) prop.get(key);
            key = saveConvert(key);
            val = saveConvert(val);
            writeln(awriter, key + "=" + val);
        }
        awriter.flush();
    }

    /**
     * Write a line to a file
     */
    private static void writeln(BufferedWriter bw, String s) throws IOException
    {
        bw.write(s);
        bw.newLine();
    }

    /**
     * Converts unicodes to encoded \\uxxxx
     * and writes out any of the characters in specialSaveChars
     * with a preceding slash
     */
    private static String saveConvert(String theString)
    {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len*2);

        for (int x=0; x<len; )
        {
            aChar = theString.charAt(x++);
            switch (aChar)
            {
            case '\\':
                outBuffer.append('\\');
                outBuffer.append('\\');
                continue;

            case '\t':
                outBuffer.append('\\');
                outBuffer.append('t');
                continue;

            case '\n':
                outBuffer.append('\\');
                outBuffer.append('n');
                continue;

            case '\r':
                outBuffer.append('\\');
                outBuffer.append('r');
                continue;

            case '\f':
                outBuffer.append('\\');
                outBuffer.append('f');
                continue;

            default:
                if ((aChar < 20) || (aChar > 127))
                {
                    outBuffer.append('\\');
                    outBuffer.append('u');
                    outBuffer.append(toHex((aChar >> 12) & 0xF));
                    outBuffer.append(toHex((aChar >> 8) & 0xF));
                    outBuffer.append(toHex((aChar >> 4) & 0xF));
                    outBuffer.append(toHex((aChar >> 0) & 0xF));
                }
                else
                {
                    if (specialSaveChars.indexOf(aChar) != -1)
                        outBuffer.append('\\');

                    outBuffer.append(aChar);
                }
            }
        }

        return outBuffer.toString();
    }

    /**
     * Write a Properties to a Stream an leave the stream in tact
     */
    public static void load(Properties prop, InputStream in) throws IOException
    {
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        StringBuffer file = new StringBuffer();

        boolean alive = true;

        try
        {
            while (alive)
            {
                String line = bin.readLine();

                if (line == null || line.equals(DONE))
                {
                    alive = false;
                }
                else
                {
                    file.append(line);
                    file.append(SEPARATOR);
                }
            }
        }
        catch (Exception ex)
        {
            Reporter.informUser(PropertiesUtil.class, ex);
        }

        InputStream ain = new ByteArrayInputStream(file.toString().getBytes());
        loadInternal(ain, prop);
    }

    /**
     * Reads a property list (key and element pairs) from the input stream.
     * @param in the input stream
     * @exception IOException  if an error occurred when reading from the input stream.
     */
    private static void loadInternal(InputStream inStream, Properties prop) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "8859_1"));
        while (true)
        {
            // Get next line
            String line = in.readLine();
            if (line == null)
                return;

            if (line.length() > 0)
            {
                // Continue lines that end in slashes if they are not comments
                char firstChar = line.charAt(0);
                if ((firstChar != '#') && (firstChar != '!'))
                {
                    while (continueLine(line))
                    {
                        String nextLine = in.readLine();
                        if(nextLine == null)
                            nextLine = new String("");

                        String loppedLine = line.substring(0, line.length()-1);

                        // Advance beyond whitespace on new line
                        int startIndex=0;
                        for(startIndex=0; startIndex<nextLine.length(); startIndex++)
                            if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1)
                                break;
                        nextLine = nextLine.substring(startIndex,nextLine.length());
                        line = new String(loppedLine+nextLine);
                    }

                    // Find start of key
                    int len = line.length();
                    int keyStart;
                    for(keyStart=0; keyStart<len; keyStart++)
                    {
                        if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
                            break;
                    }

                    // Find separation between key and value
                    int separatorIndex;
                    for(separatorIndex=keyStart; separatorIndex<len; separatorIndex++)
                    {
                        char currentChar = line.charAt(separatorIndex);
                        if (currentChar == '\\')
                            separatorIndex++;
                        else if(keyValueSeparators.indexOf(currentChar) != -1)
                            break;
                    }

                    // Skip over whitespace after key if any
                    int valueIndex;
                    for (valueIndex=separatorIndex; valueIndex<len; valueIndex++)
                        if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                            break;

                    // Skip over one non whitespace key value separators if any
                    if (valueIndex < len)
                        if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1)
                            valueIndex++;

                    // Skip over white space after other separators if any
                    while (valueIndex < len)
                    {
                        if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                            break;
                        valueIndex++;
                    }

                    String key = line.substring(keyStart, separatorIndex);
                    String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

                    // Convert then store key and value
                    key = loadConvert(key);
                    value = loadConvert(value);

                    prop.put(key, value);
                }
            }
        }
    }

    /**
     * Returns true if the given line is a line that must
     * be appended to the next line
     */
    private static boolean continueLine(String line)
    {
        int slashCount = 0;
        int index = line.length() - 1;
        while((index >= 0) && (line.charAt(index--) == '\\'))
            slashCount++;

        return (slashCount % 2 == 1);
    }

    /**
     * Converts encoded \\uxxxx to unicode chars
     * and changes special saved chars to their original forms
     */
    private static String loadConvert(String theString)
    {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x=0; x<len; )
        {
            aChar = theString.charAt(x++);
            if (aChar == '\\')
            {
                aChar = theString.charAt(x++);
                if (aChar == 'u')
                {
                    // Read the xxxx
                    int value=0;
                    for (int i=0; i<4; i++)
                    {
                        aChar = theString.charAt(x++);
                        switch (aChar)
                        {
                        case '0': case '1': case '2': case '3': case '4':
                        case '5': case '6': case '7': case '8': case '9':
                            value = (value << 4) + aChar - '0';
                            break;

                        case 'a': case 'b': case 'c':
                        case 'd': case 'e': case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;

                        case 'A': case 'B': case 'C':
                        case 'D': case 'E': case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;

                        default:
                            throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }

                    outBuffer.append((char)value);
                }
                else
                {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';

                    outBuffer.append(aChar);
                }
            }
            else
            {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * Convert a nibble to a hex character
     * @param nibble the nibble to convert.
     */
    private static char toHex(int nibble)
    {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit =
    {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

    /** Cache of line separator */
    private static final String SEPARATOR = System.getProperty("line.separator");

    /** The end marker text */
    private static final String DONE = "DONE";

    /* From Properties */
    private static final String keyValueSeparators = "=: \t\r\n\f";
    private static final String strictKeyValueSeparators = "=:";
    private static final String specialSaveChars = "=: \t\r\n\f#!";
    private static final String whiteSpaceChars = " \t\r\n\f";
}
