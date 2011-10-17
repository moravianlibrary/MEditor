/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.view.window;

import com.smartgwt.client.util.SC;

import cz.fi.muni.xkremser.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public final class EditorSC {

    public static final void objectIsLock(LangConstants lang, String lockOwner, String lockDescription) {
        StringBuffer objectLockedBuffer = new StringBuffer();

        if ("".equals(lockOwner)) {
            objectLockedBuffer.append(lang.lockedByUser());
            objectLockedBuffer.append(": ").append("<br>").append("<br>");
            objectLockedBuffer.append("".equals(lockDescription) ? lang.noDescription() : lockDescription);
            System.err.println(lang.noDescription());

        } else {
            objectLockedBuffer.append(lang.objectLockedBy());
            objectLockedBuffer.append(": ").append("<br>").append("<br>");
            objectLockedBuffer.append(lockOwner);
            objectLockedBuffer.append("<br>").append("<br>");
            objectLockedBuffer.append(lang.withDescription());
            objectLockedBuffer.append(": ").append("<br>").append("<br>");
            objectLockedBuffer.append("".equals(lockDescription.trim()) ? lang.noDescription()
                    : lockDescription);
        }
        SC.say(lang.objectIsLocked(), objectLockedBuffer.toString());
    }

    public static final void serverRecentlyMofifiedError(LangConstants lang) {
        SC.say(lang.attemptingError());
    }
}
