
package org.crosswire.jsword.view.applet;

import java.applet.Applet;
import java.awt.BorderLayout;

import org.crosswire.jsword.view.awt.Display;

/**
* PageApplet.
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
* @version D0.I0.T0
*/
public class Page extends Applet
{
    /**
    * Construct the applet containing the text filed and label
    */
    public Page()
    {
        setLayout(new BorderLayout());
        add("Center", display);

        /*
        text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) { updateText(); }
        });
        */
    }

    /**
    * Update the text area by attempting to understand the
    * text in it
    */
    public void updateText()
    {
        /*
        try
        {
            String input = text.getText();

            Passage ref = PassageFactory.createPassage(input);
            text.setText(ref.getName());
            messages.setText("Passage contains: "+ref.getOverview());
        }
        catch (Exception ex)
        {
            messages.setText(ex.getMessage());
        }
        */
    }

    /** For any messages */
    private Display display = new Display();
}
