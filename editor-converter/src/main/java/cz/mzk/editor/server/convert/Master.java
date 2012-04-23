package cz.mzk.editor.server.convert;
import java.util.UUID;

import akka.actor.UntypedActor;


/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

/**
 * @author Jiri Kremser
 * @version 23.4.2012
 */
public class Master extends UntypedActor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceive(Object message) throws Exception {
        Converter convertor = Converter.getInstance();
        if (message instanceof Ok) {
            convertor.finishTask(((Ok)message).uuid());
        } else if (message instanceof NotOk) {
            convertor.finishWithError(((NotOk)message).uuid());
        }
        if (convertor.allFinished()) {
            convertor.finish();
        }
    }
}
